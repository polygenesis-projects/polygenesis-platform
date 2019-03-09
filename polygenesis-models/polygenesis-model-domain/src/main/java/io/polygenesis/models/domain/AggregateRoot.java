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

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.data.PackageName;
import java.util.Set;

/**
 * The type Aggregate root.
 *
 * @author Christos Tsakostas
 */
public class AggregateRoot {

  private PackageName packageName;
  private Name name;
  private Set<AbstractProperty> properties;
  private Set<StateMutationMethod> stateMutationMethods;
  private Set<StateQueryMethod> stateQueryMethods;
  private Persistence persistence;
  private Set<Constructor> constructors;
  private Boolean multiTenant;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root.
   *
   * @param packageName the package name
   * @param name the name
   * @param properties the properties
   * @param persistence the persistence
   * @param constructors the constructors
   */
  public AggregateRoot(
      PackageName packageName,
      Name name,
      Set<AbstractProperty> properties,
      Persistence persistence,
      Set<Constructor> constructors,
      Boolean multiTenant) {
    setPackageName(packageName);
    setName(name);
    setProperties(properties);
    setPersistence(persistence);
    setConstructors(constructors);
    setMultiTenant(multiTenant);
  }

  /**
   * Instantiates a new Aggregate root.
   *
   * @param packageName the package name
   * @param name the name
   * @param properties the properties
   * @param stateMutationMethods the state mutation methods
   * @param stateQueryMethods the state query methods
   * @param persistence the persistence
   * @param constructors the constructors
   */
  public AggregateRoot(
      PackageName packageName,
      Name name,
      Set<AbstractProperty> properties,
      Set<StateMutationMethod> stateMutationMethods,
      Set<StateQueryMethod> stateQueryMethods,
      Persistence persistence,
      Set<Constructor> constructors,
      Boolean multiTenant) {
    setPackageName(packageName);
    setName(name);
    setProperties(properties);
    setStateMutationMethods(stateMutationMethods);
    setStateQueryMethods(stateQueryMethods);
    setPersistence(persistence);
    setConstructors(constructors);
    setMultiTenant(multiTenant);
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
   * @param name the name
   */
  private void setName(Name name) {
    this.name = name;
  }

  /**
   * Sets properties.
   *
   * @param properties the properties
   */
  private void setProperties(Set<AbstractProperty> properties) {
    this.properties = properties;
  }

  /**
   * Sets state mutation methods.
   *
   * @param stateMutationMethods the state mutation methods
   */
  private void setStateMutationMethods(Set<StateMutationMethod> stateMutationMethods) {
    this.stateMutationMethods = stateMutationMethods;
  }

  /**
   * Sets state query methods.
   *
   * @param stateQueryMethods the state query methods
   */
  private void setStateQueryMethods(Set<StateQueryMethod> stateQueryMethods) {
    this.stateQueryMethods = stateQueryMethods;
  }

  /**
   * Sets persistence.
   *
   * @param persistence the persistence
   */
  private void setPersistence(Persistence persistence) {
    this.persistence = persistence;
  }

  /**
   * Sets constructors.
   *
   * @param constructors the constructors
   */
  private void setConstructors(Set<Constructor> constructors) {
    this.constructors = constructors;
  }

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   */
  private void setMultiTenant(Boolean multiTenant) {
    this.multiTenant = multiTenant;
  }
}
