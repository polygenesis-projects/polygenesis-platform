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

package io.polygenesis.representations.typescript.action;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Map;
import java.util.Objects;

/**
 * The type Action index representation.
 *
 * @author Christos Tsakostas
 */
public class ActionIndexRepresentation {

  private Map<String, String> importAliases;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action index representation.
   *
   * @param importAliases the import aliases
   */
  public ActionIndexRepresentation(Map<String, String> importAliases) {
    setImportAliases(importAliases);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets import aliases.
   *
   * @return the import aliases
   */
  public Map<String, String> getImportAliases() {
    return importAliases;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets import aliases.
   *
   * @param importAliases the import aliases
   */
  private void setImportAliases(Map<String, String> importAliases) {
    Assertion.isNotNull(importAliases, "importAliases is required");
    this.importAliases = importAliases;
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
    ActionIndexRepresentation that = (ActionIndexRepresentation) o;
    return Objects.equals(importAliases, that.importAliases);
  }

  @Override
  public int hashCode() {
    return Objects.hash(importAliases);
  }
}
