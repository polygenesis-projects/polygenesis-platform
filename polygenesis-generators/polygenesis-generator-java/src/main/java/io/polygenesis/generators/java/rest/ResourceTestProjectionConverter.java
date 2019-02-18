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
import io.polygenesis.models.rest.Resource;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Resource test projection converter.
 *
 * @author Christos Tsakostas
 */
public class ResourceTestProjectionConverter extends ResourceProjectionConverter
    implements Converter<Resource, ResourceProjection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource test projection converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param endpointProjectionConverter the endpoint projection converter
   */
  public ResourceTestProjectionConverter(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      EndpointProjectionConverter endpointProjectionConverter) {
    super(fromDataTypeToJavaConverter, endpointProjectionConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ResourceProjection convert(Resource resource, Object... args) {
    return new ResourceProjection(
        projectPackageName(resource),
        projectImports(),
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
   * Project imports set.
   *
   * @return the set
   */
  protected Set<String> projectImports() {
    Set<String> imports = new TreeSet<>();

    imports.add("static org.assertj.core.api.Assertions.assertThat");
    imports.add("static org.assertj.core.api.Assertions.assertThatThrownBy");
    imports.add("static org.mockito.Mockito.mock");
    imports.add("org.junit.Before");
    imports.add("org.junit.Test");

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

    stringBuilder.append("Test for the Spring REST Controller for ");

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
    stringBuilder.append("RestServiceTest");

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
    stringBuilder.append("RestServiceTest");

    return stringBuilder.toString();
  }
}
