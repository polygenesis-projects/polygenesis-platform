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

package io.polygenesis.generators.angular;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.angular.exporters.once.OnceExporter;
import io.polygenesis.generators.angular.exporters.reactivestate.StoreExporter;
import io.polygenesis.generators.angular.exporters.ui.UiExporter;
import io.polygenesis.models.reactivestate.ReactiveStateMetamodelRepository;
import io.polygenesis.models.ui.UiFeatureMetamodelRepository;
import io.polygenesis.models.ui.UiLayoutContainerMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * PolyGenesis Angular MetamodelGenerator.
 *
 * @author Christos Tsakostas
 */
public class AngularMetamodelGenerator extends AbstractMetamodelGenerator {

  private final OnceExporter onceExporter;
  private final StoreExporter storeExporter;
  private final UiExporter uiExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new PolyGenesis Angular MetamodelGenerator.
   *
   * @param generationPath the generation path
   * @param onceExporter the once exporter
   * @param storeExporter the store exporter
   * @param uiExporter the ui exporter
   */
  public AngularMetamodelGenerator(
      Path generationPath,
      OnceExporter onceExporter,
      StoreExporter storeExporter,
      UiExporter uiExporter) {
    super(generationPath);

    Assertion.isNotNull(onceExporter, "onceExporter is required");
    this.onceExporter = onceExporter;

    Assertion.isNotNull(storeExporter, "StoreExporter is required");
    this.storeExporter = storeExporter;

    Assertion.isNotNull(uiExporter, "UiExporter is required");
    this.uiExporter = uiExporter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public void generate(Set<MetamodelRepository> modelRepositories) {

    Path generationPathApp = Paths.get(getGenerationPath().toString(), "app");

    onceExporter.export(
        getGenerationPath(),
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, UiLayoutContainerMetamodelRepository.class));

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, ReactiveStateMetamodelRepository.class)
        .getItems()
        .forEach(store -> storeExporter.export(generationPathApp, store));

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, UiFeatureMetamodelRepository.class)
        .getItems()
        .forEach(feature -> uiExporter.export(getGenerationPath(), feature));
  }
}
