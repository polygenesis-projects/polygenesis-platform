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

package io.polygenesis.generators.java.transformers.rdbms;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformer.code.AbstractInterfaceTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import io.polygenesis.transformer.code.FunctionToMethodRepresentationTransformer;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Spring data repository interface representable.
 *
 * @author Christos Tsakostas
 */
public class SpringDataRepositoryInterfaceTransformer
    extends AbstractInterfaceTransformer<Persistence> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Spring data repository interface representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param functionToMethodRepresentationTransformer the function to method representation
   *     converter
   */
  @SuppressWarnings("CPD-START")
  public SpringDataRepositoryInterfaceTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FunctionToMethodRepresentationTransformer functionToMethodRepresentationTransformer) {
    super(fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<MethodRepresentation> methodRepresentations(Persistence source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String packageName(Persistence source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Persistence source, Object... args) {
    Set<String> imports = new TreeSet<>();

    if (source.getMultiTenant()) {
      imports.add("com.oregor.trinity4j.domain.SpringDataTenantRepository");
    } else {
      imports.add("com.oregor.trinity4j.domain.SpringDataRepository");
    }

    return imports;
  }

  @Override
  public Set<String> annotations(Persistence source, Object... args) {
    return new LinkedHashSet<>();
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String description(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The Spring Data Repository Interface for ");

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(source.getAggregateRootObjectName().getText()));

    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Persistence source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getObjectName().getText()));
    stringBuilder.append("Repository");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText()));
    stringBuilder.append("Repository");
    stringBuilder.append(" ");
    stringBuilder.append("extends");
    stringBuilder.append(" ");

    if (source.getMultiTenant()) {
      stringBuilder.append("SpringDataTenantRepository<");
    } else {
      stringBuilder.append("SpringDataRepository<");
    }

    stringBuilder.append(TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText()));
    stringBuilder.append(", ");
    stringBuilder.append(
        TextConverter.toUpperCamel(source.getAggregateRootIdObjectName().getText()));
    stringBuilder.append(">");

    return stringBuilder.toString();
  }
}