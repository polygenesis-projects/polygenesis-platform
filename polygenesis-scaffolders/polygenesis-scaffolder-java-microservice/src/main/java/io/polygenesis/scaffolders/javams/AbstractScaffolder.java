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
 * The type Abstract scaffolder.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractScaffolder {

  /** The Freemarker service. */
  protected final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract scaffolder.
   *
   * @param freemarkerService the freemarker service
   */
  public AbstractScaffolder(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  /**
   * Scaffold.
   *
   * @param generationPath the generation path
   * @param projectDescription the project description
   * @param dataModel the data model
   */
  public abstract void scaffold(
      Path generationPath, ProjectDescription projectDescription, Map<String, Object> dataModel);

  /**
   * Ensure sources.
   *
   * @param generationPath the generation path
   * @param projectDescription the project description
   */
  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================
  protected void ensureSources(Path generationPath, ProjectDescription projectDescription) {
    PathService.ensurePath(generationPath);

    PathService.ensurePath(
        Paths.get(
            generationPath.toString(), "src/main/java", toPath(projectDescription.getGroupId())));

    PathService.ensurePath(Paths.get(generationPath.toString(), "src/main/resources"));

    PathService.ensurePath(
        Paths.get(
            generationPath.toString(), "src/test/java", toPath(projectDescription.getGroupId())));

    PathService.ensurePath(Paths.get(generationPath.toString(), "src/test/resources"));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================
  private String toPath(String groupId) {
    return Paths.get(groupId.replaceAll("\\.", "/")).toString();
  }
}
