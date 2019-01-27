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

import io.polygenesis.models.ui.UiModelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Once exporter.
 *
 * @author Christos Tsakostas
 */
public class OnceExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final OnceSourceRootExporter onceSourceRootExporter;
  private final OnceEnvironmentsExporter onceEnvironmentsExporter;
  private final OnceCoreModuleExporter onceCoreModuleExporter;
  private final OnceSharedModuleExporter onceSharedModuleExporter;
  private final OnceAppModuleExporter onceAppModuleExporter;
  private final OnceLandingModuleExporter onceLandingModuleExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Once exporter.
   *
   * @param onceSourceRootExporter the once source root exporter
   * @param onceEnvironmentsExporter the once environments exporter
   * @param onceCoreModuleExporter the once core module exporter
   * @param onceSharedModuleExporter the once shared module exporter
   * @param onceAppModuleExporter the once app module exporter
   * @param onceLandingModuleExporter the once landing module exporter
   */
  public OnceExporter(
      OnceSourceRootExporter onceSourceRootExporter,
      OnceEnvironmentsExporter onceEnvironmentsExporter,
      OnceCoreModuleExporter onceCoreModuleExporter,
      OnceSharedModuleExporter onceSharedModuleExporter,
      OnceAppModuleExporter onceAppModuleExporter,
      OnceLandingModuleExporter onceLandingModuleExporter) {
    this.onceSourceRootExporter = onceSourceRootExporter;
    this.onceEnvironmentsExporter = onceEnvironmentsExporter;
    this.onceCoreModuleExporter = onceCoreModuleExporter;
    this.onceSharedModuleExporter = onceSharedModuleExporter;
    this.onceAppModuleExporter = onceAppModuleExporter;
    this.onceLandingModuleExporter = onceLandingModuleExporter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param uiModelRepository the ui model repository
   */
  public void export(Path generationPath, UiModelRepository uiModelRepository) {
    Path generationPathApp = Paths.get(generationPath.toString(), "app");
    Path generationPathEnvironments = Paths.get(generationPath.toString(), "environments");

    onceSourceRootExporter.export(generationPath);
    onceEnvironmentsExporter.export(generationPathEnvironments);
    onceCoreModuleExporter.export(generationPathApp);
    onceSharedModuleExporter.export(generationPathApp, uiModelRepository);
    onceAppModuleExporter.export(generationPathApp);
    onceLandingModuleExporter.export(generationPathApp);
  }
}
