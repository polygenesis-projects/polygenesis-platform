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

package io.polygenesis.generators.java.domain;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.PackageName;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Constants exporter.
 *
 * @author Christos Tsakostas
 */
public class ConstantsExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Constants exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public ConstantsExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param tablePrefix the table prefix
   */
  public void export(Path generationPath, PackageName rootPackageName, String tablePrefix) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("groupId", rootPackageName.getText());
    dataModel.put("tablePrefix", tablePrefix);

    freemarkerService.exportIfNotExists(
        dataModel,
        "polygenesis-generator-java-domain/Constants.java.ftl",
        Paths.get(
            generationPath.toString(),
            "src/main/java",
            rootPackageName.toPath().toString(),
            "Constants.java"));
  }
}
