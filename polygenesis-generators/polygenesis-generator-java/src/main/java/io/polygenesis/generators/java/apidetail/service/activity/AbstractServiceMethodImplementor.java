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

package io.polygenesis.generators.java.apidetail.service.activity;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.BaseDomainEntity;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.Projection;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Abstract service method implementor.
 *
 * @author Christos Tsakostas
 */
@Deprecated
public abstract class AbstractServiceMethodImplementor {

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Aggregate root data model map.
   *
   * @param serviceImplementation the service implementation
   * @param serviceMethod the service method
   * @param methodRepresentation the method representation
   * @return the map
   */
  protected Map<String, Object> aggregateRootDataModel(
      ServiceImplementation serviceImplementation,
      ServiceMethod serviceMethod,
      MethodRepresentation methodRepresentation) {
    return defaultDataModel(serviceImplementation, serviceMethod, methodRepresentation);
  }

  /**
   * Aggregate root data model with thing identity map.
   *
   * @param serviceImplementation the service implementation
   * @param serviceMethod the service method
   * @param methodRepresentation the method representation
   * @return the map
   */
  protected Map<String, Object> aggregateRootDataModelWithThingIdentity(
      ServiceImplementation serviceImplementation,
      ServiceMethod serviceMethod,
      MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel =
        aggregateRootDataModel(serviceImplementation, serviceMethod, methodRepresentation);

    dataModel.put(
        "thingIdentity",
        serviceMethod
            .getRequestDto()
            .getThingIdentityAsOptional()
            .orElseThrow(IllegalArgumentException::new));

    return dataModel;
  }

  /**
   * Aggregate entity data model map.
   *
   * @param serviceImplementation the service implementation
   * @param serviceMethod the service method
   * @param methodRepresentation the method representation
   * @return the map
   */
  protected Map<String, Object> aggregateEntityDataModel(
      ServiceImplementation serviceImplementation,
      ServiceMethod serviceMethod,
      MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel =
        aggregateRootDataModel(serviceImplementation, serviceMethod, methodRepresentation);

    dataModel.put(
        "parentThingIdentity",
        serviceMethod
            .getRequestDto()
            .getParentThingIdentityAsOptional()
            .orElseThrow(IllegalArgumentException::new));

    return dataModel;
  }

  /**
   * Aggregate entity data model with thing identity map.
   *
   * @param serviceImplementation the service implementation
   * @param serviceMethod the service method
   * @param methodRepresentation the method representation
   * @return the map
   */
  protected Map<String, Object> aggregateEntityDataModelWithThingIdentity(
      ServiceImplementation serviceImplementation,
      ServiceMethod serviceMethod,
      MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel =
        aggregateEntityDataModel(serviceImplementation, serviceMethod, methodRepresentation);

    dataModel.put(
        "thingIdentity",
        serviceMethod
            .getRequestDto()
            .getThingIdentityAsOptional()
            .orElseThrow(IllegalArgumentException::new));

    return dataModel;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Default data model map.
   *
   * @param serviceImplementation the service implementation
   * @param serviceMethod the service method
   * @param methodRepresentation the method representation
   * @return the map
   */
  private Map<String, Object> defaultDataModel(
      ServiceImplementation serviceImplementation,
      ServiceMethod serviceMethod,
      MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = new HashMap<>();

    dataModel.put("representation", methodRepresentation);

    try {
      AggregateRootPersistable aggregateRoot = retrieveAggregateRoot(serviceImplementation);

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

    } catch (Exception e) {

      Projection projection = retrieveProjection(serviceImplementation);

      dataModel.put(
          "aggregateRootDataType",
          TextConverter.toUpperCamel(projection.getObjectName().getText()));
      dataModel.put(
          "aggregateRootVariable",
          TextConverter.toLowerCamel(projection.getObjectName().getText()));
      dataModel.put(
          "persistenceVariable",
          TextConverter.toLowerCamel(projection.getPersistence().getObjectName().getText()));
      dataModel.put("properties", propertiesOfConstructorFor(projection));
      dataModel.put("requestDto", serviceMethod.getRequestDto());

      dataModel.put("multiTenant", projection.getMultiTenant());

      //      dataModel.put(
      //          "aggregateRootIdDataType",
      //          TextConverter.toUpperCamel(projection.aggregateRootId().getData().getDataType()));

      dataModel.put(
          "aggregateRootIdDataType",
          TextConverter.toUpperCamel(projection.getObjectName().getText() + "Id"));

      dataModel.put(
          "converterVariable",
          serviceImplementation.domainObjectConverter().getVariableName().getText());
    }
    return dataModel;
  }

  /**
   * Aggregate root base domain object.
   *
   * @param serviceImplementation the service implementation
   * @return the base domain object
   */
  // TODO: needs refactoring
  private AggregateRootPersistable retrieveAggregateRoot(
      ServiceImplementation serviceImplementation) {
    if (serviceImplementation.getOptionalParentAggregateRoot().isPresent()) {
      return serviceImplementation
          .getOptionalParentAggregateRoot()
          .orElseThrow(IllegalArgumentException::new);
    }

    if (serviceImplementation.getOptionalDomainEntity().isPresent()) {
      BaseDomainEntity domainEntity =
          serviceImplementation
              .getOptionalDomainEntity()
              .orElseThrow(IllegalArgumentException::new);

      if (domainEntity instanceof AggregateRootPersistable) {
        return (AggregateRootPersistable) domainEntity;
      } else {
        throw new IllegalArgumentException(
            String.format(
                "Domain Object=%s is not of type AggregateRootPersistable",
                domainEntity.getObjectName().getText()));
      }

    } else {
      throw new IllegalArgumentException(
          String.format(
              "No Domain Entity exists for service implementation with name=%s",
              serviceImplementation.getService().getServiceName().getText()));
    }
  }

  private Projection retrieveProjection(ServiceImplementation serviceImplementation) {
    if (serviceImplementation.getOptionalDomainEntity().isPresent()) {
      BaseDomainEntity domainEntity =
          serviceImplementation
              .getOptionalDomainEntity()
              .orElseThrow(IllegalArgumentException::new);

      if (domainEntity instanceof Projection) {
        return (Projection) domainEntity;
      } else {
        throw new IllegalArgumentException(
            String.format(
                "Domain Object=%s is not of type Projection",
                domainEntity.getObjectName().getText()));
      }

    } else {
      throw new IllegalArgumentException(
          String.format(
              "No Domain Entity Projection exists for service implementation with name=%s",
              serviceImplementation.getService().getServiceName().getText()));
    }
  }

  /**
   * Properties of constructor for set.
   *
   * @param domainObject the domain object
   * @return the set
   */
  private Set<DomainObjectProperty<?>> propertiesOfConstructorFor(BaseDomainObject domainObject) {
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
}
