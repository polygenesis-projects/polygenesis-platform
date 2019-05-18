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

/**
 * The type State mutation command.
 *
 * @author Christos Tsakostas
 */
public class StateMutationMethod extends BaseMethod {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private DomainEvent domainEvent;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new State mutation method.
   *
   * @param function the function
   */
  public StateMutationMethod(Function function) {
    super(function);
  }

  /**
   * Instantiates a new State mutation method.
   *
   * @param function the function
   * @param domainEvent the domain event
   */
  public StateMutationMethod(Function function, DomainEvent domainEvent) {
    super(function);
    setDomainEvent(domainEvent);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

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
    StateMutationMethod that = (StateMutationMethod) o;
    return Objects.equals(domainEvent, that.domainEvent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), domainEvent);
  }
}
