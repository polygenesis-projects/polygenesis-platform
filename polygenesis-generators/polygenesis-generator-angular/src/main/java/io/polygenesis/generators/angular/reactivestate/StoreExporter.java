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

package io.polygenesis.generators.angular.reactivestate;

import io.polygenesis.models.reactivestate.Store;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * State Store exporter.
 *
 * @author Christos Tsakostas
 */
public class StoreExporter {

  private ActionExporter actionExporter;
  private ReducerExporter reducerExporter;
  private IndexReducerExporter indexReducerExporter;
  private EffectExporter effectExporter;
  private ServiceExporter serviceExporter;
  private ModelExporter modelExporter;
  private ModuleExporter moduleExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Store exporter.
   *
   * @param actionExporter the action exporter
   * @param reducerExporter the reducer exporter
   * @param indexReducerExporter the index reducer exporter
   * @param effectExporter the effect exporter
   * @param serviceExporter the service exporter
   * @param modelExporter the model exporter
   * @param moduleExporter the module exporter
   */
  public StoreExporter(
      ActionExporter actionExporter,
      ReducerExporter reducerExporter,
      IndexReducerExporter indexReducerExporter,
      EffectExporter effectExporter,
      ServiceExporter serviceExporter,
      ModelExporter modelExporter,
      ModuleExporter moduleExporter) {
    this.actionExporter = actionExporter;
    this.reducerExporter = reducerExporter;
    this.indexReducerExporter = indexReducerExporter;
    this.effectExporter = effectExporter;
    this.serviceExporter = serviceExporter;
    this.modelExporter = modelExporter;
    this.moduleExporter = moduleExporter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param store the store
   */
  public void export(Path generationPath, Store store) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("store", store);

    actionExporter.exportActions(generationPath, store, dataModel);
    reducerExporter.exportReducers(generationPath, store, dataModel);
    indexReducerExporter.exportIndexReducer(generationPath, store, dataModel);
    effectExporter.exportEffects(generationPath, store, dataModel);
    serviceExporter.exportService(generationPath, store, dataModel);
    modelExporter.exportModels(generationPath, store, dataModel);
    moduleExporter.exportModule(generationPath, store, dataModel);
  }
}
