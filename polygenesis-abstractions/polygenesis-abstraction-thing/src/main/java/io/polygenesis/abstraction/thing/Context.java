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

package io.polygenesis.abstraction.thing;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.core.AbstractionRepository;
import java.util.Objects;
import java.util.Set;

/**
 * The type Context.
 *
 * @author Christos Tsakostas
 */
public class Context {

  private Name name;
  private Set<AbstractionRepository<?>> abstractionRepositories;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Context.
   *
   * @param name the name
   * @param abstractionRepositories the abstraction repositories
   */
  public Context(Name name, Set<AbstractionRepository<?>> abstractionRepositories) {
    setName(name);
    setAbstractionRepositories(abstractionRepositories);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets name.
   *
   * @return the name
   */
  public Name getName() {
    return name;
  }

  /**
   * Gets abstraction repositories.
   *
   * @return the abstraction repositories
   */
  public Set<AbstractionRepository<?>> getAbstractionRepositories() {
    return abstractionRepositories;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

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
   * Sets abstraction repositories.
   *
   * @param abstractionRepositories the abstraction repositories
   */
  private void setAbstractionRepositories(Set<AbstractionRepository<?>> abstractionRepositories) {
    Assertion.isNotNull(abstractionRepositories, "abstractionRepositories is required");
    this.abstractionRepositories = abstractionRepositories;
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
    Context context = (Context) o;
    return Objects.equals(name, context.name)
        && Objects.equals(abstractionRepositories, context.abstractionRepositories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, abstractionRepositories);
  }
}
