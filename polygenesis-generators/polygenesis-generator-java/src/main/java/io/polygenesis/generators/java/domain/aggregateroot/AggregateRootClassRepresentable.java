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

package io.polygenesis.generators.java.domain.aggregateroot;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.PackageName;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.models.domain.AbstractAggregateRootId;
import io.polygenesis.models.domain.AbstractProperty;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.commons.ParameterRepresentation;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Abstract aggregate root class representable.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootClassRepresentable extends AbstractClassRepresentable<AggregateRoot> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract aggregate root class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AggregateRootClassRepresentable(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(AggregateRoot source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    source
        .getProperties()
        .forEach(
            property -> {
              switch (property.getPropertyType()) {
                case AGGREGATE_ROOT_ID:
                case ABSTRACT_AGGREGATE_ROOT_ID:
                  break;
                case PRIMITIVE:
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          makeVariableDataType(property), makeVariableName(property)));
                  break;
                case PRIMITIVE_COLLECTION:
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          makeAnnotationsForPrimitiveCollection(
                              source, property.getData().getAsDataArray())));
                  break;
                case VALUE_OBJECT:
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          makeAnnotationsForValueObject(property.getData().getAsDataGroup())));
                  break;
                default:
                  throw new IllegalStateException(
                      String.format(
                          "Cannot project variable=%s", property.getVariableName().getText()));
              }
            });

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      AggregateRoot source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create no-args constructor
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(createNoArgsConstructorForPersistence());

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(
        createConstructorWithSetters(
            source.getName().getText(),
            makeConstructorParameterRepresentation(source.getProperties())));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(AggregateRoot source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = fieldRepresentations(source);
    return methodRepresentationsForGettersAndGuards(fieldRepresentations);
  }

  @Override
  public String packageName(AggregateRoot source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(AggregateRoot source, Object... args) {
    PackageName rootPackageName = (PackageName) args[0];
    Set<String> imports = new TreeSet<>();

    source
        .getProperties()
        .stream()
        .filter(
            property -> !property.getPropertyType().equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID))
        .forEach(
            property -> {
              if (property.getPropertyType().equals(PropertyType.PRIMITIVE_COLLECTION)
                  || property.getPropertyType().equals(PropertyType.VALUE_OBJECT_COLLECTION)
                  || property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_COLLECTION)) {
                imports.add("java.util.List");
                imports.add("javax.persistence.ElementCollection");
                imports.add("javax.persistence.CollectionTable");
                imports.add("javax.persistence.JoinColumn");
                imports.add("javax.persistence.Column");
              }

              if (property.getData().isDataPrimitive()) {
                DataPrimitive dataPrimitive = property.getData().getAsDataPrimitive();
                if (dataPrimitive.getPrimitiveType().equals(PrimitiveType.DATETIME)) {
                  imports.add("java.time.LocalDateTime");
                }
              }

              if (property.getData().isDataGroup()) {
                imports.add("javax.persistence.Embedded");
                imports.add("javax.persistence.AttributeOverride");
                imports.add("javax.persistence.Column");

                DataGroup dataGroup = property.getData().getAsDataGroup();
                if (!dataGroup.getPackageName().equals(source.getPackageName())) {
                  imports.add(
                      dataGroup.getPackageName().getText()
                          + "."
                          + TextConverter.toUpperCamel(dataGroup.getDataType()));
                }
              }
            });

    // Additional imports
    imports.add("com.oregor.ddd4j.check.assertion.Assertion");

    if (source.hasSuperclass()) {
      imports.add(
          String.format(
              "%s.%s",
              source.getSuperclass().getPackageName().getText(),
              TextConverter.toUpperCamel(source.getSuperclass().getName().getText())));
    }

    if (source.getInstantiationType().equals(InstantiationType.NORMAL)) {
      imports.add(rootPackageName.getText() + ".Constants");
      imports.add("javax.persistence.Entity");
      imports.add("javax.persistence.Table");
    } else {
      imports.add("com.oregor.ddd4j.core.AggregateRootId");
      imports.add("javax.persistence.MappedSuperclass");
    }

    return imports;
  }

  @Override
  public Set<String> annotations(AggregateRoot source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    if (source.getInstantiationType().equals(InstantiationType.NORMAL)) {
      annotations.add("@Entity");
      annotations.add(
          String.format(
              "@Table(name = Constants.DEFAULT_TABLE_PREFIX + \"%s\")",
              TextConverter.toLowerHyphen(source.getName().getText())));
    } else {
      annotations.add("@MappedSuperclass");
    }

    return annotations;
  }

  @Override
  public String description(AggregateRoot source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getName().getText()));

    stringBuilder.append(" Aggregate Root.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(AggregateRoot source, Object... args) {
    if (source.getInstantiationType().equals(InstantiationType.NORMAL)) {
      return MODIFIER_PUBLIC;
    } else {
      return MODIFIER_PUBLIC + " " + MODIFIER_ABSTRACT;
    }
  }

  @Override
  public String simpleObjectName(AggregateRoot source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(AggregateRoot source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getName().getText()));

    if (source.getInstantiationType().equals(InstantiationType.ABSTRACT)) {
      stringBuilder.append("<I extends AggregateRootId>");
    }

    stringBuilder.append(" extends ");
    if (source.hasSuperclass()) {
      stringBuilder.append(TextConverter.toUpperCamel(source.getSuperclass().getName().getText()));
    } else {
      throw new IllegalStateException();
    }

    if (source.getInstantiationType().equals(InstantiationType.ABSTRACT)) {
      stringBuilder.append("<I>");
    } else {
      stringBuilder.append("<");
      stringBuilder.append(TextConverter.toUpperCamel(source.getName().getText()));
      stringBuilder.append("Id");
      stringBuilder.append(">");
    }

    return stringBuilder.toString();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make constructor parameter representation set.
   *
   * @param properties the properties
   * @return the set
   */
  private Set<ParameterRepresentation> makeConstructorParameterRepresentation(
      Set<AbstractProperty> properties) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    properties
        .stream()
        .forEach(
            property -> {
              if (property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID)) {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        makeVariableDataType(property), makeVariableName(property), true));
              } else if (property
                  .getPropertyType()
                  .equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID)) {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        makeVariableDataType(property), makeVariableName(property), true));
              } else {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        makeVariableDataType(property), makeVariableName(property)));
              }
            });

    return parameterRepresentations;
  }

  /**
   * Make annotations for value object set.
   *
   * @param dataGroup the data group
   * @return the set
   */
  private Set<String> makeAnnotationsForValueObject(DataGroup dataGroup) {
    Set<String> annotations = new LinkedHashSet<>();
    annotations.add("@Embedded");

    dataGroup
        .getModels()
        .forEach(
            model -> {
              if (model.isDataPrimitive()) {
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("@AttributeOverride(\n");

                stringBuilder.append(
                    String.format(
                        "\t\t\tname = \"%s\",\n",
                        TextConverter.toLowerCamel(model.getVariableName().getText())));

                stringBuilder.append(
                    String.format(
                        "\t\t\tcolumn = @Column(name = \"%s%s\"))",
                        TextConverter.toLowerCamel(dataGroup.getVariableName().getText()),
                        TextConverter.toUpperCamel(model.getVariableName().getText())));

                annotations.add(stringBuilder.toString());
              } else {
                throw new UnsupportedOperationException();
              }
            });

    return annotations;
  }

  /**
   * Make annotations for primitive collection set.
   *
   * @param aggregateRoot the aggregate root
   * @param dataArray the data array
   * @return the set
   */
  private Set<String> makeAnnotationsForPrimitiveCollection(
      AggregateRoot aggregateRoot, DataArray dataArray) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@ElementCollection");

    String tableName =
        String.format(
            "%s_%s",
            TextConverter.toLowerUnderscore(aggregateRoot.getName().getText()),
            TextConverter.toLowerUnderscore(dataArray.getVariableName().getText()));

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("@CollectionTable(\n");
    stringBuilder.append(
        String.format("\t\t\tname = Constants.DEFAULT_TABLE_PREFIX + \"%s\",\n", tableName));
    stringBuilder.append(String.format("\t\t\tjoinColumns = {\n"));
    stringBuilder.append(String.format("\t\t\t\t\t@JoinColumn(name = \"%s\")", "root_id"));
    if (aggregateRoot.getMultiTenant()) {
      stringBuilder.append(",\n");
      stringBuilder.append(String.format("\t\t\t\t\t@JoinColumn(name = \"%s\")\n", "tenant_id"));
    } else {
      stringBuilder.append("\n");
    }
    stringBuilder.append(String.format("\t\t\t}\n"));
    stringBuilder.append("\t)");

    annotations.add(stringBuilder.toString());

    annotations.add(
        String.format(
            "@Column(name = \"%s\")",
            TextConverter.toLowerCamel(dataArray.getArrayElement().getVariableName().getText())));

    return annotations;
  }

  /**
   * Make variable data type string.
   *
   * @param property the property
   * @return the string
   */
  private String makeVariableDataType(AbstractProperty property) {
    switch (property.getPropertyType()) {
      case ABSTRACT_AGGREGATE_ROOT_ID:
        return ((AbstractAggregateRootId) property).getGenericTypeParameter().getText();
      case PRIMITIVE_COLLECTION:
        return String.format(
            "List<%s>",
            fromDataTypeToJavaConverter.getDeclaredVariableType(
                property.getTypeParameterData().getDataType()));
      default:
        return fromDataTypeToJavaConverter.getDeclaredVariableType(
            property.getData().getDataType());
    }
  }

  /**
   * Make variable name string.
   *
   * @param property the property
   * @return the string
   */
  private String makeVariableName(AbstractProperty property) {
    return property.getVariableName().getText();
  }
}
