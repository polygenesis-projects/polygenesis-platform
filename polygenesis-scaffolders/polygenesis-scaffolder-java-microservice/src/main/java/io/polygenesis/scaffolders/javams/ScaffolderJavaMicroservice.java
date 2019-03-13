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

package io.polygenesis.scaffolders.javams;

import io.polygenesis.core.Scaffolder;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The type PolyGenesis scaffolder java microservice.
 *
 * @author Christos Tsakostas
 */
public class ScaffolderJavaMicroservice implements Scaffolder {

  private final MavenSettingsScaffolder mavenSettingsScaffolder;
  private final RootScaffolder rootScaffolder;
  private final DomainModelScaffolder domainModelScaffolder;
  private final DomainServicesScaffolder domainServicesScaffolder;
  private final ApiScaffolder apiScaffolder;
  private final ApiImplScaffolder apiImplScaffolder;
  private final PrimaryAdaptersScaffolder primaryAdaptersScaffolder;
  private final SecondaryAdaptersScaffolder secondaryAdaptersScaffolder;
  private final AppScaffolder appScaffolder;
  private final ModuleScaffolder moduleScaffolder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scaffolder java microservice.
   *
   * @param mavenSettingsScaffolder the maven settings scaffolder
   * @param rootScaffolder the root scaffolder
   * @param domainModelScaffolder the domain model scaffolder
   * @param domainServicesScaffolder the domain services scaffolder
   * @param apiScaffolder the api scaffolder
   * @param apiImplScaffolder the api impl scaffolder
   * @param primaryAdaptersScaffolder the primary adapters scaffolder
   * @param secondaryAdaptersScaffolder the secondary adapters scaffolder
   * @param appScaffolder the app scaffolder
   * @param moduleScaffolder the module scaffolder
   */
  public ScaffolderJavaMicroservice(
      MavenSettingsScaffolder mavenSettingsScaffolder,
      RootScaffolder rootScaffolder,
      DomainModelScaffolder domainModelScaffolder,
      DomainServicesScaffolder domainServicesScaffolder,
      ApiScaffolder apiScaffolder,
      ApiImplScaffolder apiImplScaffolder,
      PrimaryAdaptersScaffolder primaryAdaptersScaffolder,
      SecondaryAdaptersScaffolder secondaryAdaptersScaffolder,
      AppScaffolder appScaffolder,
      ModuleScaffolder moduleScaffolder) {
    this.mavenSettingsScaffolder = mavenSettingsScaffolder;
    this.rootScaffolder = rootScaffolder;
    this.domainModelScaffolder = domainModelScaffolder;
    this.domainServicesScaffolder = domainServicesScaffolder;
    this.apiScaffolder = apiScaffolder;
    this.apiImplScaffolder = apiImplScaffolder;
    this.primaryAdaptersScaffolder = primaryAdaptersScaffolder;
    this.secondaryAdaptersScaffolder = secondaryAdaptersScaffolder;
    this.appScaffolder = appScaffolder;
    this.moduleScaffolder = moduleScaffolder;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Scaffold.
   *
   * @param generationPath the generation path
   * @param projectDescription the project description
   */
  public void scaffold(Path generationPath, ProjectDescription projectDescription) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("projectDescription", projectDescription);

    mavenSettingsScaffolder.scaffold(generationPath, projectDescription, dataModel);
    rootScaffolder.scaffold(generationPath, projectDescription, dataModel);
    moduleScaffolder.scaffold(generationPath, projectDescription, dataModel);

    if (projectDescription.isMicroservice()) {
      domainModelScaffolder.scaffold(generationPath, projectDescription, dataModel);
      domainServicesScaffolder.scaffold(generationPath, projectDescription, dataModel);
      apiScaffolder.scaffold(generationPath, projectDescription, dataModel);
      apiImplScaffolder.scaffold(generationPath, projectDescription, dataModel);
      primaryAdaptersScaffolder.scaffold(generationPath, projectDescription, dataModel);
      secondaryAdaptersScaffolder.scaffold(generationPath, projectDescription, dataModel);
      appScaffolder.scaffold(generationPath, projectDescription, dataModel);
    }
  }
}
