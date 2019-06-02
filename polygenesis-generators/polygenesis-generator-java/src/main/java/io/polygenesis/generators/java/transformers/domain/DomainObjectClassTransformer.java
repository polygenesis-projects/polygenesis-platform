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

package io.polygenesis.generators.java.transformers.domain;

import static io.polygenesis.models.domain.PropertyType.PROJECTION_ID;

import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.AbstractAggregateRootId;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.DomainObjectType;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.Mapper;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformer.code.AbstractClassTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Domain object class representable.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class DomainObjectClassTransformer<S extends BaseDomainObject>
    extends AbstractClassTransformer<S> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public DomainObjectClassTransformer(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
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
                case AGGREGATE_ENTITY_ID:
                case AGGREGATE_ROOT_ID:
                case ABSTRACT_AGGREGATE_ROOT_ID:
                case PROJECTION_ID:
                case SUPPORTIVE_ENTITY_ID:
                case TENANT_ID:
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
                case REFERENCE:
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          makeVariableDataType(property), makeVariableName(property)));
                  break;
                case MAP:
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          makeVariableDataType(property), makeVariableName(property)));
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
            constructor ->
                constructorRepresentations.add(
                    createConstructorWithSetters(
                        source.getObjectName().getText(),
                        makeConstructorParameterRepresentation(constructor.getProperties()))));

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
    Set<String> imports = new TreeSet<>();

    source
        .getProperties()
        .stream()
        .filter(
            property -> !property.getPropertyType().equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID))
        .forEach(
            property -> {
              imports.addAll(importsPrimitiveOrValueObjectCollection(property));
              imports.addAll(importsAggregateEntityCollection(property));
              imports.addAll(importsReferenceToAggregateRoot(property));

              if (!property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY)
                  && !property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_COLLECTION)) {
                imports.addAll(importsPrimitive(property));
                imports.addAll(importsValueObject(source, property));
              }
            });

    imports.add("com.oregor.trinity4j.commons.assertion.Assertion");
    imports.addAll(importsSuperClass(source));
    imports.addAll(importsSupportiveEntity(source, args));

    return imports;
  }

  private Set<String> importsSuperClass(S source) {
    Set<String> imports = new TreeSet<>();

    if (source.hasSuperclass()) {
      BaseDomainObject superClass = source.getSuperClass();

      imports.add(
          String.format(
              "%s.%s",
              superClass.getPackageName().getText(),
              TextConverter.toUpperCamel(superClass.getObjectName().getText())));
    }

    return imports;
  }

  private Set<String> importsSupportiveEntity(S source, Object... args) {
    Set<String> imports = new TreeSet<>();
    PackageName rootPackageName = (PackageName) args[0];

    if (!source.getDomainObjectType().equals(DomainObjectType.HELPER_ENTITY)) {
      if (source.getInstantiationType().equals(InstantiationType.CONCRETE)) {
        imports.add(rootPackageName.getText() + ".Constants");
        imports.add("javax.persistence.Entity");
        imports.add("javax.persistence.Table");
      } else {
        imports.add("com.oregor.trinity4j.domain.AggregateRootId");
        imports.add("javax.persistence.MappedSuperclass");
      }
    } else {
      imports.add("com.oregor.trinity4j.domain.SupportiveEntity");
    }

    return imports;
  }

  @SuppressWarnings("rawtypes")
  private Set<String> importsPrimitiveOrValueObjectCollection(DomainObjectProperty property) {
    Set<String> imports = new TreeSet<>();

    if (property.getPropertyType().equals(PropertyType.PRIMITIVE_COLLECTION)
        || property.getPropertyType().equals(PropertyType.VALUE_OBJECT_COLLECTION)) {
      imports.add("java.util.List");
      imports.add("javax.persistence.ElementCollection");
      imports.add("javax.persistence.CollectionTable");
      imports.add("javax.persistence.JoinColumn");
      imports.add("javax.persistence.JoinColumns");
      imports.add("javax.persistence.Column");
    }

    return imports;
  }

  @SuppressWarnings("rawtypes")
  private Set<String> importsAggregateEntityCollection(DomainObjectProperty property) {
    Set<String> imports = new TreeSet<>();

    if (property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_COLLECTION)) {
      imports.add("java.util.List");
      imports.add("javax.persistence.CascadeType");
      imports.add("javax.persistence.FetchType");
      imports.add("javax.persistence.OneToMany");
    }

    return imports;
  }

  @SuppressWarnings("rawtypes")
  private Set<String> importsReferenceToAggregateRoot(DomainObjectProperty property) {
    Set<String> imports = new TreeSet<>();

    if (property.getPropertyType().equals(PropertyType.REFERENCE_TO_AGGREGATE_ROOT)) {
      imports.add("javax.persistence.FetchType");
      imports.add("javax.persistence.JoinColumn");
      imports.add("javax.persistence.JoinColumns");
      imports.add("javax.persistence.ManyToOne");
    }

    return imports;
  }

  @SuppressWarnings("rawtypes")
  private Set<String> importsPrimitive(DomainObjectProperty property) {
    Set<String> imports = new TreeSet<>();

    if (property.getData().isDataPrimitive()) {
      DataPrimitive dataPrimitive = property.getData().getAsDataPrimitive();

      switch (dataPrimitive.getPrimitiveType()) {
        case DATETIME:
          imports.add("java.time.LocalDateTime");
          break;
        case DECIMAL:
          imports.add("java.math.BigDecimal");
          break;
        case UUID:
          imports.add("java.util.UUID");
          break;
        default:
          break;
      }
    }

    return imports;
  }

  @SuppressWarnings("rawtypes")
  private Set<String> importsValueObject(S source, DomainObjectProperty property) {
    Set<String> imports = new TreeSet<>();

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

    return imports;
  }

  @Override
  public Set<String> annotations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(S source, Object... args) {
    return "Not set yet";
  }

  @Override
  public String modifiers(S source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(S source, Object... args) {
    return "empty simpleObjectName";
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
      BaseDomainObject superClass = source.getSuperClass();

      stringBuilder.append(TextConverter.toUpperCamel(superClass.getObjectName().getText()));
    } else {
      throw new IllegalStateException(
          String.format(
              "No superclass defined for domain object=%s", source.getObjectName().getText()));
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

                stringBuilder.append(String.format("@AttributeOverride(%n"));

                stringBuilder.append(
                    String.format(
                        "\t\t\tname = \"%s\",%n",
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
    stringBuilder.append(String.format("@CollectionTable(%n"));
    stringBuilder.append(
        String.format("\t\t\tname = Constants.DEFAULT_TABLE_PREFIX + \"%s\",%n", tableName));
    stringBuilder.append(String.format("\t\t\tjoinColumns = {%n"));
    stringBuilder.append(String.format("\t\t\t\t\t@JoinColumn(name = \"%s\")", "root_id"));
    if (source.getMultiTenant()) {
      // TODO
      stringBuilder.append("");
      //      stringBuilder.append(String.format(",%n"));
      //      stringBuilder.append(String.format("\t\t\t\t\t@JoinColumn(name = \"%s\")%n",
      // "tenant_id"));
    } else {
      stringBuilder.append(String.format("%n"));
    }

    stringBuilder.append(String.format("\t\t\t}%n"));
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

    stringBuilder.append(String.format("@OneToMany(%n"));
    stringBuilder.append(
        String.format(
            "\t\t\tmappedBy = \"%s\",%n",
            TextConverter.toLowerCamel(source.getObjectName().getText())));
    stringBuilder.append(String.format("\t\t\tcascade = CascadeType.ALL,%n"));
    stringBuilder.append(String.format("\t\t\torphanRemoval = true,%n"));
    stringBuilder.append(String.format("\t\t\tfetch = FetchType.EAGER%n"));
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

    stringBuilder.append(String.format("@ManyToOne(fetch = FetchType.LAZY)%n"));
    stringBuilder.append(String.format("\t@JoinColumns({%n"));
    stringBuilder.append("\t\t\t@JoinColumn(name = \"root_id\")");
    if (source.getMultiTenant()) {
      // TODO
      stringBuilder.append("");
      //      stringBuilder.append(String.format(",%n"));
      //      stringBuilder.append(String.format("\t\t\t@JoinColumn(name = \"tenant_id\")%n"));
    } else {
      stringBuilder.append(String.format("%n"));
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
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected String makeVariableDataType(DomainObjectProperty property) {
    switch (property.getPropertyType()) {
      case ABSTRACT_AGGREGATE_ROOT_ID:
        return ((AbstractAggregateRootId) property).getGenericTypeParameter().getText();
      case PRIMITIVE_COLLECTION:
        return String.format(
            "List<%s>",
            fromDataTypeToJavaConverter.convert(property.getTypeParameterData().getDataType()));
      case AGGREGATE_ENTITY_COLLECTION:
        return String.format(
            "List<%s>",
            fromDataTypeToJavaConverter.convert(property.getTypeParameterData().getDataType()));
      case MAP:
        Mapper mapper = Mapper.class.cast(property);
        return String.format(
            "Map<%s, %s>",
            fromDataTypeToJavaConverter.convert(mapper.getTypeParameterDataKey().getDataType()),
            fromDataTypeToJavaConverter.convert(mapper.getTypeParameterDataValue().getDataType()));
      default:
        return fromDataTypeToJavaConverter.convert(property.getData().getDataType());
    }
  }

  /**
   * Make variable name string.
   *
   * @param property the property
   * @return the string
   */
  @SuppressWarnings("rawtypes")
  protected String makeVariableName(DomainObjectProperty property) {
    return property.getData().getVariableName().getText();
  }

  /**
   * Make constructor parameter representation set.
   *
   * @param properties the properties
   * @return the set
   */
  @SuppressWarnings("rawtypes")
  protected Set<ParameterRepresentation> makeConstructorParameterRepresentation(
      Set<DomainObjectProperty> properties) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    properties
        .stream()
        .forEach(
            property -> {
              if (property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID)
                  || property.getPropertyType().equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID)
                  || property.getPropertyType().equals(PROJECTION_ID)) {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        makeVariableDataType(property),
                        makeVariableName(property),
                        DataPurpose.thingIdentity()));
              } else if (property.getPropertyType().equals(PropertyType.TENANT_ID)) {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        makeVariableDataType(property),
                        makeVariableName(property),
                        DataPurpose.tenantIdentity()));
              } else {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        makeVariableDataType(property), makeVariableName(property)));
              }
            });

    return parameterRepresentations;
  }
}
