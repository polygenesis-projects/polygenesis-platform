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
 * The type Once landing module exporter.
 *
 * @author Christos Tsakostas
 */
public class OnceLandingModuleExporter {

  // ===============================================================================================
  // FREEMARKER TEMPLATES
  // ===============================================================================================

  private static final String FTL_LANDING_MODULE_TS =
      "polygenesis-angular-generator/src/app/landing/landing.module.ts.ftl";
  private static final String FTL_LANDING_ROUTING_MODULE_TS =
      "polygenesis-angular-generator/src/app/landing/landing-routing.module.ts.ftl";
  private static final String FTL_LANDING_COMPONENT_HTML =
      "polygenesis-angular-generator/src/app/landing/containers/landing-container/landing-container"
          + ".component.html.ftl";
  private static final String FTL_LANDING_COMPONENT_SCSS =
      "polygenesis-angular-generator/src/app/landing/containers/landing-container/landing-container"
          + ".component.scss.ftl";
  private static final String FTL_LANDING_COMPONENT_TS =
      "polygenesis-angular-generator/src/app/landing/containers/landing-container/landing-container"
          + ".component.ts.ftl";
  private static final String FTL_LANDING_COMPONENT_SPEC_TS =
      "polygenesis-angular-generator/src/app/landing/containers/landing-container/landing-container"
          + ".component.spec.ts.ftl";

  // ===============================================================================================
  // OUTPUT FILES
  // ===============================================================================================

  private static final String FILE_LANDING_MODULE_TS = "landing/landing.module.ts";
  private static final String FILE_LANDING_ROUTING_MODULE_TS = "landing/landing-routing.module.ts";
  private static final String FILE_LANDING_COMPONENT_HTML =
      "landing/containers/landing-container/landing-container.component.html";
  private static final String FILE_LANDING_COMPONENT_SASS =
      "landing/containers/landing-container/landing-container.component.scss";
  private static final String FILE_LANDING_COMPONENT_TS =
      "landing/containers/landing-container/landing-container.component.ts";
  private static final String FILE_LANDING_COMPONENT_SPEC_TS =
      "landing/containers/landing-container/landing-container.component.spec.ts";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Once landing module exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public OnceLandingModuleExporter(FreemarkerService freemarkerService) {
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

    exportLandingModuleTs(generationPath, dataModel);
    exportLandingRoutingModuleTs(generationPath, dataModel);
    exportLandingContainerComponentHtml(generationPath, dataModel);
    exportLandingContainerComponentSass(generationPath, dataModel);
    exportLandingContainerComponentTs(generationPath, dataModel);
    exportLandingContainerComponentSpecTs(generationPath, dataModel);
  }

  private void exportLandingModuleTs(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_LANDING_MODULE_TS,
        Paths.get(generationPath.toString(), FILE_LANDING_MODULE_TS));
  }

  private void exportLandingRoutingModuleTs(Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_LANDING_ROUTING_MODULE_TS,
        Paths.get(generationPath.toString(), FILE_LANDING_ROUTING_MODULE_TS));
  }

  private void exportLandingContainerComponentHtml(
      Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_LANDING_COMPONENT_HTML,
        Paths.get(generationPath.toString(), FILE_LANDING_COMPONENT_HTML));
  }

  private void exportLandingContainerComponentSass(
      Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_LANDING_COMPONENT_SCSS,
        Paths.get(generationPath.toString(), FILE_LANDING_COMPONENT_SASS));
  }

  private void exportLandingContainerComponentTs(
      Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_LANDING_COMPONENT_TS,
        Paths.get(generationPath.toString(), FILE_LANDING_COMPONENT_TS));
  }

  private void exportLandingContainerComponentSpecTs(
      Path generationPath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        FTL_LANDING_COMPONENT_SPEC_TS,
        Paths.get(generationPath.toString(), FILE_LANDING_COMPONENT_SPEC_TS));
  }
}
