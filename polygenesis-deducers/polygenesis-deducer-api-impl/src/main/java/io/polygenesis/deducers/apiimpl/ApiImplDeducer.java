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
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceModelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationModelRepository;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.BaseDomainEntity;
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

  private final DomainEntityConverterDeducer domainEntityConverterDeducer;
  private final ServiceImplementationDeducer serviceImplementationDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl deducer.
   *
   * @param domainEntityConverterDeducer the aggregate root converter deducer
   * @param serviceImplementationDeducer the service implementation deducer
   */
  public ApiImplDeducer(
      DomainEntityConverterDeducer domainEntityConverterDeducer,
      ServiceImplementationDeducer serviceImplementationDeducer) {
    this.domainEntityConverterDeducer = domainEntityConverterDeducer;
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

    Set<DomainEntityConverter> domainEntityConverters = new LinkedHashSet<>();
    fillDomainEntityConverters(
        domainEntityConverters, thingRepository, serviceModelRepository, domainModelRepository);

    Set<ServiceImplementation> serviceImplementations = new LinkedHashSet<>();
    fillServiceImplementations(
        serviceImplementations,
        serviceModelRepository,
        domainEntityConverters,
        domainModelRepository);

    return new ServiceImplementationModelRepository(serviceImplementations, domainEntityConverters);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Fill domain entity converters.
   *
   * @param domainEntityConverters the domain entity converters
   * @param thingRepository the thing repository
   * @param serviceModelRepository the service model repository
   * @param domainModelRepository the domain model repository
   */
  protected void fillDomainEntityConverters(
      Set<DomainEntityConverter> domainEntityConverters,
      ThingRepository thingRepository,
      ServiceModelRepository serviceModelRepository,
      DomainModelRepository domainModelRepository) {
    thingRepository
        .getApiThings()
        .forEach(
            thing -> {
              Optional<BaseDomainEntity> optionalDomainObject =
                  getOptionalDomainEntity(thing, domainModelRepository);

              if (optionalDomainObject.isPresent()) {
                domainEntityConverters.add(
                    domainEntityConverterDeducer.deduce(
                        optionalDomainObject.get(),
                        serviceModelRepository.getServicesBy(thing.getThingName())));
              }
            });
  }

  /**
   * Fill service implementations.
   *
   * @param serviceImplementations the service implementations
   * @param serviceModelRepository the service model repository
   * @param domainEntityConverters the domain entity converters
   */
  private void fillServiceImplementations(
      Set<ServiceImplementation> serviceImplementations,
      ServiceModelRepository serviceModelRepository,
      Set<DomainEntityConverter> domainEntityConverters,
      DomainModelRepository domainModelRepository) {
    serviceModelRepository
        .getServices()
        .forEach(
            service ->
                serviceImplementations.add(
                    serviceImplementation(service, domainEntityConverters, domainModelRepository)));
  }

  /**
   * Service implementation service implementation.
   *
   * @param service the service
   * @param domainEntityConverters the domain entity converters
   * @param domainModelRepository the domain model repository
   * @return the service implementation
   */
  private ServiceImplementation serviceImplementation(
      Service service,
      Set<DomainEntityConverter> domainEntityConverters,
      DomainModelRepository domainModelRepository) {

    Optional<DomainEntityConverter> optionalDomainEntityConverter =
        getOptionalDomainObjectConverter(
            domainEntityConverters, new ObjectName(service.getThingName().getText()));

    DomainEntityConverter domainEntityConverter = optionalDomainEntityConverter.get();

    if (optionalDomainEntityConverter.isPresent()) {
      return serviceImplementationDeducer.deduce(
          service,
          domainEntityConverter.getDomainEntity(),
          aggregateRootParent(domainModelRepository, domainEntityConverter.getDomainEntity()),
          domainEntityConverter);
    } else {
      return serviceImplementationDeducer.deduce(service);
    }
  }

  /**
   * Gets optional domain object converter.
   *
   * @param domainEntityConverters the domain object converters
   * @param domainObjectName the domain object name
   * @return the optional domain object converter
   */
  private Optional<DomainEntityConverter> getOptionalDomainObjectConverter(
      Set<DomainEntityConverter> domainEntityConverters, ObjectName domainObjectName) {
    return domainEntityConverters
        .stream()
        .filter(
            domainObjectConverter ->
                domainObjectConverter.getDomainEntity().getObjectName().equals(domainObjectName))
        .findFirst();
  }

  /**
   * Gets optional domain entity.
   *
   * @param thing the thing
   * @param domainModelRepository the domain model repository
   * @return the optional domain object
   */
  private Optional<BaseDomainEntity> getOptionalDomainEntity(
      Thing thing, DomainModelRepository domainModelRepository) {
    ObjectName objectName = new ObjectName(thing.getThingName().getText());

    Optional<BaseDomainEntity> optionalBaseDomainEntity =
        domainModelRepository
            .getAggregateRoots()
            .stream()
            .filter(aggregateRoot -> aggregateRoot.getObjectName().equals(objectName))
            .map(aggregateRoot -> (BaseDomainEntity) aggregateRoot)
            .findFirst();

    if (optionalBaseDomainEntity.isPresent()) {
      return optionalBaseDomainEntity;
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

  /**
   * Aggregate root parent optional.
   *
   * @param domainModelRepository the domain model repository
   * @param domainEntity the domain entity
   * @return the optional
   */
  protected Optional<AggregateRootPersistable> aggregateRootParent(
      DomainModelRepository domainModelRepository, BaseDomainEntity<?> domainEntity) {

    return domainModelRepository
        .getAggregateRoots()
        .stream()
        .filter(aggregateRoot -> aggregateRoot.contains(domainEntity))
        .map(AggregateRootPersistable.class::cast)
        .findFirst();
  }
}
