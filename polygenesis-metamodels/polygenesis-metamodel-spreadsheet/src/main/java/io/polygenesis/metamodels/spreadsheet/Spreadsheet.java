/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.metamodels.spreadsheet;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.AbstractMetamodel;
import java.util.Objects;
import java.util.Set;

public class Spreadsheet extends AbstractMetamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<Sheet> sheets;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Spreadsheet.
   *
   * @param objectName the object name
   * @param sheets the sheets
   */
  public Spreadsheet(ObjectName objectName, Set<Sheet> sheets) {
    super(objectName);
    setSheets(sheets);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets sheets.
   *
   * @return the sheets
   */
  public Set<Sheet> getSheets() {
    return sheets;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets sheets.
   *
   * @param sheets the sheets
   */
  private void setSheets(Set<Sheet> sheets) {
    Assertion.isNotNull(sheets, "sheets is required");
    this.sheets = sheets;
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
    Spreadsheet that = (Spreadsheet) o;
    return Objects.equals(sheets, that.sheets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), sheets);
  }
}
