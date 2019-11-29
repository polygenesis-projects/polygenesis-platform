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

package io.polygenesis.generators.java.domain.aggregaterootid;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.InstantiationType;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Aggregate root exporter.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootIdExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final AggregateRootIdLegacyClassTransformer aggregateRootIdClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root projection exporter.
   *
   * @param freemarkerService the freemarker service
   * @param aggregateRootIdClassRepresentable the aggregate root ID projection converter
   */
  public AggregateRootIdExporter(
      FreemarkerService freemarkerService,
      AggregateRootIdLegacyClassTransformer aggregateRootIdClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.aggregateRootIdClassRepresentable = aggregateRootIdClassRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param aggregateRoot the aggregateRoot
   */
  public void export(Path generationPath, AggregateRoot aggregateRoot) {
    Map<String, Object> dataModel = new HashMap<>();
    if (aggregateRoot.getInstantiationType().equals(InstantiationType.ABSTRACT)) {
      return;
    }

    dataModel.put("representation", aggregateRootIdClassRepresentable.create(aggregateRoot));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, aggregateRoot));
  }

  private Path makeFileName(Path generationPath, AggregateRoot aggregateRoot) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        aggregateRoot.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(aggregateRoot.getObjectName().getText()) + "Id.java");
  }
}
