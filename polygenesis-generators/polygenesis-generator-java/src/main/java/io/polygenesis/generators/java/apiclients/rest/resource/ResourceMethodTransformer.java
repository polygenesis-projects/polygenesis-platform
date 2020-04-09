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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.models.rest.HttpMethod;
import io.polygenesis.models.rest.PathContentType;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Resource method transformer.
 *
 * @author Christos Tsakostas
 */
public class ResourceMethodTransformer extends AbstractMethodTransformer<Endpoint> {

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
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final ResourceActivityRegistry resourceActivityRegistry;
  private final ResourceMethodParameterRepresentationService
      resourceMethodParameterRepresentationService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param resourceActivityRegistry the resource activity registry
   * @param resourceMethodParameterRepresentationService the resource method parameter
   *     representation service
   */
  public ResourceMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      ResourceActivityRegistry resourceActivityRegistry,
      ResourceMethodParameterRepresentationService resourceMethodParameterRepresentationService) {
    super(dataTypeTransformer);
    this.resourceActivityRegistry = resourceActivityRegistry;
    this.resourceMethodParameterRepresentationService =
        resourceMethodParameterRepresentationService;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodRepresentationType methodType(Endpoint source, Object... args) {
    return super.methodType(source, args);
  }

  @Override
  public Set<String> imports(Endpoint source, Object... args) {
    return super.imports(source, args);
  }

  @Override
  public Set<String> annotations(Endpoint source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList(makeFullMapping(source)));
  }

  @Override
  public String description(Endpoint source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(Endpoint source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String methodName(Endpoint source, Object... args) {
    return super.methodName(source, args);
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(Endpoint source, Object... args) {
    return resourceMethodParameterRepresentationService.parameterRepresentations(source, args);
  }

  @Override
  public String returnValue(Endpoint source, Object... args) {
    return super.returnValue(source, args);
  }

  @Override
  public String implementation(Endpoint source, Object... args) {
    return resourceActivityRegistry.activityFor(source, args);
  }

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
                        endpoint.getServiceMethod().getFunction().getName().getFullName()));
              }

              stringBuilder.append("\"");
            });

    stringBuilder.append("})");

    return stringBuilder.toString();
  }
}
