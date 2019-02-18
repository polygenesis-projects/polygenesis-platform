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
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.shared.AbstractObjectProjectionMaker;
import io.polygenesis.generators.java.shared.ArgumentProjection;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.FunctionProjection;
import io.polygenesis.models.rest.HttpMethod;
import io.polygenesis.models.rest.Resource;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Resource projection converter.
 *
 * @author Christos Tsakostas
 */
public class ResourceProjectionConverter extends AbstractObjectProjectionMaker
    implements Converter<Resource, ResourceProjection> {

  private final EndpointProjectionConverter endpointProjectionConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource projection converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param endpointProjectionConverter the endpoint projection converter
   */
  public ResourceProjectionConverter(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      EndpointProjectionConverter endpointProjectionConverter) {
    super(fromDataTypeToJavaConverter);
    this.endpointProjectionConverter = endpointProjectionConverter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ResourceProjection convert(Resource resource, Object... args) {
    PackageName rootPackageName = (PackageName) args[0];

    return new ResourceProjection(
        projectPackageName(resource),
        projectImports(resource, rootPackageName),
        projectDescription(resource),
        projectObjectName(resource),
        projectObjectNameWithOptionalExtendsImplements(resource),
        projectVariables(resource),
        projectConstructors(resource),
        projectMethods(resource));
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Project package name string.
   *
   * @param resource the resource
   * @return the string
   */
  protected String projectPackageName(Resource resource) {
    return resource.getPackageName().getText();
  }

  /**
   * Project imports set.
   *
   * @param resource the resource
   * @return the set
   */
  protected Set<String> projectImports(Resource resource, PackageName rootPackageName) {
    Set<String> imports = new TreeSet<>();

    imports.add("org.springframework.web.bind.annotation.RestController");
    imports.add("org.springframework.web.bind.annotation.RequestMapping");
    imports.add("com.oregor.ddd4j.rest.AbstractRestController");
    imports.add(String.format("%s.RestConstants", rootPackageName.getText()));

    resource
        .getEndpoints()
        .forEach(
            endpoint -> {
              if (endpoint.getHttpMethod().equals(HttpMethod.POST)) {
                imports.add("org.springframework.web.bind.annotation.PostMapping");
                imports.add("org.springframework.web.bind.annotation.RequestBody");
              } else if (endpoint.getHttpMethod().equals(HttpMethod.PUT)) {
                imports.add("org.springframework.web.bind.annotation.PutMapping");
                imports.add("org.springframework.web.bind.annotation.RequestBody");
              } else if (endpoint.getHttpMethod().equals(HttpMethod.GET)) {
                imports.add("org.springframework.web.bind.annotation.GetMapping");
              } else if (endpoint.getHttpMethod().equals(HttpMethod.DELETE)) {
                imports.add("org.springframework.web.bind.annotation.DeleteMapping");
              }
            });

    return imports;
  }

  /**
   * Project description string.
   *
   * @param resource the resource
   * @return the string
   */
  protected String projectDescription(Resource resource) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The Spring REST Controller for ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(resource.getName().getText()));

    stringBuilder.append(" Services.");

    return stringBuilder.toString();
  }

  /**
   * Project object name string.
   *
   * @param resource the resource
   * @return the string
   */
  protected String projectObjectName(Resource resource) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(resource.getName().getText()));
    stringBuilder.append("RestService");

    return stringBuilder.toString();
  }

  /**
   * Project object name with optional extends implements string.
   *
   * @param resource the resource
   * @return the string
   */
  protected String projectObjectNameWithOptionalExtendsImplements(Resource resource) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(resource.getName().getText()));
    stringBuilder.append("RestService");
    stringBuilder.append(" extends AbstractRestController");

    return stringBuilder.toString();
  }

  /**
   * Project variables set.
   *
   * @param resource the resource
   * @return the set
   */
  protected Set<ArgumentProjection> projectVariables(Resource resource) {
    Set<ArgumentProjection> keyValues = new LinkedHashSet<>();

    resource
        .getServices()
        .forEach(
            service ->
                keyValues.add(
                    new ArgumentProjection(
                        TextConverter.toUpperCamel(service.getServiceName().getText()),
                        TextConverter.toLowerCamel(service.getServiceName().getText()))));

    return keyValues;
  }

  /**
   * Project constructors set.
   *
   * @param resource the resource
   * @return the set
   */
  protected Set<ConstructorProjection> projectConstructors(Resource resource) {
    return new LinkedHashSet<>(
        Arrays.asList(new ConstructorProjection(projectVariables(resource), "")));
  }

  /**
   * Project methods set.
   *
   * @param resource the resource
   * @return the set
   */
  protected Set<FunctionProjection> projectMethods(Resource resource) {
    Set<FunctionProjection> functionProjections = new LinkedHashSet<>();

    resource
        .getEndpoints()
        .forEach(
            endpoint -> functionProjections.add(endpointProjectionConverter.convert(endpoint)));

    return functionProjections;
  }
}
