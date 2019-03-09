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

package io.polygenesis.core.datatype;

import io.polygenesis.core.iomodel.DataKind;
import java.util.Optional;

/**
 * The type Array data type.
 *
 * @author Christos Tsakostas
 */
public class ArrayDataType extends AbstractDataType {

  private ArrayType arrayType;
  private AbstractDataType elementDataType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Array data type.
   *
   * @param arrayType the kind of array data type
   * @param elementDataType the element data type
   */
  public ArrayDataType(ArrayType arrayType, AbstractDataType elementDataType) {
    super(DataKind.ARRAY, new DataTypeName(arrayType.name()));
    this.arrayType = arrayType;
    this.elementDataType = elementDataType;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets element data type.
   *
   * @return the element data type
   */
  public AbstractDataType getElementDataType() {
    return elementDataType;
  }

  /**
   * Gets kind of array data type.
   *
   * @return the kind of array data type
   */
  public ArrayType getArrayType() {
    return arrayType;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Optional<PackageName> getOptionalPackageName() {
    return Optional.empty();
  }
}
