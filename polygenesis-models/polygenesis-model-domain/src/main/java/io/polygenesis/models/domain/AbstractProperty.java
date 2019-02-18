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

import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.VariableName;
import java.util.Optional;

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
  // ABSTRACT
  // ===============================================================================================

  /**
   * Gets io model group as optional.
   *
   * @return the io model group as optional
   */
  public abstract Optional<IoModelGroup> getIoModelGroupAsOptional();

  /**
   * Gets io model.
   *
   * @return the io model
   */
  public abstract IoModel getIoModel();

  /**
   * Gets as key value.
   *
   * @return the as key value
   */
  public abstract KeyValue getAsKeyValue();

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
