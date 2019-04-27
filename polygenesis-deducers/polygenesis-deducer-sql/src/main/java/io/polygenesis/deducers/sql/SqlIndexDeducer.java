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

package io.polygenesis.deducers.sql;

import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.models.domain.DomainMetamodelRepository;
import io.polygenesis.models.sql.Index;
import io.polygenesis.models.sql.SqlIndexMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Sql index deducer.
 *
 * @author Christos Tsakostas
 */
public class SqlIndexDeducer implements Deducer<SqlIndexMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final IndexDeducer indexDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Sql index deducer.
   *
   * @param indexDeducer the index deducer
   */
  public SqlIndexDeducer(IndexDeducer indexDeducer) {
    this.indexDeducer = indexDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public SqlIndexMetamodelRepository deduce(
      ThingRepository thingRepository, Set<MetamodelRepository> modelRepositories) {
    if (thingRepository.getApiThings().isEmpty()) {
      throw new IllegalArgumentException("thingRepository cannot be empty");
    }

    Set<Index> indices = new LinkedHashSet<>();

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, DomainMetamodelRepository.class)
        .getItems()
        .forEach(aggregateRoot -> indices.add(indexDeducer.deduce()));

    return new SqlIndexMetamodelRepository(indices);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

}
