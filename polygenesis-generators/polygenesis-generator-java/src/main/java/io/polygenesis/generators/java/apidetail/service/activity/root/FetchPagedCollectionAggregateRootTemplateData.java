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

package io.polygenesis.generators.java.apidetail.service.activity.root;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.DtoType;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Fetch paged collection aggregate root template data.
 *
 * @author Christos Tsakostas
 */
public class FetchPagedCollectionAggregateRootTemplateData {

  private String aggregateRootIdDataType;

  private Dto responseDto;

  private Set<ParameterRepresentation> parameterRepresentations;
  private String aggregateRootDataType;
  private String aggregateRootVariable;

  @SuppressWarnings("rawtypes")
  private Set<DomainObjectProperty> properties;

  private String persistenceVariable;
  private Dto requestDto;
  private String converterVariable;
  private Boolean multiTenant;
  private String returnValue;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Fetch paged collection aggregate root template data. */
  public FetchPagedCollectionAggregateRootTemplateData() {
    aggregateRootIdDataType = "aggregateRootIdDataType";

    parameterRepresentations = new LinkedHashSet<>();
    aggregateRootDataType = "aggregateRootDataType";
    aggregateRootVariable = "aggregateRootVariable";
    properties = new LinkedHashSet<>();
    persistenceVariable = "persistenceVariable";

    requestDto =
        new Dto(
            DtoType.API_REQUEST,
            new DataObject(new ObjectName("requestDto"), new PackageName("com.oregor")),
            false);

    responseDto =
        new Dto(
            DtoType.API_REQUEST,
            new DataObject(new ObjectName("responseDto"), new PackageName("com.oregor")),
            false);

    converterVariable = "converterVariable";
    multiTenant = false;
    returnValue = "returnValue";
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets aggregate root id data type.
   *
   * @return the aggregate root id data type
   */
  public String getAggregateRootIdDataType() {
    return aggregateRootIdDataType;
  }

  /**
   * Gets response dto.
   *
   * @return the response dto
   */
  public Dto getResponseDto() {
    return responseDto;
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
  @SuppressWarnings("rawtypes")
  public Set<DomainObjectProperty> getProperties() {
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

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets aggregate root id data type.
   *
   * @param aggregateRootIdDataType the aggregate root id data type
   */
  public void setAggregateRootIdDataType(String aggregateRootIdDataType) {
    this.aggregateRootIdDataType = aggregateRootIdDataType;
  }

  /**
   * Sets response dto.
   *
   * @param responseDto the response dto
   */
  public void setResponseDto(Dto responseDto) {
    this.responseDto = responseDto;
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
  @SuppressWarnings("rawtypes")
  public void setProperties(Set<DomainObjectProperty> properties) {
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
}
