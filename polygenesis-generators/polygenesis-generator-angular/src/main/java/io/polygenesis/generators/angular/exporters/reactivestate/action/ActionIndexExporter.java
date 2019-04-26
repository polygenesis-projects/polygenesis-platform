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

package io.polygenesis.generators.angular.exporters.reactivestate.action;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.models.reactivestate.ActionGroup;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Action index exporter.
 *
 * @author Christos Tsakostas
 */
public class ActionIndexExporter {

  private static final String FREEMARKER_ACTION_INDEX =
      "polygenesis-representation-typescript/ngrx/actions/index.ts.ftl";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ActionIndexRepresentable actionIndexRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action index exporter.
   *
   * @param freemarkerService the freemarker service
   * @param actionIndexRepresentable the action index representable
   */
  public ActionIndexExporter(
      FreemarkerService freemarkerService, ActionIndexRepresentable actionIndexRepresentable) {
    this.freemarkerService = freemarkerService;
    this.actionIndexRepresentable = actionIndexRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export action index.
   *
   * @param actionsGenerationPath the actions generation path
   * @param actionGroups the action groups
   */
  public void exportActionIndex(Path actionsGenerationPath, Set<ActionGroup> actionGroups) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", actionIndexRepresentable.create(actionGroups));

    freemarkerService.export(
        dataModel,
        FREEMARKER_ACTION_INDEX,
        Paths.get(actionsGenerationPath.toString(), "index.ts"));
  }
}
