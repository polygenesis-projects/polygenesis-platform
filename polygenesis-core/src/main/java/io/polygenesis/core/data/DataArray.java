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

package io.polygenesis.core.data;

import java.util.Objects;

/**
 * The type data array.
 *
 * @author Christos Tsakostas
 */
public class DataArray extends Data {

  private final Data arrayElement;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new data array.
   *
   * @param arrayElement the array element
   */
  public DataArray(Data arrayElement) {
    this(null, arrayElement);
  }

  /**
   * Instantiates a new data array.
   *
   * @param variableName the variable name
   */
  public DataArray(VariableName variableName) {
    this(variableName, null);
  }

  /**
   * Instantiates a new data array.
   *
   * @param variableName the variable name
   * @param arrayElement the array element
   */
  public DataArray(VariableName variableName, Data arrayElement) {
    super(DataPrimaryType.ARRAY, variableName);
    this.arrayElement = arrayElement;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets array element.
   *
   * @return the array element
   */
  public Data getArrayElement() {
    return arrayElement;
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATION
  // ===============================================================================================

  @Override
  public String getDataType() {
    return DataPrimaryType.ARRAY.name();
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
    if (!super.equals(o)) {
      return false;
    }
    DataArray that = (DataArray) o;
    return Objects.equals(arrayElement, that.arrayElement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), arrayElement);
  }
}
