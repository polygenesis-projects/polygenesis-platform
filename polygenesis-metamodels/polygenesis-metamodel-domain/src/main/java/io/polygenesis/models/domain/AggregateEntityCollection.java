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

package io.polygenesis.models.domain;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.commons.assertion.Assertion;

/**
 * The type Aggregate entity collection.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityCollection extends BaseProperty<DataArray> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private AggregateEntity aggregateEntity;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity collection.
   *
   * @param data the data
   */
  public AggregateEntityCollection(DataArray data) {
    super(PropertyType.AGGREGATE_ENTITY_COLLECTION, data);
  }

  /**
   * Instantiates a new Aggregate entity collection.
   *
   * @param data the data
   * @param aggregateEntity the aggregate entity
   */
  public AggregateEntityCollection(DataArray data, AggregateEntity aggregateEntity) {
    super(PropertyType.AGGREGATE_ENTITY_COLLECTION, data);
    setAggregateEntity(aggregateEntity);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets aggregate entity.
   *
   * @return the aggregate entity
   */
  public AggregateEntity getAggregateEntity() {
    return aggregateEntity;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets aggregate entity.
   *
   * @param aggregateEntity the aggregate entity
   */
  private void setAggregateEntity(AggregateEntity aggregateEntity) {
    Assertion.isNotNull(aggregateEntity, "aggregateEntity is required");
    this.aggregateEntity = aggregateEntity;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Data getTypeParameterData() {
    return getData().getArrayElement();
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

}
