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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * The type Abstract domain object.
 *
 * @author Christos Tsakostas
 */
public abstract class BaseDomainObject implements SubClassable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private DomainObjectType domainObjectType;
  private InstantiationType instantiationType;
  private ObjectName objectName;
  private PackageName packageName;

  @SuppressWarnings("rawtypes")
  private Set<DomainObjectProperty> properties;

  private Set<Constructor> constructors;
  private Boolean multiTenant;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Base domain object.
   *
   * @param domainObjectType the domain object type
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param multiTenant the multi tenant
   */
  @SuppressWarnings("rawtypes")
  public BaseDomainObject(
      DomainObjectType domainObjectType,
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty> properties,
      Set<Constructor> constructors,
      Boolean multiTenant) {
    setDomainObjectType(domainObjectType);
    setInstantiationType(instantiationType);
    setObjectName(objectName);
    setPackageName(packageName);
    setProperties(properties);
    setConstructors(constructors);
    setMultiTenant(multiTenant);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets optional persistence.
   *
   * @return the optional persistence
   */
  public Optional<Persistence> getOptionalPersistence() {
    return Optional.empty();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets domain object type.
   *
   * @return the domain object type
   */
  public DomainObjectType getDomainObjectType() {
    return domainObjectType;
  }

  /**
   * Gets instantiation type.
   *
   * @return the instantiation type
   */
  public InstantiationType getInstantiationType() {
    return instantiationType;
  }

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
   * Gets properties.
   *
   * @return the properties
   */
  @SuppressWarnings("rawtypes")
  public Set<DomainObjectProperty> getProperties() {
    return properties;
  }

  /**
   * Gets constructors.
   *
   * @return the constructors
   */
  public Set<Constructor> getConstructors() {
    return constructors;
  }

  /**
   * Gets multi tenant.
   *
   * @return the multi tenant
   */
  public Boolean getMultiTenant() {
    return multiTenant;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets domain object type.
   *
   * @param domainObjectType the domain object type
   */
  private void setDomainObjectType(DomainObjectType domainObjectType) {
    Assertion.isNotNull(domainObjectType, "domainObjectType is required");
    this.domainObjectType = domainObjectType;
  }

  /**
   * Sets instantiation type.
   *
   * @param instantiationType the instantiation type
   */
  private void setInstantiationType(InstantiationType instantiationType) {
    Assertion.isNotNull(instantiationType, "instantiationType is required");
    this.instantiationType = instantiationType;
  }

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
   * Sets properties.
   *
   * @param properties the properties
   */
  @SuppressWarnings("rawtypes")
  private void setProperties(Set<DomainObjectProperty> properties) {
    Assertion.isNotNull(properties, "properties is required");
    this.properties = properties;
  }

  /**
   * Sets constructors.
   *
   * @param constructors the constructors
   */
  private void setConstructors(Set<Constructor> constructors) {
    Assertion.isNotNull(constructors, "constructors is required");
    this.constructors = constructors;
  }

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   */
  private void setMultiTenant(Boolean multiTenant) {
    Assertion.isNotNull(multiTenant, "multiTenant is required");
    this.multiTenant = multiTenant;
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
    BaseDomainObject that = (BaseDomainObject) o;
    return domainObjectType == that.domainObjectType
        && instantiationType == that.instantiationType
        && Objects.equals(objectName, that.objectName)
        && Objects.equals(packageName, that.packageName)
        && Objects.equals(properties, that.properties)
        && Objects.equals(constructors, that.constructors)
        && Objects.equals(multiTenant, that.multiTenant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        domainObjectType,
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant);
  }
}
