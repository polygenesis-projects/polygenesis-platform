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

package io.polygenesis.models.sql;

import java.util.Objects;

/**
 * The type Column.
 *
 * @author Christos Tsakostas
 */
public class Column {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String name;
  private ColumnDataType columnDataType;
  private Integer length;
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
    this(name, columnDataType, 0, requiredType, false);
  }

  /**
   * Instantiates a new Column.
   *
   * @param name the name
   * @param columnDataType the column data type
   * @param length the length
   * @param requiredType the required type
   */
  public Column(
      String name, ColumnDataType columnDataType, Integer length, RequiredType requiredType) {
    this(name, columnDataType, length, requiredType, false);
  }

  /**
   * Instantiates a new Column.
   *
   * @param name the name
   * @param columnDataType the column data type
   * @param length the length
   * @param requiredType the required type
   * @param primaryKey the primary key
   */
  public Column(
      String name,
      ColumnDataType columnDataType,
      Integer length,
      RequiredType requiredType,
      Boolean primaryKey) {
    setName(name);
    setColumnDataType(columnDataType);
    setLength(length);
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
   * Gets length.
   *
   * @return the length
   */
  public Integer getLength() {
    return length;
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
   * Sets length.
   *
   * @param length the length
   */
  private void setLength(Integer length) {
    this.length = length;
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
        && Objects.equals(length, column.length)
        && requiredType == column.requiredType
        && Objects.equals(primaryKey, column.primaryKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, columnDataType, length, requiredType, primaryKey);
  }
}
