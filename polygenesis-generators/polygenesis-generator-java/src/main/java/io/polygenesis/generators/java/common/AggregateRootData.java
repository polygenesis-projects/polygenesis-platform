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

package io.polygenesis.generators.java.common;

/**
 * The type Aggregate root data.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String aggregateRootDataType;
  private String aggregateRootVariable;
  private String aggregateRootIdDataType;
  private String aggregateRootIdVariable;
  private String aggregateRootRepositoryVariable;
  private Boolean multiTenant;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root data.
   *
   * @param aggregateRootDataType the aggregate root data type
   * @param aggregateRootVariable the aggregate root variable
   * @param aggregateRootIdDataType the aggregate root id data type
   * @param aggregateRootIdVariable the aggregate root id variable
   * @param aggregateRootRepositoryVariable the aggregate root repository variable
   * @param multiTenant the multi tenant
   */
  public AggregateRootData(
      String aggregateRootDataType,
      String aggregateRootVariable,
      String aggregateRootIdDataType,
      String aggregateRootIdVariable,
      String aggregateRootRepositoryVariable,
      Boolean multiTenant) {
    this.aggregateRootDataType = aggregateRootDataType;
    this.aggregateRootVariable = aggregateRootVariable;
    this.aggregateRootIdDataType = aggregateRootIdDataType;
    this.aggregateRootIdVariable = aggregateRootIdVariable;
    this.aggregateRootRepositoryVariable = aggregateRootRepositoryVariable;
    this.multiTenant = multiTenant;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

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
   * Gets aggregate root id data type.
   *
   * @return the aggregate root id data type
   */
  public String getAggregateRootIdDataType() {
    return aggregateRootIdDataType;
  }

  /**
   * Gets aggregate root id variable.
   *
   * @return the aggregate root id variable
   */
  public String getAggregateRootIdVariable() {
    return aggregateRootIdVariable;
  }

  /**
   * Gets aggregate root repository variable.
   *
   * @return the aggregate root repository variable
   */
  public String getAggregateRootRepositoryVariable() {
    return aggregateRootRepositoryVariable;
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
