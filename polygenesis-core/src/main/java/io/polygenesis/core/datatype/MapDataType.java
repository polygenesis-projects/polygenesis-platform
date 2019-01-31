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

/**
 * The type Map data type.
 *
 * @author Christos Tsakostas
 */
public class MapDataType extends AbstractMultipleDataType {

  private AbstractObjectDataType simpleDataTypeKey;
  private AbstractObjectDataType simpleDataTypeValue;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Map data type.
   *
   * @param dataTypeName the data type name
   * @param simpleDataTypeKey the simple data type key
   * @param simpleDataTypeValue the simple data type value
   */
  public MapDataType(
      DataTypeName dataTypeName,
      AbstractObjectDataType simpleDataTypeKey,
      AbstractObjectDataType simpleDataTypeValue) {
    super(dataTypeName);
    this.simpleDataTypeKey = simpleDataTypeKey;
    this.simpleDataTypeValue = simpleDataTypeValue;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets simple data type key.
   *
   * @return the simple data type key
   */
  public AbstractObjectDataType getSimpleDataTypeKey() {
    return simpleDataTypeKey;
  }

  /**
   * Gets simple data type value.
   *
   * @return the simple data type value
   */
  public AbstractObjectDataType getSimpleDataTypeValue() {
    return simpleDataTypeValue;
  }
}
