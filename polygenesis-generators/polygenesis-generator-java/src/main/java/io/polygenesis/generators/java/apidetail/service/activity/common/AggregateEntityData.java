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

package io.polygenesis.generators.java.apidetail.service.activity.common;

import io.polygenesis.models.domain.DomainObjectProperty;
import java.util.Set;

/**
 * The type Aggregate entity data.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String dataType;
  private String variable;
  private String idDataType;
  private String idVariable;
  private String repositoryVariable;
  private String parentMethodName;
  private Set<DomainObjectProperty<?>> properties;
  private Boolean multiTenant;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity data.
   *
   * @param dataType the data type
   * @param variable the variable
   * @param idDataType the id data type
   * @param idVariable the id variable
   * @param repositoryVariable the repository variable
   * @param parentMethodName the parent method name
   * @param properties the properties
   * @param multiTenant the multi tenant
   */
  public AggregateEntityData(
      String dataType,
      String variable,
      String idDataType,
      String idVariable,
      String repositoryVariable,
      String parentMethodName,
      Set<DomainObjectProperty<?>> properties,
      Boolean multiTenant) {
    this.dataType = dataType;
    this.variable = variable;
    this.idDataType = idDataType;
    this.idVariable = idVariable;
    this.repositoryVariable = repositoryVariable;
    this.parentMethodName = parentMethodName;
    this.properties = properties;
    this.multiTenant = multiTenant;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public String getDataType() {
    return dataType;
  }

  /**
   * Gets variable.
   *
   * @return the variable
   */
  public String getVariable() {
    return variable;
  }

  /**
   * Gets id data type.
   *
   * @return the id data type
   */
  public String getIdDataType() {
    return idDataType;
  }

  /**
   * Gets id variable.
   *
   * @return the id variable
   */
  public String getIdVariable() {
    return idVariable;
  }

  /**
   * Gets repository variable.
   *
   * @return the repository variable
   */
  public String getRepositoryVariable() {
    return repositoryVariable;
  }

  /**
   * Gets parent method name.
   *
   * @return the parent method name
   */
  public String getParentMethodName() {
    return parentMethodName;
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
   * Gets multi tenant.
   *
   * @return the multi tenant
   */
  public Boolean getMultiTenant() {
    return multiTenant;
  }
}
