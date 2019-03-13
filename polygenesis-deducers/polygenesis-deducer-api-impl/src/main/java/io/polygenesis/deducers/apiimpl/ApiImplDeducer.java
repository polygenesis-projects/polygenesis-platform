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

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.models.api.ServiceModelRepository;
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationModelRepository;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.DomainModelRepository;
import java.util.LinkedHashSet;
import java.util.Optional;
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

    ServiceModelRepository serviceModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, ServiceModelRepository.class);

    DomainModelRepository domainModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, DomainModelRepository.class);

    Set<ServiceImplementation> serviceImplementations = new LinkedHashSet<>();
    Set<AggregateRootConverter> aggregateRootConverters = new LinkedHashSet<>();

    thingRepository
        .getApiThings()
        .forEach(
            thing -> {
              Optional<AggregateRoot> optionalAggregateRoot =
                  domainModelRepository.getAggregateRootByName(new Name(thing.getName().getText()));

              if (optionalAggregateRoot.isPresent()) {
                AggregateRootConverter aggregateRootConverter =
                    aggregateRootConverterDeducer.deduce(
                        optionalAggregateRoot.get(),
                        serviceModelRepository.getServicesBy(thing.getName()));

                aggregateRootConverters.add(aggregateRootConverter);

                serviceModelRepository
                    .getServicesBy(thing.getName())
                    .forEach(
                        service ->
                            serviceImplementations.add(
                                serviceImplementationDeducer.deduce(
                                    service, optionalAggregateRoot.get(), aggregateRootConverter)));
              }
            });

    return new ServiceImplementationModelRepository(
        serviceImplementations, aggregateRootConverters);
  }
}
