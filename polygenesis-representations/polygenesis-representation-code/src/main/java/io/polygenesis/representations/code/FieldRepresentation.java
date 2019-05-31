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

package io.polygenesis.representations.code;

import io.polygenesis.abstraction.data.DataPurpose;
import java.util.Set;

/**
 * The type Field representation.
 *
 * @author Christos Tsakostas
 */
public class FieldRepresentation extends AbstractDataRepresentation {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   */
  public FieldRepresentation(String dataType, String variableName) {
    super(dataType, variableName);
  }

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param dataPurpose the data business type
   */
  public FieldRepresentation(String dataType, String variableName, DataPurpose dataPurpose) {
    super(dataType, variableName, dataPurpose);
  }

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   */
  public FieldRepresentation(String dataType, String variableName, Set<String> annotations) {
    super(dataType, variableName, annotations, DataPurpose.any());
  }

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   * @param dataPurpose the data business type
   */
  public FieldRepresentation(
      String dataType, String variableName, Set<String> annotations, DataPurpose dataPurpose) {
    super(dataType, variableName, annotations, dataPurpose);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

}
