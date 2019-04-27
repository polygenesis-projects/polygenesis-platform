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

package io.polygenesis.generators.java.exporters.rdbms;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.PackageName;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Rdbms test exporter.
 *
 * @author Christos Tsakostas
 */
public class RdbmsTestExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Rdbms test exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public RdbmsTestExporter(FreemarkerService freemarkerService) {
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
   */
  public void export(Path generationPath, PackageName rootPackageName) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("packageName", rootPackageName.getText().toLowerCase());

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-persistence-rdbms/RdbmsTest.java.ftl",
        makeFileName(generationPath, rootPackageName));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Path makeFileName(Path generationPath, PackageName rootPackageName) {
    return Paths.get(
        generationPath.toString(),
        "src/test/java",
        rootPackageName.toPath().toString(),
        "RdbmsTest.java");
  }
}
