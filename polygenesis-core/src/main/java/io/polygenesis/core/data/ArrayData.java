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

import io.polygenesis.core.datatype.DataKind;
import io.polygenesis.core.iomodel.VariableName;

/**
 * The type Array data.
 *
 * @author Christos Tsakostas
 */
public class ArrayData extends Data {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final Data element;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Array data.
   *
   * @param variableName the variable name
   * @param element the element
   */
  public ArrayData(VariableName variableName, Data element) {
    super(DataKind.ARRAY, variableName);
    this.element = element;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets element.
   *
   * @return the element
   */
  public Data getElement() {
    return element;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String getDataType() {
    return DataKind.ARRAY.name();
  }
}
