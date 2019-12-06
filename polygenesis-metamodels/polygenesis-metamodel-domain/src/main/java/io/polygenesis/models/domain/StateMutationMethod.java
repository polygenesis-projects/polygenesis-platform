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

import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Objects;
import java.util.Set;

/**
 * The type State mutation command.
 *
 * @author Christos Tsakostas
 */
public class StateMutationMethod extends BaseMethod {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  private DomainObject mutatedObject;

  private Set<DomainObjectProperty<?>> properties;

  // superClassProperties is Used only for construction state mutation
  private Set<DomainObjectProperty<?>> superClassProperties;

  // Used only for Aggregate Roots only
  private DomainEvent domainEvent;

  private DataRepository returnValue;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new State mutation method.
   *
   * @param mutatedObject the mutates object
   * @param function the function
   * @param properties the properties
   * @param rootPackageName the root package name
   */
  public StateMutationMethod(
      DomainObject mutatedObject,
      Function function,
      Set<DomainObjectProperty<?>> properties,
      PackageName rootPackageName) {
    super(function);
    setProperties(properties);
    setMutatedObject(mutatedObject);
    setReturnValue(makeReturnValue(rootPackageName, function));
  }

  /**
   * Instantiates a new State mutation method.
   *
   * @param mutatedObject the mutates object
   * @param function the function
   * @param properties the properties
   * @param superClassProperties the super class properties
   * @param rootPackageName the root package name
   */
  public StateMutationMethod(
      DomainObject mutatedObject,
      Function function,
      Set<DomainObjectProperty<?>> properties,
      Set<DomainObjectProperty<?>> superClassProperties,
      PackageName rootPackageName) {
    this(mutatedObject, function, properties, rootPackageName);
    setSuperClassProperties(superClassProperties);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * Assign domain event.
   *
   * @param domainEvent the domain event
   */
  public void assignDomainEvent(DomainEvent domainEvent) {
    setDomainEvent(domainEvent);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets mutates object.
   *
   * @return the mutates object
   */
  public DomainObject getMutatedObject() {
    return mutatedObject;
  }

  /**
   * Gets properties.
   *
   * @return the properties
   */
  public Set<DomainObjectProperty<?>> getProperties() {
    return properties;
  }

  /**
   * Gets super class properties.
   *
   * @return the super class properties
   */
  public Set<DomainObjectProperty<?>> getSuperClassProperties() {
    return superClassProperties;
  }

  /**
   * Gets domain event.
   *
   * @return the domain event
   */
  public DomainEvent getDomainEvent() {
    return domainEvent;
  }

  /**
   * Gets return value.
   *
   * @return the return value
   */
  public DataRepository getReturnValue() {
    return returnValue;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private DataRepository makeReturnValue(PackageName rootPackageName, Function function) {
    DataRepository returnValueDataRepository = new DataRepository();

    if (function.getPurpose().equals(Purpose.entityCreate())
        || function.getPurpose().equals(Purpose.entityModify())
        || function.getPurpose().equals(Purpose.entityFetch())) {

      returnValueDataRepository.addData(
          function.getDelegatesToFunction().getThing().getAsDataObject(rootPackageName));
    }

    return returnValueDataRepository;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setMutatedObject(DomainObject mutatedObject) {
    // TODO: Assertion.isNotNull(mutatesObject, "mutatesObject is required");
    this.mutatedObject = mutatedObject;
  }

  private void setProperties(Set<DomainObjectProperty<?>> properties) {
    Assertion.isNotNull(properties, "properties is required");
    this.properties = properties;
  }

  private void setSuperClassProperties(Set<DomainObjectProperty<?>> superClassProperties) {
    Assertion.isNotNull(superClassProperties, "superClassProperties is required");
    this.superClassProperties = superClassProperties;
  }

  private void setDomainEvent(DomainEvent domainEvent) {
    Assertion.isNotNull(domainEvent, "domainEvent is required");
    this.domainEvent = domainEvent;
  }

  private void setReturnValue(DataRepository returnValue) {
    Assertion.isNotNull(returnValue, "returnValue is required");
    this.returnValue = returnValue;
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
    if (!super.equals(o)) {
      return false;
    }
    StateMutationMethod that = (StateMutationMethod) o;
    return Objects.equals(mutatedObject, that.mutatedObject)
        && Objects.equals(properties, that.properties)
        && Objects.equals(superClassProperties, that.superClassProperties)
        && Objects.equals(domainEvent, that.domainEvent)
        && Objects.equals(returnValue, that.returnValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), properties, superClassProperties, domainEvent);
  }
}
