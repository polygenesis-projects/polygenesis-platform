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

package io.polygenesis.generators.sql;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.models.sql.SqlModelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Script exporter.
 *
 * @author Christos Tsakostas
 */
public class ScriptExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ScriptRepresentable scriptRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Script exporter.
   *
   * @param freemarkerService the freemarker service
   * @param scriptRepresentable the script representable
   */
  public ScriptExporter(
      FreemarkerService freemarkerService, ScriptRepresentable scriptRepresentable) {
    this.freemarkerService = freemarkerService;
    this.scriptRepresentable = scriptRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param sqlModelRepository the sql model repository
   * @param tablePrefix the table prefix
   */
  public void export(
      Path generationPath, SqlModelRepository sqlModelRepository, String tablePrefix) {
    Map<String, Object> dataModel = new HashMap<>();
    // TODO
    dataModel.put("representation", scriptRepresentable.create(sqlModelRepository));
    dataModel.put("representation", sqlModelRepository);
    dataModel.put("tablePrefix", tablePrefix);

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-sql-flyway/script.sql.ftl",
        makeFileName(generationPath));
  }

  private Path makeFileName(Path generationPath) {

    String name = "V2019.01.01.00.00.01__Initialize";

    return Paths.get(generationPath.toString(), "src/main/resources/db/migration", name + ".sql");
  }
}
