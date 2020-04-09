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
import java.util.Objects;

/**
 * Models a State Management Action.
 *
 * @author Christos Tsakostas
 */
public class Action {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ActionType actionType;
  private ActionName name;
  private Model payloadModel;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action.
   *
   * @param actionType the action type
   * @param name the name
   * @param payloadModel the payload model
   */
  public Action(ActionType actionType, ActionName name, Model payloadModel) {
    setActionType(actionType);
    setName(name);
    setPayloadModel(payloadModel);
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
  public ActionName getName() {
    return name;
  }

  /**
   * Gets payload model.
   *
   * @return the payload model
   */
  public Model getPayloadModel() {
    return payloadModel;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets action type.
   *
   * @param actionType the action type
   */
  private void setActionType(ActionType actionType) {
    Assertion.isNotNull(actionType, "actionType is required");
    this.actionType = actionType;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(ActionName name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets payload model.
   *
   * @param payloadModel the payload model
   */
  private void setPayloadModel(Model payloadModel) {
    Assertion.isNotNull(payloadModel, "payloadModel is required");
    this.payloadModel = payloadModel;
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
    return actionType == action.actionType
        && Objects.equals(name, action.name)
        && Objects.equals(payloadModel, action.payloadModel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionType, name, payloadModel);
  }
}
