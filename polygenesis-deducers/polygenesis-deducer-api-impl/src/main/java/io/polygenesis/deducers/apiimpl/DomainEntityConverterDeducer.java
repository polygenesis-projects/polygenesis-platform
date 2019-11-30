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

package io.polygenesis.deducers.apiimpl;

import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.DomainEntityConverterMetamodelRepository;
import io.polygenesis.models.domain.AggregateRootMetamodelRepository;
import io.polygenesis.models.domain.ProjectionMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain entity converter deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainEntityConverterDeducer
    implements Deducer<DomainEntityConverterMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateRootConverterDeducer aggregateRootConverterDeducer;
  private final ProjectionConverterDeducer projectionConverterDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public DomainEntityConverterDeducer(
      AggregateRootConverterDeducer aggregateRootConverterDeducer,
      ProjectionConverterDeducer projectionConverterDeducer) {
    this.aggregateRootConverterDeducer = aggregateRootConverterDeducer;
    this.projectionConverterDeducer = projectionConverterDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public DomainEntityConverterMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<DomainEntityConverter> domainEntityConverters = new LinkedHashSet<>();

    ServiceMetamodelRepository serviceModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, ServiceMetamodelRepository.class);

    AggregateRootMetamodelRepository aggregateRootMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, AggregateRootMetamodelRepository.class);

    ProjectionMetamodelRepository projectionMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, ProjectionMetamodelRepository.class);

    fillDomainEntityConverters(
        domainEntityConverters,
        CoreRegistry.getAbstractionRepositoryResolver()
            .resolve(abstractionRepositories, ThingRepository.class),
        serviceModelRepository,
        aggregateRootMetamodelRepository,
        projectionMetamodelRepository);

    return new DomainEntityConverterMetamodelRepository(domainEntityConverters);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Fill domain entity converters.
   *
   * @param domainEntityConverters the domain entity converters
   * @param thingRepository the thing repository
   * @param serviceModelRepository the service model repository
   * @param aggregateRootMetamodelRepository the domain model repository
   * @param projectionMetamodelRepository the projection metamodel repository
   */
  protected void fillDomainEntityConverters(
      Set<DomainEntityConverter> domainEntityConverters,
      ThingRepository thingRepository,
      ServiceMetamodelRepository serviceModelRepository,
      AggregateRootMetamodelRepository aggregateRootMetamodelRepository,
      ProjectionMetamodelRepository projectionMetamodelRepository) {

    domainEntityConverters.addAll(
        aggregateRootConverterDeducer.deduce(
            thingRepository, serviceModelRepository, aggregateRootMetamodelRepository));

    domainEntityConverters.addAll(
        projectionConverterDeducer.deduce(
            thingRepository, serviceModelRepository, projectionMetamodelRepository));
  }
}
