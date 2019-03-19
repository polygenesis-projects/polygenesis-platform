/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===========================LICENSE_END==================================
 */

package io.polygenesis.generators.java.rest;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.models.rest.HttpMethod;
import io.polygenesis.models.rest.PathContentType;
import io.polygenesis.representations.commons.ParameterRepresentation;
import io.polygenesis.representations.java.AbstractMethodRepresentable;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentationType;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Endpoint method representable.
 *
 * @author Christos Tsakostas
 */
public class EndpointMethodRepresentable extends AbstractMethodRepresentable<Endpoint> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================
  private static Map<HttpMethod, String> mapHttpMethodToSpring = new LinkedHashMap<>();

  static {
    mapHttpMethodToSpring.put(HttpMethod.GET, "GetMapping");
    mapHttpMethodToSpring.put(HttpMethod.PUT, "PutMapping");
    mapHttpMethodToSpring.put(HttpMethod.POST, "PostMapping");
    mapHttpMethodToSpring.put(HttpMethod.DELETE, "DeleteMapping");
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Endpoint method representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public EndpointMethodRepresentable(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodRepresentationType methodType(Endpoint source, Object... args) {
    return MethodRepresentationType.ANY;
  }

  @Override
  public Set<String> imports(Endpoint source, Object... args) {
    Set<String> imports = new LinkedHashSet<>();

    return imports;
  }

  @Override
  public Set<String> annotations(Endpoint source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList(makeFullMapping(source)));
  }

  @Override
  public String description(Endpoint source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("REST Endpoint for ");
    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(source.getFunction().getName().getText()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Endpoint source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String methodName(Endpoint source, Object... args) {
    return source.getFunction().getName().getText();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(Endpoint source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getFunction()
        .getArguments()
        .forEach(
            argument -> {
              if (argument.getData().isDataGroup()
                  && (source.getFunction().getGoal().isCreate()
                      || source.getFunction().getGoal().isModify())) {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        fromDataTypeToJavaConverter.getDeclaredVariableType(
                            argument.getData().getDataType()),
                        argument.getData().getVariableName().getText(),
                        new LinkedHashSet<>(Arrays.asList("@RequestBody"))));
              } else {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        fromDataTypeToJavaConverter.getDeclaredVariableType(
                            argument.getData().getDataType()),
                        argument.getData().getVariableName().getText()));
              }
            });

    return parameterRepresentations;
  }

  @Override
  public String returnValue(Endpoint source, Object... args) {
    if (source.getFunction().getReturnValue() != null) {
      return makeVariableDataType(source.getFunction().getReturnValue().getData());
    } else {
      return fromDataTypeToJavaConverter.getDeclaredVariableType(PrimitiveType.VOID.name());
    }
  }

  @Override
  public String implementation(Endpoint source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    if (source.getFunction().getReturnValue() != null) {
      stringBuilder.append("return");
      stringBuilder.append(" ");
    }
    stringBuilder.append(
        TextConverter.toLowerCamel(source.getService().getServiceName().getText()));
    stringBuilder.append(".");
    stringBuilder.append(source.getFunction().getName().getText());
    stringBuilder.append("(");
    stringBuilder.append(getParametersCommaSeparated(parameterRepresentations(source)));
    stringBuilder.append(");");

    return stringBuilder.toString();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private String makeFullMapping(Endpoint endpoint) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("@");
    stringBuilder.append(mapHttpMethodToSpring.get(endpoint.getHttpMethod()));
    stringBuilder.append("({");

    endpoint
        .getMappings()
        .stream()
        .forEach(
            mapping -> {
              stringBuilder.append("\"");
              stringBuilder.append("/");
              stringBuilder.append(
                  mapping
                      .getPathContents()
                      .stream()
                      .map(
                          pathContent ->
                              pathContent.getPathContentType().equals(PathContentType.CONSTANT)
                                  ? pathContent.getContent()
                                  : "{" + pathContent.getContent() + "}")
                      .collect(Collectors.joining("/")));

              if (endpoint.getHttpMethod().equals(HttpMethod.PUT)) {
                stringBuilder.append("/");
                stringBuilder.append(
                    TextConverter.toLowerHyphen(endpoint.getFunction().getName().getText()));
              }

              stringBuilder.append("\"");
            });

    stringBuilder.append("})");

    return stringBuilder.toString();
  }
}
