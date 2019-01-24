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

import static io.polygenesis.generators.angular.ui.UiExporterConstants.FTL_MODULE;
import static io.polygenesis.generators.angular.ui.UiExporterConstants.POSTFIX_MODULE_TS;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.path.PathService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.ui.Feature;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type Module exporter.
 *
 * @author Christos Tsakostas
 */
public class UiModuleExporter {

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Ui module exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public UiModuleExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export module.
   *
   * @param generationPath the generation path
   * @param feature the feature
   * @param dataModel the data model
   */
  public void exportModule(Path generationPath, Feature feature, Map<String, Object> dataModel) {
    Path modulePath =
        Paths.get(
            generationPath.toString(),
            TextConverter.toLowerHyphen(feature.getFeatureName().getText()));
    PathService.ensurePath(modulePath);

    freemarkerService.export(
        dataModel, FTL_MODULE, Paths.get(modulePath.toString(), makeModuleFileName(feature)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Makes module file name.
   *
   * @return the string
   */
  private String makeModuleFileName(Feature feature) {
    return TextConverter.toLowerHyphen(feature.getFeatureName().getText()) + POSTFIX_MODULE_TS;
  }
}
