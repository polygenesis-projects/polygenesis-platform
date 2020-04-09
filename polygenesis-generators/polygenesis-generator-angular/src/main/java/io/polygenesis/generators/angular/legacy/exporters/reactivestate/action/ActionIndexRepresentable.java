/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.angular.legacy.exporters.reactivestate.action;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.angular.legacy.skeletons.action.ActionIndexRepresentation;
import io.polygenesis.metamodels.stateredux.ActionGroup;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ActionIndexRepresentable {

  private static final String POSTFIX_ACTIONS = "actions";

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Create action index representation.
   *
   * @param actionGroups the action groups
   * @return the action index representation
   */
  public ActionIndexRepresentation create(Set<ActionGroup> actionGroups) {
    Map<String, String> importAliases = new LinkedHashMap<>();

    actionGroups.forEach(
        actionGroup ->
            importAliases.put(
                actionGroup.getActionGroupName().getText(),
                String.format(
                    "%s.%s",
                    TextConverter.toLowerHyphen(actionGroup.getActionGroupName().getText()),
                    POSTFIX_ACTIONS)));

    return new ActionIndexRepresentation(importAliases);
  }
}
