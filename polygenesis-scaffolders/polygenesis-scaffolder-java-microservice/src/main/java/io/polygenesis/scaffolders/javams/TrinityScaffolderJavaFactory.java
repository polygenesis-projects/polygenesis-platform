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

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.scaffolders.javams.api.ApiClientRestScaffolder;
import io.polygenesis.scaffolders.javams.api.ApiClientSubscriberScaffolder;
import io.polygenesis.scaffolders.javams.api.ApiClientsScaffolder;
import io.polygenesis.scaffolders.javams.api.ApiDetailScaffolder;
import io.polygenesis.scaffolders.javams.api.ApiScaffolder;
import io.polygenesis.scaffolders.javams.auxiliary.AuxiliaryDetailPublisherScaffolder;
import io.polygenesis.scaffolders.javams.auxiliary.AuxiliaryDetailsScaffolder;
import io.polygenesis.scaffolders.javams.auxiliary.AuxiliaryScaffolder;
import io.polygenesis.scaffolders.javams.domain.DomainDetailsPersistenceRdbmsScaffolder;
import io.polygenesis.scaffolders.javams.domain.DomainDetailsScaffolder;
import io.polygenesis.scaffolders.javams.domain.DomainModelScaffolder;
import io.polygenesis.scaffolders.javams.domain.DomainServicesScaffolder;
import java.nio.file.Path;

/**
 * The type Trinity scaffolder java factory.
 *
 * @author Christos Tsakostas
 */
public class TrinityScaffolderJavaFactory {

  // ===============================================================================================
  // SINGLETONS / STATIC
  // ===============================================================================================
  private static MavenSettingsScaffolder mavenSettingsScaffolder;
  private static RootScaffolder rootScaffolder;
  private static DomainModelScaffolder domainModelScaffolder;
  private static DomainServicesScaffolder domainServicesScaffolder;
  private static ApiScaffolder apiScaffolder;
  private static ApiDetailScaffolder apiDetailScaffolder;
  private static ApiClientsScaffolder apiClientsScaffolder;
  private static AuxiliaryScaffolder auxiliaryScaffolder;
  private static AuxiliaryDetailsScaffolder auxiliaryDetailsScaffolder;
  private static AppScaffolder appScaffolder;
  private static ModuleScaffolder moduleScaffolder;
  private static DomainDetailsScaffolder domainDetailsScaffolder;

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    MavenParentPomScaffolder mavenParentPomScaffolder =
        new MavenParentPomScaffolder(freemarkerService);
    GitIgnoreScaffolder gitIgnoreScaffolder = new GitIgnoreScaffolder(freemarkerService);

    mavenSettingsScaffolder = new MavenSettingsScaffolder(freemarkerService);
    rootScaffolder =
        new RootScaffolder(freemarkerService, mavenParentPomScaffolder, gitIgnoreScaffolder);
    domainModelScaffolder = new DomainModelScaffolder(freemarkerService);
    domainServicesScaffolder = new DomainServicesScaffolder(freemarkerService);
    apiScaffolder = new ApiScaffolder(freemarkerService);
    apiDetailScaffolder = new ApiDetailScaffolder(freemarkerService);
    appScaffolder = new AppScaffolder(freemarkerService);
    moduleScaffolder = new ModuleScaffolder(freemarkerService);

    ApiClientRestScaffolder apiClientRestScaffolder =
        new ApiClientRestScaffolder(freemarkerService);
    ApiClientSubscriberScaffolder apiClientSubscriberScaffolder =
        new ApiClientSubscriberScaffolder(freemarkerService);

    apiClientsScaffolder =
        new ApiClientsScaffolder(
            freemarkerService, apiClientRestScaffolder, apiClientSubscriberScaffolder);

    DomainDetailsPersistenceRdbmsScaffolder domainDetailsPersistenceRdbmsScaffolder =
        new DomainDetailsPersistenceRdbmsScaffolder(freemarkerService);

    AuxiliaryDetailPublisherScaffolder auxiliaryDetailPublisherScaffolder =
        new AuxiliaryDetailPublisherScaffolder(freemarkerService);

    auxiliaryScaffolder = new AuxiliaryScaffolder(freemarkerService);

    auxiliaryDetailsScaffolder =
        new AuxiliaryDetailsScaffolder(
            freemarkerService,
            domainDetailsPersistenceRdbmsScaffolder,
            auxiliaryDetailPublisherScaffolder);

    domainDetailsScaffolder = new DomainDetailsScaffolder(freemarkerService);
  }

  // ===============================================================================================
  // FACTORY
  // ===============================================================================================

  /**
   * New instance trinity scaffolder java.
   *
   * @param generationPath the generation path
   * @return the trinity scaffolder java
   */
  public static TrinityScaffolderJava newInstance(Path generationPath) {
    return new TrinityScaffolderJava(
        mavenSettingsScaffolder,
        rootScaffolder,
        domainModelScaffolder,
        domainServicesScaffolder,
        apiScaffolder,
        apiDetailScaffolder,
        apiClientsScaffolder,
        auxiliaryScaffolder,
        auxiliaryDetailsScaffolder,
        appScaffolder,
        moduleScaffolder,
        domainDetailsScaffolder);
  }
}
