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

package io.polygenesis.generators.java.apidetail.service.activity.root.entity;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.generators.java.common.AggregateEntityData;
import io.polygenesis.generators.java.common.AggregateRootData;
import io.polygenesis.generators.java.common.ParentCallingChildData;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.DtoType;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Abstract aggregate entity template data.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractAggregateEntityTemplateData {

  private AggregateRootData aggregateRootData;
  private AggregateEntityData aggregateEntityData;
  private ParentCallingChildData parentCallingChildData;

  private String aggregateRootIdDataType;
  private Data parentThingIdentity;

  private Set<ParameterRepresentation> parameterRepresentations;
  private String aggregateRootDataType;
  private String aggregateRootVariable;

  private Set<DomainObjectProperty<?>> properties;

  private String persistenceVariable;
  private Dto requestDto;
  private Dto responseDto;
  private String converterVariable;
  private Boolean multiTenant;
  private String returnValue;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Abstract aggregate entity template data. */
  @SuppressWarnings("CPD-START")
  public AbstractAggregateEntityTemplateData() {
    aggregateRootData = null;
    aggregateEntityData = null;
    parentCallingChildData = null;
    aggregateRootIdDataType = "aggregateRootIdDataType";
    parentThingIdentity = DataPrimitive.of(PrimitiveType.STRING, new VariableName("thingIdentity"));

    parameterRepresentations = new LinkedHashSet<>();
    aggregateRootDataType = "aggregateRootDataType";
    aggregateRootVariable = "aggregateRootVariable";
    properties = new LinkedHashSet<>();
    persistenceVariable = "persistenceVariable";
    requestDto =
        new Dto(
            ThingBuilder.app("dummy").createThing(),
            DtoType.API_REQUEST,
            new DataObject(new ObjectName("requestDto"), new PackageName("com.oregor")),
            false);
    converterVariable = "converterVariable";
    multiTenant = false;
    returnValue = "returnValue";
  }

  /**
   * Instantiates a new Abstract aggregate entity template data.
   *
   * @param aggregateRootData the aggregate root data
   * @param aggregateEntityData the aggregate entity data
   * @param parentCallingChildData the parent calling child data
   * @param aggregateRootIdDataType the aggregate root id data type
   * @param parentThingIdentity the parent thing identity
   * @param parameterRepresentations the parameter representations
   * @param aggregateRootDataType the aggregate root data type
   * @param aggregateRootVariable the aggregate root variable
   * @param properties the properties
   * @param persistenceVariable the persistence variable
   * @param requestDto the request dto
   * @param responseDto the response dto
   * @param converterVariable the converter variable
   * @param multiTenant the multi tenant
   * @param returnValue the return value
   */
  public AbstractAggregateEntityTemplateData(
      AggregateRootData aggregateRootData,
      AggregateEntityData aggregateEntityData,
      ParentCallingChildData parentCallingChildData,
      String aggregateRootIdDataType,
      Data parentThingIdentity,
      Set<ParameterRepresentation> parameterRepresentations,
      String aggregateRootDataType,
      String aggregateRootVariable,
      Set<DomainObjectProperty<?>> properties,
      String persistenceVariable,
      Dto requestDto,
      Dto responseDto,
      String converterVariable,
      Boolean multiTenant,
      String returnValue) {
    this.aggregateRootData = aggregateRootData;
    this.aggregateEntityData = aggregateEntityData;
    this.parentCallingChildData = parentCallingChildData;
    this.aggregateRootIdDataType = aggregateRootIdDataType;
    this.parentThingIdentity = parentThingIdentity;
    this.parameterRepresentations = parameterRepresentations;
    this.aggregateRootDataType = aggregateRootDataType;
    this.aggregateRootVariable = aggregateRootVariable;
    this.properties = properties;
    this.persistenceVariable = persistenceVariable;
    this.requestDto = requestDto;
    this.responseDto = responseDto;
    this.converterVariable = converterVariable;
    this.multiTenant = multiTenant;
    this.returnValue = returnValue;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets aggregate entity data.
   *
   * @return the aggregate entity data
   */
  public AggregateEntityData getAggregateEntityData() {
    return aggregateEntityData;
  }

  /**
   * Gets aggregate root data.
   *
   * @return the aggregate root data
   */
  public AggregateRootData getAggregateRootData() {
    return aggregateRootData;
  }

  /**
   * Gets parent calling child data.
   *
   * @return the parent calling child data
   */
  public ParentCallingChildData getParentCallingChildData() {
    return parentCallingChildData;
  }

  /**
   * Gets aggregate root id data type.
   *
   * @return the aggregate root id data type
   */
  public String getAggregateRootIdDataType() {
    return aggregateRootIdDataType;
  }

  /**
   * Gets parent thing identity.
   *
   * @return the parent thing identity
   */
  public Data getParentThingIdentity() {
    return parentThingIdentity;
  }

  /**
   * Gets parameter representations.
   *
   * @return the parameter representations
   */
  public Set<ParameterRepresentation> getParameterRepresentations() {
    return parameterRepresentations;
  }

  /**
   * Gets aggregate root data type.
   *
   * @return the aggregate root data type
   */
  public String getAggregateRootDataType() {
    return aggregateRootDataType;
  }

  /**
   * Gets aggregate root variable.
   *
   * @return the aggregate root variable
   */
  public String getAggregateRootVariable() {
    return aggregateRootVariable;
  }

  /**
   * Gets properties.
   *
   * @return the properties
   */
  public Set<DomainObjectProperty<?>> getProperties() {
    return properties;
  }

  /**
   * Gets persistence variable.
   *
   * @return the persistence variable
   */
  public String getPersistenceVariable() {
    return persistenceVariable;
  }

  /**
   * Gets request dto.
   *
   * @return the request dto
   */
  public Dto getRequestDto() {
    return requestDto;
  }

  /**
   * Gets converter variable.
   *
   * @return the converter variable
   */
  public String getConverterVariable() {
    return converterVariable;
  }

  /**
   * Gets multi tenant.
   *
   * @return the multi tenant
   */
  public Boolean getMultiTenant() {
    return multiTenant;
  }

  /**
   * Gets return value.
   *
   * @return the return value
   */
  public String getReturnValue() {
    return returnValue;
  }

  /**
   * Gets response dto.
   *
   * @return the response dto
   */
  public Dto getResponseDto() {
    return responseDto;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets aggregate root data.
   *
   * @param aggregateRootData the aggregate root data
   */
  public void setAggregateRootData(AggregateRootData aggregateRootData) {
    this.aggregateRootData = aggregateRootData;
  }

  /**
   * Sets aggregate entity data.
   *
   * @param aggregateEntityData the aggregate entity data
   */
  public void setAggregateEntityData(AggregateEntityData aggregateEntityData) {
    this.aggregateEntityData = aggregateEntityData;
  }

  /**
   * Sets aggregate root id data type.
   *
   * @param aggregateRootIdDataType the aggregate root id data type
   */
  public void setAggregateRootIdDataType(String aggregateRootIdDataType) {
    this.aggregateRootIdDataType = aggregateRootIdDataType;
  }

  /**
   * Sets parent thing identity.
   *
   * @param parentThingIdentity the parent thing identity
   */
  public void setParentThingIdentity(Data parentThingIdentity) {
    this.parentThingIdentity = parentThingIdentity;
  }

  /**
   * Sets parameter representations.
   *
   * @param parameterRepresentations the parameter representations
   */
  public void setParameterRepresentations(Set<ParameterRepresentation> parameterRepresentations) {
    this.parameterRepresentations = parameterRepresentations;
  }

  /**
   * Sets aggregate root data type.
   *
   * @param aggregateRootDataType the aggregate root data type
   */
  public void setAggregateRootDataType(String aggregateRootDataType) {
    this.aggregateRootDataType = aggregateRootDataType;
  }

  /**
   * Sets aggregate root variable.
   *
   * @param aggregateRootVariable the aggregate root variable
   */
  public void setAggregateRootVariable(String aggregateRootVariable) {
    this.aggregateRootVariable = aggregateRootVariable;
  }

  /**
   * Sets properties.
   *
   * @param properties the properties
   */
  public void setProperties(Set<DomainObjectProperty<?>> properties) {
    this.properties = properties;
  }

  /**
   * Sets persistence variable.
   *
   * @param persistenceVariable the persistence variable
   */
  public void setPersistenceVariable(String persistenceVariable) {
    this.persistenceVariable = persistenceVariable;
  }

  /**
   * Sets request dto.
   *
   * @param requestDto the request dto
   */
  public void setRequestDto(Dto requestDto) {
    this.requestDto = requestDto;
  }

  /**
   * Sets converter variable.
   *
   * @param converterVariable the converter variable
   */
  public void setConverterVariable(String converterVariable) {
    this.converterVariable = converterVariable;
  }

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   */
  public void setMultiTenant(Boolean multiTenant) {
    this.multiTenant = multiTenant;
  }

  /**
   * Sets return value.
   *
   * @param returnValue the return value
   */
  public void setReturnValue(String returnValue) {
    this.returnValue = returnValue;
  }

  /**
   * Sets response dto.
   *
   * @param responseDto the response dto
   */
  @SuppressWarnings("CPD-END")
  public void setResponseDto(Dto responseDto) {
    this.responseDto = responseDto;
  }
}
