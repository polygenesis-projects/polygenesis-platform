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
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationModelRepository;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.DomainModelRepository;
import io.polygenesis.models.domain.PropertyType;
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

  private final DomainObjectConverterDeducer domainObjectConverterDeducer;
  private final ServiceImplementationDeducer serviceImplementationDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl deducer.
   *
   * @param domainObjectConverterDeducer the aggregate root converter deducer
   * @param serviceImplementationDeducer the service implementation deducer
   */
  public ApiImplDeducer(
      DomainObjectConverterDeducer domainObjectConverterDeducer,
      ServiceImplementationDeducer serviceImplementationDeducer) {
    this.domainObjectConverterDeducer = domainObjectConverterDeducer;
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

    Set<DomainObjectConverter> domainObjectConverters = new LinkedHashSet<>();
    thingRepository
        .getApiThings()
        .forEach(
            thing -> {
              Optional<BaseDomainObject> optionalDomainObject =
                  getOptionalDomainObject(thing, modelRepositories);

              if (optionalDomainObject.isPresent()) {
                domainObjectConverters.add(
                    domainObjectConverterDeducer.deduce(
                        optionalDomainObject.get(),
                        serviceModelRepository.getServicesBy(thing.getThingName())));
              }
            });

    Set<ServiceImplementation> serviceImplementations = new LinkedHashSet<>();
    serviceModelRepository
        .getServices()
        .forEach(
            service -> {
              Optional<DomainObjectConverter> optionalDomainObjectConverter =
                  getOptionalDomainObjectConverter(
                      domainObjectConverters, new ObjectName(service.getThingName().getText()));

              DomainObjectConverter domainObjectConverter = optionalDomainObjectConverter.get();
              if (optionalDomainObjectConverter.isPresent()) {
                serviceImplementations.add(
                    serviceImplementationDeducer.deduce(
                        service, domainObjectConverter.getDomainObject(), domainObjectConverter));
              } else {
                serviceImplementations.add(serviceImplementationDeducer.deduce(service));
              }
            });

    return new ServiceImplementationModelRepository(serviceImplementations, domainObjectConverters);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Gets optional domain object converter.
   *
   * @param domainObjectConverters the domain object converters
   * @param domainObjectName the domain object name
   * @return the optional domain object converter
   */
  private Optional<DomainObjectConverter> getOptionalDomainObjectConverter(
      Set<DomainObjectConverter> domainObjectConverters, ObjectName domainObjectName) {
    return domainObjectConverters
        .stream()
        .filter(
            domainObjectConverter ->
                domainObjectConverter.getDomainObject().getObjectName().equals(domainObjectName))
        .findFirst();
  }

  /**
   * Gets optional domain object.
   *
   * @param thing the thing
   * @param modelRepositories the model repositories
   * @return the optional domain object
   */
  private Optional<BaseDomainObject> getOptionalDomainObject(
      Thing thing, Set<ModelRepository> modelRepositories) {
    DomainModelRepository domainModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, DomainModelRepository.class);

    ObjectName objectName = new ObjectName(thing.getThingName().getText());

    Optional<BaseDomainObject> optionalBaseDomainObject =
        domainModelRepository
            .getAggregateRoots()
            .stream()
            .filter(aggregateRoot -> aggregateRoot.getObjectName().equals(objectName))
            .map(aggregateRoot -> (BaseDomainObject) aggregateRoot)
            .findFirst();

    if (optionalBaseDomainObject.isPresent()) {
      return optionalBaseDomainObject;
    }

    Optional<AggregateEntityCollection> optionalAggregateEntityCollection =
        domainModelRepository
            .getAggregateRoots()
            .stream()
            .flatMap(aggregateRoot -> aggregateRoot.getProperties().stream())
            .filter(
                property ->
                    property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_COLLECTION))
            .map(AggregateEntityCollection.class::cast)
            .filter(
                aggregateEntityCollection ->
                    aggregateEntityCollection
                        .getAggregateEntity()
                        .getObjectName()
                        .equals(objectName))
            .findFirst();

    if (optionalAggregateEntityCollection.isPresent()) {
      return Optional.of(optionalAggregateEntityCollection.get().getAggregateEntity());
    }

    return Optional.empty();
  }
}
