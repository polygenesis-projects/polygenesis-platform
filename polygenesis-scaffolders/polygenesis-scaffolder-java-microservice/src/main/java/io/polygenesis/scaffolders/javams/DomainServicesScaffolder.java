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
 * The type Domain services scaffolder.
 *
 * @author Christos Tsakostas
 */
public class DomainServicesScaffolder extends AbstractScaffolder {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain services scaffolder.
   *
   * @param freemarkerService the freemarker service
   */
  public DomainServicesScaffolder(FreemarkerService freemarkerService) {
    super(freemarkerService);
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
            projectDescription.getModulePrefix() + "-domain-services-impl");

    ensureSources(modulePath, projectDescription);

    exportDomainMavenPomXml(modulePath, dataModel);
  }

  private void exportDomainMavenPomXml(Path modulePath, Map<String, Object> dataModel) {

    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/domain-services-impl/pom.xml.ftl",
        Paths.get(modulePath.toString(), "pom.xml"));
  }
}
