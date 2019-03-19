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

package io.polygenesis.implementations.java.apiimpl;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Argument;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Abstract service method implementor.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractServiceMethodImplementor {

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Data model for create or modify map.
   *
   * @param serviceImplementation the service implementation
   * @param serviceMethod the service method
   * @param methodRepresentation the method representation
   * @return the map
   */
  protected Map<String, Object> dataModelForCreateOrModify(
      ServiceImplementation serviceImplementation,
      ServiceMethod serviceMethod,
      MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = new HashMap<>();

    dataModel.put("representation", methodRepresentation);

    AggregateRootPersistable aggregateRoot = aggregateRoot(serviceImplementation);

    dataModel.put(
        "aggregateRootDataType",
        TextConverter.toUpperCamel(aggregateRoot.getObjectName().getText()));
    dataModel.put(
        "aggregateRootVariable",
        TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText()));
    dataModel.put(
        "persistenceVariable",
        TextConverter.toLowerCamel(aggregateRoot.getPersistence().getObjectName().getText()));
    dataModel.put("properties", propertiesOfConstructorFor(aggregateRoot));
    dataModel.put("requestDto", serviceMethod.getRequestDto());

    dataModel.put("multiTenant", aggregateRoot.getMultiTenant());

    dataModel.put(
        "aggregateRootIdDataType",
        TextConverter.toUpperCamel(aggregateRoot.aggregateRootId().getData().getDataType()));

    dataModel.put(
        "converterVariable",
        serviceImplementation.domainObjectConverter().getVariableName().getText());

    return dataModel;
  }

  /**
   * Aggregate root base domain object.
   *
   * @param serviceImplementation the service implementation
   * @return the base domain object
   */
  protected AggregateRootPersistable aggregateRoot(ServiceImplementation serviceImplementation) {
    BaseDomainObject<?> domainObject =
        serviceImplementation
            .getAggregateRoots()
            .stream()
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

    // TODO
    if (domainObject instanceof AggregateRootPersistable) {
      return (AggregateRootPersistable) domainObject;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Domain Object=%s is not of type AggregateRootPersistable",
              domainObject.getObjectName().getText()));
    }
  }

  /**
   * Properties of constructor for set.
   *
   * @param domainObject the domain object
   * @return the set
   */
  protected Set<DomainObjectProperty> propertiesOfConstructorFor(BaseDomainObject<?> domainObject) {
    return domainObject
        .getConstructors()
        .stream()
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format(
                        "Cannot get constructor for %s", domainObject.getObjectName().getText())))
        .getProperties();
  }

  /**
   * Thing identity data primitive.
   *
   * @param serviceMethod the service method
   * @return the data primitive
   */
  protected DataPrimitive thingIdentity(ServiceMethod serviceMethod) {
    Argument argument =
        serviceMethod
            .getFunction()
            .getArguments()
            .stream()
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

    return serviceMethod
        .getFunction()
        .retrieveThingIdentityFromArgument(argument)
        .orElseThrow(
            () ->
                new IllegalStateException(
                    String.format(
                        "No thingIdentity found in arguments of method=%s",
                        serviceMethod.getFunction().getName().getText())));
  }
}
