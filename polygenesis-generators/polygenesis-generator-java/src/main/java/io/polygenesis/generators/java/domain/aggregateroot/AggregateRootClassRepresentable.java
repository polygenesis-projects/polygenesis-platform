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
import io.polygenesis.core.data.IoModelGroup;
import io.polygenesis.core.data.PackageName;
import io.polygenesis.models.domain.AbstractProperty;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.Primitive;
import io.polygenesis.models.domain.PrimitiveCollection;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.commons.ParameterRepresentation;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Aggregate root class representable.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootClassRepresentable extends AbstractClassRepresentable<AggregateRoot> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root class representable.
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

    // TODO: refactor the following implementation
    source
        .getProperties()
        .forEach(
            property -> {
              Optional<IoModelGroup> optionalIoModelGroup = property.getIoModelGroupAsOptional();
              if (optionalIoModelGroup.isPresent()) {
                if (!property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID)) {
                  IoModelGroup ioModelGroup = optionalIoModelGroup.get();

                  fieldRepresentations.add(
                      new FieldRepresentation(
                          fromDataTypeToJavaConverter.getDeclaredVariableType(
                              ioModelGroup.getDataType()),
                          ioModelGroup.getVariableName().getText(),
                          makeAnnotationsForValueObject(ioModelGroup)));
                }
              } else {
                if (property instanceof Primitive) {
                  Primitive primitive = (Primitive) property;
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          fromDataTypeToJavaConverter.getDeclaredVariableType(
                              primitive.getIoModelPrimitive().getDataType()),
                          primitive.getVariableName().getText()));
                } else if (property instanceof PrimitiveCollection) {
                  // TODO
                  fieldRepresentations.add(
                      new FieldRepresentation(
                          "List<String>", property.getVariableName().getText()));

                } else {
                  throw new IllegalStateException(
                      String.format(
                          "Cannot project variable=%s", property.getVariableName().getText()));
                }
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
        .forEach(
            property -> {
              Optional<IoModelGroup> optionalIoModelGroup = property.getIoModelGroupAsOptional();
              if (optionalIoModelGroup.isPresent()) {
                imports.add("javax.persistence.Embedded");
                imports.add("javax.persistence.AttributeOverride");
                imports.add("javax.persistence.Column");

                IoModelGroup ioModelGroup = optionalIoModelGroup.get();
                if (!ioModelGroup.getPackageName().equals(source.getPackageName())) {
                  imports.add(
                      ioModelGroup.getPackageName().getText()
                          + "."
                          + TextConverter.toUpperCamel(ioModelGroup.getDataType()));
                }
              }
            });

    // Additional imports
    imports.add(rootPackageName.getText() + ".Constants");
    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("com.oregor.ddd4j.core.AggregateRoot");
    imports.add("javax.persistence.Entity");
    imports.add("javax.persistence.Table");

    return imports;
  }

  @Override
  public Set<String> annotations(AggregateRoot source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Entity");
    annotations.add(
        String.format(
            "@Table(name = Constants.DEFAULT_TABLE_PREFIX + \"%s\")",
            TextConverter.toLowerHyphen(source.getName().getText())));

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
    return MODIFIER_PUBLIC;
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
    stringBuilder.append(" extends ");
    stringBuilder.append("AggregateRoot<");
    stringBuilder.append(TextConverter.toUpperCamel(source.getName().getText()));
    stringBuilder.append("Id");
    stringBuilder.append(">");

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
                        fromDataTypeToJavaConverter.getDeclaredVariableType(
                            property.getAsKeyValue().getKey().toString()),
                        property.getAsKeyValue().getValue().toString(),
                        true));
              } else {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        fromDataTypeToJavaConverter.getDeclaredVariableType(
                            property.getAsKeyValue().getKey().toString()),
                        property.getAsKeyValue().getValue().toString()));
              }
            });

    return parameterRepresentations;
  }

  /**
   * Make annotations for value object set.
   *
   * @param ioModelGroup the io model group
   * @return the set
   */
  private Set<String> makeAnnotationsForValueObject(IoModelGroup ioModelGroup) {
    Set<String> annotations = new LinkedHashSet<>();
    annotations.add("@Embedded");

    ioModelGroup
        .getModels()
        .forEach(
            model -> {
              if (model.isPrimitive()) {
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("@AttributeOverride(\n");

                stringBuilder.append(
                    String.format(
                        "\t\t\tname = \"%s\",\n",
                        TextConverter.toLowerCamel(model.getVariableName().getText())));

                stringBuilder.append(
                    String.format(
                        "\t\t\tcolumn = @Column(name = \"%s%s\"))",
                        TextConverter.toLowerCamel(ioModelGroup.getVariableName().getText()),
                        TextConverter.toUpperCamel(model.getVariableName().getText())));

                annotations.add(stringBuilder.toString());
              } else {
                throw new UnsupportedOperationException();
              }
            });

    return annotations;
  }
}
