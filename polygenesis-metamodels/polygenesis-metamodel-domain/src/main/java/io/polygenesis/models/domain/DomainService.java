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

package io.polygenesis.models.domain;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Metamodel;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Domain service.
 *
 * @author Christos Tsakostas
 */
public class DomainService implements Metamodel, Generatable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private ObjectName objectName;
  private PackageName packageName;
  private Set<DomainServiceMethod> domainServiceMethods;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public DomainService(ObjectName objectName, PackageName packageName) {
    setObjectName(objectName);
    setPackageName(packageName);
    setDomainServiceMethods(new LinkedHashSet<>());
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * Append domain service method.
   *
   * @param domainServiceMethod the domain service method
   */
  public void appendDomainServiceMethod(DomainServiceMethod domainServiceMethod) {
    getDomainServiceMethods().add(domainServiceMethod);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets object name.
   *
   * @return the object name
   */
  public ObjectName getObjectName() {
    return objectName;
  }

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
  }

  /**
   * Gets domain service methods.
   *
   * @return the domain service methods
   */
  public Set<DomainServiceMethod> getDomainServiceMethods() {
    return domainServiceMethods;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets object name.
   *
   * @param objectName the object name
   */
  private void setObjectName(ObjectName objectName) {
    Assertion.isNotNull(objectName, "objectName is required");
    this.objectName = objectName;
  }

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(PackageName packageName) {
    Assertion.isNotNull(packageName, "packageName is required");
    this.packageName = packageName;
  }

  /**
   * Sets domain service methods.
   *
   * @param domainServiceMethods the domain service methods
   */
  private void setDomainServiceMethods(Set<DomainServiceMethod> domainServiceMethods) {
    Assertion.isNotNull(domainServiceMethods, "domainServiceMethods is required");
    this.domainServiceMethods = domainServiceMethods;
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
    DomainService that = (DomainService) o;
    return Objects.equals(objectName, that.objectName)
        && Objects.equals(packageName, that.packageName)
        && Objects.equals(domainServiceMethods, that.domainServiceMethods);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectName, packageName, domainServiceMethods);
  }
}
