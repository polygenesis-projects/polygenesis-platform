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

package io.polygenesis.generators.java.rdbms;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.Persistence;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Persistence impl exporter.
 *
 * @author Christos Tsakostas
 */
public class PersistenceImplExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final PersistenceImplLegacyClassTransformer persistenceImplClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Persistence impl exporter.
   *
   * @param freemarkerService the freemarker service
   * @param persistenceImplClassRepresentable the persistence impl class representable
   */
  public PersistenceImplExporter(
      FreemarkerService freemarkerService,
      PersistenceImplLegacyClassTransformer persistenceImplClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.persistenceImplClassRepresentable = persistenceImplClassRepresentable;
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
        persistenceImplClassRepresentable.create(persistence, rootPackageName, contextName));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, persistence));
  }

  private Path makeFileName(Path generationPath, Persistence persistence) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        persistence.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(persistence.getObjectName().getText()) + "Impl.java");
  }
}
