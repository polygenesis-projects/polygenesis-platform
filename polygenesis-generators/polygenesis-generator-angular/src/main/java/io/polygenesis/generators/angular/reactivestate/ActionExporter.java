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

package io.polygenesis.generators.angular.reactivestate;

import static io.polygenesis.generators.angular.reactivestate.StoreExporterConstants.FTL_ACTION;
import static io.polygenesis.generators.angular.reactivestate.StoreExporterConstants.PATH_ACTIONS;
import static io.polygenesis.generators.angular.reactivestate.StoreExporterConstants.PATH_NGRX;
import static io.polygenesis.generators.angular.reactivestate.StoreExporterConstants.POSTFIX_ACTIONS_TS;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.path.PathService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.reactivestate.Store;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type Action exporter.
 *
 * @author Christos Tsakostas
 */
public class ActionExporter {

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public ActionExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export actions.
   *
   * @param generationPath the generation path
   * @param store the store
   * @param dataModel the data model
   */
  public void exportActions(Path generationPath, Store store, Map<String, Object> dataModel) {
    Path actionsPath =
        Paths.get(
            generationPath.toString(),
            PATH_NGRX,
            TextConverter.toLowerHyphen(store.getFeatureName().getText()),
            PATH_ACTIONS);
    PathService.ensurePath(actionsPath);

    freemarkerService.export(
        dataModel, FTL_ACTION, Paths.get(actionsPath.toString(), makeActionsFileName(store)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Makes actions file name.
   *
   * @param store the store
   * @return the string
   */
  private String makeActionsFileName(Store store) {
    return TextConverter.toLowerHyphen(store.getFeatureName().getText()) + POSTFIX_ACTIONS_TS;
  }
}
