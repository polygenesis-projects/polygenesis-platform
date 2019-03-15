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

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.models.apiimpl.ServiceImplementationModelRepository;
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
  private final ServiceImplementationExporter serviceImplementationExporter;
  private final ServiceImplementationTestExporter serviceImplementationTestExporter;
  private final AggregateRootConverterExporter aggregateRootConverterExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java api impl generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param serviceImplementationExporter the api impl service exporter
   * @param serviceImplementationTestExporter the api impl service test exporter
   * @param aggregateRootConverterExporter the aggregate root converter exporter
   */
  public JavaApiImplGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ServiceImplementationExporter serviceImplementationExporter,
      ServiceImplementationTestExporter serviceImplementationTestExporter,
      AggregateRootConverterExporter aggregateRootConverterExporter) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.serviceImplementationExporter = serviceImplementationExporter;
    this.serviceImplementationTestExporter = serviceImplementationTestExporter;
    this.aggregateRootConverterExporter = aggregateRootConverterExporter;
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
    DomainModelRepository domainModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, DomainModelRepository.class);

    ServiceImplementationModelRepository serviceImplementationModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, ServiceImplementationModelRepository.class);

    if (serviceImplementationModelRepository.getServiceImplementations().isEmpty()) {
      throw new IllegalStateException();
    }

    serviceImplementationModelRepository
        .getServiceImplementations()
        .forEach(
            serviceImplementation -> {
              Optional<AggregateRoot> optionalAggregateRoot =
                  domainModelRepository.getAggregateRootByName(
                      new ObjectName(serviceImplementation.getService().getThingName().getText()));

              if (!optionalAggregateRoot.isPresent()) {
                throw new IllegalStateException(
                    String.format(
                        "Cannot get AggregateRoot with name=%s. Check that the AggregateRoot "
                            + "exists and take care of the order of generators",
                        serviceImplementation.getService().getThingName().getText()));
              }

              serviceImplementationExporter.export(
                  getGenerationPath(),
                  getRootPackageName(),
                  serviceImplementation,
                  optionalAggregateRoot.get());
              serviceImplementationTestExporter.export(getGenerationPath(), serviceImplementation);
            });

    serviceImplementationModelRepository
        .getAggregateRootConverters()
        .forEach(
            aggregateRootConverter ->
                aggregateRootConverterExporter.export(getGenerationPath(), aggregateRootConverter));
  }
}
