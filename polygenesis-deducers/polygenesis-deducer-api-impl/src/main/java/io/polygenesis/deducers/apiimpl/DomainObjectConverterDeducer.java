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
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.apiimpl.DomainObjectConverterMetamodelRepository;
import io.polygenesis.models.domain.DomainObjectMetamodelRepository;
import io.polygenesis.models.domain.ProjectionMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain object converter deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverterDeducer
    implements Deducer<DomainObjectConverterMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateRootConverterDeducer aggregateRootConverterDeducer;
  private final ProjectionConverterDeducer projectionConverterDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object converter deducer.
   *
   * @param aggregateRootConverterDeducer the aggregate root converter deducer
   * @param projectionConverterDeducer the projection converter deducer
   */
  public DomainObjectConverterDeducer(
      AggregateRootConverterDeducer aggregateRootConverterDeducer,
      ProjectionConverterDeducer projectionConverterDeducer) {
    this.aggregateRootConverterDeducer = aggregateRootConverterDeducer;
    this.projectionConverterDeducer = projectionConverterDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public DomainObjectConverterMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<DomainObjectConverter> domainObjectConverters = new LinkedHashSet<>();

    ServiceMetamodelRepository serviceModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, ServiceMetamodelRepository.class);

    DomainObjectMetamodelRepository domainObjectMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, DomainObjectMetamodelRepository.class);

    ProjectionMetamodelRepository projectionMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, ProjectionMetamodelRepository.class);

    fillDomainObjectConverters(
        domainObjectConverters,
        CoreRegistry.getAbstractionRepositoryResolver()
            .resolve(abstractionRepositories, ThingRepository.class),
        serviceModelRepository,
        domainObjectMetamodelRepository,
        projectionMetamodelRepository);

    return new DomainObjectConverterMetamodelRepository(domainObjectConverters);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Fill domain object converters.
   *
   * @param domainObjectConverters the domain object converters
   * @param thingRepository the thing repository
   * @param serviceModelRepository the service model repository
   * @param domainObjectMetamodelRepository the aggregate root metamodel repository
   * @param projectionMetamodelRepository the projection metamodel repository
   */
  protected void fillDomainObjectConverters(
      Set<DomainObjectConverter> domainObjectConverters,
      ThingRepository thingRepository,
      ServiceMetamodelRepository serviceModelRepository,
      DomainObjectMetamodelRepository domainObjectMetamodelRepository,
      ProjectionMetamodelRepository projectionMetamodelRepository) {

    domainObjectConverters.addAll(
        aggregateRootConverterDeducer.deduce(
            thingRepository, serviceModelRepository, domainObjectMetamodelRepository));

    domainObjectConverters.addAll(
        projectionConverterDeducer.deduce(
            thingRepository, serviceModelRepository, projectionMetamodelRepository));
  }
}
