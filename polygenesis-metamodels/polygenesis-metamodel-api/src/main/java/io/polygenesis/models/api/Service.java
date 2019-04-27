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

package io.polygenesis.models.api;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.CqsType;
import io.polygenesis.core.Metamodel;
import io.polygenesis.core.ThingName;
import java.util.Objects;
import java.util.Set;

/**
 * The type Service.
 *
 * @author Christos Tsakostas
 */
public class Service implements Metamodel {

  private PackageName packageName;
  private ServiceName serviceName;
  private Set<ServiceMethod> serviceMethods;
  private CqsType cqrsType;
  private ThingName thingName;
  private Set<Dto> dtos;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service.
   *
   * @param packageName the package name
   * @param serviceName the service name
   * @param serviceMethods the methods
   * @param cqrsType the cqrs type
   * @param thingName the thing name
   * @param dtos the dtos
   */
  public Service(
      PackageName packageName,
      ServiceName serviceName,
      Set<ServiceMethod> serviceMethods,
      CqsType cqrsType,
      ThingName thingName,
      Set<Dto> dtos) {
    setPackageName(packageName);
    setServiceName(serviceName);
    setServiceMethods(serviceMethods);
    setCqrsType(cqrsType);
    setThingName(thingName);
    setDtos(dtos);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(serviceName.getText());
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
   * Gets service name.
   *
   * @return the service name
   */
  public ServiceName getServiceName() {
    return serviceName;
  }

  /**
   * Gets methods.
   *
   * @return the methods
   */
  public Set<ServiceMethod> getServiceMethods() {
    return serviceMethods;
  }

  /**
   * Gets cqrs type.
   *
   * @return the cqrs type
   */
  public CqsType getCqrsType() {
    return cqrsType;
  }

  /**
   * Gets thing name.
   *
   * @return the thing name
   */
  public ThingName getThingName() {
    return thingName;
  }

  /**
   * Gets dtos.
   *
   * @return the dtos
   */
  public Set<Dto> getDtos() {
    return dtos;
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
   * Sets service name.
   *
   * @param serviceName the service name
   */
  private void setServiceName(ServiceName serviceName) {
    this.serviceName = serviceName;
  }

  /**
   * Sets methods.
   *
   * @param serviceMethods the methods
   */
  private void setServiceMethods(Set<ServiceMethod> serviceMethods) {
    this.serviceMethods = serviceMethods;
  }

  /**
   * Sets cqrs type.
   *
   * @param cqrsType the cqrs type
   */
  private void setCqrsType(CqsType cqrsType) {
    this.cqrsType = cqrsType;
  }

  /**
   * Sets thing name.
   *
   * @param thingName the thing name
   */
  private void setThingName(ThingName thingName) {
    this.thingName = thingName;
  }

  /**
   * Sets dtos.
   *
   * @param dtos the dtos
   */
  private void setDtos(Set<Dto> dtos) {
    this.dtos = dtos;
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
    Service service = (Service) o;
    return Objects.equals(packageName, service.packageName)
        && Objects.equals(serviceName, service.serviceName)
        && Objects.equals(serviceMethods, service.serviceMethods)
        && cqrsType == service.cqrsType
        && Objects.equals(thingName, service.thingName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(packageName, serviceName, serviceMethods, cqrsType, thingName);
  }
}
