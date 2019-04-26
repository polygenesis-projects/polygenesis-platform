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

package io.polygenesis.generators.angular.exporters.once;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.generators.angular.exporters.ui.container.UiContainerExporter;
import io.polygenesis.models.ui.UiLayoutContainerMetamodelRepository;
import io.polygenesis.models.ui.container.AbstractContainerWithElements;
import io.polygenesis.models.ui.container.LayoutContainer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Once shared module exporter.
 *
 * @author Christos Tsakostas
 */
public class OnceSharedModuleExporter {

  // ===============================================================================================
  // FREEMARKER TEMPLATES
  // ===============================================================================================

  private static final String FTL_APP_SHARED_MODULE_TS =
      "polygenesis-angular-generator/src/app/shared/shared.module.ts.ftl";

  // ===============================================================================================
  // OUTPUT FILES
  // ===============================================================================================
  private static final String SHARED_MODULE_FOLDER = "shared";

  private static final String FILE_APP_SHARED_MODULE_TS = "shared/shared.module.ts";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final UiContainerExporter uiContainerExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Once shared module exporter.
   *
   * @param freemarkerService the freemarker service
   * @param uiContainerExporter the ui container exporter
   */
  public OnceSharedModuleExporter(
      FreemarkerService freemarkerService, UiContainerExporter uiContainerExporter) {
    this.freemarkerService = freemarkerService;
    this.uiContainerExporter = uiContainerExporter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param uiLayoutContainerModelRepository the ui layout container model repository
   */
  public void export(
      Path generationPath, UiLayoutContainerMetamodelRepository uiLayoutContainerModelRepository) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("layouts", uiLayoutContainerModelRepository.getItems());

    exportSharedModuleTs(generationPath, dataModel);

    uiLayoutContainerModelRepository
        .getItems()
        .forEach(layoutContainer -> exportLayout(generationPath, layoutContainer));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void exportSharedModuleTs(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_APP_SHARED_MODULE_TS,
        Paths.get(generationPath.toString(), FILE_APP_SHARED_MODULE_TS));
  }

  private void exportLayout(Path generationPath, LayoutContainer layout) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("layout", layout);

    uiContainerExporter.exportLayoutContainer(
        generationPath, Paths.get(SHARED_MODULE_FOLDER), layout);

    exportComponentsFromLayout(generationPath, layout.getTop());
  }

  private void exportComponentsFromLayout(
      Path generationPath, AbstractContainerWithElements container) {
    if (container == null) {
      return;
    }

    if (container.getElementGroup() == null) {
      return;
    }

    uiContainerExporter.exportComponentContainer(
        generationPath, Paths.get(SHARED_MODULE_FOLDER), container);
  }
}
