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

package io.polygenesis.generators.java.exporters.domain.valueobject;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.commons.representations.FieldRepresentation;
import io.polygenesis.generators.java.skeletons.AbstractClassRepresentable;
import io.polygenesis.generators.java.skeletons.ConstructorRepresentation;
import io.polygenesis.generators.java.skeletons.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.skeletons.MethodRepresentation;
import io.polygenesis.models.domain.ValueObject;
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
    return fieldRepresentations(source.getData());
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

    if (!fieldRepresentations.isEmpty()) {
      constructorRepresentations.add(
          createConstructorWithSettersFromFieldRepresentations(
              source.getData().getDataType(), fieldRepresentations));
    }

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(ValueObject source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = fieldRepresentations(source);
    return methodRepresentationsForGettersAndGuards(fieldRepresentations);
  }

  @Override
  public String packageName(ValueObject source, Object... args) {
    return packageName(source.getData());
  }

  @Override
  public Set<String> imports(ValueObject source, Object... args) {
    Set<String> imports = new LinkedHashSet<>();

    imports.addAll(imports(source.getData()));
    imports.add("com.oregor.trinity4j.commons.assertion.Assertion");
    imports.add("javax.persistence.Embeddable");

    source
        .getData()
        .getModels()
        .stream()
        .filter(Data::isDataPrimitive)
        .map(Data::getAsDataPrimitive)
        .filter(dataPrimitive -> dataPrimitive.getPrimitiveType().equals(PrimitiveType.DECIMAL))
        .findFirst()
        .ifPresent(model -> imports.add("java.math.BigDecimal"));

    source
        .getData()
        .getModels()
        .stream()
        .filter(Data::isDataPrimitive)
        .map(Data::getAsDataPrimitive)
        .filter(dataPrimitive -> dataPrimitive.getPrimitiveType().equals(PrimitiveType.UUID))
        .findFirst()
        .ifPresent(model -> imports.add("java.util.UUID"));

    source
        .getData()
        .getModels()
        .stream()
        .filter(Data::isDataPrimitive)
        .map(Data::getAsDataPrimitive)
        .filter(dataPrimitive -> dataPrimitive.getPrimitiveType().equals(PrimitiveType.DATETIME))
        .findFirst()
        .ifPresent(model -> imports.add("java.time.LocalDateTime"));

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

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getData().getDataType()));

    stringBuilder.append(" Value Object.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(ValueObject source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(ValueObject source, Object... args) {
    return simpleObjectName(source.getData());
  }

  @Override
  public String fullObjectName(ValueObject source, Object... args) {
    return fullObjectName(source.getData());
  }
}
