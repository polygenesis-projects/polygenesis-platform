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

package io.polygenesis.core;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.Name;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Context builder.
 *
 * @author Christos Tsakostas
 */
public class ContextBuilder {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private Name name;
  private Set<AbstractionRepository<?>> abstractionRepositories = new LinkedHashSet<>();

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ContextBuilder(String name) {
    Assertion.isNotNull(name, "name is required");
    this.name = new Name(name);
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Add abstraction repository context builder.
   *
   * @param repository the repository
   * @return the context builder
   */
  public ContextBuilder addAbstractionRepository(AbstractionRepository<?> repository) {
    this.abstractionRepositories.add(repository);
    return this;
  }

  // ===============================================================================================
  // BUILD
  // ===============================================================================================

  /**
   * Build context.
   *
   * @return the context
   */
  public Context build() {
    return new Context(name, abstractionRepositories);
  }
}
