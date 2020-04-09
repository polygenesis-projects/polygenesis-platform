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

package io.polygenesis.metamodels.stateredux;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;
import java.util.Set;

public class ReducerGroup {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private Set<ActionGroup> actionGroups;
  private ReducerState reducerState;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Reducer group.
   *
   * @param actionGroups the action groups
   * @param reducerState the reducer state
   */
  public ReducerGroup(Set<ActionGroup> actionGroups, ReducerState reducerState) {
    setActionGroups(actionGroups);
    setReducerState(reducerState);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets action groups.
   *
   * @return the action groups
   */
  public Set<ActionGroup> getActionGroups() {
    return actionGroups;
  }

  /**
   * Gets reducer state.
   *
   * @return the reducer state
   */
  public ReducerState getReducerState() {
    return reducerState;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets action groups.
   *
   * @param actionGroups the action groups
   */
  private void setActionGroups(Set<ActionGroup> actionGroups) {
    Assertion.isNotNull(actionGroups, "actionGroups is required");
    this.actionGroups = actionGroups;
  }

  /**
   * Sets reducer state.
   *
   * @param reducerState the reducer state
   */
  private void setReducerState(ReducerState reducerState) {
    Assertion.isNotNull(reducerState, "reducerState is required");
    this.reducerState = reducerState;
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
    ReducerGroup that = (ReducerGroup) o;
    return Objects.equals(actionGroups, that.actionGroups)
        && Objects.equals(reducerState, that.reducerState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionGroups, reducerState);
  }
}
