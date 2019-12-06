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
import io.polygenesis.core.Metamodel;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.domain.DomainObject;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * The type Service implementation.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementation implements Metamodel {

  private Service service;
  private Set<ServiceDependency> dependencies;
  private Set<ServiceMethodImplementation> serviceMethodImplementations;

  private Optional<DomainObject> optionalDomainObject;
  private Optional<DomainObject> optionalParentAggregateRoot;
  private Set<DomainObjectConverter> domainObjectConverters;

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
   * @param optionalDomainObject the optional domain entity
   * @param optionalParentAggregateRoot the optional parent aggregate root
   * @param domainObjectConverters the domain entity converters
   */
  public ServiceImplementation(
      Service service,
      Set<ServiceDependency> dependencies,
      Set<ServiceMethodImplementation> serviceMethodImplementations,
      Optional<DomainObject> optionalDomainObject,
      Optional<DomainObject> optionalParentAggregateRoot,
      Set<DomainObjectConverter> domainObjectConverters) {
    setService(service);
    setDependencies(dependencies);
    setServiceMethodImplementations(serviceMethodImplementations);
    this.optionalDomainObject = optionalDomainObject;
    this.optionalParentAggregateRoot = optionalParentAggregateRoot;
    setDomainObjectConverters(domainObjectConverters);
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
  public DomainObjectConverter domainObjectConverter() {
    return domainObjectConverters.stream().findFirst().orElse(null);
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
  public Optional<DomainObject> getOptionalDomainObject() {
    return optionalDomainObject;
  }

  /**
   * Gets optional parent aggregate root.
   *
   * @return the optional parent aggregate root
   */
  public Optional<DomainObject> getOptionalParentAggregateRoot() {
    return optionalParentAggregateRoot;
  }

  /**
   * Gets domain entity converters.
   *
   * @return the domain entity converters
   */
  public Set<DomainObjectConverter> getDomainObjectConverters() {
    return domainObjectConverters;
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
   * @param optionalDomainObject the optional domain entity
   */
  public void setOptionalDomainObject(Optional<DomainObject> optionalDomainObject) {
    Assertion.isNotNull(optionalDomainObject, "optionalDomainObject is required");
    this.optionalDomainObject = optionalDomainObject;
  }

  /**
   * Sets optional parent aggregate root.
   *
   * @param optionalParentAggregateRoot the optional parent aggregate root
   */
  public void setOptionalParentAggregateRoot(Optional<DomainObject> optionalParentAggregateRoot) {
    Assertion.isNotNull(optionalParentAggregateRoot, "optionalParentAggregateRoot is required");
    this.optionalParentAggregateRoot = optionalParentAggregateRoot;
  }

  /**
   * Sets aggregate root converters.
   *
   * @param domainObjectConverters the aggregate root converters
   */
  private void setDomainObjectConverters(Set<DomainObjectConverter> domainObjectConverters) {
    Assertion.isNotNull(domainObjectConverters, "domainObjectConverters is required");
    this.domainObjectConverters = domainObjectConverters;
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
        && Objects.equals(optionalDomainObject, that.optionalDomainObject)
        && Objects.equals(optionalParentAggregateRoot, that.optionalParentAggregateRoot)
        && Objects.equals(domainObjectConverters, that.domainObjectConverters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        service,
        dependencies,
        serviceMethodImplementations,
        optionalDomainObject,
        optionalParentAggregateRoot,
        domainObjectConverters);
  }
}
