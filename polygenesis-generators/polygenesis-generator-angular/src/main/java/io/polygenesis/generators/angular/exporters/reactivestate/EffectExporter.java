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

package io.polygenesis.generators.angular.exporters.reactivestate;

import static io.polygenesis.generators.angular.exporters.reactivestate.StoreExporterConstants.FTL_EFFECT;
import static io.polygenesis.generators.angular.exporters.reactivestate.StoreExporterConstants.PATH_EFFECTS;
import static io.polygenesis.generators.angular.exporters.reactivestate.StoreExporterConstants.PATH_NGRX;
import static io.polygenesis.generators.angular.exporters.reactivestate.StoreExporterConstants.POSTFIX_EFFECTS_TS;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.path.PathService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.reactivestate.Store;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type Effect exporter.
 *
 * @author Christos Tsakostas
 */
public class EffectExporter {

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Effect exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public EffectExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export effects.
   *
   * @param generationPath the generation path
   * @param store the store
   * @param dataModel the data model
   */
  public void exportEffects(Path generationPath, Store store, Map<String, Object> dataModel) {
    Path effectsPath =
        Paths.get(
            generationPath.toString(),
            PATH_NGRX,
            TextConverter.toLowerHyphen(store.getFeatureName().getText()),
            PATH_EFFECTS);
    PathService.ensurePath(effectsPath);

    freemarkerService.export(
        dataModel, FTL_EFFECT, Paths.get(effectsPath.toString(), makeEffectsFileName(store)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Makes effects file name.
   *
   * @param store the store
   * @return the string
   */
  private String makeEffectsFileName(Store store) {
    return TextConverter.toLowerHyphen(store.getFeatureName().getText()) + POSTFIX_EFFECTS_TS;
  }
}
