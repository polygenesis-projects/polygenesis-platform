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

package io.polygenesis.deducers.apiimpl;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.models.api.ServiceModelRepository;
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationModelRepository;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.DomainModelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Api impl deducer.
 *
 * @author Christos Tsakostas
 */
public class ApiImplDeducer implements Deducer<ServiceImplementationModelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateRootConverterDeducer aggregateRootConverterDeducer;
  private final ServiceImplementationDeducer serviceImplementationDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl deducer.
   *
   * @param aggregateRootConverterDeducer the aggregate root converter deducer
   * @param serviceImplementationDeducer the service implementation deducer
   */
  public ApiImplDeducer(
      AggregateRootConverterDeducer aggregateRootConverterDeducer,
      ServiceImplementationDeducer serviceImplementationDeducer) {
    this.aggregateRootConverterDeducer = aggregateRootConverterDeducer;
    this.serviceImplementationDeducer = serviceImplementationDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ServiceImplementationModelRepository deduce(
      ThingRepository thingRepository, Set<ModelRepository> modelRepositories) {

    Set<ServiceImplementation> serviceImplementations = new LinkedHashSet<>();
    Set<AggregateRootConverter> aggregateRootConverters = new LinkedHashSet<>();

    thingRepository
        .getApiThings()
        .forEach(
            thing ->
                fillServiceImplementationsAndConverters(
                    serviceImplementations, aggregateRootConverters, modelRepositories, thing));

    return new ServiceImplementationModelRepository(
        serviceImplementations, aggregateRootConverters);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Fill service implementations and converters.
   *
   * @param serviceImplementations the service implementations
   * @param aggregateRootConverters the aggregate root converters
   * @param modelRepositories the model repositories
   * @param thing the thing
   */
  protected void fillServiceImplementationsAndConverters(
      Set<ServiceImplementation> serviceImplementations,
      Set<AggregateRootConverter> aggregateRootConverters,
      Set<ModelRepository> modelRepositories,
      Thing thing) {
    ServiceModelRepository serviceModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, ServiceModelRepository.class);

    DomainModelRepository domainModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, DomainModelRepository.class);

    AggregateRoot aggregateRoot =
        domainModelRepository
            .getAggregateRootByName(new ObjectName(thing.getThingName().getText()))
            .orElseThrow(IllegalArgumentException::new);

    AggregateRootConverter aggregateRootConverter =
        aggregateRootConverterDeducer.deduce(
            aggregateRoot, serviceModelRepository.getServicesBy(thing.getThingName()));

    aggregateRootConverters.add(aggregateRootConverter);

    serviceModelRepository
        .getServicesBy(thing.getThingName())
        .forEach(
            service ->
                serviceImplementations.add(
                    serviceImplementationDeducer.deduce(
                        service, aggregateRoot, aggregateRootConverter)));

    thing
        .getVirtualChildren()
        .forEach(
            thingVirtualChild ->
                fillServiceImplementationsAndConverters(
                    serviceImplementations,
                    aggregateRootConverters,
                    modelRepositories,
                    thingVirtualChild));

    // TODO
    //    thing.getChildren()
    //        .forEach(
    //            thingChild -> fillServiceImplementationsAndConverters(serviceImplementations,
    //                aggregateRootConverters, modelRepositories, thingChild));

  }
}
