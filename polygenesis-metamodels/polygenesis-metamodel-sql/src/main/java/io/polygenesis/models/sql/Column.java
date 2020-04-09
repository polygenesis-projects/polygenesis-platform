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

package io.polygenesis.models.sql;

import java.util.Objects;

public class Column {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String name;
  private ColumnDataType columnDataType;
  private Integer length1;
  private Integer length2;
  private RequiredType requiredType;
  private Boolean primaryKey;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Column.
   *
   * @param name the name
   * @param columnDataType the column data type
   * @param requiredType the required type
   */
  public Column(String name, ColumnDataType columnDataType, RequiredType requiredType) {
    this(name, columnDataType, 0, 0, requiredType, false);
  }

  /**
   * Instantiates a new Column.
   *
   * @param name the name
   * @param columnDataType the column data type
   * @param length1 the length1
   * @param requiredType the required type
   */
  public Column(
      String name, ColumnDataType columnDataType, Integer length1, RequiredType requiredType) {
    this(name, columnDataType, length1, 0, requiredType, false);
  }

  /**
   * Instantiates a new Column.
   *
   * @param name the name
   * @param columnDataType the column data type
   * @param length1 the length 1
   * @param length2 the length 2
   * @param requiredType the required type
   */
  public Column(
      String name,
      ColumnDataType columnDataType,
      Integer length1,
      Integer length2,
      RequiredType requiredType) {
    this(name, columnDataType, length1, length2, requiredType, false);
  }

  /**
   * Instantiates a new Column.
   *
   * @param name the name
   * @param columnDataType the column data type
   * @param length1 the length1
   * @param length2 the length 2
   * @param requiredType the required type
   * @param primaryKey the primary key
   */
  public Column(
      String name,
      ColumnDataType columnDataType,
      Integer length1,
      Integer length2,
      RequiredType requiredType,
      Boolean primaryKey) {
    setName(name);
    setColumnDataType(columnDataType);
    setLength1(length1);
    setLength2(length2);
    setRequiredType(requiredType);
    setPrimaryKey(primaryKey);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets column data type.
   *
   * @return the column data type
   */
  public ColumnDataType getColumnDataType() {
    return columnDataType;
  }

  /**
   * Gets length1.
   *
   * @return the length1
   */
  public Integer getLength1() {
    return length1;
  }

  /**
   * Gets length 2.
   *
   * @return the length 2
   */
  public Integer getLength2() {
    return length2;
  }

  /**
   * Gets required type.
   *
   * @return the required type
   */
  public RequiredType getRequiredType() {
    return requiredType;
  }

  /**
   * Gets primary key.
   *
   * @return the primary key
   */
  public Boolean getPrimaryKey() {
    return primaryKey;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(String name) {
    this.name = name;
  }

  /**
   * Sets column data type.
   *
   * @param columnDataType the column data type
   */
  private void setColumnDataType(ColumnDataType columnDataType) {
    this.columnDataType = columnDataType;
  }

  /**
   * Sets length1.
   *
   * @param length1 the length1
   */
  private void setLength1(Integer length1) {
    this.length1 = length1;
  }

  /**
   * Sets length2.
   *
   * @param length2 the length2
   */
  private void setLength2(Integer length2) {
    this.length2 = length2;
  }

  /**
   * Sets required type.
   *
   * @param requiredType the required type
   */
  private void setRequiredType(RequiredType requiredType) {
    this.requiredType = requiredType;
  }

  /**
   * Sets primary key.
   *
   * @param primaryKey the primary key
   */
  private void setPrimaryKey(Boolean primaryKey) {
    this.primaryKey = primaryKey;
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
    Column column = (Column) o;
    return Objects.equals(name, column.name)
        && columnDataType == column.columnDataType
        && Objects.equals(length1, column.length1)
        && Objects.equals(length2, column.length2)
        && requiredType == column.requiredType
        && Objects.equals(primaryKey, column.primaryKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, columnDataType, length1, length2, requiredType, primaryKey);
  }
}
