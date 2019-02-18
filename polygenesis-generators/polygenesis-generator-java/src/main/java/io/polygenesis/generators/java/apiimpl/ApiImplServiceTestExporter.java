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

package io.polygenesis.generators.java.apiimpl;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Api impl service test exporter.
 *
 * @author Christos Tsakostas
 */
public class ApiImplServiceTestExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ApiImplServiceTestProjectionConverter apiImplServiceTestProjectionConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl service test exporter.
   *
   * @param freemarkerService the freemarker service
   * @param apiImplServiceTestProjectionConverter the api impl service test projection converter
   */
  public ApiImplServiceTestExporter(
      FreemarkerService freemarkerService,
      ApiImplServiceTestProjectionConverter apiImplServiceTestProjectionConverter) {
    this.freemarkerService = freemarkerService;
    this.apiImplServiceTestProjectionConverter = apiImplServiceTestProjectionConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param serviceImplementation the service implementation
   */
  public void export(Path generationPath, ServiceImplementation serviceImplementation) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put(
        "projection", apiImplServiceTestProjectionConverter.convert(serviceImplementation));
    dataModel.put(
        "serviceInterface", serviceImplementation.getService().getServiceName().getText());
    dataModel.put(
        "serviceInterfaceImpl",
        serviceImplementation.getService().getServiceName().getText() + "Impl");

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-api-impl/ApiImplServiceTest.java.ftl",
        makeFileName(generationPath, serviceImplementation.getService()));
  }

  private Path makeFileName(Path generationPath, Service service) {

    return Paths.get(
        generationPath.toString(),
        "src/test/java",
        service.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(service.getServiceName().getText()) + "ImplTest.java");
  }
}
