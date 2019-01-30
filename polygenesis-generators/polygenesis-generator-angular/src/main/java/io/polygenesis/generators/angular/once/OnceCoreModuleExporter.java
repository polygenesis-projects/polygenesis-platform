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

package io.polygenesis.generators.angular.once;

import io.polygenesis.commons.freemarker.FreemarkerService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Once core module exporter.
 *
 * @author Christos Tsakostas
 */
public class OnceCoreModuleExporter {

  // ===============================================================================================
  // FREEMARKER TEMPLATES
  // ===============================================================================================

  private static final String FTL_APP_CORE_MODULE_TS =
      "polygenesis-angular-generator/src/app/core/core.module.ts.ftl";

  // ===============================================================================================
  // OUTPUT FILES
  // ===============================================================================================

  private static final String FILE_APP_CORE_MODULE_TS = "core/core.module.ts";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Once core module exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public OnceCoreModuleExporter(FreemarkerService freemarkerService) {
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

    exportCoreModuleTs(generationPath, dataModel);
  }

  private void exportCoreModuleTs(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_APP_CORE_MODULE_TS,
        Paths.get(generationPath.toString(), FILE_APP_CORE_MODULE_TS));
  }
}
