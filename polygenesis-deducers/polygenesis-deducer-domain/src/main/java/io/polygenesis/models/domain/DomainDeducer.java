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

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.domain.aggregateroot.AggregateRootDeducer;
import io.polygenesis.models.domain.agrregateentity.AggregateEntityDeducer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainDeducer implements Deducer<DomainObjectMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final AggregateRootDeducer aggregateRootDeducer;
  private final AggregateEntityDeducer aggregateEntityDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain deducer.
   *
   * @param rootPackageName the root package name
   * @param aggregateRootDeducer the aggregate root deducer
   * @param aggregateEntityDeducer the aggregate entity deducer
   */
  public DomainDeducer(
      PackageName rootPackageName,
      AggregateRootDeducer aggregateRootDeducer,
      AggregateEntityDeducer aggregateEntityDeducer) {
    this.rootPackageName = rootPackageName;
    this.aggregateRootDeducer = aggregateRootDeducer;
    this.aggregateEntityDeducer = aggregateEntityDeducer;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public DomainObjectMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<DomainObject> domainObjects = new LinkedHashSet<>();

    ThingRepository thingRepository =
        CoreRegistry.getAbstractionRepositoryResolver()
            .resolve(abstractionRepositories, ThingRepository.class);

    // Aggregate Roots
    Set<DomainObject> aggregateRoots =
        aggregateRootDeducer.deduceFrom(thingRepository, rootPackageName);

    domainObjects.addAll(aggregateRoots);

    // Children of Aggregate Roots -> Aggregate Entities
    Set<DomainObject> aggregateEntities = new LinkedHashSet<>();

    aggregateRoots.forEach(
        aggregateRoot -> {
          Thing thingParent =
              thingRepository
                  .getAbstractionItemByObjectName(aggregateRoot.getObjectName())
                  .orElseThrow();

          aggregateEntities.addAll(
              aggregateEntityDeducer.deduceAggregateEntities(
                  thingParent, aggregateRoot, getRootPackageName()));
        });

    domainObjects.addAll(aggregateEntities);

    return new DomainObjectMetamodelRepository(domainObjects);
  }
}
