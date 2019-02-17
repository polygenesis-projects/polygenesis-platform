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

package io.polygenesis.models.api;

import io.polygenesis.annotations.core.CqsType;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.datatype.PackageName;
import java.util.Objects;
import java.util.Set;

/**
 * The type Service.
 *
 * @author Christos Tsakostas
 */
public class Service {

  private PackageName packageName;
  private ServiceName serviceName;
  private Set<Method> methods;
  private CqsType cqrsType;
  private ThingName thingName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service.
   *
   * @param packageName the package name
   * @param serviceName the service name
   * @param methods the methods
   * @param cqrsType the cqrs type
   * @param thingName the thing name
   */
  public Service(
      PackageName packageName,
      ServiceName serviceName,
      Set<Method> methods,
      CqsType cqrsType,
      ThingName thingName) {
    setPackageName(packageName);
    setServiceName(serviceName);
    setMethods(methods);
    setCqrsType(cqrsType);
    setThingName(thingName);
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
  public Set<Method> getMethods() {
    return methods;
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
   * @param methods the methods
   */
  private void setMethods(Set<Method> methods) {
    this.methods = methods;
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
        && Objects.equals(methods, service.methods)
        && cqrsType == service.cqrsType
        && Objects.equals(thingName, service.thingName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(packageName, serviceName, methods, cqrsType, thingName);
  }
}
