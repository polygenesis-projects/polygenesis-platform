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

package io.polygenesis.generators.commons.representations;

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
   * @param isThingIdentity the is thing identity
   */
  public FieldRepresentation(String dataType, String variableName, Boolean isThingIdentity) {
    super(dataType, variableName, isThingIdentity);
  }

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   */
  public FieldRepresentation(String dataType, String variableName, Set<String> annotations) {
    super(dataType, variableName, annotations, false);
  }

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   * @param isThingIdentity the is thing identity
   */
  public FieldRepresentation(
      String dataType, String variableName, Set<String> annotations, Boolean isThingIdentity) {
    super(dataType, variableName, annotations, isThingIdentity);
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
