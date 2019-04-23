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

package io.polygenesis.scaffolders.javams.auxiliary;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.path.PathService;
import io.polygenesis.scaffolders.javams.AbstractScaffolder;
import io.polygenesis.scaffolders.javams.ProjectDescription;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type Auxiliary scaffolder.
 *
 * @author Christos Tsakostas
 */
public class AuxiliaryScaffolder extends AbstractScaffolder {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Auxiliary scaffolder.
   *
   * @param freemarkerService the freemarker service
   */
  public AuxiliaryScaffolder(FreemarkerService freemarkerService) {
    super(freemarkerService);
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  @Override
  public void scaffold(
      Path generationPath, ProjectDescription projectDescription, Map<String, Object> dataModel) {

    // Check if Microservice is enabled
    if (!projectDescription.isMicroservice()) {
      return;
    }

    Path modulePath =
        Paths.get(generationPath.toString(), projectDescription.getModulePrefix() + "-aux");

    PathService.ensurePath(generationPath);

    ensureSources(modulePath, projectDescription);

    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/auxiliary/pom.xml.ftl",
        Paths.get(modulePath.toString(), "pom.xml"));
  }
}
