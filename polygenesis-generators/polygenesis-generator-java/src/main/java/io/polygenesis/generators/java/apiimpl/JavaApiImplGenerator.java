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

package io.polygenesis.generators.java.apiimpl;

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.models.api.ServiceModelRepository;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.DomainModelRepository;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

/**
 * The type Java api impl generator.
 *
 * @author Christos Tsakostas
 */
public class JavaApiImplGenerator extends AbstractGenerator {

  private final PackageName rootPackageName;
  private final ApiImplServiceExporter apiImplServiceExporter;
  private final ApiImplServiceTestExporter apiImplServiceTestExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java api impl generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param apiImplServiceExporter the api impl service exporter
   * @param apiImplServiceTestExporter the api impl service test exporter
   */
  public JavaApiImplGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ApiImplServiceExporter apiImplServiceExporter,
      ApiImplServiceTestExporter apiImplServiceTestExporter) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.apiImplServiceExporter = apiImplServiceExporter;
    this.apiImplServiceTestExporter = apiImplServiceTestExporter;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<ModelRepository> modelRepositories) {
    ServiceModelRepository serviceModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, ServiceModelRepository.class);

    DomainModelRepository domainModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, DomainModelRepository.class);

    serviceModelRepository
        .getServices()
        .forEach(
            service -> {
              Optional<AggregateRoot> optionalAggregateRoot =
                  domainModelRepository.getAggregateRootByName(
                      new Name(service.getThingName().getText()));

              if (!optionalAggregateRoot.isPresent()) {
                throw new IllegalStateException(
                    String.format(
                        "Cannot get AggregateRoot with name=%s. Check that the AggregateRoot "
                            + "exists and take care of the order of generators",
                        service.getThingName().getText()));
              }

              apiImplServiceExporter.export(
                  getGenerationPath(), getRootPackageName(), service, optionalAggregateRoot.get());
              apiImplServiceTestExporter.export(getGenerationPath(), service);
            });
  }
}
