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

package io.polygenesis.models.reactivestate;

import io.polygenesis.annotations.core.GGoalStandardType;
import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Function;
import io.polygenesis.core.Thing;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deduces State Management Actions.
 *
 * @author Christos Tsakostas
 */
public class ActionDeducer {

  private static final Logger LOG = LoggerFactory.getLogger(ActionDeducer.class);

  private static final Map<String, Set<ActionType>> goalToActionTypeMap;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  static {
    goalToActionTypeMap = new LinkedHashMap<>();
    initializeGoalToActionTypeMap();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce set.
   *
   * @param thing the thing
   * @return the set
   */
  public Set<Action> deduce(Thing thing) {
    Set<Action> actions = new LinkedHashSet<>();

    thing.getFunctions().forEach(function -> actions.addAll(deduce(function)));

    return actions;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Deduces a set of Actions provided a Thing's Function.
   *
   * @param function the thing's function
   * @return the actions set
   */
  private Set<Action> deduce(Function function) {
    Set<Action> actions = new LinkedHashSet<>();

    if (goalToActionTypeMap.containsKey(function.getGoal().getOriginal())) {
      goalToActionTypeMap
          .get(function.getGoal().getOriginal())
          .forEach(actionType -> actions.add(createAction(function, actionType)));

      LOG.debug(
          "Actions were successfully deduced for Function={} and Goal={}",
          function.getName().getOriginal(),
          function.getGoal().getOriginal());
    } else {
      LOG.debug(
          "No Actions could be deduced for Function={} and Goal={}",
          function.getName().getOriginal(),
          function.getGoal().getOriginal());
    }

    return actions;
  }

  /**
   * Create action.
   *
   * @param function the function
   * @param actionType the action type
   * @return the action
   */
  public Action createAction(Function function, ActionType actionType) {
    return new Action(
        actionType,
        new ActionName(
            TextConverter.toUpperUnderscore(function.getName().getText())
                + "_"
                + actionType.name()));
  }

  /** Initializes {@link io.polygenesis.core.Goal} to {@link ActionType} map. */
  private static void initializeGoalToActionTypeMap() {
    goalToActionTypeMap.put(
        GoalType.CREATE.name(),
        new LinkedHashSet<>(
            Arrays.asList(ActionType.SUBMIT, ActionType.ON_SUCCESS, ActionType.ON_FAILURE)));

    goalToActionTypeMap.put(
        GoalType.MODIFY.name(),
        new LinkedHashSet<>(
            Arrays.asList(ActionType.SUBMIT, ActionType.ON_SUCCESS, ActionType.ON_FAILURE)));

    goalToActionTypeMap.put(
        GoalType.DELETE.name(),
        new LinkedHashSet<>(
            Arrays.asList(ActionType.SUBMIT, ActionType.ON_SUCCESS, ActionType.ON_FAILURE)));

    goalToActionTypeMap.put(
        GGoalStandardType.CMD_RESET,
        new LinkedHashSet<>(Collections.singletonList(ActionType.SUBMIT)));
  }
}
