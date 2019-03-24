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

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.implementations.java.rest.EndpointImplementationRegistry;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.models.rest.HttpMethod;
import io.polygenesis.models.rest.PathContentType;
import io.polygenesis.representations.commons.ParameterRepresentation;
import io.polygenesis.representations.java.AbstractMethodRepresentable;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import io.polygenesis.representations.java.MethodRepresentationType;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
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
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final EndpointImplementationRegistry endpointImplementationRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Endpoint method representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param freemarkerService the freemarker service
   * @param endpointImplementationRegistry the endpoint implementation registry
   */
  public EndpointMethodRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FreemarkerService freemarkerService,
      EndpointImplementationRegistry endpointImplementationRegistry) {
    super(fromDataTypeToJavaConverter);
    this.freemarkerService = freemarkerService;
    this.endpointImplementationRegistry = endpointImplementationRegistry;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public MethodRepresentation create(Endpoint source, Object... args) {
    MethodRepresentation methodRepresentation = super.create(source, args);

    if (endpointImplementationRegistry.isEndpointSupported(source)) {
      methodRepresentation.changeImplementationTo(
          endpointImplementationRegistry
              .implementation(freemarkerService, source, methodRepresentation)
              .orElseThrow(IllegalArgumentException::new));
    }

    return methodRepresentation;
  }

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
        TextConverter.toUpperCamelSpaces(
            source.getServiceMethod().getFunction().getName().getText()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Endpoint source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String methodName(Endpoint source, Object... args) {
    return source.getServiceMethod().getFunction().getName().getText();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(Endpoint source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    Optional<Data> thingIdentityData =
        source.getServiceMethod().getRequestDto().getThingIdentityAsOptional();

    switch (source.getHttpMethod()) {
      case GET:
        if (source.getServiceMethod().getFunction().getGoal().isFetchOne()) {
          parameterRepresentations.addAll(
              parameterRepresentationsForIdPathVariable(
                  thingIdentityData
                      .orElseThrow(IllegalArgumentException::new)
                      .getVariableName()
                      .getText()));
        } else if (source.getServiceMethod().getFunction().getGoal().isFetchPagedCollection()) {
          parameterRepresentations.addAll(parameterRepresentationsForPagedCollection());
        }
        break;
      case PUT:
        parameterRepresentations.addAll(
            parameterRepresentationsForIdPathVariable(
                thingIdentityData
                    .orElseThrow(IllegalArgumentException::new)
                    .getVariableName()
                    .getText()));
        break;
      default:
        break;
    }

    source
        .getServiceMethod()
        .getFunction()
        .getArguments()
        .forEach(
            argument -> {
              if (argument.getData().isDataGroup()
                  && (source.getServiceMethod().getFunction().getGoal().isCreate()
                      || source.getServiceMethod().getFunction().getGoal().isModify())) {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        fromDataTypeToJavaConverter.getDeclaredVariableType(
                            argument.getData().getDataType()),
                        argument.getData().getVariableName().getText(),
                        new LinkedHashSet<>(Arrays.asList("@RequestBody"))));
              }
            });

    parameterRepresentations.add(
        new ParameterRepresentation("HttpServletRequest", "httpServletRequest"));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(Endpoint source, Object... args) {
    if (source.getServiceMethod().getFunction().getReturnValue() != null) {
      return makeVariableDataType(
          source.getServiceMethod().getFunction().getReturnValue().getData());
    } else {
      return fromDataTypeToJavaConverter.getDeclaredVariableType(PrimitiveType.VOID.name());
    }
  }

  @Override
  public String implementation(Endpoint source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    // ---------------------------------------------------------------------------------------------
    if (source.getHttpMethod().equals(HttpMethod.GET)) {
      stringBuilder.append("\t\t");
      stringBuilder.append(
          TextConverter.toUpperCamel(
              source.getServiceMethod().getRequestDto().getDataGroup().getDataType()));
      stringBuilder.append(" ");
      stringBuilder.append(
          TextConverter.toLowerCamel(
              source.getServiceMethod().getRequestDto().getDataGroup().getDataType()));
      stringBuilder.append(" = new ");
      stringBuilder.append(
          TextConverter.toUpperCamel(
              source.getServiceMethod().getRequestDto().getDataGroup().getDataType()));
      stringBuilder.append("();\n");

      // -------------------------------------------------------------------------------------------
      if (source.getServiceMethod().getFunction().getGoal().isFetchPagedCollection()) {
        stringBuilder.append("\t\t");
        stringBuilder.append(
            TextConverter.toLowerCamel(
                source.getServiceMethod().getRequestDto().getDataGroup().getDataType()));
        stringBuilder.append(".setPageNumber(pageNumber);\n");

        stringBuilder.append("\t\t");
        stringBuilder.append(
            TextConverter.toLowerCamel(
                source.getServiceMethod().getRequestDto().getDataGroup().getDataType()));
        stringBuilder.append(".setPageSize(pageSize);\n");
      }
    }
    // ---------------------------------------------------------------------------------------------

    // ID
    if (source.getServiceMethod().getFunction().getGoal().isFetchOne()
        || source.getServiceMethod().getFunction().getGoal().isModify()) {
      Data data =
          source
              .getServiceMethod()
              .getRequestDto()
              .getThingIdentityAsOptional()
              .orElseThrow(IllegalArgumentException::new);

      stringBuilder.append(setThingIdenityInRequest(source, data.getVariableName().getText()));
    }

    // ---------------------------------------------------------------------------------------------
    stringBuilder.append("\t\t");
    stringBuilder.append(
        TextConverter.toLowerCamel(
            source.getServiceMethod().getRequestDto().getDataGroup().getDataType()));
    stringBuilder.append(".setTenantId(this.getTenantId(httpServletRequest));\n");
    stringBuilder.append("\t\t");
    stringBuilder.append(
        TextConverter.toLowerCamel(
            source.getServiceMethod().getRequestDto().getDataGroup().getDataType()));
    stringBuilder.append(".setIpAddress(this.getRemoteIpAddress(httpServletRequest));\n");

    // ---------------------------------------------------------------------------------------------
    stringBuilder.append("\t\t");
    if (source.getServiceMethod().getFunction().getReturnValue() != null) {
      stringBuilder.append("return");
      stringBuilder.append(" ");
    }
    stringBuilder.append(
        TextConverter.toLowerCamel(source.getService().getServiceName().getText()));
    stringBuilder.append(".");
    stringBuilder.append(source.getServiceMethod().getFunction().getName().getText());
    stringBuilder.append("(");
    stringBuilder.append(
        TextConverter.toLowerCamel(
            source.getServiceMethod().getRequestDto().getDataGroup().getDataType()));
    stringBuilder.append(");");

    return stringBuilder.toString();
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  protected String getParametersCommaSeparated(
      Set<ParameterRepresentation> parameterRepresentations) {
    return parameterRepresentations
        .stream()
        .map(parameterRepresentation -> parameterRepresentation.getVariableName())
        .filter(variableName -> !variableName.equals("httpServletRequest"))
        .collect(Collectors.joining(", "));
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
                    TextConverter.toLowerHyphen(
                        endpoint.getServiceMethod().getFunction().getName().getText()));
              }

              stringBuilder.append("\"");
            });

    stringBuilder.append("})");

    return stringBuilder.toString();
  }

  /**
   * Parameter representations for paged collection set.
   *
   * @return the set
   */
  private Set<ParameterRepresentation> parameterRepresentationsForPagedCollection() {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            "Integer", "pageNumber", new LinkedHashSet<>(Arrays.asList("@RequestParam"))));

    parameterRepresentations.add(
        new ParameterRepresentation(
            "Integer", "pageSize", new LinkedHashSet<>(Arrays.asList("@RequestParam"))));

    parameterRepresentations.add(
        new ParameterRepresentation(
            "String",
            "query",
            new LinkedHashSet<>(Arrays.asList("@RequestParam(required = false)"))));

    return parameterRepresentations;
  }

  /**
   * Parameter representations for id path variable set.
   *
   * @param idVariable the id variable
   * @return the set
   */
  private Set<ParameterRepresentation> parameterRepresentationsForIdPathVariable(
      String idVariable) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            "String",
            idVariable,
            new LinkedHashSet<>(
                Arrays.asList(String.format("@PathVariable(\"%s\")", idVariable)))));

    return parameterRepresentations;
  }

  /**
   * Sets thing idenity in request.
   *
   * @param source the source
   * @param variableName the variable name
   * @return the thing idenity in request
   */
  protected String setThingIdenityInRequest(Endpoint source, String variableName) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append(
        TextConverter.toLowerCamel(
            source.getServiceMethod().getRequestDto().getDataGroup().getDataType()));
    stringBuilder.append(".set");
    stringBuilder.append(TextConverter.toUpperCamel(variableName));
    stringBuilder.append("(");
    stringBuilder.append(TextConverter.toLowerCamel(variableName));
    stringBuilder.append(");\n");

    return stringBuilder.toString();
  }
}
