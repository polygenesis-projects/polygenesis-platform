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

package io.polygenesis.generators.java.rest.constants;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.PackageName;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Rest constants projection exporter.
 *
 * @author Christos Tsakostas
 */
public class RestConstantsProjectionExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Rest constants projection exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public RestConstantsProjectionExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param restConstantsProjection the rest constants projection
   */
  public void export(Path generationPath, RestConstantsProjection restConstantsProjection) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("projection", restConstantsProjection);

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-rest/RestConstants.java.ftl",
        makeFileName(generationPath, restConstantsProjection));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Path makeFileName(Path generationPath, RestConstantsProjection restConstantsProjection) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        new PackageName(restConstantsProjection.getPackageName()).toPath().toString(),
        "RestConstants.java");
  }
}
