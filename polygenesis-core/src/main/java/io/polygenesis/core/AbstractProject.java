/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractProject implements Project {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Name name;
  private Set<Context<? extends Abstraction>> contexts;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract project.
   *
   * @param name the name
   * @param contexts the contexts
   */
  protected AbstractProject(Name name, Set<Context<? extends Abstraction>> contexts) {
    setName(name);
    setContexts(contexts);
  }

  // ===============================================================================================
  // BEHAVIOR
  // ===============================================================================================

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets all contexts metamodel repositories.
   *
   * @return the all contexts metamodel repositories
   */
  public Set<MetamodelRepository<?>> getAllContextsMetamodelRepositories() {
    return getContexts().stream()
        .flatMap(context -> context.getMetamodelRepositories().stream())
        .collect(Collectors.toCollection(LinkedHashSet::new));
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
   * Gets contexts.
   *
   * @return the contexts
   */
  public Set<Context<? extends Abstraction>> getContexts() {
    return contexts;
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
   * Sets contexts.
   *
   * @param contexts the contexts
   */
  private void setContexts(Set<Context<? extends Abstraction>> contexts) {
    Assertion.isNotNull(contexts, "contexts is required");
    this.contexts = contexts;
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
    AbstractProject project = (AbstractProject) o;
    return Objects.equals(name, project.name) && Objects.equals(contexts, project.contexts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, contexts);
  }
}
