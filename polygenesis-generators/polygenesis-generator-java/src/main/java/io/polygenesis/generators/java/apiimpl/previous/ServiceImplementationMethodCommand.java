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

package io.polygenesis.generators.java.apiimpl.previous;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Argument;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.apiimpl.ValueObjectFromDto;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainObjectProperty;
import java.util.stream.Collectors;

/**
 * The type Service implementation method command.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationMethodCommand extends ServiceImplementationMethodShared {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation method command.
   *
   * @param freemarkerService the freemarker service
   */
  public ServiceImplementationMethodCommand(FreemarkerService freemarkerService) {
    super(freemarkerService);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Make command implementation string.
   *
   * @param serviceMethod the method
   * @param domainObject the aggregate root
   * @param domainObjectConverter the aggregate root converter
   * @return the string
   */
  public String makeCommandImplementation(
      ServiceMethod serviceMethod,
      BaseDomainObject<?> domainObject,
      DomainObjectConverter domainObjectConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    if (serviceMethod.getFunction().getGoal().isCreate()) {
      stringBuilder.append(
          makeCreateAggregateRoot(serviceMethod, domainObject, domainObjectConverter));
      stringBuilder.append("\n");
    }

    if (serviceMethod.getFunction().getGoal().isModify()) {
      stringBuilder.append(restoreAggregateRoot(serviceMethod, domainObject));
      stringBuilder.append("\n");
    }

    if (domainObject instanceof AggregateRootPersistable) {
      stringBuilder.append(makePersistAggregateRoot((AggregateRootPersistable) domainObject));
      stringBuilder.append("\n");
    }

    String returnValue = makeReturnValue(serviceMethod);
    if (!returnValue.equals("")) {
      stringBuilder.append(returnValue);
    }

    return stringBuilder.toString();
  }

  /**
   * Make crate aggregate root string.
   *
   * @param serviceMethod the method
   * @param aggregateRoot the aggregate root
   * @param domainObjectConverter the aggregate root converter
   * @return the string
   */
  protected String makeCreateAggregateRoot(
      ServiceMethod serviceMethod,
      BaseDomainObject<?> aggregateRoot,
      DomainObjectConverter domainObjectConverter) {
    if (aggregateRoot.getConstructors().size() > 1) {
      throw new IllegalStateException(
          String.format(
              "More than one Constructors for AggregateRoot=%s",
              aggregateRoot.getObjectName().getText()));
    }

    if (serviceMethod.getFunction().getArguments().size() > 1) {
      throw new IllegalStateException(
          String.format(
              "Currently, only one Argument is supported for Services. AggregateRoot=%s",
              aggregateRoot.getObjectName().getText()));
    }

    Constructor constructor =
        aggregateRoot
            .getConstructors()
            .stream()
            .findFirst()
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        String.format(
                            "Cannot get constructor for %s",
                            aggregateRoot.getObjectName().getText())));

    Argument argument =
        serviceMethod
            .getFunction()
            .getArguments()
            .stream()
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append(TextConverter.toUpperCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append(" ");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append(" = new ");
    stringBuilder.append(TextConverter.toUpperCamel(aggregateRoot.getObjectName().getText()));
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
                      fillProperty(aggregateRoot, property, argument, domainObjectConverter));
                })
            .collect(Collectors.joining(",\n")));

    stringBuilder.append("\n");
    stringBuilder.append("\t\t");
    stringBuilder.append(");");
    stringBuilder.append("\n");

    return stringBuilder.toString();
  }

  private String fillProperty(
      BaseDomainObject<?> domainObject,
      DomainObjectProperty property,
      Argument argument,
      DomainObjectConverter domainObjectConverter) {

    switch (property.getPropertyType()) {
      case AGGREGATE_ROOT_ID:
        return fillAggregateRootId(domainObject);
      case PRIMITIVE:
        return fillPrimitiveProperty(property, argument);
      case PRIMITIVE_COLLECTION:
        return fillPrimitiveCollectionProperty(property, argument);
      case VALUE_OBJECT:
        return fillValueObjectProperty(
            argument, findValueObjectFromDto(property, domainObjectConverter));
      default:
        throw new IllegalStateException();
    }
  }

  private String fillAggregateRootId(BaseDomainObject<?> aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append("Persistence");
    stringBuilder.append(".nextId()");

    return stringBuilder.toString();
  }

  private ValueObjectFromDto findValueObjectFromDto(
      DomainObjectProperty property, DomainObjectConverter domainObjectConverter) {

    return domainObjectConverter
        .getValueObjectFromDtos()
        .stream()
        .filter(valueObjectFromDto -> valueObjectFromDto.getValueObject().equals(property))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  private String fillPrimitiveCollectionProperty(DomainObjectProperty property, Argument argument) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toLowerCamel(argument.getData().getVariableName().getText()));
    stringBuilder.append(".");
    stringBuilder.append("get");
    stringBuilder.append(
        TextConverter.toUpperCamel(property.getData().getVariableName().getText()));
    stringBuilder.append("()");

    return stringBuilder.toString();
  }

  private String fillPrimitiveProperty(DomainObjectProperty property, Argument argument) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toLowerCamel(argument.getData().getVariableName().getText()));
    stringBuilder.append(".");
    stringBuilder.append("get");
    stringBuilder.append(
        TextConverter.toUpperCamel(property.getData().getVariableName().getText()));
    stringBuilder.append("()");

    return stringBuilder.toString();
  }

  private String fillValueObjectProperty(Argument argument, ValueObjectFromDto valueObjectFromDto) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("businessConverter.convert(");

    stringBuilder.append(
        TextConverter.toLowerCamel(argument.getData().getVariableName().getText()));

    stringBuilder.append(".");
    stringBuilder.append("get");
    stringBuilder.append(
        TextConverter.toUpperCamel(valueObjectFromDto.getDto().getDataGroup().getDataType()));
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
  protected String makePersistAggregateRoot(AggregateRootPersistable aggregateRoot) {
    //    String str =
    //        freemarkerService.exportToString(
    //            Stream.of(
    //                    new String[][] {
    //                      {
    //                        "persistenceName",
    //                        TextConverter.toLowerCamel(
    //                            aggregateRoot.getPersistence().getObjectName().getText())
    //                      },
    //                      {
    //                        "variableName",
    //                        TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText())
    //                      },
    //                    })
    //                .collect(Collectors.toMap(data -> data[0], data -> data[1])),
    //            "polygenesis-implementation-java-apiimpl/macro-store-aggregate-root.ftl");

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append("Persistence");
    stringBuilder.append(".store(");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append(");");
    stringBuilder.append("\n");

    return stringBuilder.toString();
  }
}
