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
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.shared.AbstractObjectProjectionMaker;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.MethodProjection;
import io.polygenesis.models.apirest.Resource;
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

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource projection converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public ResourceProjectionConverter(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ResourceProjection convert(Resource resource, Object... arg) {
    return new ResourceProjection(
        projectPackageName(resource),
        projectImports(resource),
        projectDescription(resource),
        projectObjectName(resource),
        projectObjectNameWithOptionalExtendsImplements(resource),
        projectVariables(resource),
        projectConstructors(),
        projectMethods());
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
  protected Set<String> projectImports(Resource resource) {
    Set<String> imports = new TreeSet<>();

    imports.add("org.springframework.web.bind.annotation.RestController");

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

    return stringBuilder.toString();
  }

  /**
   * Project variables set.
   *
   * @param resource the resource
   * @return the set
   */
  protected Set<KeyValue> projectVariables(Resource resource) {
    return new LinkedHashSet<>();
  }

  protected Set<ConstructorProjection> projectConstructors() {
    return new LinkedHashSet<>();
  }

  protected Set<MethodProjection> projectMethods() {
    // TODO
    return new LinkedHashSet<>();
  }
}
