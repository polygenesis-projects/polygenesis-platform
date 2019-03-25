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

package io.polygenesis.generators.java.domain.aggregateentity;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.commons.ParameterRepresentation;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Aggregate entity id class representable.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityIdClassRepresentable
    extends AbstractClassRepresentable<AggregateEntity> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity id class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AggregateEntityIdClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(AggregateEntity source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      AggregateEntity source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create no-args constructor
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(createNoArgsConstructorForPersistence());

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------

    constructorRepresentations.add(
        createConstructorWithImplementation(
            source.getObjectName().getText(),
            new LinkedHashSet<>(Arrays.asList(new ParameterRepresentation("UUID", "entityId"))),
            "\t\tsuper(entityId);"));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(AggregateEntity source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String packageName(AggregateEntity source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(AggregateEntity source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.ddd4j.domain.AggregateEntityId");
    imports.add("javax.persistence.Embeddable");
    imports.add("java.util.UUID");

    return imports;
  }

  @Override
  public Set<String> annotations(AggregateEntity source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Embeddable"));
  }

  @Override
  public String description(AggregateEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Aggregate Entity Id.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(AggregateEntity source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(AggregateEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getObjectName().getText()));
    stringBuilder.append("Id");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(AggregateEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append("Id");
    stringBuilder.append(" extends ");
    stringBuilder.append("AggregateEntityId");

    return stringBuilder.toString();
  }
}
