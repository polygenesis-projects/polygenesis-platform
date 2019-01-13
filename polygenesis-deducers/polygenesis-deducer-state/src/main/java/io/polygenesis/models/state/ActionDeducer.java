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

import io.polygenesis.annotations.core.GGoalStandardType;
import io.polygenesis.core.Function;
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
   * Deduces a set of Actions provided a Thing's Function.
   *
   * @param function the thing's function
   * @return the actions set
   */
  public Set<Action> deduce(Function function) {
    Set<Action> actions = new LinkedHashSet<>();

    if (goalToActionTypeMap.containsKey(function.getGoal().getOriginal())) {
      LOG.debug(
          "Actions were deduced for Function={} and Goal={}",
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

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================
  private static void initializeGoalToActionTypeMap() {
    goalToActionTypeMap.put(
        GGoalStandardType.CMD_CREATE,
        new LinkedHashSet<>(
            Arrays.asList(ActionType.SUBMIT, ActionType.ON_SUCCESS, ActionType.ON_FAILURE)));

    goalToActionTypeMap.put(
        GGoalStandardType.CMD_UPDATE,
        new LinkedHashSet<>(
            Arrays.asList(ActionType.SUBMIT, ActionType.ON_SUCCESS, ActionType.ON_FAILURE)));

    goalToActionTypeMap.put(
        GGoalStandardType.CMD_DELETE,
        new LinkedHashSet<>(
            Arrays.asList(ActionType.SUBMIT, ActionType.ON_SUCCESS, ActionType.ON_FAILURE)));

    goalToActionTypeMap.put(
        GGoalStandardType.CMD_RESET,
        new LinkedHashSet<>(Collections.singletonList(ActionType.SUBMIT)));
  }
}
