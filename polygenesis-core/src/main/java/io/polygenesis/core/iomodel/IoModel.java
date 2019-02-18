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

package io.polygenesis.core.iomodel;

import io.polygenesis.core.datatype.AbstractDataType;
import io.polygenesis.core.datatype.PrimitiveType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This is the base class for {@link IoModelPrimitive}, {@link IoModelGroup}, and {@link
 * IoModelArray}****.
 *
 * @author Christos Tsakostas
 */
public abstract class IoModel {

  private final IoModelGroup parent;
  private final AbstractDataType dataType;
  private final VariableName variableName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model.
   *
   * @param parent the parent
   */
  public IoModel(IoModelGroup parent) {
    this.dataType = null;
    this.variableName = null;
    this.parent = parent;
  }

  /**
   * Instantiates a new Io model.
   *
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModel(AbstractDataType dataType, VariableName variableName) {
    this.dataType = dataType;
    this.variableName = variableName;
    this.parent = null;
  }

  /**
   * Instantiates a new Io model.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param parent the parent
   */
  public IoModel(AbstractDataType dataType, VariableName variableName, IoModelGroup parent) {
    this.dataType = dataType;
    this.variableName = variableName;
    this.parent = parent;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public AbstractDataType getDataType() {
    return dataType;
  }

  /**
   * Gets variable name.
   *
   * @return the variable name
   */
  public VariableName getVariableName() {
    return variableName;
  }

  /**
   * Gets parent.
   *
   * @return the parent
   */
  public IoModelGroup getParent() {
    return parent;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Is primitive.
   *
   * @return the boolean
   */
  public boolean isPrimitive() {
    if (this instanceof IoModelPrimitive) {
      return true;
    }

    if (this instanceof IoModelArray) {
      return this.isDataTypePrimitive(getDataType());
    }

    return false;
  }

  // TODO: move into AbstractDataType
  private boolean isDataTypePrimitive(AbstractDataType dataType) {
    try {
      PrimitiveType.valueOf(dataType.getDataTypeName().getText().toUpperCase());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Is io model group boolean.
   *
   * @return the boolean
   */
  public boolean isIoModelGroup() {
    return this instanceof IoModelGroup;
  }

  /**
   * Is thing identity boolean.
   *
   * @return the boolean
   */
  public boolean isThingIdentity() {
    if (this instanceof IoModelPrimitive) {
      return ((IoModelPrimitive) this).getThingIdentity();
    } else {
      return false;
    }
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

    IoModel ioModel = (IoModel) o;

    return new EqualsBuilder()
        .append(dataType, ioModel.dataType)
        .append(variableName, ioModel.variableName)
        .append(parent, ioModel.parent)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(dataType)
        .append(variableName)
        .append(parent)
        .toHashCode();
  }
}
