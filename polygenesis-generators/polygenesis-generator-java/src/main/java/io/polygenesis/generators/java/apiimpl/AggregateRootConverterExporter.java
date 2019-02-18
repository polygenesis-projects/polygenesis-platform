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
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Aggregate root converter exporter.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootConverterExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final AggregateRootConverterProjectionConverter aggregateRootConverterProjectionConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter exporter.
   *
   * @param freemarkerService the freemarker service
   * @param aggregateRootConverterProjectionConverter the aggregate root converter converter
   */
  public AggregateRootConverterExporter(
      FreemarkerService freemarkerService,
      AggregateRootConverterProjectionConverter aggregateRootConverterProjectionConverter) {
    this.freemarkerService = freemarkerService;
    this.aggregateRootConverterProjectionConverter = aggregateRootConverterProjectionConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param aggregateRootConverter the aggregate root converter
   */
  public void export(Path generationPath, AggregateRootConverter aggregateRootConverter) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put(
        "projection", aggregateRootConverterProjectionConverter.convert(aggregateRootConverter));

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-api-impl/AggregateRootConverter.java.ftl",
        makeFileName(generationPath, aggregateRootConverter));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Path makeFileName(Path generationPath, AggregateRootConverter aggregateRootConverter) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        aggregateRootConverter.getDataType().getOptionalPackageName().get().toPath().toString(),
        TextConverter.toUpperCamel(aggregateRootConverter.getVariableName().getText()) + ".java");
  }
}
