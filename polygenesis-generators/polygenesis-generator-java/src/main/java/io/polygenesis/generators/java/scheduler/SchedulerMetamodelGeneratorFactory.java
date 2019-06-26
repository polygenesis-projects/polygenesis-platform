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

package io.polygenesis.generators.java.scheduler;

import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.JavaDataTypeTransformer;
import io.polygenesis.core.TemplateEngine;
import java.nio.file.Path;

/**
 * The type Scheduler metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public final class SchedulerMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static SchedulerGenerator schedulerGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    schedulerGenerator =
        new SchedulerGenerator(
            new SchedulerTransformer(
                dataTypeTransformer, new SchedulerMethodTransformer(dataTypeTransformer)),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private SchedulerMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance scheduler metamodel generator.
   *
   * @param generationPath the generation path
   * @return the scheduler metamodel generator
   */
  public static SchedulerMetamodelGenerator newInstance(Path generationPath) {
    return new SchedulerMetamodelGenerator(generationPath, schedulerGenerator);
  }
}
