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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This is the base class for {@link IoModelPrimitive}, {@link IoModelGroup}, and {@link
 * IoModelArray}*.
 *
 * @author Christos Tsakostas
 */
public abstract class IoModel {

  private GenericTypeName genericType;

  private DataTypeName dataType;

  private VariableName variableName;

  private IoModelGroup parent;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model.
   *
   * @param parent the parent
   */
  public IoModel(IoModelGroup parent) {
    setParent(parent);
  }

  /**
   * Instantiates a new Io model.
   *
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModel(DataTypeName dataType, VariableName variableName) {
    setDataType(dataType);
    setVariableName(variableName);
  }

  /**
   * Instantiates a new Io model.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param parent the parent
   */
  public IoModel(DataTypeName dataType, VariableName variableName, IoModelGroup parent) {
    this(dataType, variableName);
    setParent(parent);
  }

  /**
   * Instantiates a new Io model.
   *
   * @param genericType the generic type
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModel(GenericTypeName genericType, DataTypeName dataType, VariableName variableName) {
    this(dataType, variableName);
    setGenericType(genericType);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets generic type.
   *
   * @return the generic type
   */
  public GenericTypeName getGenericType() {
    return genericType;
  }

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public DataTypeName getDataType() {
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

  private boolean isDataTypePrimitive(DataTypeName dataType) {
    try {
      ModelPrimitiveType.valueOf(dataType.getText().toUpperCase());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setGenericType(GenericTypeName genericType) {
    this.genericType = genericType;
  }

  private void setDataType(DataTypeName dataType) {
    this.dataType = dataType;
  }

  private void setVariableName(VariableName variableName) {
    this.variableName = variableName;
  }

  private void setParent(IoModelGroup parent) {
    this.parent = parent;
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
        .append(genericType, ioModel.genericType)
        .append(dataType, ioModel.dataType)
        .append(variableName, ioModel.variableName)
        .append(parent, ioModel.parent)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(genericType)
        .append(dataType)
        .append(variableName)
        .append(parent)
        .toHashCode();
  }
}
