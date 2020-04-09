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

package io.polygenesis.generators.angular.legacy.exporters.reactivestate;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.path.PathService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.metamodels.stateredux.Store;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type Module exporter.
 *
 * @author Christos Tsakostas
 */
public class ModuleExporter {

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Module exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public ModuleExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export module.
   *
   * @param generationPath the generation path
   * @param store the store
   * @param dataModel the data model
   */
  public void exportModule(Path generationPath, Store store, Map<String, Object> dataModel) {
    Path modulePath =
        Paths.get(
            generationPath.toString(),
            StoreExporterConstants.PATH_NGRX,
            TextConverter.toLowerHyphen(store.getFeatureName().getText()));
    PathService.ensurePath(modulePath);

    freemarkerService.export(
        dataModel,
        StoreExporterConstants.FTL_MODULE,
        Paths.get(modulePath.toString(), makeModuleFileName(store)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Makes module file name.
   *
   * @param store the store
   * @return the string
   */
  private String makeModuleFileName(Store store) {
    return TextConverter.toLowerHyphen(store.getFeatureName().getText())
        + StoreExporterConstants.POSTFIX_MODULE_TS;
  }
}
