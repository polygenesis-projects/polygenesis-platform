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

package io.polygenesis.deducers.sql;

import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.domain.DomainObjectMetamodelRepository;
import io.polygenesis.models.sql.Index;
import io.polygenesis.models.sql.SqlIndexMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

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

  @Override
  public SqlIndexMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<Index> indices = new LinkedHashSet<>();

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(metamodelRepositories, DomainObjectMetamodelRepository.class)
        .getItems()
        .forEach(aggregateRoot -> indices.add(indexDeducer.deduce()));

    return new SqlIndexMetamodelRepository(indices);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

}
