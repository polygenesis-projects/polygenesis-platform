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

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.path.PathService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type Primary adapters scaffolder.
 *
 * @author Christos Tsakostas
 */
public class PrimaryAdaptersScaffolder extends AbstractScaffolder {

  private final PrimaryAdapterRestScaffolder primaryAdapterRestScaffolder;
  private final PrimaryAdapterSubscriberScaffolder primaryAdapterSubscriberScaffolder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Primary adapters scaffolder.
   *
   * @param freemarkerService the freemarker service
   * @param primaryAdapterRestScaffolder the primary adapter rest scaffolder
   * @param primaryAdapterSubscriberScaffolder the primary subscriber scaffolder
   */
  public PrimaryAdaptersScaffolder(
      FreemarkerService freemarkerService,
      PrimaryAdapterRestScaffolder primaryAdapterRestScaffolder,
      PrimaryAdapterSubscriberScaffolder primaryAdapterSubscriberScaffolder) {
    super(freemarkerService);
    this.primaryAdapterRestScaffolder = primaryAdapterRestScaffolder;
    this.primaryAdapterSubscriberScaffolder = primaryAdapterSubscriberScaffolder;
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  @Override
  public void scaffold(
      Path generationPath, ProjectDescription projectDescription, Map<String, Object> dataModel) {
    Path modulePath =
        Paths.get(
            generationPath.toString(), projectDescription.getModulePrefix() + "-primary-adapters");
    PathService.ensurePath(generationPath);

    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/primary-adapters/pom.xml.ftl",
        Paths.get(modulePath.toString(), "pom.xml"));

    primaryAdapterRestScaffolder.scaffold(generationPath, projectDescription, dataModel);
    primaryAdapterSubscriberScaffolder.scaffold(generationPath, projectDescription, dataModel);
  }
}
