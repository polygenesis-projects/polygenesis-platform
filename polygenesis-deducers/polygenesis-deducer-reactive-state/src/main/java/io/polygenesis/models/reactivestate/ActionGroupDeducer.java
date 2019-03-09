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
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Thing;
import io.polygenesis.core.datatype.PrimitiveType;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceModelRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Deduces State Management Action Groups.
 *
 * @author Christos Tsakostas
 */
public class ActionGroupDeducer {

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
   * @param serviceModelRepository the service model repository
   * @return the set
   */
  public Set<ActionGroup> deduce(Thing thing, ServiceModelRepository serviceModelRepository) {
    Set<ActionGroup> actionGroups = new LinkedHashSet<>();

    Set<Service> services = serviceModelRepository.getServicesBy(thing.getName());

    services.forEach(
        service ->
            service
                .getMethods()
                .forEach(
                    method -> {
                      Set<Action> actions = new LinkedHashSet<>();

                      // Submit
                      actions.add(
                          createAction(
                              method.getFunction().getName(),
                              ActionType.SUBMIT,
                              new Model(method.getRequestDto().getOriginatingIoModelGroup())));

                      // Success
                      actions.add(
                          createAction(
                              method.getFunction().getName(),
                              ActionType.ON_SUCCESS,
                              new Model(method.getResponseDto().getOriginatingIoModelGroup())));

                      // Failure
                      actions.add(
                          createAction(
                              method.getFunction().getName(),
                              ActionType.ON_FAILURE,
                              new Model(
                                  IoModelPrimitive.of(
                                      PrimitiveType.STRING, new VariableName("errorMessage")))));

                      actionGroups.add(
                          new ActionGroup(
                              new ActionGroupName(
                                  String.format(
                                      "%s-%s-%s",
                                      thing.getName().getText(),
                                      TextConverter.toLowerCamel(
                                          ActionGroupType.API.name().toLowerCase()),
                                      TextConverter.toLowerHyphen(
                                          method.getFunction().getGoal().getText()))),
                              ActionGroupType.API,
                              actions));
                    }));

    return actionGroups;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  //  private Set<Action> deduceApiActions(Function function) {
  //    Set<Action> actions = new LinkedHashSet<>();
  //
  //    if (goalToActionTypeMap.containsKey(function.getGoal().getOriginal())) {
  //      goalToActionTypeMap
  //          .get(function.getGoal().getOriginal())
  //          .forEach(actionType -> actions.add(createAction(function, actionType)));
  //
  //      LOG.debug(
  //          "Actions were successfully deduced for Function={} and Goal={}",
  //          function.getName().getOriginal(),
  //          function.getGoal().getOriginal());
  //    } else {
  //      LOG.debug(
  //          "No Actions could be deduced for Function={} and Goal={}",
  //          function.getName().getOriginal(),
  //          function.getGoal().getOriginal());
  //    }
  //
  //    return actions;
  //  }

  /**
   * Create action.
   *
   * @param functionName the function name
   * @param actionType the action type
   * @param model the model
   * @return the action
   */
  public Action createAction(FunctionName functionName, ActionType actionType, Model model) {
    return new Action(
        actionType,
        new ActionName(
            TextConverter.toUpperUnderscore(functionName.getText()) + "_" + actionType.name()),
        model);
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
