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

package io.polygenesis.generators.angular;

import io.polygenesis.commons.assertions.Assertion;
import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.generators.angular.freemarker.FreemarkerConfig;
import io.polygenesis.generators.angular.freemarker.FreemarkerService;
import io.polygenesis.generators.angular.state.StoreExporter;
import io.polygenesis.models.state.StateModelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * PolyGenesis Angular Generator.
 *
 * @author Christos Tsakostas
 */
public class PolyGenesisAngularGenerator extends AbstractGenerator {

  private final StoreExporter storeExporter;

  private StateModelRepository stateModelRepository;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Poly genesis angular generator.
   *
   * @param generationPath the generation path
   */
  public PolyGenesisAngularGenerator(Path generationPath, StoreExporter storeExporter) {
    super(generationPath);
    Assertion.isNotNull(storeExporter, "StoreExporter is required");
    this.storeExporter = storeExporter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<ModelRepository> modelRepositories) {
    initializeModelRepositories(modelRepositories);

    stateModelRepository
        .getStores()
        .forEach(store -> storeExporter.export(getGenerationPath(), store));

    expFtl(getGenerationPath());
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================
  private void initializeModelRepositories(Set<ModelRepository> modelRepositories) {
    stateModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, StateModelRepository.class);
  }

  private void expFtl(Path generationPath) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("pmName", "CHRIS");
    dataModel.put("country", "Greece");

    FreemarkerService.export(
        FreemarkerConfig.getInstance().getConfiguration(),
        dataModel,
        "template.ftl",
        Paths.get(generationPath.toString(), "template-output.html"));
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

}
