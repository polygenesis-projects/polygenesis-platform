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

package io.polygenesis.generators.java.batchprocess;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.batchprocess.command.BatchProcessCommandGenerator;
import io.polygenesis.generators.java.batchprocess.command.BatchProcessCommandMethodTransformer;
import io.polygenesis.generators.java.batchprocess.command.BatchProcessCommandTransformer;
import io.polygenesis.generators.java.batchprocess.command.activity.ProcessCommandActivityGenerator;
import io.polygenesis.generators.java.batchprocess.command.activity.ProcessCommandActivityTransformer;
import io.polygenesis.generators.java.batchprocess.process.BatchProcessGenerator;
import io.polygenesis.generators.java.batchprocess.process.BatchProcessMethodTransformer;
import io.polygenesis.generators.java.batchprocess.process.BatchProcessTransformer;
import io.polygenesis.generators.java.batchprocess.process.activity.BatchProcessActivityRegistry;
import io.polygenesis.generators.java.batchprocess.query.BatchProcessQueryGenerator;
import io.polygenesis.generators.java.batchprocess.query.BatchProcessQueryMethodTransformer;
import io.polygenesis.generators.java.batchprocess.query.BatchProcessQueryTransformer;
import io.polygenesis.generators.java.batchprocess.query.activity.ProcessQueryActivityGenerator;
import io.polygenesis.generators.java.batchprocess.query.activity.ProcessQueryActivityTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type Periodic process metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public final class BatchProcessMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static BatchProcessGenerator batchProcessGenerator;
  private static BatchProcessCommandGenerator batchProcessCommandGenerator;
  private static BatchProcessQueryGenerator batchProcessQueryGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    batchProcessGenerator =
        new BatchProcessGenerator(
            new BatchProcessTransformer(
                dataTypeTransformer,
                new BatchProcessMethodTransformer(
                    dataTypeTransformer, new BatchProcessActivityRegistry())),
            templateEngine,
            exporter);

    batchProcessCommandGenerator =
        new BatchProcessCommandGenerator(
            new BatchProcessCommandTransformer(
                dataTypeTransformer,
                new BatchProcessCommandMethodTransformer(
                    dataTypeTransformer,
                    new ProcessCommandActivityGenerator(
                        new ProcessCommandActivityTransformer(), templateEngine))),
            templateEngine,
            exporter);

    batchProcessQueryGenerator =
        new BatchProcessQueryGenerator(
            new BatchProcessQueryTransformer(
                dataTypeTransformer,
                new BatchProcessQueryMethodTransformer(
                    dataTypeTransformer,
                    new ProcessQueryActivityGenerator(
                        new ProcessQueryActivityTransformer(), templateEngine))),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private BatchProcessMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance periodic process metamodel generator.
   *
   * @param generationPath the generation path
   * @return the periodic process metamodel generator
   */
  public static BatchProcessMetamodelGenerator newInstance(
      Path generationPath, ContextName contextName) {
    return new BatchProcessMetamodelGenerator(
        generationPath,
        contextName,
        batchProcessGenerator,
        batchProcessCommandGenerator,
        batchProcessQueryGenerator);
  }
}
