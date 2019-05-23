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
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.Projection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Projection exporter.
 *
 * @author Christos Tsakostas
 */
public class ProjectionExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ProjectionClassRepresentable projectionClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection exporter.
   *
   * @param freemarkerService the freemarker service
   * @param projectionClassRepresentable the projection class representable
   */
  public ProjectionExporter(
      FreemarkerService freemarkerService,
      ProjectionClassRepresentable projectionClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.projectionClassRepresentable = projectionClassRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param projection the projection
   * @param rootPackageName the root package name
   */
  public void export(Path generationPath, Projection projection, PackageName rootPackageName) {
    Map<String, Object> dataModel = new HashMap<>();

    dataModel.put(
        "representation", projectionClassRepresentable.create(projection, rootPackageName));

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
        TextConverter.toUpperCamel(projection.getObjectName().getText()) + ".java");
  }
}
