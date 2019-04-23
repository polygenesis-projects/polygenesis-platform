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

package io.polygenesis.generators.angular.once;

import io.polygenesis.commons.freemarker.FreemarkerService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Once source root exporter.
 *
 * @author Christos Tsakostas
 */
public class OnceSourceRootExporter {

  // ===============================================================================================
  // FREEMARKER TEMPLATES
  // ===============================================================================================

  private static final String FTL_INDEX_HTML = "polygenesis-angular-generator/src/index.html.ftl";
  private static final String FTL_STYLES_SCSS = "polygenesis-angular-generator/src/styles.scss.ftl";
  private static final String FTL_MAIN_TS = "polygenesis-angular-generator/src/main.ts.ftl";

  // ===============================================================================================
  // OUTPUT FILES
  // ===============================================================================================

  private static final String FILE_INDEX_HTML = "index.html";
  private static final String FILE_STYLES_SCSS = "styles.scss";
  private static final String FILE_MAIN_TS = "main.ts";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Once exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public OnceSourceRootExporter(FreemarkerService freemarkerService) {
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

    exportIndexHtml(generationPath, dataModel);
    exportStylesScss(generationPath, dataModel);
    exportMainTs(generationPath, dataModel);
  }

  private void exportIndexHtml(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel, FTL_INDEX_HTML, Paths.get(generationPath.toString(), FILE_INDEX_HTML));
  }

  private void exportStylesScss(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel, FTL_STYLES_SCSS, Paths.get(generationPath.toString(), FILE_STYLES_SCSS));
  }

  private void exportMainTs(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel, FTL_MAIN_TS, Paths.get(generationPath.toString(), FILE_MAIN_TS));
  }
}
