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

package io.polygenesis.generators.sql;

import java.util.Objects;
import java.util.Set;

/**
 * The type Script representation.
 *
 * @author Christos Tsakostas
 */
public class ScriptRepresentation {

  private Set<String> createTables;
  private Set<String> createTableIndices;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public ScriptRepresentation(Set<String> createTables, Set<String> createTableIndices) {
    setCreateTables(createTables);
    setCreateTableIndices(createTableIndices);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets create tables.
   *
   * @return the create tables
   */
  public Set<String> getCreateTables() {
    return createTables;
  }

  /**
   * Gets create table indices.
   *
   * @return the create table indices
   */
  public Set<String> getCreateTableIndices() {
    return createTableIndices;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets create tables.
   *
   * @param createTables the create tables
   */
  private void setCreateTables(Set<String> createTables) {
    this.createTables = createTables;
  }

  /**
   * Sets create table indices.
   *
   * @param createTableIndices the create table indices
   */
  private void setCreateTableIndices(Set<String> createTableIndices) {
    this.createTableIndices = createTableIndices;
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
    ScriptRepresentation that = (ScriptRepresentation) o;
    return Objects.equals(createTables, that.createTables)
        && Objects.equals(createTableIndices, that.createTableIndices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createTables, createTableIndices);
  }
}
