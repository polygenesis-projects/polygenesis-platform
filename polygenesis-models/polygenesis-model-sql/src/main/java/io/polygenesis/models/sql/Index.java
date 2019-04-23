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
import java.util.Objects;

/**
 * The type Index.
 *
 * @author Christos Tsakostas
 */
public class Index implements Model {

  private IndexName indexName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Index.
   *
   * @param indexName the index name
   */
  public Index(IndexName indexName) {
    setIndexName(indexName);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(indexName.getText());
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets index name.
   *
   * @return the index name
   */
  public IndexName getIndexName() {
    return indexName;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets index name.
   *
   * @param indexName the index name
   */
  private void setIndexName(IndexName indexName) {
    Assertion.isNotNull(indexName, "indexName is required");
    this.indexName = indexName;
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
    Index index = (Index) o;
    return Objects.equals(indexName, index.indexName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indexName);
  }
}
