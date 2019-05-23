/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.abstraction.data;

import java.util.Objects;

/**
 * The type Data map.
 *
 * @author Christos Tsakostas
 */
public class DataMap extends Data {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final Data key;
  private final Data value;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data map.
   *
   * @param variableName the variable name
   * @param dataPurpose the data business type
   * @param dataValidator the data validator
   * @param key the key
   * @param value the value
   */
  public DataMap(
      VariableName variableName,
      DataPurpose dataPurpose,
      DataValidator dataValidator,
      Data key,
      Data value) {
    super(DataPrimaryType.MAP, variableName, dataPurpose, dataValidator);
    this.key = key;
    this.value = value;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets key.
   *
   * @return the key
   */
  public Data getKey() {
    return key;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public Data getValue() {
    return value;
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public String getDataType() {
    return DataPrimaryType.MAP.name();
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
    DataMap dataMap = (DataMap) o;
    return Objects.equals(key, dataMap.key) && Objects.equals(value, dataMap.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), key, value);
  }
}
