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

package io.polygenesis.deducers.apiimpl;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.apiimpl.DomainObjectConverterMetamodelRepository;
import io.polygenesis.models.apiimpl.ServiceDependency;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationMetamodelRepository;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectMetamodelRepository;
import io.polygenesis.models.domain.Persistence;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class ServiceImplementationDeducer
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

  @Override
  public ServiceImplementationMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    ServiceMetamodelRepository serviceModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, ServiceMetamodelRepository.class);

    DomainObjectMetamodelRepository domainObjectMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, DomainObjectMetamodelRepository.class);

    DomainObjectConverterMetamodelRepository domainObjectConverterMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, DomainObjectConverterMetamodelRepository.class);

    Set<ServiceImplementation> serviceImplementations = new LinkedHashSet<>();

    fillServiceImplementations(
        serviceImplementations,
        serviceModelRepository,
        domainObjectMetamodelRepository,
        domainObjectConverterMetamodelRepository);

    return new ServiceImplementationMetamodelRepository(serviceImplementations);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  private void fillServiceImplementations(
      Set<ServiceImplementation> serviceImplementations,
      ServiceMetamodelRepository serviceModelRepository,
      DomainObjectMetamodelRepository domainObjectMetamodelRepository,
      DomainObjectConverterMetamodelRepository domainObjectConverterMetamodelRepository) {
    serviceModelRepository
        .getItems()
        .forEach(
            service ->
                serviceImplementations.add(
                    makeServiceImplementation(
                        service,
                        domainObjectConverterMetamodelRepository.getItems(),
                        domainObjectMetamodelRepository)));
  }

  private ServiceImplementation makeServiceImplementation(
      Service service,
      Set<DomainObjectConverter> domainObjectConverters,
      DomainObjectMetamodelRepository domainObjectMetamodelRepository) {

    Optional<DomainObjectConverter> optionalDomainObjectConverter =
        getOptionalDomainObjectConverter(
            domainObjectConverters, new ObjectName(service.getThingName().getText()));

    DomainObject domainObject = null;
    DomainObjectConverter domainObjectConverter = null;
    DomainObject parentAggregateRoot = null;

    if (optionalDomainObjectConverter.isPresent()) {
      domainObjectConverter = optionalDomainObjectConverter.get();
      domainObject = domainObjectConverter.getDomainObject();
    } else {
      domainObject = domainObjectMetamodelRepository.findEntityByThingName(service.getThingName());
    }

    return new ServiceImplementation(
        service,
        dependencies(domainObject, domainObjectConverter, parentAggregateRoot),
        serviceMethodImplementationDeducer.deduce(service),
        domainObjectConverter != null
            ? new LinkedHashSet<>(Arrays.asList(domainObjectConverter))
            : new LinkedHashSet<>(),
        domainObject,
        parentAggregateRoot);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<ServiceDependency> dependencies(
      DomainObject domainObject,
      DomainObjectConverter domainObjectConverter,
      DomainObject parentAggregateRoot) {
    Set<ServiceDependency> dependencies = new LinkedHashSet<>();

    if (domainObject != null && domainObject.getOptionalPersistence().isPresent()) {
      Persistence persistence =
          domainObject.getOptionalPersistence().orElseThrow(IllegalArgumentException::new);

      dependencies.add(
          new ServiceDependency(
              persistence.getObjectName(),
              persistence.getPackageName(),
              new VariableName(persistence.getObjectName().getText())));
    }

    if (parentAggregateRoot != null) {
      Persistence persistence = parentAggregateRoot.getPersistence();

      dependencies.add(
          new ServiceDependency(
              persistence.getObjectName(),
              persistence.getPackageName(),
              new VariableName(persistence.getObjectName().getText())));
    }

    if (domainObjectConverter != null) {
      dependencies.add(
          new ServiceDependency(
              domainObjectConverter.getObjectName(),
              domainObjectConverter.getPackageName(),
              domainObjectConverter.getVariableName()));
    }

    return dependencies;
  }

  /**
   * Gets optional domain object converter.
   *
   * @param domainObjectConverters the domain object converters
   * @param domainObjectName the domain object name
   * @return the optional domain object converter
   */
  protected Optional<DomainObjectConverter> getOptionalDomainObjectConverter(
      Set<DomainObjectConverter> domainObjectConverters, ObjectName domainObjectName) {
    return domainObjectConverters.stream()
        .filter(
            domainObjectConverter ->
                domainObjectConverter.getDomainObject().getObjectName().equals(domainObjectName))
        .findFirst();
  }
}
