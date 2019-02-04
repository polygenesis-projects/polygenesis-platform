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
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootProjectionConverter;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.domain.AggregateRoot;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Api impl service exporter.
 *
 * @author Christos Tsakostas
 */
public class ApiImplServiceExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ApiImplServiceProjectionConverter apiImplServiceProjectionConverter;
  private final AggregateRootProjectionConverter aggregateRootProjectionConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl service exporter.
   *
   * @param freemarkerService the freemarker service
   * @param apiImplServiceProjectionConverter the api impl service projection converter
   * @param aggregateRootProjectionConverter the aggregate root projection converter
   */
  public ApiImplServiceExporter(
      FreemarkerService freemarkerService,
      ApiImplServiceProjectionConverter apiImplServiceProjectionConverter,
      AggregateRootProjectionConverter aggregateRootProjectionConverter) {
    this.freemarkerService = freemarkerService;
    this.apiImplServiceProjectionConverter = apiImplServiceProjectionConverter;
    this.aggregateRootProjectionConverter = aggregateRootProjectionConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param service the service
   * @param aggregateRoot the aggregate root
   */
  public void export(
      Path generationPath,
      PackageName rootPackageName,
      Service service,
      AggregateRoot aggregateRoot) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("projection", apiImplServiceProjectionConverter.make(service));
    dataModel.put(
        "aggregateRootProjection",
        aggregateRootProjectionConverter.convert(aggregateRoot, rootPackageName));

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-api-impl/ApiImplService.java.ftl",
        makeFileName(generationPath, service));
  }

  private Path makeFileName(Path generationPath, Service service) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        service.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(service.getServiceName().getText()) + "Impl.java");
  }
}