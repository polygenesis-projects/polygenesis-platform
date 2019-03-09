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

package io.polygenesis.generators.java.domain.valueobject;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.ValueObject;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Value object class representable.
 *
 * @author Christos Tsakostas
 */
public class ValueObjectClassRepresentable extends AbstractClassRepresentable<ValueObject> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public ValueObjectClassRepresentable(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(ValueObject source, Object... args) {
    return fieldRepresentations(source.getIoModelGroup());
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      ValueObject source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create no-args constructor
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(createNoArgsConstructorForPersistence());

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------
    Set<FieldRepresentation> fieldRepresentations = fieldRepresentations(source);

    constructorRepresentations.add(
        createConstructorWithSettersFromFieldRepresentations(
            source.getOriginatingIoModelGroup().getDataType(),
            fieldRepresentations));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(ValueObject source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = fieldRepresentations(source);
    return methodRepresentationsForGettersAndGuards(fieldRepresentations);
  }

  @Override
  public String packageName(ValueObject source, Object... args) {
    return packageName(source.getIoModelGroup());
  }

  @Override
  public Set<String> imports(ValueObject source, Object... args) {
    Set<String> imports = new LinkedHashSet<>();

    imports.addAll(imports(source.getIoModelGroup()));
    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("javax.persistence.Embeddable");

    return imports;
  }

  @Override
  public Set<String> annotations(ValueObject source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Embeddable"));
  }

  @Override
  public String description(ValueObject source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(
            source.getIoModelGroup().getDataType()));

    stringBuilder.append(" Value Object.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(ValueObject source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(ValueObject source, Object... args) {
    return simpleObjectName(source.getIoModelGroup());
  }

  @Override
  public String fullObjectName(ValueObject source, Object... args) {
    return fullObjectName(source.getIoModelGroup());
  }
}
