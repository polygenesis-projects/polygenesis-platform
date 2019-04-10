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

package io.polygenesis.models.domain;

import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Model;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingRepository;
import java.util.Set;

/**
 * The type Domain deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainDeducer implements Deducer<DomainModelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final AggregateRootDeducer aggregateRootDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain deducer.
   *
   * @param rootPackageName the root package name
   * @param aggregateRootDeducer the aggregate root deducer
   */
  public DomainDeducer(PackageName rootPackageName, AggregateRootDeducer aggregateRootDeducer) {
    this.rootPackageName = rootPackageName;
    this.aggregateRootDeducer = aggregateRootDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public DomainModelRepository deduce(
      ThingRepository thingRepository, Set<ModelRepository<? extends Model>> modelRepositories) {
    return new DomainModelRepository(
        aggregateRootDeducer.deduceFrom(thingRepository, rootPackageName));
  }
}
