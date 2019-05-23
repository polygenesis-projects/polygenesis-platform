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

package io.polygenesis.generators.java.exporters.domain.projection;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.Projection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Aggregate root exporter.
 *
 * @author Christos Tsakostas
 */
public class ProjectionIdExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ProjectionIdClassRepresentable projectionIdClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root projection exporter.
   *
   * @param freemarkerService the freemarker service
   * @param projectionIdClassRepresentable the aggregate root ID projection converter
   */
  public ProjectionIdExporter(
      FreemarkerService freemarkerService,
      ProjectionIdClassRepresentable projectionIdClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.projectionIdClassRepresentable = projectionIdClassRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param projection the projection
   */
  public void export(Path generationPath, Projection projection) {
    Map<String, Object> dataModel = new HashMap<>();
    if (projection.getInstantiationType().equals(InstantiationType.ABSTRACT)) {
      return;
    }

    dataModel.put("representation", projectionIdClassRepresentable.create(projection));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, projection));
  }

  private Path makeFileName(Path generationPath, Projection projection) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        projection.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(projection.getObjectName().getText()) + "Id.java");
  }
}
