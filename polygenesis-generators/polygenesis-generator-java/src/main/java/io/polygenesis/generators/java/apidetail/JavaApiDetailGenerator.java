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

package io.polygenesis.generators.java.apidetail;

import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverterMetamodelRepository;
import io.polygenesis.models.apiimpl.ServiceImplementationMetamodelRepository;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Java api detail generator.
 *
 * @author Christos Tsakostas
 */
public class JavaApiDetailGenerator extends AbstractGenerator {

  private final PackageName rootPackageName;
  private final ServiceImplementationExporter serviceImplementationExporter;
  private final ServiceImplementationTestExporter serviceImplementationTestExporter;
  private final DomainObjectConverterExporter domainObjectConverterExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java api detail generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param serviceImplementationExporter the service implementation exporter
   * @param serviceImplementationTestExporter the service implementation test exporter
   * @param domainObjectConverterExporter the domain object converter exporter
   */
  public JavaApiDetailGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ServiceImplementationExporter serviceImplementationExporter,
      ServiceImplementationTestExporter serviceImplementationTestExporter,
      DomainObjectConverterExporter domainObjectConverterExporter) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.serviceImplementationExporter = serviceImplementationExporter;
    this.serviceImplementationTestExporter = serviceImplementationTestExporter;
    this.domainObjectConverterExporter = domainObjectConverterExporter;
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

  @SuppressWarnings("rawtypes")
  @Override
  public void generate(Set<MetamodelRepository> modelRepositories) {
    ServiceImplementationMetamodelRepository serviceImplementationModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, ServiceImplementationMetamodelRepository.class);

    if (serviceImplementationModelRepository.getItems().isEmpty()) {
      throw new IllegalStateException();
    }

    serviceImplementationModelRepository
        .getItems()
        .forEach(
            serviceImplementation -> {
              serviceImplementationExporter.export(getGenerationPath(), serviceImplementation);
              serviceImplementationTestExporter.export(getGenerationPath(), serviceImplementation);
            });

    DomainEntityConverterMetamodelRepository domainEntityConverterModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, DomainEntityConverterMetamodelRepository.class);

    domainEntityConverterModelRepository
        .getItems()
        .forEach(
            domainEntityConverter ->
                domainObjectConverterExporter.export(getGenerationPath(), domainEntityConverter));
  }
}
