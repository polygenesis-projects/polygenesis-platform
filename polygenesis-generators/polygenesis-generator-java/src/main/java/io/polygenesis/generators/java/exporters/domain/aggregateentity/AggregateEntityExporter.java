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
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.transformers.domain.aggregateentity.AggregateEntityLegacyClassTransformer;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.AggregateRoot;
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
  private final AggregateEntityLegacyClassTransformer aggregateEntityClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity exporter.
   *
   * @param freemarkerService the freemarker service
   * @param aggregateEntityClassRepresentable the aggregate entity class representable
   */
  public AggregateEntityExporter(
      FreemarkerService freemarkerService,
      AggregateEntityLegacyClassTransformer aggregateEntityClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.aggregateEntityClassRepresentable = aggregateEntityClassRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param aggregateEntity the aggregate entity
   * @param rootPackageName the root package name
   * @param aggregateRoot the aggregate root
   */
  public void export(
      Path generationPath,
      AggregateEntity aggregateEntity,
      PackageName rootPackageName,
      AggregateRoot aggregateRoot) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put(
        "representation",
        aggregateEntityClassRepresentable.create(aggregateEntity, rootPackageName, aggregateRoot));

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
        TextConverter.toUpperCamel(aggregateEntity.getObjectName().getText()) + ".java");
  }
}
