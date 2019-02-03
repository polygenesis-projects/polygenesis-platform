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

package io.polygenesis.generators.java.api;

import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.models.api.ServiceModelRepository;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Java api impl generator.
 *
 * @author Christos Tsakostas
 */
public class JavaApiImplGenerator extends AbstractGenerator {

  private final ApiImplServiceExporter apiImplServiceExporter;

  private ServiceModelRepository serviceModelRepository;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java api impl generator.
   *
   * @param generationPath the generation path
   * @param apiImplServiceExporter the api impl service exporter
   */
  public JavaApiImplGenerator(Path generationPath, ApiImplServiceExporter apiImplServiceExporter) {
    super(generationPath);
    this.apiImplServiceExporter = apiImplServiceExporter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<ModelRepository> modelRepositories) {
    initializeModelRepositories(modelRepositories);

    serviceModelRepository
        .getServices()
        .forEach(
            service -> {
              apiImplServiceExporter.export(getGenerationPath(), service);
            });
  }

  /**
   * Initialize model repositories.
   *
   * @param modelRepositories the model repositories
   */
  private void initializeModelRepositories(Set<ModelRepository> modelRepositories) {
    serviceModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, ServiceModelRepository.class);
  }
}
