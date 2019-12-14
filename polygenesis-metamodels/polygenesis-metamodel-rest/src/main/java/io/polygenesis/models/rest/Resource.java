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

package io.polygenesis.models.rest;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Metamodel;
import io.polygenesis.models.api.Service;
import java.util.Set;

/**
 * The type Resource.
 *
 * @author Christos Tsakostas
 */
public class Resource implements Generatable, Metamodel {

  private PackageName packageName;
  private ObjectName objectName;
  private Set<Endpoint> endpoints;
  private Set<Service> services;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource.
   *
   * @param packageName the package name
   * @param objectName the name
   * @param endpoints the endpoints
   * @param services the services
   */
  public Resource(
      PackageName packageName,
      ObjectName objectName,
      Set<Endpoint> endpoints,
      Set<Service> services) {
    setPackageName(packageName);
    setObjectName(objectName);
    setEndpoints(endpoints);
    setServices(services);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public ObjectName getObjectName() {
    return objectName;
  }

  /**
   * Gets endpoints.
   *
   * @return the endpoints
   */
  public Set<Endpoint> getEndpoints() {
    return endpoints;
  }

  /**
   * Gets services.
   *
   * @return the services
   */
  public Set<Service> getServices() {
    return services;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(PackageName packageName) {
    this.packageName = packageName;
  }

  /**
   * Sets name.
   *
   * @param objectName the name
   */
  private void setObjectName(ObjectName objectName) {
    this.objectName = objectName;
  }

  /**
   * Sets endpoints.
   *
   * @param endpoints the endpoints
   */
  private void setEndpoints(Set<Endpoint> endpoints) {
    this.endpoints = endpoints;
  }

  /**
   * Sets services.
   *
   * @param services the services
   */
  private void setServices(Set<Service> services) {
    this.services = services;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

}
