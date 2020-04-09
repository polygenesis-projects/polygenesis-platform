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

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.FeatureName;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.StoreExporterConstants;
import io.polygenesis.metamodels.stateredux.ActionGroup;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ActionGroupExporter {

  private static final String FREEMARKER_ACTION_GROUP =
      "polygenesis-representation-typescript/ngrx/actions/action-group.ts.ftl";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ActionGroupRepresentable actionGroupRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action group exporter.
   *
   * @param freemarkerService the freemarker service
   * @param actionGroupRepresentable the action group representable
   */
  public ActionGroupExporter(
      FreemarkerService freemarkerService, ActionGroupRepresentable actionGroupRepresentable) {
    this.freemarkerService = freemarkerService;
    this.actionGroupRepresentable = actionGroupRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export actions.
   *
   * @param actionsGenerationPath the actions generation path
   * @param featureName the feature name
   * @param actionGroup the action group
   */
  public void exportActionGroup(
      Path actionsGenerationPath, FeatureName featureName, ActionGroup actionGroup) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", actionGroupRepresentable.create(featureName, actionGroup));

    freemarkerService.export(
        dataModel,
        FREEMARKER_ACTION_GROUP,
        Paths.get(actionsGenerationPath.toString(), makeActionsFileName(actionGroup)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Makes actions file name.
   *
   * @param actionGroup the action group
   * @return the string
   */
  private String makeActionsFileName(ActionGroup actionGroup) {
    return String.format(
        "%s.%s",
        TextConverter.toLowerHyphen(actionGroup.getActionGroupName().getText()),
        StoreExporterConstants.POSTFIX_ACTIONS_TS);
  }
}
