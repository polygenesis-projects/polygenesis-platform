/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.generators.java.apiclients.rest.resource;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.EndpointImplementationRegistry;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.models.rest.HttpMethod;
import io.polygenesis.models.rest.PathContentType;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.legacy.AbstractLegacyMethodTransformer;
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
public class EndpointLegacyMethodTransformer extends AbstractLegacyMethodTransformer<Endpoint> {

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
   * @param dataTypeTransformer the from data type to java converter
   * @param freemarkerService the freemarker service
   * @param endpointImplementationRegistry the endpoint implementation registry
   */
  public EndpointLegacyMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      FreemarkerService freemarkerService,
      EndpointImplementationRegistry endpointImplementationRegistry) {
    super(dataTypeTransformer);
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
    return new LinkedHashSet<>();
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

    Optional<Data> optionalParentThingIdentityData =
        source.getServiceMethod().getRequestDto().getParentThingIdentityAsOptional();

    if (optionalParentThingIdentityData.isPresent()) {
      parameterRepresentations.addAll(
          parameterRepresentationsForIdPathVariable(
              optionalParentThingIdentityData
                  .orElseThrow(IllegalArgumentException::new)
                  .getVariableName()
                  .getText()));
    }

    Optional<Data> thingIdentityData =
        source.getServiceMethod().getRequestDto().getThingIdentityAsOptional();

    switch (source.getHttpMethod()) {
      case GET:
        if (source.getServiceMethod().getFunction().getPurpose().isFetchOne()) {
          parameterRepresentations.addAll(
              parameterRepresentationsForIdPathVariable(
                  thingIdentityData
                      .orElseThrow(IllegalArgumentException::new)
                      .getVariableName()
                      .getText()));
        } else if (source.getServiceMethod().getFunction().getPurpose().isFetchPagedCollection()) {
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

    if (source.getServiceMethod().getFunction().getPurpose().isCreate()
        || source.getServiceMethod().getFunction().getPurpose().isModify()) {
      parameterRepresentations.add(
          new ParameterRepresentation(
              dataTypeTransformer.convert(
                  source.getServiceMethod().getRequestDto().getDataObject().getDataType()),
              source.getServiceMethod().getRequestDto().getDataObject().getVariableName().getText(),
              new LinkedHashSet<>(Arrays.asList("@RequestBody"))));
    }

    parameterRepresentations.add(
        new ParameterRepresentation("HttpServletRequest", "httpServletRequest"));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(Endpoint source, Object... args) {
    if (source.getServiceMethod().getFunction().getReturnValue() != null) {
      return makeVariableDataType(source.getServiceMethod().getFunction().getReturnValue());
    } else {
      return dataTypeTransformer.convert(PrimitiveType.VOID.name());
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
              source.getServiceMethod().getRequestDto().getDataObject().getDataType()));
      stringBuilder.append(" ");
      stringBuilder.append(
          TextConverter.toLowerCamel(
              source.getServiceMethod().getRequestDto().getDataObject().getDataType()));
      stringBuilder.append(" = new ");
      stringBuilder.append(
          TextConverter.toUpperCamel(
              source.getServiceMethod().getRequestDto().getDataObject().getDataType()));
      stringBuilder.append("();\n");

      // -------------------------------------------------------------------------------------------
      if (source.getServiceMethod().getFunction().getPurpose().isFetchPagedCollection()) {
        stringBuilder.append("\t\t");
        stringBuilder.append(
            TextConverter.toLowerCamel(
                source.getServiceMethod().getRequestDto().getDataObject().getDataType()));
        stringBuilder.append(".setPageNumber(pageNumber);\n");

        stringBuilder.append("\t\t");
        stringBuilder.append(
            TextConverter.toLowerCamel(
                source.getServiceMethod().getRequestDto().getDataObject().getDataType()));
        stringBuilder.append(".setPageSize(pageSize);\n");
      }
    }
    // ---------------------------------------------------------------------------------------------

    // ID
    if (source.getServiceMethod().getFunction().getPurpose().isFetchOne()
        || source.getServiceMethod().getFunction().getPurpose().isModify()) {
      Data data =
          source
              .getServiceMethod()
              .getRequestDto()
              .getThingIdentityAsOptional()
              .orElseThrow(IllegalArgumentException::new);

      stringBuilder.append(setThingIdentityInRequest(source, data.getVariableName().getText()));
    }

    // ---------------------------------------------------------------------------------------------
    stringBuilder.append("\t\t");
    stringBuilder.append(
        TextConverter.toLowerCamel(
            source.getServiceMethod().getRequestDto().getDataObject().getDataType()));
    stringBuilder.append(".setTenantId(this.getTenantId(httpServletRequest));\n");
    stringBuilder.append("\t\t");
    stringBuilder.append(
        TextConverter.toLowerCamel(
            source.getServiceMethod().getRequestDto().getDataObject().getDataType()));
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
            source.getServiceMethod().getRequestDto().getDataObject().getDataType()));
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
        .map(ParameterRepresentation::getVariableName)
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
            new LinkedHashSet<>(
                Arrays.asList("@RequestParam(required = false, defaultValue = \"\")"))));

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
   * Sets thing identity in request.
   *
   * @param source the source
   * @param variableName the variable name
   * @return the thing idenity in request
   */
  protected String setThingIdentityInRequest(Endpoint source, String variableName) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append(
        TextConverter.toLowerCamel(
            source.getServiceMethod().getRequestDto().getDataObject().getDataType()));
    stringBuilder.append(".set");
    stringBuilder.append(TextConverter.toUpperCamel(variableName));
    stringBuilder.append("(");
    stringBuilder.append(TextConverter.toLowerCamel(variableName));
    stringBuilder.append(");\n");

    return stringBuilder.toString();
  }
}
