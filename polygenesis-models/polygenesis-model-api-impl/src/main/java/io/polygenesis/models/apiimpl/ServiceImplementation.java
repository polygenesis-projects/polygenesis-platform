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

import io.polygenesis.models.api.Service;
import io.polygenesis.models.domain.AggregateRoot;
import java.util.Objects;
import java.util.Set;

/**
 * The type Service implementation.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementation {

  private Service service;
  private Set<Dependency> dependencies;
  private AggregateRoot aggregateRoot;
  private AggregateRootConverter aggregateRootConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation.
   *
   * @param service the service
   * @param dependencies the dependencies
   * @param aggregateRoot the aggregate root
   * @param aggregateRootConverter the aggregate root converter
   */
  public ServiceImplementation(
      Service service,
      Set<Dependency> dependencies,
      AggregateRoot aggregateRoot,
      AggregateRootConverter aggregateRootConverter) {
    setService(service);
    setDependencies(dependencies);
    setAggregateRoot(aggregateRoot);
    setAggregateRootConverter(aggregateRootConverter);
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
  public Set<Dependency> getDependencies() {
    return dependencies;
  }

  /**
   * Gets aggregate root.
   *
   * @return the aggregate root
   */
  public AggregateRoot getAggregateRoot() {
    return aggregateRoot;
  }

  /**
   * Gets aggregate root converter.
   *
   * @return the aggregate root converter
   */
  public AggregateRootConverter getAggregateRootConverter() {
    return aggregateRootConverter;
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
    this.service = service;
  }

  /**
   * Sets dependencies.
   *
   * @param dependencies the dependencies
   */
  private void setDependencies(Set<Dependency> dependencies) {
    this.dependencies = dependencies;
  }

  /**
   * Sets aggregate root.
   *
   * @param aggregateRoot the aggregate root
   */
  private void setAggregateRoot(AggregateRoot aggregateRoot) {
    this.aggregateRoot = aggregateRoot;
  }

  /**
   * Sets aggregate root converter.
   *
   * @param aggregateRootConverter the aggregate root converter
   */
  private void setAggregateRootConverter(AggregateRootConverter aggregateRootConverter) {
    this.aggregateRootConverter = aggregateRootConverter;
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
        && Objects.equals(aggregateRoot, that.aggregateRoot)
        && Objects.equals(aggregateRootConverter, that.aggregateRootConverter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(service, dependencies, aggregateRoot, aggregateRootConverter);
  }
}
