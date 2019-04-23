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

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.data.Data;
import java.util.Objects;

/**
 * The type Base property.
 *
 * @param <D> the type parameter
 * @author Christos Tsakostas
 */
public abstract class BaseProperty<D extends Data> implements DomainObjectProperty<D> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private PropertyType propertyType;
  private D data;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Base property.
   *
   * @param propertyType the property type
   * @param data the data
   */
  public BaseProperty(PropertyType propertyType, D data) {
    setPropertyType(propertyType);
    setData(data);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  @Override
  public PropertyType getPropertyType() {
    return propertyType;
  }

  @Override
  public D getData() {
    return data;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Is aggregate entity boolean.
   *
   * @return the boolean
   */
  public boolean isAggregateEntity() {
    return getPropertyType().equals(PropertyType.AGGREGATE_ENTITY);
  }

  /**
   * Is aggregate entity collection boolean.
   *
   * @return the boolean
   */
  public boolean isAggregateEntityCollection() {
    return getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_COLLECTION);
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets property type.
   *
   * @param propertyType the property type
   */
  private void setPropertyType(PropertyType propertyType) {
    Assertion.isNotNull(propertyType, "propertyType is required");
    this.propertyType = propertyType;
  }

  /**
   * Sets data.
   *
   * @param data the data
   */
  private void setData(D data) {
    Assertion.isNotNull(data, "data is required");
    this.data = data;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseProperty<? extends Data> that = (BaseProperty<? extends Data>) o;
    return propertyType == that.propertyType && Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(propertyType, data);
  }
}
