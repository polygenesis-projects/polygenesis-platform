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

package io.polygenesis.models.apiimpl;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Model;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.BaseDomainEntity;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * The type Service implementation.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementation implements Model {

  private Service service;
  private Set<ServiceDependency> dependencies;
  private Set<ServiceMethodImplementation> serviceMethodImplementations;

  private Optional<BaseDomainEntity<?>> optionalDomainEntity;
  private Optional<AggregateRootPersistable> optionalParentAggregateRoot;
  private Set<DomainEntityConverter> domainEntityConverters;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation.
   *
   * @param service the service
   * @param dependencies the dependencies
   * @param serviceMethodImplementations the service method implementations
   */
  public ServiceImplementation(
      Service service,
      Set<ServiceDependency> dependencies,
      Set<ServiceMethodImplementation> serviceMethodImplementations) {
    this(
        service,
        dependencies,
        serviceMethodImplementations,
        Optional.empty(),
        Optional.empty(),
        new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Service implementation.
   *
   * @param service the service
   * @param dependencies the dependencies
   * @param serviceMethodImplementations the service method implementations
   * @param optionalDomainEntity the optional domain entity
   * @param optionalParentAggregateRoot the optional parent aggregate root
   * @param domainEntityConverters the domain entity converters
   */
  public ServiceImplementation(
      Service service,
      Set<ServiceDependency> dependencies,
      Set<ServiceMethodImplementation> serviceMethodImplementations,
      Optional<BaseDomainEntity<?>> optionalDomainEntity,
      Optional<AggregateRootPersistable> optionalParentAggregateRoot,
      Set<DomainEntityConverter> domainEntityConverters) {
    setService(service);
    setDependencies(dependencies);
    setServiceMethodImplementations(serviceMethodImplementations);
    this.optionalDomainEntity = optionalDomainEntity;
    this.optionalParentAggregateRoot = optionalParentAggregateRoot;
    setDomainEntityConverters(domainEntityConverters);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    throw new UnsupportedOperationException();
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Domain object converter domain object converter.
   *
   * @return the domain object converter
   */
  public DomainEntityConverter domainObjectConverter() {
    return domainEntityConverters
        .stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException());
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets service.
   *
   * @return the service
   */
  public Service getService() {
    return service;
  }

  /**
   * Gets dependencies.
   *
   * @return the dependencies
   */
  public Set<ServiceDependency> getDependencies() {
    return dependencies;
  }

  /**
   * Gets service method implementations.
   *
   * @return the service method implementations
   */
  public Set<ServiceMethodImplementation> getServiceMethodImplementations() {
    return serviceMethodImplementations;
  }

  /**
   * Gets optional domain entity.
   *
   * @return the optional domain entity
   */
  public Optional<BaseDomainEntity<?>> getOptionalDomainEntity() {
    return optionalDomainEntity;
  }

  /**
   * Gets optional parent aggregate root.
   *
   * @return the optional parent aggregate root
   */
  public Optional<AggregateRootPersistable> getOptionalParentAggregateRoot() {
    return optionalParentAggregateRoot;
  }

  /**
   * Gets domain entity converters.
   *
   * @return the domain entity converters
   */
  public Set<DomainEntityConverter> getDomainEntityConverters() {
    return domainEntityConverters;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets service.
   *
   * @param service the service
   */
  private void setService(Service service) {
    Assertion.isNotNull(service, "service is required");
    this.service = service;
  }

  /**
   * Sets dependencies.
   *
   * @param dependencies the dependencies
   */
  private void setDependencies(Set<ServiceDependency> dependencies) {
    Assertion.isNotNull(dependencies, "dependencies is required");
    this.dependencies = dependencies;
  }

  /**
   * Sets service method implementations.
   *
   * @param serviceMethodImplementations the service method implementations
   */
  private void setServiceMethodImplementations(
      Set<ServiceMethodImplementation> serviceMethodImplementations) {
    Assertion.isNotNull(serviceMethodImplementations, "serviceMethodImplementations is required");
    this.serviceMethodImplementations = serviceMethodImplementations;
  }

  /**
   * Sets optional domain entity.
   *
   * @param optionalDomainEntity the optional domain entity
   */
  public void setOptionalDomainEntity(Optional<BaseDomainEntity<?>> optionalDomainEntity) {
    Assertion.isNotNull(optionalDomainEntity, "optionalDomainEntity is required");
    this.optionalDomainEntity = optionalDomainEntity;
  }

  /**
   * Sets optional parent aggregate root.
   *
   * @param optionalParentAggregateRoot the optional parent aggregate root
   */
  public void setOptionalParentAggregateRoot(
      Optional<AggregateRootPersistable> optionalParentAggregateRoot) {
    Assertion.isNotNull(optionalParentAggregateRoot, "optionalParentAggregateRoot is required");
    this.optionalParentAggregateRoot = optionalParentAggregateRoot;
  }

  /**
   * Sets aggregate root converters.
   *
   * @param domainEntityConverters the aggregate root converters
   */
  private void setDomainEntityConverters(Set<DomainEntityConverter> domainEntityConverters) {
    Assertion.isNotNull(domainEntityConverters, "domainEntityConverters is required");
    this.domainEntityConverters = domainEntityConverters;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceImplementation that = (ServiceImplementation) o;
    return Objects.equals(service, that.service)
        && Objects.equals(dependencies, that.dependencies)
        && Objects.equals(serviceMethodImplementations, that.serviceMethodImplementations)
        && Objects.equals(optionalDomainEntity, that.optionalDomainEntity)
        && Objects.equals(optionalParentAggregateRoot, that.optionalParentAggregateRoot)
        && Objects.equals(domainEntityConverters, that.domainEntityConverters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        service,
        dependencies,
        serviceMethodImplementations,
        optionalDomainEntity,
        optionalParentAggregateRoot,
        domainEntityConverters);
  }
}
