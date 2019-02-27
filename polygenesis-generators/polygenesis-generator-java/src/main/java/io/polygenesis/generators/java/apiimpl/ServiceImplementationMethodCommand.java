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
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.apiimpl.ValueObjectFromDto;
import io.polygenesis.models.domain.AbstractProperty;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.Constructor;
import java.util.stream.Collectors;

/**
 * The type Service implementation method command.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationMethodCommand extends ServiceImplementationMethodShared {

  /**
   * Make command implementation string.
   *
   * @param function the function
   * @param aggregateRoot the aggregate root
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  public String makeCommandImplementation(
      Function function,
      AggregateRoot aggregateRoot,
      AggregateRootConverter aggregateRootConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    if (function.getGoal().isCreate()) {
      stringBuilder.append(
          makeCreateAggregateRoot(function, aggregateRoot, aggregateRootConverter));
      stringBuilder.append("\n");
    }

    stringBuilder.append(makePersistAggregateRoot(aggregateRoot));
    stringBuilder.append("\n");

    String returnValue = makeReturnValue(function);
    if (!returnValue.equals("")) {
      stringBuilder.append(returnValue);
    }

    return stringBuilder.toString();
  }

  /**
   * Make crate aggregate root string.
   *
   * @param function the function
   * @param aggregateRoot the aggregate root
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  protected String makeCreateAggregateRoot(
      Function function,
      AggregateRoot aggregateRoot,
      AggregateRootConverter aggregateRootConverter) {
    if (aggregateRoot.getConstructors().size() > 1) {
      throw new IllegalStateException(
          String.format(
              "More than one Constructors for AggregateRoot=%s",
              aggregateRoot.getName().getText()));
    }

    if (function.getArguments().size() > 1) {
      throw new IllegalStateException(
          String.format(
              "Currently, only one Argument is supported for Services. AggregateRoot=%s",
              aggregateRoot.getName().getText()));
    }

    Constructor constructor =
        aggregateRoot
            .getConstructors()
            .stream()
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

    Argument argument =
        function.getArguments().stream().findFirst().orElseThrow(IllegalArgumentException::new);

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append(TextConverter.toUpperCamelSpaces(aggregateRoot.getName().getText()));
    stringBuilder.append(" ");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getName().getText()));
    stringBuilder.append(" = new ");
    stringBuilder.append(TextConverter.toUpperCamelSpaces(aggregateRoot.getName().getText()));
    stringBuilder.append("(");
    stringBuilder.append("\n");

    stringBuilder.append(
        constructor
            .getProperties()
            .stream()
            .map(
                property -> {
                  return String.format(
                      "\t\t\t\t%s",
                      fillProperty(aggregateRoot, property, argument, aggregateRootConverter));
                })
            .collect(Collectors.joining(",\n")));

    stringBuilder.append("\n");
    stringBuilder.append("\t\t");
    stringBuilder.append(");");
    stringBuilder.append("\n");

    return stringBuilder.toString();
  }

  private String fillProperty(
      AggregateRoot aggregateRoot,
      AbstractProperty property,
      Argument argument,
      AggregateRootConverter aggregateRootConverter) {

    switch (property.getPropertyType()) {
      case AGGREGATE_ROOT_ID:
        return fillAggregateRootId(aggregateRoot);
      case PRIMITIVE:
        return fillPrimitiveProperty(property, argument);
      case VALUE_OBJECT:
        return fillValueObjectProperty(
            argument, findValueObjectFromDto(property, aggregateRootConverter));
      default:
        throw new IllegalStateException();
    }
  }

  private String fillAggregateRootId(AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getName().getText()));
    stringBuilder.append("Persistence");
    stringBuilder.append(".nextId()");

    return stringBuilder.toString();
  }

  private ValueObjectFromDto findValueObjectFromDto(
      AbstractProperty property, AggregateRootConverter aggregateRootConverter) {

    return aggregateRootConverter
        .getValueObjectFromDtos()
        .stream()
        .filter(valueObjectFromDto -> valueObjectFromDto.getValueObject().equals(property))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  private String fillPrimitiveProperty(AbstractProperty property, Argument argument) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toLowerCamel(argument.getModel().getVariableName().getText()));
    stringBuilder.append(".");
    stringBuilder.append("get");
    stringBuilder.append(TextConverter.toUpperCamel(property.getVariableName().getText()));
    stringBuilder.append("()");

    return stringBuilder.toString();
  }

  private String fillValueObjectProperty(Argument argument, ValueObjectFromDto valueObjectFromDto) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("businessConverter.convert(");

    stringBuilder.append(
        TextConverter.toLowerCamel(argument.getModel().getVariableName().getText()));

    stringBuilder.append(".");
    stringBuilder.append("get");
    stringBuilder.append(
        TextConverter.toUpperCamel(
            valueObjectFromDto
                .getDto()
                .getOriginatingIoModelGroup()
                .getClassDataType()
                .getDataTypeName()
                .getText()));
    stringBuilder.append("()");
    stringBuilder.append(")");

    return stringBuilder.toString();
  }

  /**
   * Make persist aggregate root string.
   *
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String makePersistAggregateRoot(AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getName().getText()));
    stringBuilder.append("Persistence");
    stringBuilder.append(".store(");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getName().getText()));
    stringBuilder.append(");");
    stringBuilder.append("\n");

    return stringBuilder.toString();
  }
}
