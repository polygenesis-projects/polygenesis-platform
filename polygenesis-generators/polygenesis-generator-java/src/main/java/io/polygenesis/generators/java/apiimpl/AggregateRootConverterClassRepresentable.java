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

package io.polygenesis.generators.java.apiimpl;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Aggregate root converter class representable.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootConverterClassRepresentable
    extends AbstractClassRepresentable<AggregateRootConverter> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateRootConverterMethodRepresentable aggregateRootConverterMethodRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param aggregateRootConverterMethodRepresentable the aggregate root converter method
   *     representable
   */
  public AggregateRootConverterClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      AggregateRootConverterMethodRepresentable aggregateRootConverterMethodRepresentable) {
    super(fromDataTypeToJavaConverter);
    this.aggregateRootConverterMethodRepresentable = aggregateRootConverterMethodRepresentable;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      AggregateRootConverter source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      AggregateRootConverter source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      AggregateRootConverter source, Object... args) {
    Set<Function> functions = new LinkedHashSet<>();

    defineFunctionsForValueObjectFromDto(functions, source);
    defineFunctionsForFetchOneDtoFromAggregateRoot(functions, source);
    defineFunctionsForFetchCollectionDtoFromAggregateRoot(functions, source);

    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();
    functions.forEach(
        function ->
            methodRepresentations.add(aggregateRootConverterMethodRepresentable.create(function)));

    return methodRepresentations;
  }

  @Override
  public String packageName(AggregateRootConverter source, Object... args) {
    return source.getDataType().getOptionalPackageName().get().getText();
  }

  @Override
  public Set<String> imports(AggregateRootConverter source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("org.springframework.stereotype.Component");

    source
        .getValueObjectFromDtos()
        .forEach(
            valueObjectFromDto -> {
              if (!valueObjectFromDto
                  .getValueObject()
                  .getIoModelGroup()
                  .getPackageName()
                  .equals(source.getDataType().getOptionalPackageName().get())) {

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(
                    valueObjectFromDto
                        .getValueObject()
                        .getIoModelGroup()
                        .getPackageName()
                        .getText());
                stringBuilder.append(".");
                stringBuilder.append(
                    TextConverter.toUpperCamel(
                        valueObjectFromDto.getValueObject().getVariableName().getText()));

                imports.add(stringBuilder.toString());
              }

              if (!valueObjectFromDto
                  .getDto()
                  .getOriginatingIoModelGroup()
                  .getPackageName()
                  .equals(source.getDataType().getOptionalPackageName().get())) {

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(
                    valueObjectFromDto
                        .getDto()
                        .getOriginatingIoModelGroup()
                        .getPackageName()
                        .getText());
                stringBuilder.append(".");
                stringBuilder.append(
                    TextConverter.toUpperCamel(
                        valueObjectFromDto
                            .getDto()
                            .getOriginatingIoModelGroup()
                            .getVariableName()
                            .getText()));

                imports.add(stringBuilder.toString());
              }
            });

    return imports;
  }

  @Override
  public Set<String> annotations(AggregateRootConverter source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Component");

    return annotations;
  }

  @Override
  public String description(AggregateRootConverter source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(source.getDataType().getDataTypeName().getText()));

    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(AggregateRootConverter source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(AggregateRootConverter source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamel(source.getDataType().getDataTypeName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(AggregateRootConverter source, Object... args) {
    return simpleObjectName(source);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Define functions for value object from dto.
   *
   * @param functions the functions
   * @param aggregateRootConverter the aggregate root converter
   */
  private void defineFunctionsForValueObjectFromDto(
      Set<Function> functions, AggregateRootConverter aggregateRootConverter) {
    aggregateRootConverter
        .getValueObjectFromDtos()
        .forEach(
            valueObjectFromDto -> {
              Thing thing = new Thing(new ThingName("Converter"));
              Goal goal = new Goal("CONVERT");

              Function function =
                  new Function(
                      thing,
                      goal,
                      new FunctionName("convert"),
                      new LinkedHashSet<>(
                          Arrays.asList(
                              new Argument(
                                  valueObjectFromDto.getDto().getOriginatingIoModelGroup()))),
                      new ReturnValue(valueObjectFromDto.getValueObject().getIoModelGroup()));

              functions.add(function);
            });
  }

  /**
   * Define functions for fetch one dto from aggregate root.
   *
   * @param functions the functions
   * @param aggregateRootConverter the aggregate root converter
   */
  private void defineFunctionsForFetchOneDtoFromAggregateRoot(
      Set<Function> functions, AggregateRootConverter aggregateRootConverter) {
    aggregateRootConverter
        .getFetchOneDtoFromAggregateRoots()
        .forEach(
            fetchOneDtoFromAggregateRoot -> {
              functions.add(
                  makeFunction(
                      new LinkedHashSet<>(
                          Arrays.asList(
                              new Argument(
                                  transformAggregateRootToIoModelGroup(
                                      fetchOneDtoFromAggregateRoot.getAggregateRoot())))),
                      new ReturnValue(
                          fetchOneDtoFromAggregateRoot.getDto().getOriginatingIoModelGroup())));
            });
  }

  /**
   * Define functions for fetch collection dto from aggregate root.
   *
   * @param functions the functions
   * @param aggregateRootConverter the aggregate root converter
   */
  private void defineFunctionsForFetchCollectionDtoFromAggregateRoot(
      Set<Function> functions, AggregateRootConverter aggregateRootConverter) {
    aggregateRootConverter
        .getFetchCollectionDtoFromAggregateRoots()
        .forEach(
            fetchCollectionDtoFromAggregateRoot -> {
              functions.add(
                  makeFunction(
                      new LinkedHashSet<>(
                          Arrays.asList(
                              new Argument(
                                  transformAggregateRootToIoModelGroup(
                                      fetchCollectionDtoFromAggregateRoot.getAggregateRoot())))),
                      new ReturnValue(
                          fetchCollectionDtoFromAggregateRoot
                              .getDto()
                              .getOriginatingIoModelGroup())));
            });
  }

  /**
   * Transform aggregate root to io model group io model group.
   *
   * @param aggregateRoot the aggregate root
   * @return the io model group
   */
  private IoModelGroup transformAggregateRootToIoModelGroup(AggregateRoot aggregateRoot) {

    IoModelGroup ioModelGroup =
        new IoModelGroup(
            new ObjectName(aggregateRoot.getName().getText()), aggregateRoot.getPackageName());

    aggregateRoot
        .getProperties()
        .forEach(property -> ioModelGroup.addIoModel(property.getIoModel()));

    return ioModelGroup;
  }

  /**
   * Make function function.
   *
   * @param arguments the arguments
   * @param returnValue the return value
   * @return the function
   */
  private Function makeFunction(Set<Argument> arguments, ReturnValue returnValue) {

    Thing thing = new Thing(new ThingName("Converter"));
    Goal goal = new Goal("CONVERT");

    return new Function(
        thing,
        goal,
        new FunctionName(
            String.format(
                "convertTo%s", TextConverter.toUpperCamel(returnValue.getModel().getDataType()))),
        arguments,
        returnValue);
  }
}
