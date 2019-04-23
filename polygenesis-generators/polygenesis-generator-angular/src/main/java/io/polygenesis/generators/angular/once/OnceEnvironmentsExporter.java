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
 * The type Once environments exporter.
 *
 * @author Christos Tsakostas
 */
public class OnceEnvironmentsExporter {

  // ===============================================================================================
  // FREEMARKER TEMPLATES
  // ===============================================================================================

  private static final String FTL_ENVIRONMENT_DEV =
      "polygenesis-angular-generator/src/environments/environment.ts.ftl";
  private static final String FTL_ENVIRONMENT_PROD =
      "polygenesis-angular-generator/src/environments/environment.prod.ts.ftl";

  // ===============================================================================================
  // OUTPUT FILES
  // ===============================================================================================

  private static final String FILE_ENVIRONMENT_DEV = "environment.ts";
  private static final String FILE_ENVIRONMENT_PROD = "environment.prod.ts";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Once environments exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public OnceEnvironmentsExporter(FreemarkerService freemarkerService) {
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

    exportEnvironmentsTs(generationPath, dataModel);
  }

  private void exportEnvironmentsTs(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel, FTL_ENVIRONMENT_DEV, Paths.get(generationPath.toString(), FILE_ENVIRONMENT_DEV));

    freemarkerService.export(
        dataModel,
        FTL_ENVIRONMENT_PROD,
        Paths.get(generationPath.toString(), FILE_ENVIRONMENT_PROD));
  }
}
