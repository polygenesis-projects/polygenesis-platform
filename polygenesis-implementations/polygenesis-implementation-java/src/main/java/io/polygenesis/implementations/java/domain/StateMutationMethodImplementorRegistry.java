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

package io.polygenesis.implementations.java.domain;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.GoalType;
import io.polygenesis.core.ThingScopeType;
import io.polygenesis.implementations.java.AbstractMethodImplementorRegistry;
import io.polygenesis.implementations.java.ScopeGoalTuple;
import io.polygenesis.models.domain.StateMutationMethod;

/**
 * The type State mutation method implementor registry.
 *
 * @author Christos Tsakostas
 */
public class StateMutationMethodImplementorRegistry
    extends AbstractMethodImplementorRegistry<StateMutationMethod> {

  // ===============================================================================================
  // CONSTRUCTORS
  // ===============================================================================================

  /**
   * Instantiates a new State mutation method implementor registry.
   *
   * @param freemarkerService the freemarker service
   */
  public StateMutationMethodImplementorRegistry(FreemarkerService freemarkerService) {
    super(freemarkerService);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public void initializeScopeAndGoalMap() {
    // AGGREGATE ROOT
    scopeAndGoalMap.put(
        new ScopeGoalTuple(
            ThingScopeType.DOMAIN_AGGREGATE_ROOT, GoalType.AGGREGATE_ROOT_CREATE_ENTITY.name()),
        new CreateAggregateEntity());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(
            ThingScopeType.DOMAIN_AGGREGATE_ROOT, GoalType.AGGREGATE_ROOT_UPDATE_ENTITY.name()),
        new UpdateAggregateEntity());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(
            ThingScopeType.DOMAIN_AGGREGATE_ROOT, GoalType.AGGREGATE_ROOT_DELETE_ENTITY.name()),
        new DeleteAggregateEntity());
  }
}
