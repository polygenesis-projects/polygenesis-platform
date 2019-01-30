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
 * The type Secondary adapters scaffolder.
 *
 * @author Christos Tsakostas
 */
public class SecondaryAdaptersScaffolder extends AbstractScaffolder {

  private final SecondaryAdapterRdbmsScaffolder secondaryAdapterRdbmsScaffolder;
  private final SecondaryAdapterPublisherScaffolder secondaryAdapterPublisherScaffolder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Secondary adapters scaffolder.
   *
   * @param freemarkerService the freemarker service
   * @param secondaryAdapterRdbmsScaffolder the secondary adapter rdbms scaffolder
   * @param secondaryAdapterPublisherScaffolder the secondary adapter publisher scaffolder
   */
  public SecondaryAdaptersScaffolder(
      FreemarkerService freemarkerService,
      SecondaryAdapterRdbmsScaffolder secondaryAdapterRdbmsScaffolder,
      SecondaryAdapterPublisherScaffolder secondaryAdapterPublisherScaffolder) {
    super(freemarkerService);
    this.secondaryAdapterRdbmsScaffolder = secondaryAdapterRdbmsScaffolder;
    this.secondaryAdapterPublisherScaffolder = secondaryAdapterPublisherScaffolder;
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  @Override
  public void scaffold(
      Path generationPath, ProjectDescription projectDescription, Map<String, Object> dataModel) {
    Path modulePath =
        Paths.get(
            generationPath.toString(),
            projectDescription.getModulePrefix() + "-secondary-adapters");
    PathService.ensurePath(generationPath);

    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/secondary-adapters/pom.xml.ftl",
        Paths.get(modulePath.toString(), "pom.xml"));

    secondaryAdapterRdbmsScaffolder.scaffold(generationPath, projectDescription, dataModel);
    secondaryAdapterPublisherScaffolder.scaffold(generationPath, projectDescription, dataModel);
  }
}
