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

package io.polygenesis.implementations.java;

import io.polygenesis.core.ThingScopeType;
import java.util.Objects;

/**
 * The type Scope goal tuple.
 *
 * @author Christos Tsakostas
 */
public class ScopeGoalTuple {

  private ThingScopeType thingScopeType;
  private String goal;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scope goal tuple.
   *
   * @param thingScopeType the thing scope type
   * @param goal the goal
   */
  public ScopeGoalTuple(ThingScopeType thingScopeType, String goal) {
    this.thingScopeType = thingScopeType;
    this.goal = goal;
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
    ScopeGoalTuple that = (ScopeGoalTuple) o;
    return thingScopeType == that.thingScopeType && Objects.equals(goal, that.goal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(thingScopeType, goal);
  }
}
