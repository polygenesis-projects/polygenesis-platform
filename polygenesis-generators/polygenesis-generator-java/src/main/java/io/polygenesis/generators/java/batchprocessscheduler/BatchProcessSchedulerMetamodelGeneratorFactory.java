/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.java.batchprocessscheduler;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.PassiveFileExporter;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.batchprocessscheduler.scheduler.BatchProcessMethodSchedulerTransformer;
import io.polygenesis.generators.java.batchprocessscheduler.scheduler.BatchProcessSchedulerGenerator;
import io.polygenesis.generators.java.batchprocessscheduler.scheduler.BatchProcessSchedulerTransformer;
import io.polygenesis.generators.java.batchprocessscheduler.scheduler.activity.ConfigureSchedulerRouteActivityGenerator;
import io.polygenesis.generators.java.batchprocessscheduler.scheduler.activity.ConfigureSchedulerRouteActivityTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

public final class BatchProcessSchedulerMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static BatchProcessSchedulerGenerator batchProcessSchedulerGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new PassiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    batchProcessSchedulerGenerator =
        new BatchProcessSchedulerGenerator(
            new BatchProcessSchedulerTransformer(
                dataTypeTransformer,
                new BatchProcessMethodSchedulerTransformer(
                    dataTypeTransformer,
                    new ConfigureSchedulerRouteActivityGenerator(
                        new ConfigureSchedulerRouteActivityTransformer(), templateEngine))),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private BatchProcessSchedulerMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance periodic process metamodel generator.
   *
   * @param generationPath  the generation path
   * @param rootPackageName the root package name
   * @param contextName     the context name
   * @return the periodic process metamodel generator
   */
  public static BatchProcessSchedulerMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new BatchProcessSchedulerMetamodelGenerator(
        generationPath, rootPackageName, contextName, batchProcessSchedulerGenerator);
  }
}
