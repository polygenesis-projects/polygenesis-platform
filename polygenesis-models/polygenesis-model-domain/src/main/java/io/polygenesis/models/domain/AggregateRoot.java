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

package io.polygenesis.models.domain;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.commons.text.Name;
import io.polygenesis.core.data.PackageName;
import java.util.Objects;
import java.util.Set;

/**
 * The type Aggregate root.
 *
 * @author Christos Tsakostas
 */
public class AggregateRoot {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * No super class aggregate root.
   *
   * @return the aggregate root
   */
  public static AggregateRoot noSuperClass() {
    return new AggregateRoot();
  }

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private AggregateRoot superclass;
  private InstantiationType instantiationType;
  private PackageName packageName;
  private Name name;
  private Set<AbstractProperty> properties;
  private Set<Constructor> constructors;
  private Set<StateMutationMethod> stateMutationMethods;
  private Set<StateQueryMethod> stateQueryMethods;
  private Set<FactoryMethod> factoryMethods;
  private Persistence persistence;
  private Boolean multiTenant;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private AggregateRoot() {
    super();
  }

  /**
   * Instantiates a new Aggregate root without Persistence, usually for abstract aggregate roots.
   *
   * @param superclass the superclass
   * @param instantiationType the instantiation type
   * @param packageName the package name
   * @param name the name
   * @param properties the properties
   * @param constructors the constructors
   * @param stateMutationMethods the state mutation methods
   * @param stateQueryMethods the state query methods
   * @param factoryMethods the factory methods
   * @param multiTenant the multi tenant
   */
  public AggregateRoot(
      AggregateRoot superclass,
      InstantiationType instantiationType,
      PackageName packageName,
      Name name,
      Set<AbstractProperty> properties,
      Set<Constructor> constructors,
      Set<StateMutationMethod> stateMutationMethods,
      Set<StateQueryMethod> stateQueryMethods,
      Set<FactoryMethod> factoryMethods,
      Boolean multiTenant) {
    setSuperclass(superclass);
    setInstantiationType(instantiationType);
    setPackageName(packageName);
    setName(name);
    setProperties(properties);
    setConstructors(constructors);
    setStateMutationMethods(stateMutationMethods);
    setStateQueryMethods(stateQueryMethods);
    setFactoryMethods(factoryMethods);
    setMultiTenant(multiTenant);
  }

  /**
   * Instantiates a new Aggregate root.
   *
   * @param superclass the superclass
   * @param instantiationType the instantiation type
   * @param packageName the package name
   * @param name the name
   * @param properties the properties
   * @param constructors the constructors
   * @param stateMutationMethods the state mutation methods
   * @param stateQueryMethods the state query methods
   * @param factoryMethods the factory methods
   * @param persistence the persistence
   * @param multiTenant the multi tenant
   */
  public AggregateRoot(
      AggregateRoot superclass,
      InstantiationType instantiationType,
      PackageName packageName,
      Name name,
      Set<AbstractProperty> properties,
      Set<Constructor> constructors,
      Set<StateMutationMethod> stateMutationMethods,
      Set<StateQueryMethod> stateQueryMethods,
      Set<FactoryMethod> factoryMethods,
      Persistence persistence,
      Boolean multiTenant) {
    setSuperclass(superclass);
    setInstantiationType(instantiationType);
    setPackageName(packageName);
    setName(name);
    setProperties(properties);
    setConstructors(constructors);
    setStateMutationMethods(stateMutationMethods);
    setStateQueryMethods(stateQueryMethods);
    setFactoryMethods(factoryMethods);
    setPersistence(persistence);
    setMultiTenant(multiTenant);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * Change superclass to.
   *
   * @param superclass the superclass
   */
  public void changeSuperclassTo(AggregateRoot superclass) {
    setSuperclass(superclass);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Has superclass boolean.
   *
   * @return the boolean
   */
  public boolean hasSuperclass() {
    return getSuperclass().getName() != null;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets superclass.
   *
   * @return the superclass
   */
  public AggregateRoot getSuperclass() {
    return superclass;
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
  public Name getName() {
    return name;
  }

  /**
   * Gets properties.
   *
   * @return the properties
   */
  public Set<AbstractProperty> getProperties() {
    return properties;
  }

  /**
   * Gets state mutation methods.
   *
   * @return the state mutation methods
   */
  public Set<StateMutationMethod> getStateMutationMethods() {
    return stateMutationMethods;
  }

  /**
   * Gets state query methods.
   *
   * @return the state query methods
   */
  public Set<StateQueryMethod> getStateQueryMethods() {
    return stateQueryMethods;
  }

  /**
   * Gets factory methods.
   *
   * @return the factory methods
   */
  public Set<FactoryMethod> getFactoryMethods() {
    return factoryMethods;
  }

  /**
   * Gets persistence.
   *
   * @return the persistence
   */
  public Persistence getPersistence() {
    return persistence;
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
   * Sets superclass.
   *
   * @param superclass the superclass
   */
  private void setSuperclass(AggregateRoot superclass) {
    Assertion.isNotNull(superclass, "superclass is required");
    this.superclass = superclass;
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
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(PackageName packageName) {
    Assertion.isNotNull(packageName, "packageName is required");
    this.packageName = packageName;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(Name name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets properties.
   *
   * @param properties the properties
   */
  private void setProperties(Set<AbstractProperty> properties) {
    Assertion.isNotNull(properties, "properties is required");
    this.properties = properties;
  }

  /**
   * Sets state mutation methods.
   *
   * @param stateMutationMethods the state mutation methods
   */
  private void setStateMutationMethods(Set<StateMutationMethod> stateMutationMethods) {
    Assertion.isNotNull(stateMutationMethods, "stateMutationMethods is required");
    this.stateMutationMethods = stateMutationMethods;
  }

  /**
   * Sets state query methods.
   *
   * @param stateQueryMethods the state query methods
   */
  private void setStateQueryMethods(Set<StateQueryMethod> stateQueryMethods) {
    Assertion.isNotNull(stateQueryMethods, "stateQueryMethods is required");
    this.stateQueryMethods = stateQueryMethods;
  }

  /**
   * Sets factory methods.
   *
   * @param factoryMethods the factory methods
   */
  private void setFactoryMethods(Set<FactoryMethod> factoryMethods) {
    Assertion.isNotNull(factoryMethods, "factoryMethods is required");
    this.factoryMethods = factoryMethods;
  }

  /**
   * Sets persistence.
   *
   * @param persistence the persistence
   */
  private void setPersistence(Persistence persistence) {
    Assertion.isNotNull(persistence, "persistence is required");
    this.persistence = persistence;
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
    AggregateRoot that = (AggregateRoot) o;
    return Objects.equals(superclass, that.superclass)
        && instantiationType == that.instantiationType
        && Objects.equals(packageName, that.packageName)
        && Objects.equals(name, that.name)
        && Objects.equals(properties, that.properties)
        && Objects.equals(constructors, that.constructors)
        && Objects.equals(stateMutationMethods, that.stateMutationMethods)
        && Objects.equals(stateQueryMethods, that.stateQueryMethods)
        && Objects.equals(factoryMethods, that.factoryMethods)
        && Objects.equals(persistence, that.persistence)
        && Objects.equals(multiTenant, that.multiTenant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        superclass,
        instantiationType,
        packageName,
        name,
        properties,
        constructors,
        stateMutationMethods,
        stateQueryMethods,
        factoryMethods,
        persistence,
        multiTenant);
  }
}
