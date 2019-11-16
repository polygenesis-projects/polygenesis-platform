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

package io.polygenesis.generators.java.repository.inmemory;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.repository.inmemory.initialization.SupportiveEntityInitializationGenerator;
import io.polygenesis.generators.java.repository.inmemory.initialization.SupportiveEntityInitializationMethodTransformer;
import io.polygenesis.generators.java.repository.inmemory.initialization.SupportiveEntityInitializationTransformer;
import io.polygenesis.generators.java.repository.inmemory.initialization.activity.SupportiveEntityInitializationActivityRegistry;
import io.polygenesis.generators.java.repository.inmemory.supportiveentity.SupportiveEntityRepositoryImplGenerator;
import io.polygenesis.generators.java.repository.inmemory.supportiveentity.SupportiveEntityRepositoryImplMethodTransformer;
import io.polygenesis.generators.java.repository.inmemory.supportiveentity.SupportiveEntityRepositoryImplTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type In memory metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public final class InMemoryMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private static SupportiveEntityRepositoryImplGenerator supportiveEntityRepositoryImplGenerator;
  private static SupportiveEntityInitializationGenerator supportiveEntityInitializationGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter activeFileExporter = new ActiveFileExporter();
    final DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    supportiveEntityRepositoryImplGenerator =
        new SupportiveEntityRepositoryImplGenerator(
            new SupportiveEntityRepositoryImplTransformer(
                dataTypeTransformer,
                new SupportiveEntityRepositoryImplMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    supportiveEntityInitializationGenerator =
        new SupportiveEntityInitializationGenerator(
            new SupportiveEntityInitializationTransformer(
                dataTypeTransformer,
                new SupportiveEntityInitializationMethodTransformer(
                    dataTypeTransformer, new SupportiveEntityInitializationActivityRegistry())),
            templateEngine,
            activeFileExporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private InMemoryMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance in memory metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the in memory metamodel generator
   */
  public static InMemoryMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ObjectName contextName) {
    return new InMemoryMetamodelGenerator(
        generationPath,
        rootPackageName,
        contextName,
        supportiveEntityRepositoryImplGenerator,
        supportiveEntityInitializationGenerator);
  }
}
