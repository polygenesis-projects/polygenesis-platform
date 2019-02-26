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

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Function;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.shared.AbstractFunctionProjectionMaker;
import io.polygenesis.generators.java.shared.ArgumentProjection;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.models.rest.HttpMethod;
import io.polygenesis.models.rest.PathContentType;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Endpoint projection converter.
 *
 * @author Christos Tsakostas
 */
public class EndpointProjectionConverter extends AbstractFunctionProjectionMaker
    implements Converter<Endpoint, EndpointProjection> {

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
   * Instantiates a new Endpoint projection converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public EndpointProjectionConverter(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public EndpointProjection convert(Endpoint source, Object... args) {
    return new EndpointProjection(
        source.getFunction(),
        projectName(source.getFunction()),
        projectDescription(),
        projectReturnValue(source.getFunction()),
        projectArguments(source.getFunction()),
        projectAnnotations(source),
        projectServiceName(source));
  }

  @Override
  protected Set<ArgumentProjection> projectArguments(Function function) {
    Set<ArgumentProjection> arguments = new LinkedHashSet<>();

    function
        .getArguments()
        .forEach(
            argument -> {
              if (argument.getModel().isIoModelGroup()
                  && (function.getGoal().isCreate() || function.getGoal().isModify())) {
                arguments.add(
                    new ArgumentProjection(
                        fromDataTypeToJavaConverter.getDeclaredVariableType(argument.getModel()),
                        argument.getModel().getVariableName().getText(),
                        "@RequestBody"));
              } else {
                arguments.add(
                    new ArgumentProjection(
                        fromDataTypeToJavaConverter.getDeclaredVariableType(argument.getModel()),
                        argument.getModel().getVariableName().getText()));
              }
            });

    return arguments;
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Project annotations set.
   *
   * @param endpoint the endpoint
   * @return the set
   */
  protected Set<String> projectAnnotations(Endpoint endpoint) {
    return new LinkedHashSet<>(Arrays.asList(makeFullMapping(endpoint)));
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
              stringBuilder.append("\"");
            });

    stringBuilder.append("})");

    return stringBuilder.toString();
  }

  private String projectServiceName(Endpoint endpoint) {
    return TextConverter.toLowerCamel(endpoint.getService().getServiceName().getText());
  }
}
