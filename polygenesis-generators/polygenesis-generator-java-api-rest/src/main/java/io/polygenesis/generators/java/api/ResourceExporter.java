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

package io.polygenesis.generators.java.api;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.api.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Resource exporter.
 *
 * @author Christos Tsakostas
 */
public class ResourceExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public ResourceExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param resource the resource
   */
  public void export(Path generationPath, Resource resource) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("resource", resource);

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-api-rest/Resource.java.ftl",
        makeFileName(generationPath, resource));
  }

  private Path makeFileName(Path generationPath, Resource resource) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        resource.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(resource.getName().getText()) + "RestService.java");
  }
}
