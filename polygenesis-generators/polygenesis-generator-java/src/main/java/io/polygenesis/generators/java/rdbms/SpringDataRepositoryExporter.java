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

package io.polygenesis.generators.java.rdbms;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.Persistence;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Spring data repository exporter.
 *
 * @author Christos Tsakostas
 */
public class SpringDataRepositoryExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final SpringDataRepositoryProjectionConverter springDataRepositoryProjectionConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Spring data repository exporter.
   *
   * @param freemarkerService the freemarker service
   * @param springDataRepositoryProjectionConverter the spring data repository projection converter
   */
  public SpringDataRepositoryExporter(
      FreemarkerService freemarkerService,
      SpringDataRepositoryProjectionConverter springDataRepositoryProjectionConverter) {
    this.freemarkerService = freemarkerService;
    this.springDataRepositoryProjectionConverter = springDataRepositoryProjectionConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param persistence the persistence
   */
  public void export(Path generationPath, Persistence persistence) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("projection", springDataRepositoryProjectionConverter.convert(persistence));
    dataModel.put("aggregateRoot", persistence.getAggregateRootName().getText());

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-persistence-rdbms/SpringDataRepository.java.ftl",
        makeFileName(generationPath, persistence));
  }

  private Path makeFileName(Path generationPath, Persistence persistence) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        persistence.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(persistence.getAggregateRootName().getText())
            + "Repository.java");
  }
}
