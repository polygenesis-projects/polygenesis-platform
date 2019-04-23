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
 * The type Aggregate root persistable.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootPersistable extends AggregateRoot {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Persistence persistence;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root persistable.
   *
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param multiTenant the multi tenant
   * @param stateMutationMethods the state mutation methods
   * @param stateQueryMethods the state query methods
   * @param factoryMethods the factory methods
   * @param superClass the super class
   * @param persistence the persistence
   */
  @SuppressWarnings("rawtypes")
  public AggregateRootPersistable(
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty> properties,
      Set<Constructor> constructors,
      Boolean multiTenant,
      Set<StateMutationMethod> stateMutationMethods,
      Set<StateQueryMethod> stateQueryMethods,
      Set<FactoryMethod> factoryMethods,
      AggregateRoot superClass,
      Persistence persistence) {
    super(
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant,
        stateMutationMethods,
        stateQueryMethods,
        factoryMethods,
        superClass);
    setPersistence(persistence);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets persistence.
   *
   * @return the persistence
   */
  public Persistence getPersistence() {
    return persistence;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets persistence.
   *
   * @param persistence the persistence
   */
  private void setPersistence(Persistence persistence) {
    Assertion.isNotNull(persistence, "persistence is required");
    this.persistence = persistence;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Optional<Persistence> getOptionalPersistence() {
    return Optional.of(getPersistence());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    AggregateRootPersistable that = (AggregateRootPersistable) o;
    return Objects.equals(persistence, that.persistence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), persistence);
  }
}
