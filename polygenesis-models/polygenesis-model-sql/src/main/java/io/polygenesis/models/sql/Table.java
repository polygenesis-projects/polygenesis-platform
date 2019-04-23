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

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Model;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Table.
 *
 * @author Christos Tsakostas
 */
public class Table implements Model {

  private TableName tableName;
  private Set<Column> columns;
  private Boolean multiTenant;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Table.
   *
   * @param tableName the table name
   * @param columns the columns
   * @param multiTenant the multi tenant
   */
  public Table(TableName tableName, Set<Column> columns, Boolean multiTenant) {
    setTableName(tableName);
    setColumns(columns);
    setMultiTenant(multiTenant);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(tableName.getText());
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets primary keys.
   *
   * @return the primary keys
   */
  public Set<Column> getPrimaryKeys() {
    return columns
        .stream()
        .filter(column -> column.getPrimaryKey())
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Has primary keys boolean.
   *
   * @return the boolean
   */
  public boolean hasPrimaryKeys() {
    return columns.stream().anyMatch(column -> column.getPrimaryKey());
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets table name.
   *
   * @return the table name
   */
  public TableName getTableName() {
    return tableName;
  }

  /**
   * Gets columns.
   *
   * @return the columns
   */
  public Set<Column> getColumns() {
    return columns;
  }

  /**
   * Gets multi tenant.
   *
   * @return the multi tenant
   */
  public Boolean getMultiTenant() {
    return multiTenant;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets table name.
   *
   * @param tableName the table name
   */
  private void setTableName(TableName tableName) {
    Assertion.isNotNull(tableName, "tableName is required");
    this.tableName = tableName;
  }

  /**
   * Sets columns.
   *
   * @param columns the columns
   */
  private void setColumns(Set<Column> columns) {
    this.columns = columns;
  }

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   */
  private void setMultiTenant(Boolean multiTenant) {
    Assertion.isNotNull(multiTenant, "multiTenant is required");
    this.multiTenant = multiTenant;
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
    Table table = (Table) o;
    return Objects.equals(tableName, table.tableName)
        && Objects.equals(columns, table.columns)
        && Objects.equals(multiTenant, table.multiTenant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tableName, columns, multiTenant);
  }
}
