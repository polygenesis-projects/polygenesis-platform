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

import io.polygenesis.scaffolders.javams.api.ApiClientsScaffolder;
import io.polygenesis.scaffolders.javams.api.ApiDetailScaffolder;
import io.polygenesis.scaffolders.javams.api.ApiScaffolder;
import io.polygenesis.scaffolders.javams.auxiliary.AuxiliaryDetailsScaffolder;
import io.polygenesis.scaffolders.javams.auxiliary.AuxiliaryScaffolder;
import io.polygenesis.scaffolders.javams.domain.DomainDetailsScaffolder;
import io.polygenesis.scaffolders.javams.domain.DomainModelScaffolder;
import io.polygenesis.scaffolders.javams.domain.DomainServicesScaffolder;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Trinity scaffolder java.
 *
 * @author Christos Tsakostas
 */
public class TrinityScaffolderJava {

  private final MavenSettingsScaffolder mavenSettingsScaffolder;
  private final RootScaffolder rootScaffolder;
  private final DomainModelScaffolder domainModelScaffolder;
  private final DomainServicesScaffolder domainServicesScaffolder;
  private final ApiScaffolder apiScaffolder;
  private final ApiDetailScaffolder apiDetailScaffolder;
  private final ApiClientsScaffolder apiClientsScaffolder;
  private final AuxiliaryScaffolder auxiliaryScaffolder;
  private final AuxiliaryDetailsScaffolder auxiliaryDetailsScaffolder;
  private final AppScaffolder appScaffolder;
  private final ModuleScaffolder moduleScaffolder;
  private final DomainDetailsScaffolder domainDetailsScaffolder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Trinity scaffolder java.
   *
   * @param mavenSettingsScaffolder the maven settings scaffolder
   * @param rootScaffolder the root scaffolder
   * @param domainModelScaffolder the domain model scaffolder
   * @param domainServicesScaffolder the domain services scaffolder
   * @param apiScaffolder the api scaffolder
   * @param apiDetailScaffolder the api detail scaffolder
   * @param apiClientsScaffolder the api clients scaffolder
   * @param auxiliaryScaffolder the auxiliary scaffolder
   * @param auxiliaryDetailsScaffolder the auxiliary details scaffolder
   * @param appScaffolder the app scaffolder
   * @param moduleScaffolder the module scaffolder
   * @param domainDetailsScaffolder the domain details scaffolder
   */
  public TrinityScaffolderJava(
      MavenSettingsScaffolder mavenSettingsScaffolder,
      RootScaffolder rootScaffolder,
      DomainModelScaffolder domainModelScaffolder,
      DomainServicesScaffolder domainServicesScaffolder,
      ApiScaffolder apiScaffolder,
      ApiDetailScaffolder apiDetailScaffolder,
      ApiClientsScaffolder apiClientsScaffolder,
      AuxiliaryScaffolder auxiliaryScaffolder,
      AuxiliaryDetailsScaffolder auxiliaryDetailsScaffolder,
      AppScaffolder appScaffolder,
      ModuleScaffolder moduleScaffolder,
      DomainDetailsScaffolder domainDetailsScaffolder) {
    this.mavenSettingsScaffolder = mavenSettingsScaffolder;
    this.rootScaffolder = rootScaffolder;
    this.domainModelScaffolder = domainModelScaffolder;
    this.domainServicesScaffolder = domainServicesScaffolder;
    this.apiScaffolder = apiScaffolder;
    this.apiDetailScaffolder = apiDetailScaffolder;
    this.apiClientsScaffolder = apiClientsScaffolder;
    this.auxiliaryScaffolder = auxiliaryScaffolder;
    this.auxiliaryDetailsScaffolder = auxiliaryDetailsScaffolder;
    this.appScaffolder = appScaffolder;
    this.moduleScaffolder = moduleScaffolder;
    this.domainDetailsScaffolder = domainDetailsScaffolder;
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
      apiDetailScaffolder.scaffold(generationPath, projectDescription, dataModel);
      apiClientsScaffolder.scaffold(generationPath, projectDescription, dataModel);
      auxiliaryScaffolder.scaffold(generationPath, projectDescription, dataModel);
      auxiliaryDetailsScaffolder.scaffold(generationPath, projectDescription, dataModel);
      appScaffolder.scaffold(generationPath, projectDescription, dataModel);
      domainDetailsScaffolder.scaffold(generationPath, projectDescription, dataModel);
      apiClientsScaffolder.scaffold(generationPath, projectDescription, dataModel);
    }
  }
}
