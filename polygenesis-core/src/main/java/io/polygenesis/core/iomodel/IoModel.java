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

import java.util.Objects;

/**
 * This is the base class for {@link IoModelPrimitive}, {@link IoModelGroup}, and {@link
 * IoModelArray}.
 *
 * @author Christos Tsakostas
 */
public abstract class IoModel {

  private final DataKind dataKind;
  private final VariableName variableName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model.
   *
   * @param dataKind the data kind
   */
  public IoModel(DataKind dataKind) {
    this(dataKind, null);
  }

  /**
   * Instantiates a new Io model.
   *
   * @param dataKind the data kind
   * @param variableName the variable name
   */
  public IoModel(DataKind dataKind, VariableName variableName) {
    this.dataKind = dataKind;
    this.variableName = variableName;
  }

  // ===============================================================================================
  // ABSTRACT
  // ===============================================================================================

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public abstract String getDataType();

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data kind.
   *
   * @return the data kind
   */
  public DataKind getDataKind() {
    return dataKind;
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

  /**
   * Is primitive.
   *
   * @return the boolean
   */
  public boolean isPrimitive() {
    return getDataKind().equals(DataKind.PRIMITIVE);
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
   * Is io model array boolean.
   *
   * @return the boolean
   */
  public boolean isIoModelArray() {
    return this instanceof IoModelArray;
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
    return dataKind == ioModel.dataKind && Objects.equals(variableName, ioModel.variableName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataKind, variableName);
  }
}
