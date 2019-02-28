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
  private final SpringDataRepositoryInterfaceRepresentable
      springDataRepositoryInterfaceRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public SpringDataRepositoryExporter(
      FreemarkerService freemarkerService,
      SpringDataRepositoryInterfaceRepresentable springDataRepositoryInterfaceRepresentable) {
    this.freemarkerService = freemarkerService;
    this.springDataRepositoryInterfaceRepresentable = springDataRepositoryInterfaceRepresentable;
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
    dataModel.put("representation", springDataRepositoryInterfaceRepresentable.create(persistence));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Interface.java.ftl",
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
