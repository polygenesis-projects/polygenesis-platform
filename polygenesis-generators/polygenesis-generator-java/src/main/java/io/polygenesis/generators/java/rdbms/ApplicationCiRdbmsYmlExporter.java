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

package io.polygenesis.generators.java.rdbms;

import io.polygenesis.commons.freemarker.FreemarkerService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Application ci rdbms yml exporter.
 *
 * @author Christos Tsakostas
 */
public class ApplicationCiRdbmsYmlExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Application ci rdbms yml exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public ApplicationCiRdbmsYmlExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   */
  public void export(Path generationPath) {
    Map<String, Object> dataModel = new HashMap<>();

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-persistence-rdbms/application-ci-rdbms.yml.ftl",
        makeFileName(generationPath));
  }

  private Path makeFileName(Path generationPath) {

    return Paths.get(generationPath.toString(), "src/test/resources", "application-ci-rdbms.yml");
  }
}
