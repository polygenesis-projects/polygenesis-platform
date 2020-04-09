/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.java.apiclients.rest;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.apiclients.rest.aspect.RestServiceAspect;
import io.polygenesis.generators.java.apiclients.rest.aspect.RestServiceAspectGenerator;
import io.polygenesis.generators.java.apiclients.rest.constants.RestConstantsProjection;
import io.polygenesis.generators.java.apiclients.rest.constants.RestConstantsProjectionExporter;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.rest.Resource;
import io.polygenesis.models.rest.RestMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class ApiClientRestMetamodelGenerator extends AbstractMetamodelGenerator {

  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final ResourceGenerator resourceGenerator;
  private final RestConstantsProjectionExporter restConstantsProjectionExporter;
  private final RestServiceAspectGenerator restServiceAspectGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api client rest metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param resourceGenerator the resource generator
   * @param restConstantsProjectionExporter the rest constants projection exporter
   * @param restServiceAspectGenerator the rest service aspect generator
   */
  public ApiClientRestMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      ResourceGenerator resourceGenerator,
      RestConstantsProjectionExporter restConstantsProjectionExporter,
      RestServiceAspectGenerator restServiceAspectGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.resourceGenerator = resourceGenerator;
    this.restConstantsProjectionExporter = restConstantsProjectionExporter;
    this.restServiceAspectGenerator = restServiceAspectGenerator;
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

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ContextName getContextName() {
    return contextName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, RestMetamodelRepository.class)
        .getItems()
        .forEach(
            resource -> {
              resourceGenerator.generate(
                  resource,
                  resourceExportInfo(getGenerationPath(), resource),
                  getRootPackageName());
            });

    restConstantsProjectionExporter.export(
        getGenerationPath(),
        new RestConstantsProjection(getRootPackageName().getText(), getContextName().getText()));

    // SERVICE ASPECT
    RestServiceAspect serviceAspect = new RestServiceAspect(getContextName(), getRootPackageName());

    restServiceAspectGenerator.generate(
        serviceAspect,
        restServiceAspectExportInfo(getGenerationPath(), serviceAspect),
        getRootPackageName(),
        getContextName());
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  private ExportInfo restServiceAspectExportInfo(
      Path generationPath, RestServiceAspect restServiceAspect) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            restServiceAspect.getRootPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(restServiceAspect.getName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo resourceExportInfo(Path generationPath, Resource resource) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            resource.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(resource.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
