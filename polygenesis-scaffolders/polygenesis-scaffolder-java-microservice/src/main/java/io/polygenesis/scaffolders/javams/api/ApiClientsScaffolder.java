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

package io.polygenesis.scaffolders.javams.api;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.path.PathService;
import io.polygenesis.scaffolders.javams.AbstractScaffolder;
import io.polygenesis.scaffolders.javams.Layer;
import io.polygenesis.scaffolders.javams.ProjectDescription;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type Primary adapters scaffolder.
 *
 * @author Christos Tsakostas
 */
public class ApiClientsScaffolder extends AbstractScaffolder {

  private final ApiClientRestScaffolder apiClientRestScaffolder;
  private final ApiClientSubscriberScaffolder apiClientSubscriberScaffolder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public ApiClientsScaffolder(
      FreemarkerService freemarkerService,
      ApiClientRestScaffolder apiClientRestScaffolder,
      ApiClientSubscriberScaffolder apiClientSubscriberScaffolder) {
    super(freemarkerService);
    this.apiClientRestScaffolder = apiClientRestScaffolder;
    this.apiClientSubscriberScaffolder = apiClientSubscriberScaffolder;
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  @Override
  public void scaffold(
      Path generationPath, ProjectDescription projectDescription, Map<String, Object> dataModel) {

    // Check if Layer is enabled
    if (!projectDescription.getLayers().contains(Layer.PRIMARY_ADAPTER_SUBSCRIBER_ACTIVEMQ)
        && !projectDescription.getLayers().contains(Layer.PRIMARY_ADAPTER_REST_SPRING)
        && !projectDescription.getLayers().contains(Layer.PRIMARY_ADAPTER_REST_CLIENT_SPRING)) {
      return;
    }

    Path modulePath =
        Paths.get(generationPath.toString(), projectDescription.getModulePrefix() + "-api-clients");
    PathService.ensurePath(generationPath);

    freemarkerService.export(
        dataModel,
        "polygenesis-scaffolder-java-microservice/api-clients/pom.xml.ftl",
        Paths.get(modulePath.toString(), "pom.xml"));

    apiClientRestScaffolder.scaffold(generationPath, projectDescription, dataModel);
    apiClientSubscriberScaffolder.scaffold(generationPath, projectDescription, dataModel);
  }
}
