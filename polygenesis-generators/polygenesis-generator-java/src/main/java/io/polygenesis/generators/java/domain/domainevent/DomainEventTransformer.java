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

package io.polygenesis.generators.java.domain.domainevent;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.domain.DomainObjectClassTransformer;
import io.polygenesis.models.domain.DomainEvent;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Domain event transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainEventTransformer extends DomainObjectClassTransformer<DomainEvent, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain event transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainEventTransformer(
      DataTypeTransformer dataTypeTransformer, DomainEventMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(DomainEvent source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(DomainEvent source, Object... args) {
    return new LinkedHashSet<>(
        Collections.singletonList(
            FieldRepresentation.withModifiers(
                "static final long",
                "serialVersionUID = 1L",
                dataTypeTransformer.getModifierPrivate())));
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(DomainEvent source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    source
        .getProperties()
        .forEach(
            property -> {
              switch (property.getPropertyType()) {
                case REFERENCE_TO_AGGREGATE_ROOT:
                  fieldRepresentations.add(
                      FieldRepresentation.withModifiers(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          dataTypeTransformer.getModifierPrivate()));
                  break;
                case AGGREGATE_ENTITY_ID:
                case AGGREGATE_ROOT_ID:
                case ABSTRACT_AGGREGATE_ROOT_ID:
                case PROJECTION_ID:
                case SUPPORTIVE_ENTITY_ID:
                case TENANT_ID:
                  break;
                case PRIMITIVE:
                  fieldRepresentations.add(
                      FieldRepresentation.withModifiers(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          dataTypeTransformer.getModifierPrivate()));
                  break;
                case PRIMITIVE_COLLECTION:
                  fieldRepresentations.add(
                      FieldRepresentation.withModifiers(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          dataTypeTransformer.getModifierPrivate()));
                  break;
                case VALUE_OBJECT:
                  fieldRepresentations.add(
                      FieldRepresentation.withModifiers(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          dataTypeTransformer.getModifierPrivate()));
                  break;
                case VALUE_OBJECT_COLLECTION:
                  // TODO
                  throw new UnsupportedOperationException();
                case AGGREGATE_ENTITY:
                  fieldRepresentations.add(
                      FieldRepresentation.withModifiers(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          dataTypeTransformer.getModifierPrivate()));
                  break;
                case AGGREGATE_ENTITY_COLLECTION:
                  fieldRepresentations.add(
                      FieldRepresentation.withModifiers(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          dataTypeTransformer.getModifierPrivate()));
                  break;
                case REFERENCE:
                  fieldRepresentations.add(
                      FieldRepresentation.withModifiers(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          dataTypeTransformer.getModifierPrivate()));
                  break;
                case MAP:
                  fieldRepresentations.add(
                      FieldRepresentation.withModifiers(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          dataTypeTransformer.getModifierPrivate()));
                  break;
                default:
                  throw new IllegalStateException(
                      String.format(
                          "Cannot project variable=%s",
                          property.getData().getVariableName().getText()));
              }
            });

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainEvent source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create no-args constructor
    // ---------------------------------------------------------------------------------------------
    boolean hasNonEmptyConstructors =
        source
            .getConstructors()
            .stream()
            .anyMatch(constructor -> !constructor.getProperties().isEmpty());

    if (hasNonEmptyConstructors) {
      constructorRepresentations.add(
          createNoArgsConstructorForPersistence(
              source.getInstantiationType().equals(InstantiationType.ABSTRACT)
                  ? dataTypeTransformer.getModifierProtected()
                  : dataTypeTransformer.getModifierPrivate()));
    }

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------
    source
        .getConstructors()
        .stream()
        .forEach(
            constructor ->
                constructorRepresentations.add(
                    createConstructorWithDirectAssignment(
                        source.getObjectName().getText(),
                        makeConstructorParameterRepresentation(constructor.getProperties()))));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(DomainEvent source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = stateFieldRepresentations(source);
    return methodRepresentationsForGetters(fieldRepresentations);
  }

  @Override
  public String packageName(DomainEvent source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainEvent source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.DomainMessage");
    imports.add("com.oregor.trinity4j.domain.DomainMessageInfo");
    imports.add("com.oregor.trinity4j.domain.DomainMessageType");

    imports.addAll(super.imports(source, args));

    return imports;
  }

  @Override
  public Set<String> annotations(DomainEvent source, Object... args) {
    return new LinkedHashSet<>(
        Collections.singletonList(
            "@DomainMessageInfo(type = DomainMessageType.EVENT, version = 1)"));
  }

  @Override
  public String description(DomainEvent source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Domain Event.");

    return stringBuilder.toString();
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(DomainEvent source, Object... args) {
    return String.format("%s extends DomainMessage", simpleObjectName(source, args));
  }
}
