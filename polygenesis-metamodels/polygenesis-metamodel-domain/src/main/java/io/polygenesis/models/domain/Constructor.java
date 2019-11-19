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

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;
import java.util.Set;

/**
 * The type Constructor.
 *
 * @author Christos Tsakostas
 */
public class Constructor extends BaseMethod {

  private Set<DomainObjectProperty<?>> properties;
  private DomainEvent domainEvent;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Constructor.
   *
   * @param function the function
   * @param properties the properties
   */
  public Constructor(Function function, Set<DomainObjectProperty<?>> properties) {
    super(function);
    setProperties(properties);
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
   * Gets properties.
   *
   * @return the properties
   */
  public Set<DomainObjectProperty<?>> getProperties() {
    return properties;
  }

  /**
   * Gets domain event.
   *
   * @return the domain event
   */
  public DomainEvent getDomainEvent() {
    return domainEvent;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets properties.
   *
   * @param properties the properties
   */
  private void setProperties(Set<DomainObjectProperty<?>> properties) {
    Assertion.isNotNull(properties, "properties is required");
    this.properties = properties;
  }

  /**
   * Sets domain event.
   *
   * @param domainEvent the domain event
   */
  private void setDomainEvent(DomainEvent domainEvent) {
    Assertion.isNotNull(domainEvent, "domainEvent is required");
    this.domainEvent = domainEvent;
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
    Constructor that = (Constructor) o;
    return Objects.equals(properties, that.properties)
        && Objects.equals(domainEvent, that.domainEvent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), properties, domainEvent);
  }
}
