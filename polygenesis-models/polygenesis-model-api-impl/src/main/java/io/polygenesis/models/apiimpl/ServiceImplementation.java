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

package io.polygenesis.models.apiimpl;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.domain.BaseDomainObject;
import java.util.Objects;
import java.util.Set;

/**
 * The type Service implementation.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementation {

  private Service service;
  private Set<ServiceDependency> dependencies;
  private Set<BaseDomainObject<?>> aggregateRoots;
  private Set<DomainObjectConverter> domainObjectConverters;
  private Set<ServiceMethodImplementation> serviceMethodImplementations;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation.
   *
   * @param service the service
   * @param dependencies the dependencies
   * @param aggregateRoots the aggregate roots
   * @param domainObjectConverters the aggregate root converters
   * @param serviceMethodImplementations the service method implementations
   */
  public ServiceImplementation(
      Service service,
      Set<ServiceDependency> dependencies,
      Set<BaseDomainObject<?>> aggregateRoots,
      Set<DomainObjectConverter> domainObjectConverters,
      Set<ServiceMethodImplementation> serviceMethodImplementations) {
    setService(service);
    setDependencies(dependencies);
    setAggregateRoots(aggregateRoots);
    setDomainObjectConverters(domainObjectConverters);
    setServiceMethodImplementations(serviceMethodImplementations);
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
    return domainObjectConverters
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
   * Gets aggregate roots.
   *
   * @return the aggregate roots
   */
  public Set<BaseDomainObject<?>> getAggregateRoots() {
    return aggregateRoots;
  }

  /**
   * Gets aggregate root converters.
   *
   * @return the aggregate root converters
   */
  public Set<DomainObjectConverter> getDomainObjectConverters() {
    return domainObjectConverters;
  }

  /**
   * Gets service method implementations.
   *
   * @return the service method implementations
   */
  public Set<ServiceMethodImplementation> getServiceMethodImplementations() {
    return serviceMethodImplementations;
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
   * Sets aggregate roots.
   *
   * @param aggregateRoots the aggregate roots
   */
  private void setAggregateRoots(Set<BaseDomainObject<?>> aggregateRoots) {
    Assertion.isNotNull(aggregateRoots, "aggregateRoots is required");
    this.aggregateRoots = aggregateRoots;
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
        && Objects.equals(aggregateRoots, that.aggregateRoots)
        && Objects.equals(domainObjectConverters, that.domainObjectConverters)
        && Objects.equals(serviceMethodImplementations, that.serviceMethodImplementations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        service,
        dependencies,
        aggregateRoots,
        domainObjectConverters,
        serviceMethodImplementations);
  }
}
