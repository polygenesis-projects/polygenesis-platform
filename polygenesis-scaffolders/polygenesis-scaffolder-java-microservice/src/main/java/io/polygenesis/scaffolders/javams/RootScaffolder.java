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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

/**
 * The type Root scaffolder.
 *
 * @author Christos Tsakostas
 */
public class RootScaffolder extends AbstractScaffolder {

  private static final String FTL_PATH = "/ftl/polygenesis-scaffolder-java-microservice";

  private final MavenParentPomScaffolder mavenParentPomScaffolder;
  private final GitIgnoreScaffolder gitIgnoreScaffolder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Root scaffolder.
   *
   * @param freemarkerService the freemarker service
   * @param mavenParentPomScaffolder the maven parent pom scaffolder
   * @param gitIgnoreScaffolder the git ignore scaffolder
   */
  public RootScaffolder(
      FreemarkerService freemarkerService,
      MavenParentPomScaffolder mavenParentPomScaffolder,
      GitIgnoreScaffolder gitIgnoreScaffolder) {
    super(freemarkerService);
    this.mavenParentPomScaffolder = mavenParentPomScaffolder;
    this.gitIgnoreScaffolder = gitIgnoreScaffolder;
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  @Override
  public void scaffold(
      Path generationPath, ProjectDescription projectDescription, Map<String, Object> dataModel) {

    mavenParentPomScaffolder.scaffold(generationPath, projectDescription, dataModel);
    gitIgnoreScaffolder.scaffold(generationPath, projectDescription, dataModel);

    try {
      PathService.ensurePath(Paths.get(generationPath.toString(), ".mvn/wrapper/"));

      Path targetPrepareToCommit = Paths.get(generationPath.toString(), "prepare-to-commit.sh");
      if (!Files.exists(targetPrepareToCommit)) {
        Files.copy(
            getClass().getResourceAsStream(FTL_PATH + "/prepare-to-commit.sh"),
            targetPrepareToCommit,
            StandardCopyOption.REPLACE_EXISTING);
      }

      Path targetMvnw = Paths.get(generationPath.toString(), "mvnw");
      if (!Files.exists(targetMvnw)) {
        Files.copy(
            getClass().getResourceAsStream(FTL_PATH + "/mvnw"),
            targetMvnw,
            StandardCopyOption.REPLACE_EXISTING);
      }

      Path targetMvnwCmd = Paths.get(generationPath.toString(), "mvnw.cmd");
      if (!Files.exists(targetMvnwCmd)) {
        Files.copy(
            getClass().getResourceAsStream(FTL_PATH + "/mvnw.cmd"),
            targetMvnwCmd,
            StandardCopyOption.REPLACE_EXISTING);
      }

      Files.copy(
          getClass().getResourceAsStream(FTL_PATH + "/.mvn/wrapper/maven-wrapper.jar"),
          Paths.get(generationPath.toString(), ".mvn/wrapper/maven-wrapper.jar"),
          StandardCopyOption.REPLACE_EXISTING);

      Files.copy(
          getClass().getResourceAsStream(FTL_PATH + "/.mvn/wrapper/maven-wrapper.properties"),
          Paths.get(generationPath.toString(), ".mvn/wrapper/maven-wrapper.properties"),
          StandardCopyOption.REPLACE_EXISTING);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }
}
