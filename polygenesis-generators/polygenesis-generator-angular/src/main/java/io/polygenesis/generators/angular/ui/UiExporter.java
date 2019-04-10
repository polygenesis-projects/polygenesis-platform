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

package io.polygenesis.generators.angular.ui;

import io.polygenesis.generators.angular.ui.container.UiContainerExporter;
import io.polygenesis.models.ui.Feature;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Ui exporter.
 *
 * @author Christos Tsakostas
 */
public class UiExporter {

  private final UiModuleExporter uiModuleExporter;
  private final UiContainerExporter uiContainerExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public UiExporter(UiModuleExporter uiModuleExporter, UiContainerExporter uiContainerExporter) {
    this.uiModuleExporter = uiModuleExporter;
    this.uiContainerExporter = uiContainerExporter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param feature the feature
   */
  public void export(Path generationPath, Feature feature) {
    Path generationPathApp = Paths.get(generationPath.toString(), "app");

    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("feature", feature);

    feature
        .getContainers()
        .forEach(
            container ->
                uiContainerExporter.exportFeatureContainer(generationPathApp, feature, container));

    uiModuleExporter.exportModule(generationPathApp, feature, dataModel);
  }
}
