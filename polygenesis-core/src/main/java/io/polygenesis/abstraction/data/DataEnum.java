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

import io.polygenesis.commons.valueobjects.VariableName;

/**
 * The type Data enum.
 *
 * @author Christos Tsakostas
 */
public class DataEnum extends Data {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data enum.
   *
   * @param dataPrimaryType the data primary type
   * @param variableName the variable name
   * @param dataPurpose the data purpose
   * @param dataValidator the data validator
   */
  public DataEnum(
      DataPrimaryType dataPrimaryType,
      VariableName variableName,
      DataPurpose dataPurpose,
      DataValidator dataValidator) {
    super(dataPrimaryType, variableName, dataPurpose, dataValidator);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String getDataType() {
    return DataPrimaryType.ENUM.name();
  }
}
