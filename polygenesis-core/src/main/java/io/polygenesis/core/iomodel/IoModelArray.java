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

import io.polygenesis.core.datatype.ClassDataType;
import java.util.Objects;

/**
 * The type Io model array.
 *
 * @author Christos Tsakostas
 */
public class IoModelArray extends IoModelGroup {

  private final IoModel arrayElement;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model array.
   */
  public IoModelArray() {
    this.arrayElement = null;
  }

  /**
   * Instantiates a new Io model array.
   *
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModelArray(ClassDataType dataType, VariableName variableName) {
    super(dataType, variableName);
    this.arrayElement = null;
  }

  /**
   * Instantiates a new Io model array.
   *
   * @param arrayElement the array element
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModelArray(
      IoModel arrayElement,
      ClassDataType dataType,
      VariableName variableName) {
    super(dataType, variableName);
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
  public IoModel getArrayElement() {
    return arrayElement;
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
    IoModelArray that = (IoModelArray) o;
    return Objects.equals(arrayElement, that.arrayElement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), arrayElement);
  }
}
