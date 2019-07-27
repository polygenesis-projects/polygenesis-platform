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

package io.polygenesis.generators.angular.legacy.exporters.once;

import io.polygenesis.commons.freemarker.FreemarkerService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Once app module exporter.
 *
 * @author Christos Tsakostas
 */
public class OnceAppModuleExporter {

  // ===============================================================================================
  // FREEMARKER TEMPLATES
  // ===============================================================================================

  private static final String FTL_APP_MODULE =
      "polygenesis-angular-generator/src/app/app.module.ts.ftl";
  private static final String FTL_APP_ROUTING_MODULE =
      "polygenesis-angular-generator/src/app/app-routing.module.ts.ftl";
  private static final String FTL_APP_COMPONENT_HTML =
      "polygenesis-angular-generator/src/app/app.component.html.ftl";
  private static final String FTL_APP_COMPONENT_SCSS =
      "polygenesis-angular-generator/src/app/app.component.scss.ftl";
  private static final String FTL_APP_COMPONENT_TS =
      "polygenesis-angular-generator/src/app/app.component.ts.ftl";
  private static final String FTL_APP_COMPONENT_SPEC_TS =
      "polygenesis-angular-generator/src/app/app.component.spec.ts.ftl";

  // ===============================================================================================
  // OUTPUT FILES
  // ===============================================================================================

  private static final String FILE_APP_MODULE = "app.module.ts";
  private static final String FILE_APP_ROUTING_MODULE = "app-routing.module.ts";
  private static final String FILE_APP_COMPONENT_HTML = "app.component.html";
  private static final String FILE_APP_COMPONENT_SASS = "app.component.scss";
  private static final String FILE_APP_COMPONENT_TS = "app.component.ts";
  private static final String FILE_APP_COMPONENT_SPEC_TS = "app.component.spec.ts";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Once app module exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public OnceAppModuleExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   */
  public void export(Path generationPath) {
    Map<String, Object> dataModel = new HashMap<>();

    exportAppModule(generationPath, dataModel);
    exportAppRoutingModule(generationPath, dataModel);
    exportAppComponentHtml(generationPath, dataModel);
    exportAppComponentSass(generationPath, dataModel);
    exportAppComponentTs(generationPath, dataModel);
    exportAppComponentSpecTs(generationPath, dataModel);
  }

  private void exportAppModule(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel, FTL_APP_MODULE, Paths.get(generationPath.toString(), FILE_APP_MODULE));
  }

  private void exportAppRoutingModule(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_APP_ROUTING_MODULE,
        Paths.get(generationPath.toString(), FILE_APP_ROUTING_MODULE));
  }

  private void exportAppComponentHtml(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_APP_COMPONENT_HTML,
        Paths.get(generationPath.toString(), FILE_APP_COMPONENT_HTML));
  }

  private void exportAppComponentSass(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_APP_COMPONENT_SCSS,
        Paths.get(generationPath.toString(), FILE_APP_COMPONENT_SASS));
  }

  private void exportAppComponentTs(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_APP_COMPONENT_TS,
        Paths.get(generationPath.toString(), FILE_APP_COMPONENT_TS));
  }

  private void exportAppComponentSpecTs(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_APP_COMPONENT_SPEC_TS,
        Paths.get(generationPath.toString(), FILE_APP_COMPONENT_SPEC_TS));
  }
}
