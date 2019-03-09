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

import io.polygenesis.core.iomodel.DataBusinessType;
import io.polygenesis.core.iomodel.DataKind;
import io.polygenesis.core.iomodel.VariableName;

/**
 * The type Data.
 *
 * @author Christos Tsakostas
 */
public abstract class Data {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final DataKind dataKind;
  private final VariableName variableName;
  private final DataBusinessType dataBusinessType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data.
   *
   * @param dataKind the data kind
   * @param variableName the variable name
   */
  public Data(DataKind dataKind, VariableName variableName) {
    this(dataKind, variableName, DataBusinessType.ANY);
  }

  /**
   * Instantiates a new Data.
   *
   * @param dataKind the data kind
   * @param variableName the variable name
   * @param dataBusinessType the data business type
   */
  public Data(DataKind dataKind, VariableName variableName, DataBusinessType dataBusinessType) {
    this.dataKind = dataKind;
    this.variableName = variableName;
    this.dataBusinessType = dataBusinessType;
  }

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

  /**
   * Gets data business type.
   *
   * @return the data business type
   */
  public DataBusinessType getDataBusinessType() {
    return dataBusinessType;
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
  // OVERRIDES
  // ===============================================================================================

}
