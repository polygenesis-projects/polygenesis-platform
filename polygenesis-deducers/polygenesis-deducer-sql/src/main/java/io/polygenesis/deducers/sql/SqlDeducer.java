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

package io.polygenesis.deducers.sql;

import io.polygenesis.core.Deducer;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.models.sql.Index;
import io.polygenesis.models.sql.SqlModelRepository;
import io.polygenesis.models.sql.Table;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Sql deducer.
 *
 * @author Christos Tsakostas
 */
public class SqlDeducer implements Deducer<SqlModelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final TableDeducer tableDeducer;
  private final IndexDeducer indexDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Sql deducer.
   *
   * @param tableDeducer the table deducer
   * @param indexDeducer the index deducer
   */
  public SqlDeducer(TableDeducer tableDeducer, IndexDeducer indexDeducer) {
    this.tableDeducer = tableDeducer;
    this.indexDeducer = indexDeducer;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public SqlModelRepository deduce(
      ThingRepository thingRepository, Set<ModelRepository> modelRepositories) {
    if (thingRepository.getThings().isEmpty()) {
      throw new IllegalArgumentException("thingRepository cannot be empty");
    }

    Set<Table> tables = new LinkedHashSet<>();
    Set<Index> indices = new LinkedHashSet<>();

    thingRepository
        .getThings()
        .forEach(
            thing -> {
              tables.add(tableDeducer.deduce());
              indices.add(indexDeducer.deduce());
            });

    return new SqlModelRepository(tables, indices);
  }
}
