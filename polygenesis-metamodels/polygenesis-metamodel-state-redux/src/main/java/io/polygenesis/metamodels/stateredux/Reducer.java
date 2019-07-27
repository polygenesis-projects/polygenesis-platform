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

package io.polygenesis.metamodels.stateredux;

import io.polygenesis.commons.assertion.Assertion;

/**
 * A Reducer is a function called when an {@link Action} is requested and its role is to alter the
 * State of the {@link Store}, if necessary.
 *
 * @author Christos Tsakostas
 */
public class Reducer {

  private Action action;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Reducer.
   *
   * @param action the action
   */
  public Reducer(Action action) {
    setAction(action);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets action.
   *
   * @return the action
   */
  public Action getAction() {
    return action;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setAction(Action action) {
    Assertion.isNotNull(action, "action is required");
    this.action = action;
  }
}
