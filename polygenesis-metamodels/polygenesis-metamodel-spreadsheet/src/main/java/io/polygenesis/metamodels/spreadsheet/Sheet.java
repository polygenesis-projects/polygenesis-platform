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
import io.polygenesis.commons.valueobjects.Name;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Sheet {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Name name;
  private Set<Cell> cells;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Sheet.
   *
   * @param name the name
   * @param cells the cells
   */
  public Sheet(Name name, Set<Cell> cells) {
    setName(name);
    setCells(cells);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets name.
   *
   * @return the name
   */
  public Name getName() {
    return name;
  }

  /**
   * Gets cells.
   *
   * @return the cells
   */
  public Set<Cell> getCells() {
    return cells;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets number of rows.
   *
   * @return the number of rows
   */
  public Integer getNumberOfRows() {
    return getCells().stream()
            .map(Cell::getRowIndex)
            .max(Comparator.comparing(RowIndex::getIndex))
            .map(RowIndex::getIndex)
            .orElseThrow(IllegalStateException::new)
        + 1;
  }

  /**
   * Gets cells by row index.
   *
   * @param rowIndex the row index
   * @return the cells by row index
   */
  public Set<Cell> getCellsByRowIndex(Integer rowIndex) {
    return getCells().stream()
        .filter(cell -> cell.getRowIndex().getIndex().equals(rowIndex))
        .collect(Collectors.toSet());
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(Name name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets cells.
   *
   * @param cells the cells
   */
  private void setCells(Set<Cell> cells) {
    Assertion.isNotNull(cells, "cells is required");
    this.cells = cells;
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
    Sheet sheet = (Sheet) o;
    return Objects.equals(name, sheet.name) && Objects.equals(cells, sheet.cells);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, cells);
  }
}
