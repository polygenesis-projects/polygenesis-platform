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

package io.polygenesis.models.domain;

import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.VariableName;

/**
 * The type Abstract property.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractProperty {

  private PropertyType propertyType;
  private VariableName variableName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract property.
   *
   * @param propertyType the property type
   * @param variableName the variable name
   */
  public AbstractProperty(PropertyType propertyType, VariableName variableName) {
    setPropertyType(propertyType);
    setVariableName(variableName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets property type.
   *
   * @return the property type
   */
  public PropertyType getPropertyType() {
    return propertyType;
  }

  /**
   * Gets variable name.
   *
   * @return the variable name
   */
  public VariableName getVariableName() {
    return variableName;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  // ===============================================================================================
  // ABSTRACT
  // ===============================================================================================

  /**
   * Gets data.
   *
   * @return the data
   */
  public abstract Data getData();

  /**
   * Gets data related to type parameter.
   *
   * <p>Applies only to collections.
   *
   * @return the type parameter data
   */
  public abstract Data getTypeParameterData();

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets property type.
   *
   * @param propertyType the property type
   */
  private void setPropertyType(PropertyType propertyType) {
    this.propertyType = propertyType;
  }

  /**
   * Sets variable name.
   *
   * @param variableName the variable name
   */
  private void setVariableName(VariableName variableName) {
    this.variableName = variableName;
  }
}
