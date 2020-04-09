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
import java.util.Objects;

public class Cell {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private RowIndex rowIndex;
  private ColumnIndex columnIndex;
  private Value value;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Cell.
   *
   * @param rowIndex the row index
   * @param columnIndex the column index
   * @param value the value
   */
  public Cell(RowIndex rowIndex, ColumnIndex columnIndex, Value value) {
    setRowIndex(rowIndex);
    setColumnIndex(columnIndex);
    setValue(value);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets row index.
   *
   * @return the row index
   */
  public RowIndex getRowIndex() {
    return rowIndex;
  }

  /**
   * Gets column index.
   *
   * @return the column index
   */
  public ColumnIndex getColumnIndex() {
    return columnIndex;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public Value getValue() {
    return value;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets row index.
   *
   * @param rowIndex the row index
   */
  private void setRowIndex(RowIndex rowIndex) {
    Assertion.isNotNull(rowIndex, "rowIndex is required");
    this.rowIndex = rowIndex;
  }

  /**
   * Sets column index.
   *
   * @param columnIndex the column index
   */
  private void setColumnIndex(ColumnIndex columnIndex) {
    Assertion.isNotNull(columnIndex, "columnIndex is required");
    this.columnIndex = columnIndex;
  }

  /**
   * Sets value.
   *
   * @param value the value
   */
  private void setValue(Value value) {
    Assertion.isNotNull(value, "value is required");
    this.value = value;
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
    Cell cell = (Cell) o;
    return Objects.equals(rowIndex, cell.rowIndex)
        && Objects.equals(columnIndex, cell.columnIndex)
        && Objects.equals(value, cell.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rowIndex, columnIndex, value);
  }
}
