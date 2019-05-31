/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.generators.java.api.exporter;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.api.transformer.ServiceInterfaceTransformer;
import io.polygenesis.models.api.Service;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Api service exporter.
 *
 * @author Christos Tsakostas
 */
public class ServiceExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ServiceInterfaceTransformer serviceInterfaceTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service exporter.
   *
   * @param freemarkerService the freemarker service
   * @param serviceInterfaceTransformer the service interface representable
   */
  public ServiceExporter(
      FreemarkerService freemarkerService,
      ServiceInterfaceTransformer serviceInterfaceTransformer) {
    this.freemarkerService = freemarkerService;
    this.serviceInterfaceTransformer = serviceInterfaceTransformer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param service the service
   */
  public void export(Path generationPath, Service service) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", serviceInterfaceTransformer.create(service));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Interface.java.ftl",
        makeFileName(generationPath, service));
  }

  private Path makeFileName(Path generationPath, Service service) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        service.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(service.getServiceName().getText()) + ".java");
  }
}
