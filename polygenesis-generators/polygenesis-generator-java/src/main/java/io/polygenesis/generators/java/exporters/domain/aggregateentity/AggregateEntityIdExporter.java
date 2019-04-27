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

package io.polygenesis.generators.java.exporters.domain.aggregateentity;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.InstantiationType;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Aggregate entity id exporter.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityIdExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final AggregateEntityIdClassRepresentable aggregateEntityIdClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity id exporter.
   *
   * @param freemarkerService the freemarker service
   * @param aggregateEntityIdClassRepresentable the aggregate entity id class representable
   */
  public AggregateEntityIdExporter(
      FreemarkerService freemarkerService,
      AggregateEntityIdClassRepresentable aggregateEntityIdClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.aggregateEntityIdClassRepresentable = aggregateEntityIdClassRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param aggregateEntity the aggregateEntity
   */
  public void export(Path generationPath, AggregateEntity aggregateEntity) {
    Map<String, Object> dataModel = new HashMap<>();
    if (aggregateEntity.getInstantiationType().equals(InstantiationType.ABSTRACT)) {
      return;
    }

    dataModel.put("representation", aggregateEntityIdClassRepresentable.create(aggregateEntity));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, aggregateEntity));
  }

  private Path makeFileName(Path generationPath, AggregateEntity aggregateEntity) {
    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        aggregateEntity.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(aggregateEntity.getObjectName().getText()) + "Id.java");
  }
}
