/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.angular.legacy.exporters.reactivestate.model;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.StoreExporterConstants;
import io.polygenesis.metamodels.stateredux.Model;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ModelExporter {

  private static final String FREEMARKER_TEMPLATE_MODEL =
      "polygenesis-representation-typescript/ngrx/models/model.ts.ftl";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ModelRepresentable modelRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Metamodel exporter.
   *
   * @param freemarkerService the freemarker service
   * @param modelRepresentable the model representable
   */
  public ModelExporter(FreemarkerService freemarkerService, ModelRepresentable modelRepresentable) {
    this.freemarkerService = freemarkerService;
    this.modelRepresentable = modelRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export models.
   *
   * @param modelsPath the models path
   * @param model the model
   */
  public void exportModels(Path modelsPath, Model model) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", modelRepresentable.create(model));

    freemarkerService.export(
        dataModel,
        FREEMARKER_TEMPLATE_MODEL,
        Paths.get(modelsPath.toString(), makeModelFileName(model)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Makes model file name.
   *
   * @param model the model
   * @return the string
   */
  private String makeModelFileName(Model model) {
    return String.format(
        "%s.%s",
        TextConverter.toLowerHyphen(model.getData().getDataType()),
        StoreExporterConstants.POSTFIX_MODEL_TS);
  }
}
