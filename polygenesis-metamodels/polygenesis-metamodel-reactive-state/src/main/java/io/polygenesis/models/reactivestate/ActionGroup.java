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

package io.polygenesis.models.reactivestate;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;
import java.util.Set;

/**
 * Groups related {@link Action Actions} together.
 *
 * <p>For example, suppose you call an {@link Action} named {@code CreateSomeThing}. An {@link
 * Effect}*** is automatically executed and a REST call is made to the backend. Depending on the
 * result the two related {@link Action Actions} may be {@code CreateSomeThingOnSuccess} and {@code
 * CreateSomeThingOnFailure}***.
 *
 * <p>{@link Store} is the container of the reactive state management modelling.
 *
 * @author Christos Tsakostas
 */
public class ActionGroup {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private ActionGroupName actionGroupName;
  private ActionGroupType actionGroupType;
  private Set<Action> actions;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action group.
   *
   * @param actionGroupName the action group name
   * @param actionGroupType the action group type
   * @param actions the actions
   */
  public ActionGroup(
      ActionGroupName actionGroupName, ActionGroupType actionGroupType, Set<Action> actions) {
    setActionGroupName(actionGroupName);
    setActionGroupType(actionGroupType);
    setActions(actions);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets action group name.
   *
   * @return the action group name
   */
  public ActionGroupName getActionGroupName() {
    return actionGroupName;
  }

  /**
   * Gets action group type.
   *
   * @return the action group type
   */
  public ActionGroupType getActionGroupType() {
    return actionGroupType;
  }

  /**
   * Gets actions.
   *
   * @return the actions
   */
  public Set<Action> getActions() {
    return actions;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets action group name.
   *
   * @param actionGroupName the action group name
   */
  private void setActionGroupName(ActionGroupName actionGroupName) {
    Assertion.isNotNull(actionGroupName, "actionGroupName is required");
    this.actionGroupName = actionGroupName;
  }

  /**
   * Sets action group type.
   *
   * @param actionGroupType the action group type
   */
  private void setActionGroupType(ActionGroupType actionGroupType) {
    Assertion.isNotNull(actionGroupType, "actionGroupType is required");
    this.actionGroupType = actionGroupType;
  }

  /**
   * Sets actions.
   *
   * @param actions the actions
   */
  private void setActions(Set<Action> actions) {
    Assertion.isNotNull(actions, "actions is required");
    this.actions = actions;
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
    ActionGroup that = (ActionGroup) o;
    return actionGroupType == that.actionGroupType && Objects.equals(actions, that.actions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionGroupType, actions);
  }
}
