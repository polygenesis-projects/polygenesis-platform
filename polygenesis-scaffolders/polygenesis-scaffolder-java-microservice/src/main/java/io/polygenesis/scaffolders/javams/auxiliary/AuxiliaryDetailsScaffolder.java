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
import io.polygenesis.scaffolders.javams.Layer;
import io.polygenesis.scaffolders.javams.ProjectDescription;
import io.polygenesis.scaffolders.javams.domain.DomainDetailsPersistenceRdbmsScaffolder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type Secondary adapters scaffolder.
 *
 * @author Christos Tsakostas
 */
public class AuxiliaryDetailsScaffolder extends AbstractScaffolder {

  private final DomainDetailsPersistenceRdbmsScaffolder domainDetailsPersistenceRdbmsScaffolder;
  private final AuxiliaryDetailPublisherScaffolder auxiliaryDetailPublisherScaffolder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Secondary adapters scaffolder.
   *
   * @param freemarkerService the freemarker service
   * @param domainDetailsPersistenceRdbmsScaffolder the secondary adapter rdbms scaffolder
   * @param auxiliaryDetailPublisherScaffolder the secondary adapter publisher scaffolder
   */
  public AuxiliaryDetailsScaffolder(
      FreemarkerService freemarkerService,
      DomainDetailsPersistenceRdbmsScaffolder domainDetailsPersistenceRdbmsScaffolder,
      AuxiliaryDetailPublisherScaffolder auxiliaryDetailPublisherScaffolder) {
    super(freemarkerService);
    this.domainDetailsPersistenceRdbmsScaffolder = domainDetailsPersistenceRdbmsScaffolder;
    this.auxiliaryDetailPublisherScaffolder = auxiliaryDetailPublisherScaffolder;
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  @Override
  public void scaffold(
      Path generationPath, ProjectDescription projectDescription, Map<String, Object> dataModel) {

    // Check if Layer is enabled
    if (!projectDescription.getLayers().contains(Layer.SECONDARY_ADAPTER_PUBLISHER_ACTIVEMQ)
        && !projectDescription
            .getLayers()
            .contains(Layer.SECONDARY_ADAPTER_PERSISTENCE_SPRING_DATA_JPA)) {
      return;
    }

    Path modulePath =
        Paths.get(generationPath.toString(), projectDescription.getModulePrefix() + "-aux-details");
    PathService.ensurePath(generationPath);

    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/auxiliary-details/pom.xml.ftl",
        Paths.get(modulePath.toString(), "pom.xml"));

    domainDetailsPersistenceRdbmsScaffolder.scaffold(generationPath, projectDescription, dataModel);
    auxiliaryDetailPublisherScaffolder.scaffold(generationPath, projectDescription, dataModel);
  }
}
