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
import java.nio.file.Path;

/**
 * The type Scaffolder java microservice factory.
 *
 * @author Christos Tsakostas
 */
public class ScaffolderJavaMicroserviceFactory {

  // ===============================================================================================
  // SINGLETONS / STATIC
  // ===============================================================================================
  private static final MavenSettingsScaffolder mavenSettingsScaffolder;
  private static final RootScaffolder rootScaffolder;
  private static final DomainModelScaffolder domainModelScaffolder;
  private static final DomainServicesScaffolder domainServicesScaffolder;
  private static final ApiScaffolder apiScaffolder;
  private static final ApiImplScaffolder apiImplScaffolder;
  private static final PrimaryAdaptersScaffolder primaryAdaptersScaffolder;
  private static final SecondaryAdaptersScaffolder secondaryAdaptersScaffolder;
  private static final AppScaffolder appScaffolder;
  private static final ModuleScaffolder moduleScaffolder;

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
    apiImplScaffolder = new ApiImplScaffolder(freemarkerService);
    appScaffolder = new AppScaffolder(freemarkerService);
    moduleScaffolder = new ModuleScaffolder(freemarkerService);

    PrimaryAdapterRestScaffolder primaryAdapterRestScaffolder =
        new PrimaryAdapterRestScaffolder(freemarkerService);
    PrimaryAdapterSubscriberScaffolder primaryAdapterSubscriberScaffolder =
        new PrimaryAdapterSubscriberScaffolder(freemarkerService);

    primaryAdaptersScaffolder =
        new PrimaryAdaptersScaffolder(
            freemarkerService, primaryAdapterRestScaffolder, primaryAdapterSubscriberScaffolder);

    SecondaryAdapterRdbmsScaffolder secondaryAdapterRdbmsScaffolder =
        new SecondaryAdapterRdbmsScaffolder(freemarkerService);

    SecondaryAdapterPublisherScaffolder secondaryAdapterPublisherScaffolder =
        new SecondaryAdapterPublisherScaffolder(freemarkerService);

    secondaryAdaptersScaffolder =
        new SecondaryAdaptersScaffolder(
            freemarkerService,
            secondaryAdapterRdbmsScaffolder,
            secondaryAdapterPublisherScaffolder);
  }

  // ===============================================================================================
  // FACTORY
  // ===============================================================================================

  /**
   * New instance scaffolder java microservice.
   *
   * @param generationPath the generation path
   * @return the scaffolder java microservice
   */
  public static ScaffolderJavaMicroservice newInstance(Path generationPath) {
    return new ScaffolderJavaMicroservice(
        mavenSettingsScaffolder,
        rootScaffolder,
        domainModelScaffolder,
        domainServicesScaffolder,
        apiScaffolder,
        apiImplScaffolder,
        primaryAdaptersScaffolder,
        secondaryAdaptersScaffolder,
        appScaffolder,
        moduleScaffolder);
  }
}
