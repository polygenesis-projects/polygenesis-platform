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

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.ModelRepository;
import java.util.Optional;
import java.util.Set;

/**
 * The type Domain model repository.
 *
 * @author Christos Tsakostas
 */
public class DomainModelRepository implements ModelRepository {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<AggregateRoot> aggregateRoots;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain model repository.
   *
   * @param aggregateRoots the aggregate roots
   */
  public DomainModelRepository(Set<AggregateRoot> aggregateRoots) {
    setAggregateRoots(aggregateRoots);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * Add aggregate root.
   *
   * @param aggregateRoot the aggregate root
   */
  public void addAggregateRoot(AggregateRoot aggregateRoot) {
    getAggregateRoots().add(aggregateRoot);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets aggregate root by name.
   *
   * @param objectName the name
   * @return the aggregate root by name
   */
  public Optional<AggregateRoot> getAggregateRootByName(ObjectName objectName) {
    return aggregateRoots
        .stream()
        .filter(aggregateRoot -> aggregateRoot.getObjectName().equals(objectName))
        .findFirst();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets aggregate roots.
   *
   * @return the aggregate roots
   */
  public Set<AggregateRoot> getAggregateRoots() {
    return aggregateRoots;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets aggregate roots.
   *
   * @param aggregateRoots the aggregate roots
   */
  private void setAggregateRoots(Set<AggregateRoot> aggregateRoots) {
    this.aggregateRoots = aggregateRoots;
  }
}
