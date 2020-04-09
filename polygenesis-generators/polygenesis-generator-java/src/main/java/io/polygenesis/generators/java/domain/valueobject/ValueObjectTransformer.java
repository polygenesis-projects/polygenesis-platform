/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.domain.ValueObject;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ValueObjectTransformer extends AbstractClassTransformer<ValueObject, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ValueObjectTransformer(
      DataTypeTransformer dataTypeTransformer, ValueObjectMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(ValueObject source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(ValueObject source, Object... args) {
    return new LinkedHashSet<>(
        Collections.singletonList(
            FieldRepresentation.withModifiers(
                "static final long",
                "serialVersionUID = 1L",
                dataTypeTransformer.getModifierPrivate())));
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(ValueObject source, Object... args) {
    Set<FieldRepresentation> variables = new LinkedHashSet<>();

    source
        .getData()
        .getModels()
        .forEach(
            model ->
                variables.add(
                    FieldRepresentation.withModifiers(
                        dataTypeTransformer.convert(model.getDataType()),
                        model.getVariableName().getText(),
                        dataTypeTransformer.getModifierPrivate())));

    return variables;
  }

  @Override
  public String packageName(ValueObject source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(ValueObject source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.commons.assertion.Assertion");
    imports.add("javax.persistence.Embeddable");
    imports.add("java.io.Serializable");

    source.getData().getModels().stream()
        .filter(Data::isDataPrimitive)
        .map(Data::getAsDataPrimitive)
        .filter(dataPrimitive -> dataPrimitive.getPrimitiveType().equals(PrimitiveType.DECIMAL))
        .findFirst()
        .ifPresent(model -> imports.add("java.math.BigDecimal"));

    source.getData().getModels().stream()
        .filter(Data::isDataPrimitive)
        .map(Data::getAsDataPrimitive)
        .filter(dataPrimitive -> dataPrimitive.getPrimitiveType().equals(PrimitiveType.UUID))
        .findFirst()
        .ifPresent(model -> imports.add("java.util.UUID"));

    source.getData().getModels().stream()
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
    return String.format(
        "The %s Value Object.", TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      ValueObject source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create no-args constructor
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(
        createNoArgsConstructorForPersistence(dataTypeTransformer.getModifierPrivate()));

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------
    Set<FieldRepresentation> fieldRepresentations = stateFieldRepresentations(source);

    if (!fieldRepresentations.isEmpty()) {
      constructorRepresentations.add(
          createConstructorWithSettersFromFieldRepresentations(
              source.getData().getDataType(), fieldRepresentations));
    }

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(ValueObject source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = stateFieldRepresentations(source);
    return methodRepresentationsForGettersAndGuards(fieldRepresentations);
  }

  @Override
  public String fullObjectName(ValueObject source, Object... args) {
    return String.format("%s implements Serializable", simpleObjectName(source, args));
  }
}
