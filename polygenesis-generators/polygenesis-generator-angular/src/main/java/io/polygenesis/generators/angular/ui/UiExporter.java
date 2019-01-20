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

package io.polygenesis.generators.angular.ui;

import io.polygenesis.models.ui.Feature;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Ui exporter.
 *
 * @author Christos Tsakostas
 */
public class UiExporter {

  private UiModuleExporter uiModuleExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public UiExporter(UiModuleExporter uiModuleExporter) {
    this.uiModuleExporter = uiModuleExporter;
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
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("feature", feature);

    uiModuleExporter.exportModule(generationPath, feature, dataModel);
  }
}
