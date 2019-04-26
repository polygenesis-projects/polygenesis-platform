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

package io.polygenesis.generators.angular.skeletons;

import java.util.Map;
import java.util.Set;

/**
 * The type Interface representation.
 *
 * @author Christos Tsakostas
 */
public class InterfaceRepresentation extends AbstractTypescriptObjectRepresentation {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Interface representation.
   *
   * @param importObjects the import objects
   * @param importAliases the import aliases
   * @param description the description
   * @param simpleObjectName the simple object name
   * @param fullObjectName the full object name
   */
  public InterfaceRepresentation(
      Map<Set<String>, String> importObjects,
      Map<String, String> importAliases,
      String description,
      String simpleObjectName,
      String fullObjectName) {
    super(importObjects, importAliases, description, simpleObjectName, fullObjectName);
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
