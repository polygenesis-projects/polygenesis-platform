/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

import com.oregor.ddd4j.check.assertion.Assertion;
import java.util.Objects;

/**
 * The type Table.
 *
 * @author Christos Tsakostas
 */
public class Table {

  private TableName tableName;
  private Boolean multiTenant;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Table.
   *
   * @param tableName the table name
   */
  public Table(TableName tableName) {
    setTableName(tableName);
    setMultiTenant(false);
  }

  /**
   * Instantiates a new Table.
   *
   * @param tableName the table name
   * @param multiTenant the multi tenant
   */
  public Table(TableName tableName, Boolean multiTenant) {
    setTableName(tableName);
    setMultiTenant(multiTenant);
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
        && Objects.equals(multiTenant, table.multiTenant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tableName, multiTenant);
  }
}
