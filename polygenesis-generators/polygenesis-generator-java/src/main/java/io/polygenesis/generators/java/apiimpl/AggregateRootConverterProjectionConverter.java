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

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.generators.java.shared.AbstractServiceProjectionMaker;
import io.polygenesis.generators.java.shared.ArgumentProjection;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.FunctionProjection;
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.domain.AggregateRoot;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Aggregate root converter projection converter.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootConverterProjectionConverter extends AbstractServiceProjectionMaker
    implements Converter<AggregateRootConverter, AggregateRootConverterProjection> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private AggregateRootConverterMethodProjectionConverter
      aggregateRootConverterMethodProjectionConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter projection converter.
   *
   * @param aggregateRootConverterMethodProjectionConverter the aggregate root converter method
   *     projection converter
   */
  public AggregateRootConverterProjectionConverter(
      AggregateRootConverterMethodProjectionConverter
          aggregateRootConverterMethodProjectionConverter) {
    super(aggregateRootConverterMethodProjectionConverter);
    this.aggregateRootConverterMethodProjectionConverter =
        aggregateRootConverterMethodProjectionConverter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public AggregateRootConverterProjection convert(
      AggregateRootConverter aggregateRootConverter, Object... args) {
    return new AggregateRootConverterProjection(
        projectPackageName(aggregateRootConverter),
        projectImports(aggregateRootConverter),
        projectDescription(aggregateRootConverter),
        projectObjectName(aggregateRootConverter),
        projectObjectNameWithOptionalExtendsImplements(aggregateRootConverter),
        projectVariables(),
        projectConstructors(),
        fillFunctionProjections(aggregateRootConverter));
  }

  /**
   * Project package name string.
   *
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  protected String projectPackageName(AggregateRootConverter aggregateRootConverter) {
    return aggregateRootConverter.getDataType().getOptionalPackageName().get().getText();
  }

  /**
   * Project imports set.
   *
   * @param aggregateRootConverter the aggregate root converter
   * @return the set
   */
  protected Set<String> projectImports(AggregateRootConverter aggregateRootConverter) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("org.springframework.stereotype.Component");

    aggregateRootConverter
        .getValueObjectFromDtos()
        .forEach(
            valueObjectFromDto -> {
              if (!valueObjectFromDto
                  .getValueObject()
                  .getIoModelGroup()
                  .getClassDataType()
                  .getOptionalPackageName()
                  .get()
                  .equals(aggregateRootConverter.getDataType().getOptionalPackageName().get())) {

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(
                    valueObjectFromDto
                        .getValueObject()
                        .getIoModelGroup()
                        .getClassDataType()
                        .getOptionalPackageName()
                        .get()
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
                  .getClassDataType()
                  .getOptionalPackageName()
                  .get()
                  .equals(aggregateRootConverter.getDataType().getOptionalPackageName().get())) {

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(
                    valueObjectFromDto
                        .getDto()
                        .getOriginatingIoModelGroup()
                        .getClassDataType()
                        .getOptionalPackageName()
                        .get()
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

  /**
   * Project description string.
   *
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  protected String projectDescription(AggregateRootConverter aggregateRootConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(
            aggregateRootConverter.getDataType().getDataTypeName().getText()));

    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  /**
   * Project object name string.
   *
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  protected String projectObjectName(AggregateRootConverter aggregateRootConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamel(
            aggregateRootConverter.getDataType().getDataTypeName().getText()));

    return stringBuilder.toString();
  }

  /**
   * Project object name with optional extends implements string.
   *
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  protected String projectObjectNameWithOptionalExtendsImplements(
      AggregateRootConverter aggregateRootConverter) {
    return projectObjectName(aggregateRootConverter);
  }

  /**
   * Project variables set.
   *
   * @return the set
   */
  protected Set<ArgumentProjection> projectVariables() {
    return new LinkedHashSet<>();
  }

  /**
   * Project constructors set.
   *
   * @return the set
   */
  protected Set<ConstructorProjection> projectConstructors() {
    return new LinkedHashSet<>();
  }

  /**
   * Fill function projections set.
   *
   * @param aggregateRootConverter the aggregate root converter
   * @return the set
   */
  protected Set<FunctionProjection> fillFunctionProjections(
      AggregateRootConverter aggregateRootConverter) {
    Set<Function> functions = new LinkedHashSet<>();

    defineFunctionsForValueObjectFromDto(functions, aggregateRootConverter);
    defineFunctionsForFetchOneDtoFromAggregateRoot(functions, aggregateRootConverter);
    defineFunctionsForFetchCollectionDtoFromAggregateRoot(functions, aggregateRootConverter);

    Set<FunctionProjection> functionProjections = new LinkedHashSet<>();
    functions.forEach(
        function ->
            functionProjections.add(
                aggregateRootConverterMethodProjectionConverter.convert(function)));

    return functionProjections;
  }

  /**
   * Define functions for value object from dto.
   *
   * @param functions the functions
   * @param aggregateRootConverter the aggregate root converter
   */
  protected void defineFunctionsForValueObjectFromDto(
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

  protected void defineFunctionsForFetchOneDtoFromAggregateRoot(
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

  protected void defineFunctionsForFetchCollectionDtoFromAggregateRoot(
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

  private IoModelGroup transformAggregateRootToIoModelGroup(AggregateRoot aggregateRoot) {

    IoModelGroup ioModelGroup =
        new IoModelGroup(
            new ClassDataType(
                new DataTypeName(aggregateRoot.getName().getText()),
                aggregateRoot.getPackageName()));

    aggregateRoot
        .getProperties()
        .forEach(property -> ioModelGroup.addIoModel(property.getIoModel()));

    return ioModelGroup;
  }

  private Function makeFunction(Set<Argument> arguments, ReturnValue returnValue) {

    Thing thing = new Thing(new ThingName("Converter"));
    Goal goal = new Goal("CONVERT");

    return new Function(thing, goal, new FunctionName("convert"), arguments, returnValue);
  }
}
