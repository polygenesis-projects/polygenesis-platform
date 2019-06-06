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

package io.polygenesis.generators.java.exporters.rdbms.projection.testing;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.transformers.rdbms.projection.testing.ProjectionRepositoryImplTestLegacyClassTransformer;
import io.polygenesis.models.domain.Persistence;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Projection repository impl test exporter.
 *
 * @author Christos Tsakostas
 */
public class ProjectionRepositoryImplTestExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ProjectionRepositoryImplTestLegacyClassTransformer
      projectionRepositoryImplTestClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection repository impl test exporter.
   *
   * @param freemarkerService the freemarker service
   * @param persistenceImplClassRepresentable the persistence impl class representable
   */
  public ProjectionRepositoryImplTestExporter(
      FreemarkerService freemarkerService,
      ProjectionRepositoryImplTestLegacyClassTransformer persistenceImplClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.projectionRepositoryImplTestClassRepresentable = persistenceImplClassRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param persistence the persistence
   * @param rootPackageName the root package name
   * @param contextName the context name
   */
  public void export(
      Path generationPath,
      Persistence persistence,
      PackageName rootPackageName,
      ObjectName contextName) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put(
        "representation",
        projectionRepositoryImplTestClassRepresentable.create(
            persistence, rootPackageName, contextName));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, persistence));
  }

  private Path makeFileName(Path generationPath, Persistence persistence) {

    return Paths.get(
        generationPath.toString(),
        "src/test/java",
        persistence.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(persistence.getObjectName().getText()) + "ImplTest.java");
  }
}
