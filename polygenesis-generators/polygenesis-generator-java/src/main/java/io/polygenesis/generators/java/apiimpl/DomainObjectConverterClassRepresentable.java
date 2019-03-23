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
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingBuilder;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.domain.BaseDomainObject;
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
public class DomainObjectConverterClassRepresentable
    extends AbstractClassRepresentable<DomainObjectConverter> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final DomainObjectConverterMethodRepresentable domainObjectConverterMethodRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param domainObjectConverterMethodRepresentable the aggregate root converter method
   *     representable
   */
  public DomainObjectConverterClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      DomainObjectConverterMethodRepresentable domainObjectConverterMethodRepresentable) {
    super(fromDataTypeToJavaConverter);
    this.domainObjectConverterMethodRepresentable = domainObjectConverterMethodRepresentable;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      DomainObjectConverter source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainObjectConverter source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      DomainObjectConverter source, Object... args) {
    Set<Function> functions = new LinkedHashSet<>();

    defineFunctionsForValueObjectFromDto(functions, source);
    defineFunctionsForFetchOneDtoFromAggregateRoot(functions, source);
    defineFunctionsForFetchCollectionDtoFromAggregateRoot(functions, source);

    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();
    functions.forEach(
        function ->
            methodRepresentations.add(domainObjectConverterMethodRepresentable.create(function)));

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainObjectConverter source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainObjectConverter source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("org.springframework.stereotype.Component");

    source
        .getValueObjectFromDtos()
        .forEach(
            valueObjectFromDto -> {
              if (!valueObjectFromDto
                  .getValueObject()
                  .getData()
                  .getPackageName()
                  .equals(source.getPackageName())) {

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(
                    valueObjectFromDto.getValueObject().getData().getPackageName().getText());
                stringBuilder.append(".");
                stringBuilder.append(
                    TextConverter.toUpperCamel(
                        valueObjectFromDto.getValueObject().getData().getVariableName().getText()));

                imports.add(stringBuilder.toString());
              }

              if (!valueObjectFromDto
                  .getDto()
                  .getDataGroup()
                  .getPackageName()
                  .equals(source.getPackageName())) {

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(
                    valueObjectFromDto.getDto().getDataGroup().getPackageName().getText());
                stringBuilder.append(".");
                stringBuilder.append(
                    TextConverter.toUpperCamel(
                        valueObjectFromDto.getDto().getDataGroup().getVariableName().getText()));

                imports.add(stringBuilder.toString());
              }
            });

    return imports;
  }

  @Override
  public Set<String> annotations(DomainObjectConverter source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Component");

    return annotations;
  }

  @Override
  public String description(DomainObjectConverter source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(DomainObjectConverter source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(DomainObjectConverter source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(DomainObjectConverter source, Object... args) {
    return simpleObjectName(source);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Define functions for value object from dto.
   *
   * @param functions the functions
   * @param domainObjectConverter the aggregate root converter
   */
  private void defineFunctionsForValueObjectFromDto(
      Set<Function> functions, DomainObjectConverter domainObjectConverter) {
    domainObjectConverter
        .getValueObjectFromDtos()
        .forEach(
            valueObjectFromDto -> {
              Thing thing =
                  ThingBuilder.generic().setThingName(new ThingName("Converter")).createThing();
              Goal goal = new Goal("CONVERT");

              Function function =
                  new Function(
                      thing,
                      goal,
                      new FunctionName("convertToVo"),
                      new LinkedHashSet<>(
                          Arrays.asList(new Argument(valueObjectFromDto.getDto().getDataGroup()))),
                      new ReturnValue(valueObjectFromDto.getValueObject().getData()));

              functions.add(function);
            });
  }

  /**
   * Define functions for fetch one dto from aggregate root.
   *
   * @param functions the functions
   * @param domainObjectConverter the aggregate root converter
   */
  private void defineFunctionsForFetchOneDtoFromAggregateRoot(
      Set<Function> functions, DomainObjectConverter domainObjectConverter) {
    domainObjectConverter
        .getFetchOneDtoFromAggregateRoots()
        .forEach(
            fetchOneDtoFromAggregateRoot -> {
              functions.add(
                  makeFunction(
                      new LinkedHashSet<>(
                          Arrays.asList(
                              new Argument(
                                  transformDomainObjectToDataGroup(
                                      fetchOneDtoFromAggregateRoot.getDomainObject())))),
                      new ReturnValue(fetchOneDtoFromAggregateRoot.getDto().getDataGroup())));
            });
  }

  /**
   * Define functions for fetch collection dto from aggregate root.
   *
   * @param functions the functions
   * @param domainObjectConverter the aggregate root converter
   */
  private void defineFunctionsForFetchCollectionDtoFromAggregateRoot(
      Set<Function> functions, DomainObjectConverter domainObjectConverter) {
    domainObjectConverter
        .getFetchCollectionDtoFromAggregateRoots()
        .forEach(
            fetchCollectionDtoFromAggregateRoot -> {
              functions.add(
                  makeFunction(
                      new LinkedHashSet<>(
                          Arrays.asList(
                              new Argument(
                                  transformDomainObjectToDataGroup(
                                      fetchCollectionDtoFromAggregateRoot.getDomainObject())))),
                      new ReturnValue(
                          fetchCollectionDtoFromAggregateRoot.getDto().getDataGroup())));
            });
  }

  /**
   * Transform domain object to data group data group.
   *
   * @param domainObject the domain object
   * @return the data group
   */
  private DataGroup transformDomainObjectToDataGroup(BaseDomainObject<?> domainObject) {

    DataGroup dataGroup =
        new DataGroup(
            new ObjectName(domainObject.getObjectName().getText()), domainObject.getPackageName());

    domainObject.getProperties().forEach(property -> dataGroup.addData(property.getData()));

    return dataGroup;
  }

  /**
   * Make function function.
   *
   * @param arguments the arguments
   * @param returnValue the return value
   * @return the function
   */
  private Function makeFunction(Set<Argument> arguments, ReturnValue returnValue) {

    Thing thing = ThingBuilder.generic().setThingName(new ThingName("Converter")).createThing();
    Goal goal = new Goal("CONVERT");

    return new Function(
        thing,
        goal,
        new FunctionName(
            String.format(
                "convertTo%s", TextConverter.toUpperCamel(returnValue.getData().getDataType()))),
        arguments,
        returnValue);
  }
}
