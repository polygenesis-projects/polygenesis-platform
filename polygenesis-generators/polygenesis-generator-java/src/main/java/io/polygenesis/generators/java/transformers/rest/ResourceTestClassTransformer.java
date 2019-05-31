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

package io.polygenesis.generators.java.transformers.rest;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.rest.Resource;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Resource test class representable.
 *
 * @author Christos Tsakostas
 */
public class ResourceTestClassTransformer extends ResourceClassTransformer {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource test class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param endpointMethodRepresentable the endpoint method representable
   */
  public ResourceTestClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      EndpointMethodTransformer endpointMethodRepresentable) {
    super(fromDataTypeToJavaConverter, endpointMethodRepresentable);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<String> imports(Resource source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("static org.assertj.core.api.Assertions.assertThat");
    imports.add("static org.assertj.core.api.Assertions.assertThatThrownBy");
    imports.add("static org.mockito.Mockito.mock");
    imports.add("org.junit.Before");
    imports.add("org.junit.Test");

    return imports;
  }

  @Override
  public String description(Resource source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Test for the Spring REST Controller for ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Services.");

    return stringBuilder.toString();
  }

  @Override
  public String simpleObjectName(Resource source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getObjectName().getText()));
    stringBuilder.append("RestServiceTest");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Resource source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append("RestServiceTest");

    return stringBuilder.toString();
  }
}
