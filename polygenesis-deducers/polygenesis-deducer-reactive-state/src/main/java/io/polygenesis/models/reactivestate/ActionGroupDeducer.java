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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.GoalType;
import io.polygenesis.core.Thing;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
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
  public Set<ActionGroup> deduce(Thing thing, ServiceMetamodelRepository serviceModelRepository) {
    Set<ActionGroup> actionGroups = new LinkedHashSet<>();

    Set<Service> services = serviceModelRepository.getServicesBy(thing.getThingName());

    services.forEach(
        service ->
            service
                .getServiceMethods()
                .forEach(
                    method -> {
                      Set<Action> actions = new LinkedHashSet<>();

                      // Submit
                      actions.add(
                          createAction(
                              method.getFunction().getName(),
                              ActionType.SUBMIT,
                              new Model(method.getRequestDto().getDataGroup())));

                      // Success
                      actions.add(
                          createAction(
                              method.getFunction().getName(),
                              ActionType.ON_SUCCESS,
                              new Model(method.getResponseDto().getDataGroup())));

                      // Failure
                      actions.add(
                          createAction(
                              method.getFunction().getName(),
                              ActionType.ON_FAILURE,
                              new Model(
                                  DataPrimitive.of(
                                      PrimitiveType.STRING, new VariableName("errorMessage")))));

                      actionGroups.add(
                          new ActionGroup(
                              new ActionGroupName(
                                  String.format(
                                      "%s-%s-%s",
                                      thing.getThingName().getText(),
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
        // TODO
        "RESET", new LinkedHashSet<>(Collections.singletonList(ActionType.SUBMIT)));
  }
}
