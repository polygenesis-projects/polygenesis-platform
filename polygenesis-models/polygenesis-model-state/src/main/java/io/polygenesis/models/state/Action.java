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

package io.polygenesis.models.state;

import io.polygenesis.commons.assertions.Assertion;
import io.polygenesis.commons.text.Text;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Models a State Management Action.
 *
 * @author Christos Tsakostas
 */
public class Action {

  private ActionType actionType;
  private Text name;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action.
   *
   * @param actionType the action type
   * @param name the name
   */
  public Action(ActionType actionType, Text name) {
    setActionType(actionType);
    setName(name);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets action type.
   *
   * @return the action type
   */
  public ActionType getActionType() {
    return actionType;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public Text getName() {
    return name;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================
  private void setActionType(ActionType actionType) {
    Assertion.isNotNull(actionType, "ActionType is required");
    this.actionType = actionType;
  }

  private void setName(Text name) {
    Assertion.isNotNull(name, "Name is required");
    this.name = name;
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

    Action action = (Action) o;

    return new EqualsBuilder()
        .append(actionType, action.actionType)
        .append(name, action.name)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(actionType).append(name).toHashCode();
  }
}
