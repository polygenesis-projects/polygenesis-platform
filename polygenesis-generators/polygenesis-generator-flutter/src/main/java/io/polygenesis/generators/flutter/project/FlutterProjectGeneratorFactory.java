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

package io.polygenesis.generators.flutter.project;

import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.flutter.project.app.FlutterAppActivityRegistry;
import io.polygenesis.generators.flutter.project.app.FlutterAppGenerator;
import io.polygenesis.generators.flutter.project.app.FlutterAppMethodTransformer;
import io.polygenesis.generators.flutter.project.app.FlutterAppTransformer;
import io.polygenesis.transformers.dart.DartDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type Flutter project generator factory.
 *
 * @author Christos Tsakostas
 */
public class FlutterProjectGeneratorFactory {

  private static FlutterAppGenerator flutterAppGenerator;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new DartDataTypeTransformer();
    FlutterProjectDataExtractor flutterProjectDataExtractor = new FlutterProjectDataExtractor();

    flutterAppGenerator =
        new FlutterAppGenerator(
            new FlutterAppTransformer(
                dataTypeTransformer,
                new FlutterAppMethodTransformer(
                    dataTypeTransformer, new FlutterAppActivityRegistry()),
                flutterProjectDataExtractor),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance flutter project generator.
   *
   * @param generationPath the generation path
   * @return the flutter project generator
   */
  public static FlutterProjectGenerator newInstance(Path generationPath) {
    return new FlutterProjectGenerator(generationPath, flutterAppGenerator);
  }
}
