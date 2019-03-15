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

package io.polygenesis.generators.java.domain;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.models.domain.AbstractAggregateRootId;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.DomainObjectType;
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
 * The type Domain object class representable.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class DomainObjectClassRepresentable<S extends BaseDomainObject<?>>
    extends AbstractClassRepresentable<S> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public DomainObjectClassRepresentable(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(S source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    source
        .getProperties()
        .forEach(
            property -> {
              switch (property.getPropertyType()) {
                case REFERENCE_TO_AGGREGATE_ROOT:
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          makeAnnotationsForReferenceToAggregateRoot(source)));
                  break;
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
                case VALUE_OBJECT_COLLECTION:
                  // TODO
                  throw new UnsupportedOperationException();
                case AGGREGATE_ENTITY:
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          makeAnnotationsForValueObject(property.getData().getAsDataGroup())));
                  break;
                case AGGREGATE_ENTITY_COLLECTION:
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          makeVariableDataType(property),
                          makeVariableName(property),
                          makeAnnotationsForAggregateEntityCollection(source)));
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
  public Set<ConstructorRepresentation> constructorRepresentations(S source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create no-args constructor
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(createNoArgsConstructorForPersistence());

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------
    source
        .getConstructors()
        .forEach(
            constructor -> {
              constructorRepresentations.add(
                  createConstructorWithSetters(
                      source.getObjectName().getText(),
                      makeConstructorParameterRepresentation(constructor.getProperties())));
            });

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(S source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = fieldRepresentations(source);
    return methodRepresentationsForGettersAndGuards(fieldRepresentations);
  }

  @Override
  public String packageName(S source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(S source, Object... args) {
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
                  || property.getPropertyType().equals(PropertyType.VALUE_OBJECT_COLLECTION)) {
                imports.add("java.util.List");
                imports.add("javax.persistence.ElementCollection");
                imports.add("javax.persistence.CollectionTable");
                imports.add("javax.persistence.JoinColumn");
                imports.add("javax.persistence.JoinColumns");
                imports.add("javax.persistence.Column");
              }

              if (property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_COLLECTION)) {
                imports.add("java.util.List");
                imports.add("javax.persistence.CascadeType");
                imports.add("javax.persistence.FetchType");
                imports.add("javax.persistence.OneToMany");
              }

              if (property.getPropertyType().equals(PropertyType.REFERENCE_TO_AGGREGATE_ROOT)) {
                imports.add("javax.persistence.FetchType");
                imports.add("javax.persistence.JoinColumn");
                imports.add("javax.persistence.JoinColumns");
                imports.add("javax.persistence.ManyToOne");
              }

              if (!property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY)
                  && !property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_COLLECTION)) {

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
              }
            });

    // Additional imports
    imports.add("com.oregor.ddd4j.check.assertion.Assertion");

    // SuperClass
    if (source.hasSuperclass()) {
      BaseDomainObject<?> superClass = (BaseDomainObject<?>) source.getSuperClass();

      imports.add(
          String.format(
              "%s.%s",
              superClass.getPackageName().getText(),
              TextConverter.toUpperCamel(superClass.getObjectName().getText())));
    }

    if (source.getInstantiationType().equals(InstantiationType.CONCRETE)) {
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
  public Set<String> annotations(S source, Object... args) {
    return null;
  }

  @Override
  public String description(S source, Object... args) {
    return null;
  }

  @Override
  public String modifiers(S source, Object... args) {
    return null;
  }

  @Override
  public String simpleObjectName(S source, Object... args) {
    return null;
  }

  @Override
  public String fullObjectName(S source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    if (source.getInstantiationType().equals(InstantiationType.ABSTRACT)) {
      if (source.getDomainObjectType().equals(DomainObjectType.AGGREGATE_ROOT)) {
        stringBuilder.append("<I extends AggregateRootId>");
      } else if (source.getDomainObjectType().equals(DomainObjectType.AGGREGATE_ENTITY)) {
        stringBuilder.append("<I extends AggregateEntityId>");
      } else {
        throw new UnsupportedOperationException();
      }
    }

    stringBuilder.append(" extends ");
    if (source.hasSuperclass()) {
      BaseDomainObject<?> superClass = (BaseDomainObject<?>) source.getSuperClass();

      stringBuilder.append(TextConverter.toUpperCamel(superClass.getObjectName().getText()));
    } else {
      throw new IllegalStateException();
    }

    if (source.getInstantiationType().equals(InstantiationType.ABSTRACT)) {
      stringBuilder.append("<I>");
    } else {
      stringBuilder.append("<");
      stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
      stringBuilder.append("Id");
      stringBuilder.append(">");
    }

    return stringBuilder.toString();
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Make annotations for value object set.
   *
   * @param dataGroup the data group
   * @return the set
   */
  protected Set<String> makeAnnotationsForValueObject(DataGroup dataGroup) {
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
   * @param source the source
   * @param dataArray the data array
   * @return the set
   */
  protected Set<String> makeAnnotationsForPrimitiveCollection(S source, DataArray dataArray) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@ElementCollection");

    String tableName =
        String.format(
            "%s_%s",
            TextConverter.toLowerUnderscore(source.getObjectName().getText()),
            TextConverter.toLowerUnderscore(dataArray.getVariableName().getText()));

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("@CollectionTable(\n");
    stringBuilder.append(
        String.format("\t\t\tname = Constants.DEFAULT_TABLE_PREFIX + \"%s\",\n", tableName));
    stringBuilder.append(String.format("\t\t\tjoinColumns = {\n"));
    stringBuilder.append(String.format("\t\t\t\t\t@JoinColumn(name = \"%s\")", "root_id"));
    if (source.getMultiTenant()) {
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
   * Make annotations for aggregate entity collection set.
   *
   * @param source the source
   * @return the set
   */
  protected Set<String> makeAnnotationsForAggregateEntityCollection(S source) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("@OneToMany(\n");
    stringBuilder.append(
        String.format(
            "\t\t\tmappedBy = \"%s\",\n",
            TextConverter.toLowerCamel(source.getObjectName().getText())));
    stringBuilder.append("\t\t\tcascade = CascadeType.ALL,\n");
    stringBuilder.append("\t\t\torphanRemoval = true,\n");
    stringBuilder.append("\t\t\tfetch = FetchType.EAGER\n");
    stringBuilder.append("\t)");

    Set<String> annotations = new LinkedHashSet<>();
    annotations.add(stringBuilder.toString());

    return annotations;
  }

  /**
   * Make annotations for reference to aggregate root set.
   *
   * @return the set
   */
  protected Set<String> makeAnnotationsForReferenceToAggregateRoot(S source) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("@ManyToOne(fetch = FetchType.LAZY)\n");
    stringBuilder.append("\t@JoinColumns({\n");
    stringBuilder.append("\t\t\t@JoinColumn(name = \"root_id\")");
    if (source.getMultiTenant()) {
      stringBuilder.append(",\n");
      stringBuilder.append("\t\t\t@JoinColumn(name = \"tenant_id\")\n");
    } else {
      stringBuilder.append("\n");
    }
    stringBuilder.append("\t})");

    Set<String> annotations = new LinkedHashSet<>();
    annotations.add(stringBuilder.toString());

    return annotations;
  }

  /**
   * Make variable data type string.
   *
   * @param property the property
   * @return the string
   */
  protected String makeVariableDataType(DomainObjectProperty property) {
    switch (property.getPropertyType()) {
      case ABSTRACT_AGGREGATE_ROOT_ID:
        return ((AbstractAggregateRootId) property).getGenericTypeParameter().getText();
      case PRIMITIVE_COLLECTION:
        return String.format(
            "List<%s>",
            fromDataTypeToJavaConverter.getDeclaredVariableType(
                property.getTypeParameterData().getDataType()));
      case AGGREGATE_ENTITY_COLLECTION:
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
  protected String makeVariableName(DomainObjectProperty property) {
    return property.getData().getVariableName().getText();
  }

  /**
   * Make constructor parameter representation set.
   *
   * @param properties the properties
   * @return the set
   */
  protected Set<ParameterRepresentation> makeConstructorParameterRepresentation(
      Set<DomainObjectProperty> properties) {
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
}
