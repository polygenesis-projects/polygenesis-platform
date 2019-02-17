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

package io.polygenesis.generators.java.domain.aggregateentity;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.AggregateEntity;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Aggregate entity exporter.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public AggregateEntityExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param aggregateEntity the aggregate entity
   */
  public void export(Path generationPath, AggregateEntity aggregateEntity) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("aggregateEntity", aggregateEntity);

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-domain/AggregateEntity.java.ftl",
        makeFileName(generationPath, aggregateEntity));
  }

  private Path makeFileName(Path generationPath, AggregateEntity aggregateEntity) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        aggregateEntity.getClassDataType().getOptionalPackageName().get().toPath().toString(),
        TextConverter.toUpperCamel(aggregateEntity.getVariableName().getText()) + ".java");
  }
}
