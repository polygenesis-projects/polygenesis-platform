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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.apidetail.converter.DomainObjectConverterExporter;
import io.polygenesis.generators.java.apidetail.service.ServiceDetailGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.apiimpl.DomainEntityConverterMetamodelRepository;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Java api detail generator.
 *
 * @author Christos Tsakostas
 */
public class JavaApiDetailMetamodelGenerator extends AbstractMetamodelGenerator {

  private final PackageName rootPackageName;
  private final ServiceDetailGenerator serviceDetailGenerator;
  private final DomainObjectConverterExporter domainObjectConverterExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java api detail metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param serviceDetailGenerator the service detail generator
   * @param domainObjectConverterExporter the domain object converter exporter
   */
  public JavaApiDetailMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ServiceDetailGenerator serviceDetailGenerator,
      DomainObjectConverterExporter domainObjectConverterExporter) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.serviceDetailGenerator = serviceDetailGenerator;
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

  @Override
  public void generate(Set<MetamodelRepository<?>> metamodelRepositories) {
    ServiceImplementationMetamodelRepository serviceImplementationModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, ServiceImplementationMetamodelRepository.class);

    serviceImplementationModelRepository
        .getItems()
        .forEach(
            serviceImplementation -> {
              serviceDetailGenerator.generate(
                  serviceImplementation,
                  serviceExportInfo(getGenerationPath(), serviceImplementation),
                  metamodelRepositories);
            });

    DomainEntityConverterMetamodelRepository domainEntityConverterModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, DomainEntityConverterMetamodelRepository.class);

    domainEntityConverterModelRepository
        .getItems()
        .forEach(
            domainEntityConverter ->
                domainObjectConverterExporter.export(getGenerationPath(), domainEntityConverter));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo serviceExportInfo(
      Path generationPath, ServiceImplementation serviceImplementation) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            serviceImplementation.getService().getPackageName().toPath().toString()),
        TextConverter.toUpperCamel(serviceImplementation.getService().getServiceName().getText())
            + "Impl"
            + FolderFileConstants.JAVA_POSTFIX);
  }
}
