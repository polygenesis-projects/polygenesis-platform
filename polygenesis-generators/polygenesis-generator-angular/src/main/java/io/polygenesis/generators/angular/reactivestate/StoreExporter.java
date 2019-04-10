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

package io.polygenesis.generators.angular.reactivestate;

import static io.polygenesis.generators.angular.reactivestate.StoreExporterConstants.PATH_ACTIONS;
import static io.polygenesis.generators.angular.reactivestate.StoreExporterConstants.PATH_MODELS;
import static io.polygenesis.generators.angular.reactivestate.StoreExporterConstants.PATH_NGRX;

import io.polygenesis.commons.path.PathService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.angular.reactivestate.action.ActionGroupExporter;
import io.polygenesis.generators.angular.reactivestate.action.ActionIndexExporter;
import io.polygenesis.generators.angular.reactivestate.model.ModelExporter;
import io.polygenesis.models.reactivestate.Store;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * State Store exporter.
 *
 * @author Christos Tsakostas
 */
public class StoreExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private ActionGroupExporter actionGroupExporter;
  private ActionIndexExporter actionIndexExporter;
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
   * @param actionGroupExporter the action group exporter
   * @param actionIndexExporter the action index exporter
   * @param reducerExporter the reducer exporter
   * @param indexReducerExporter the index reducer exporter
   * @param effectExporter the effect exporter
   * @param serviceExporter the service exporter
   * @param modelExporter the model exporter
   * @param moduleExporter the module exporter
   */
  public StoreExporter(
      ActionGroupExporter actionGroupExporter,
      ActionIndexExporter actionIndexExporter,
      ReducerExporter reducerExporter,
      IndexReducerExporter indexReducerExporter,
      EffectExporter effectExporter,
      ServiceExporter serviceExporter,
      ModelExporter modelExporter,
      ModuleExporter moduleExporter) {
    this.actionGroupExporter = actionGroupExporter;
    this.actionIndexExporter = actionIndexExporter;
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
   * @param generationPathApp the generation app path
   * @param store the store
   */
  public void export(Path generationPathApp, Store store) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("store", store);

    Path actionsGenerationPath =
        Paths.get(
            generationPathApp.toString(),
            PATH_NGRX,
            TextConverter.toLowerHyphen(store.getFeatureName().getText()),
            PATH_ACTIONS);
    PathService.ensurePath(actionsGenerationPath);

    store
        .getActionGroups()
        .forEach(
            actionGroup ->
                actionGroupExporter.exportActionGroup(
                    actionsGenerationPath, store.getFeatureName(), actionGroup));

    actionIndexExporter.exportActionIndex(actionsGenerationPath, store.getActionGroups());

    reducerExporter.exportReducers(generationPathApp, store, dataModel);
    indexReducerExporter.exportIndexReducer(generationPathApp, store, dataModel);
    effectExporter.exportEffects(generationPathApp, store, dataModel);
    serviceExporter.exportService(generationPathApp, store, dataModel);

    Path modelsPath =
        Paths.get(
            generationPathApp.toString(),
            PATH_NGRX,
            TextConverter.toLowerHyphen(store.getFeatureName().getText()),
            PATH_MODELS);
    PathService.ensurePath(modelsPath);

    store
        .getModels()
        .forEach(
            model -> {
              if (model.getModel().isDataGroup()) {
                modelExporter.exportModels(modelsPath, model);
              } else {
                throw new IllegalArgumentException();
              }
            });

    moduleExporter.exportModule(generationPathApp, store, dataModel);
  }
}
