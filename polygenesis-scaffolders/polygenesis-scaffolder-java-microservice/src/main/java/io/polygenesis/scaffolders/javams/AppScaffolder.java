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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type App scaffolder.
 *
 * @author Christos Tsakostas
 */
public class AppScaffolder extends AbstractScaffolder {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new App scaffolder.
   *
   * @param freemarkerService the freemarker service
   */
  public AppScaffolder(FreemarkerService freemarkerService) {
    super(freemarkerService);
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  @Override
  public void scaffold(
      Path generationPath, ProjectDescription projectDescription, Map<String, Object> dataModel) {

    // Check if Layer is enabled
    if (!projectDescription.getLayers().contains(Layer.APP)) {
      return;
    }

    Path modulePath =
        Paths.get(generationPath.toString(), projectDescription.getModulePrefix() + "-service");

    ensureSources(modulePath, projectDescription);

    if (projectDescription
        .getLayers()
        .contains(Layer.SECONDARY_ADAPTER_PERSISTENCE_SPRING_DATA_JPA)) {
      exportApplicationYml(modulePath, dataModel);
      exportConfigApplicationDevYml(modulePath, dataModel);
      exportConfigApplicationCiYml(modulePath, dataModel);
    } else {
      exportApplicationNakedYml(modulePath, dataModel);
    }

    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/app/pom.xml.ftl",
        Paths.get(modulePath.toString(), "pom.xml"));

    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/app/Application.java.ftl",
        Paths.get(
            modulePath.toString(),
            "src/main/java",
            toPath(projectDescription.getGroupId()),
            "Application.java"));

    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/app/ApplicationTest.java.ftl",
        Paths.get(
            modulePath.toString(),
            "src/test/java",
            toPath(projectDescription.getGroupId()),
            "ApplicationTest.java"));
  }

  private void exportApplicationYml(Path modulePath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/app/resources/application.yml.ftl",
        Paths.get(modulePath.toString(), "src/main/resources", "application.yml"));
  }

  private void exportConfigApplicationDevYml(Path modulePath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/app/resources/config/application-dev.yml.ftl",
        Paths.get(modulePath.toString(), "src/main/resources/config", "application-dev.yml"));
  }

  private void exportConfigApplicationCiYml(Path modulePath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/app/resources/config/application-ci.yml.ftl",
        Paths.get(modulePath.toString(), "src/main/resources/config", "application-ci.yml"));
  }

  private void exportApplicationNakedYml(Path modulePath, Map<String, Object> dataModel) {
    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/app/resources/application-naked.yml.ftl",
        Paths.get(modulePath.toString(), "src/main/resources", "application.yml"));
  }
}
