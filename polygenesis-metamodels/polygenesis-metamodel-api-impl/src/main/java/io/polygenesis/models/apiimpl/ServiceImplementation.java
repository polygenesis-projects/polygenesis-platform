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

package io.polygenesis.models.apiimpl;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Metamodel;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.domain.DomainObject;
import java.util.Objects;
import java.util.Set;

public class ServiceImplementation implements Generatable, Metamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Service service;
  private Set<ServiceDependency> dependencies;
  private Set<ServiceMethodImplementation> serviceMethodImplementations;
  private Set<DomainObjectConverter> domainObjectConverters;

  // ===============================================================================================
  // STATE - OPTIONAL
  // ===============================================================================================
  private DomainObject domainObject;
  private DomainObject parentAggregateRoot;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation.
   *
   * @param service the service
   * @param dependencies the dependencies
   * @param serviceMethodImplementations the service method implementations
   * @param domainObjectConverters the domain object converters
   * @param domainObject the domain object
   * @param parentAggregateRoot the parent aggregate root
   */
  public ServiceImplementation(
      Service service,
      Set<ServiceDependency> dependencies,
      Set<ServiceMethodImplementation> serviceMethodImplementations,
      Set<DomainObjectConverter> domainObjectConverters,
      DomainObject domainObject,
      DomainObject parentAggregateRoot) {
    setService(service);
    setDependencies(dependencies);
    setServiceMethodImplementations(serviceMethodImplementations);
    setDomainObjectConverters(domainObjectConverters);

    if (domainObject != null) {
      setDomainObject(domainObject);
    }

    if (parentAggregateRoot != null) {
      setParentAggregateRoot(parentAggregateRoot);
    }
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
   * Gets domain object converters.
   *
   * @return the domain object converters
   */
  public Set<DomainObjectConverter> getDomainObjectConverters() {
    return domainObjectConverters;
  }

  /**
   * Gets domain object.
   *
   * @return the domain object
   */
  public DomainObject getDomainObject() {
    return domainObject;
  }

  /**
   * Gets parent aggregate root.
   *
   * @return the parent aggregate root
   */
  public DomainObject getParentAggregateRoot() {
    return parentAggregateRoot;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setService(Service service) {
    Assertion.isNotNull(service, "service is required");
    this.service = service;
  }

  private void setDependencies(Set<ServiceDependency> dependencies) {
    Assertion.isNotNull(dependencies, "dependencies is required");
    this.dependencies = dependencies;
  }

  private void setServiceMethodImplementations(
      Set<ServiceMethodImplementation> serviceMethodImplementations) {
    Assertion.isNotNull(serviceMethodImplementations, "serviceMethodImplementations is required");
    this.serviceMethodImplementations = serviceMethodImplementations;
  }

  private void setDomainObjectConverters(Set<DomainObjectConverter> domainObjectConverters) {
    Assertion.isNotNull(domainObjectConverters, "domainObjectConverters is required");
    this.domainObjectConverters = domainObjectConverters;
  }

  private void setDomainObject(DomainObject domainObject) {
    Assertion.isNotNull(domainObject, "domainObject is required");
    this.domainObject = domainObject;
  }

  private void setParentAggregateRoot(DomainObject parentAggregateRoot) {
    Assertion.isNotNull(parentAggregateRoot, "parentAggregateRoot is required");
    this.parentAggregateRoot = parentAggregateRoot;
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
        && Objects.equals(domainObjectConverters, that.domainObjectConverters)
        && Objects.equals(domainObject, that.domainObject)
        && Objects.equals(parentAggregateRoot, that.parentAggregateRoot);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        service,
        dependencies,
        serviceMethodImplementations,
        domainObjectConverters,
        domainObject,
        parentAggregateRoot);
  }
}
