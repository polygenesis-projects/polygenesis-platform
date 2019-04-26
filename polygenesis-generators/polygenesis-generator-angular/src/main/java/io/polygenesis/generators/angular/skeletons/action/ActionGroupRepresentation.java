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

package io.polygenesis.generators.angular.skeletons.action;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The type Action group representation.
 *
 * @author Christos Tsakostas
 */
public class ActionGroupRepresentation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Map<Set<String>, String> importObjects;
  private ActionEnumeration actionEnumeration;
  private Set<ActionClass> actionClasses;
  private ActionUnion actionUnion;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action group representation.
   *
   * @param importObjects the import objects
   * @param actionEnumeration the action enumeration
   * @param actionClasses the action classes
   * @param actionUnion the action union
   */
  public ActionGroupRepresentation(
      Map<Set<String>, String> importObjects,
      ActionEnumeration actionEnumeration,
      Set<ActionClass> actionClasses,
      ActionUnion actionUnion) {
    setImportObjects(importObjects);
    setActionEnumeration(actionEnumeration);
    setActionClasses(actionClasses);
    setActionUnion(actionUnion);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets import objects.
   *
   * @return the import objects
   */
  public Map<Set<String>, String> getImportObjects() {
    return importObjects;
  }

  /**
   * Gets action enumeration.
   *
   * @return the action enumeration
   */
  public ActionEnumeration getActionEnumeration() {
    return actionEnumeration;
  }

  /**
   * Gets action classes.
   *
   * @return the action classes
   */
  public Set<ActionClass> getActionClasses() {
    return actionClasses;
  }

  /**
   * Gets action union.
   *
   * @return the action union
   */
  public ActionUnion getActionUnion() {
    return actionUnion;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets import objects.
   *
   * @param importObjects the import objects
   */
  private void setImportObjects(Map<Set<String>, String> importObjects) {
    Assertion.isNotNull(importObjects, "importObjects is required");
    this.importObjects = importObjects;
  }

  /**
   * Sets action enumeration.
   *
   * @param actionEnumeration the action enumeration
   */
  private void setActionEnumeration(ActionEnumeration actionEnumeration) {
    Assertion.isNotNull(actionEnumeration, "actionEnumeration is required");
    this.actionEnumeration = actionEnumeration;
  }

  /**
   * Sets action classes.
   *
   * @param actionClasses the action classes
   */
  private void setActionClasses(Set<ActionClass> actionClasses) {
    Assertion.isNotNull(actionClasses, "actionClasses is required");
    this.actionClasses = actionClasses;
  }

  /**
   * Sets action union.
   *
   * @param actionUnion the action union
   */
  private void setActionUnion(ActionUnion actionUnion) {
    Assertion.isNotNull(actionUnion, "actionUnion is required");
    this.actionUnion = actionUnion;
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
    if (!super.equals(o)) {
      return false;
    }
    ActionGroupRepresentation that = (ActionGroupRepresentation) o;
    return Objects.equals(importObjects, that.importObjects)
        && Objects.equals(actionEnumeration, that.actionEnumeration)
        && Objects.equals(actionClasses, that.actionClasses)
        && Objects.equals(actionUnion, that.actionUnion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(), importObjects, actionEnumeration, actionClasses, actionUnion);
  }
}
