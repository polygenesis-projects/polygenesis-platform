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

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.DomainEntityConverterMetamodelRepository;
import io.polygenesis.models.apiimpl.ServiceDependency;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationMetamodelRepository;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.BaseDomainEntity;
import io.polygenesis.models.domain.DomainMetamodelRepository;
import io.polygenesis.models.domain.Persistence;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Service implementation deducer.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationDeducer extends BaseApiImplementationDeducer
    implements Deducer<ServiceImplementationMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ServiceMethodImplementationDeducer serviceMethodImplementationDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation deducer.
   *
   * @param serviceMethodImplementationDeducer the service method implementation deducer
   */
  public ServiceImplementationDeducer(
      ServiceMethodImplementationDeducer serviceMethodImplementationDeducer) {
    this.serviceMethodImplementationDeducer = serviceMethodImplementationDeducer;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public ServiceImplementationMetamodelRepository deduce(
      Set<AbstractionRepository> abstractionRepositories,
      Set<MetamodelRepository> modelRepositories) {
    ServiceMetamodelRepository serviceModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, ServiceMetamodelRepository.class);

    DomainMetamodelRepository domainModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, DomainMetamodelRepository.class);

    DomainEntityConverterMetamodelRepository domainEntityConverterModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, DomainEntityConverterMetamodelRepository.class);

    Set<ServiceImplementation> serviceImplementations = new LinkedHashSet<>();

    fillServiceImplementations(
        serviceImplementations,
        serviceModelRepository,
        domainEntityConverterModelRepository,
        domainModelRepository);

    return new ServiceImplementationMetamodelRepository(serviceImplementations);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Fill service implementations.
   *
   * @param serviceImplementations the service implementations
   * @param serviceModelRepository the service model repository
   * @param domainEntityConverterModelRepository the domain entity converter model repository
   * @param domainModelRepository the domain model repository
   */
  private void fillServiceImplementations(
      Set<ServiceImplementation> serviceImplementations,
      ServiceMetamodelRepository serviceModelRepository,
      DomainEntityConverterMetamodelRepository domainEntityConverterModelRepository,
      DomainMetamodelRepository domainModelRepository) {
    serviceModelRepository
        .getItems()
        .forEach(
            service ->
                serviceImplementations.add(
                    makeServiceImplementation(
                        service,
                        domainEntityConverterModelRepository.getItems(),
                        domainModelRepository)));
  }

  /**
   * Service implementation service implementation.
   *
   * @param service the service
   * @param domainEntityConverters the domain entity converters
   * @param domainModelRepository the domain model repository
   * @return the service implementation
   */
  private ServiceImplementation makeServiceImplementation(
      Service service,
      Set<DomainEntityConverter> domainEntityConverters,
      DomainMetamodelRepository domainModelRepository) {

    Optional<DomainEntityConverter> optionalDomainEntityConverter =
        getOptionalDomainObjectConverter(
            domainEntityConverters, new ObjectName(service.getThingName().getText()));

    if (optionalDomainEntityConverter.isPresent()) {
      DomainEntityConverter domainEntityConverter = optionalDomainEntityConverter.get();

      return makeServiceImplementation(
          service,
          domainEntityConverter.getDomainEntity(),
          aggregateRootParent(domainModelRepository, domainEntityConverter.getDomainEntity()),
          domainEntityConverter);
    } else {
      return new ServiceImplementation(
          service, new LinkedHashSet<>(), serviceMethodImplementationDeducer.deduce(service));
    }
  }

  /**
   * Make service implementation service implementation.
   *
   * @param service the service
   * @param domainEntity the domain entity
   * @param optionalParentAggregateRoot the optional parent aggregate root
   * @param domainEntityConverter the domain entity converter
   * @return the service implementation
   */
  public ServiceImplementation makeServiceImplementation(
      Service service,
      BaseDomainEntity domainEntity,
      Optional<AggregateRootPersistable> optionalParentAggregateRoot,
      DomainEntityConverter domainEntityConverter) {

    return new ServiceImplementation(
        service,
        dependencies(domainEntity, domainEntityConverter, optionalParentAggregateRoot),
        serviceMethodImplementationDeducer.deduce(service),
        Optional.of(domainEntity),
        optionalParentAggregateRoot,
        new LinkedHashSet<>(Arrays.asList(domainEntityConverter)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Dependencies set.
   *
   * @param domainEntity the domain entity
   * @param domainEntityConverter the domain entity converter
   * @param optionalAggregateRootPersistable the optional aggregate root persistable
   * @return the set
   */
  private Set<ServiceDependency> dependencies(
      BaseDomainEntity domainEntity,
      DomainEntityConverter domainEntityConverter,
      Optional<AggregateRootPersistable> optionalAggregateRootPersistable) {
    Set<ServiceDependency> dependencies = new LinkedHashSet<>();

    if (domainEntity.getOptionalPersistence().isPresent()) {
      Persistence persistence =
          domainEntity.getOptionalPersistence().orElseThrow(IllegalArgumentException::new);

      dependencies.add(
          new ServiceDependency(
              persistence.getObjectName(),
              persistence.getPackageName(),
              new VariableName(persistence.getObjectName().getText())));
    }

    if (optionalAggregateRootPersistable.isPresent()) {
      Persistence persistence = optionalAggregateRootPersistable.get().getPersistence();

      dependencies.add(
          new ServiceDependency(
              persistence.getObjectName(),
              persistence.getPackageName(),
              new VariableName(persistence.getObjectName().getText())));
    }

    dependencies.add(
        new ServiceDependency(
            domainEntityConverter.getObjectName(),
            domainEntityConverter.getPackageName(),
            domainEntityConverter.getVariableName()));

    return dependencies;
  }
}
