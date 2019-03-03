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
import io.polygenesis.core.ModelRepository;
import java.util.Set;

/**
 * The type Sql model repository.
 *
 * @author Christos Tsakostas
 */
public class SqlModelRepository implements ModelRepository {

  private Set<Table> tables;
  private Set<Index> indices;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Sql model repository.
   *
   * @param tables the tables
   * @param indices the indices
   */
  public SqlModelRepository(Set<Table> tables, Set<Index> indices) {
    setTables(tables);
    setIndices(indices);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets tables.
   *
   * @return the tables
   */
  public Set<Table> getTables() {
    return tables;
  }

  /**
   * Gets indices.
   *
   * @return the indices
   */
  public Set<Index> getIndices() {
    return indices;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets tables.
   *
   * @param tables the tables
   */
  private void setTables(Set<Table> tables) {
    Assertion.isNotNull(tables, "tables is required");
    this.tables = tables;
  }

  /**
   * Sets indices.
   *
   * @param indices the indices
   */
  private void setIndices(Set<Index> indices) {
    Assertion.isNotNull(indices, "indices is required");
    this.indices = indices;
  }
}
