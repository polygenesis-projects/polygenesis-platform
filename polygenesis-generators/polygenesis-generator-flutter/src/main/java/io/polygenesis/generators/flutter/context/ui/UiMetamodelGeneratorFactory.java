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

package io.polygenesis.generators.flutter.context.ui;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.flutter.context.ui.screen.ScreenActivityRegistry;
import io.polygenesis.generators.flutter.context.ui.screen.ScreenGenerator;
import io.polygenesis.generators.flutter.context.ui.screen.ScreenMethodTransformer;
import io.polygenesis.generators.flutter.context.ui.screen.ScreenTransformer;
import io.polygenesis.generators.flutter.context.ui.widget.WidgetActivityRegistry;
import io.polygenesis.generators.flutter.context.ui.widget.WidgetGenerator;
import io.polygenesis.generators.flutter.context.ui.widget.WidgetMethodTransformer;
import io.polygenesis.generators.flutter.context.ui.widget.WidgetTransformer;
import io.polygenesis.transformers.dart.DartDataTypeTransformer;
import java.nio.file.Path;

public class UiMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private static ScreenGenerator screenGenerator;
  private static WidgetGenerator widgetGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new DartDataTypeTransformer();

    screenGenerator =
        new ScreenGenerator(
            new ScreenTransformer(
                dataTypeTransformer,
                new ScreenMethodTransformer(dataTypeTransformer, new ScreenActivityRegistry())),
            templateEngine,
            exporter);

    widgetGenerator =
        new WidgetGenerator(
            new WidgetTransformer(
                dataTypeTransformer,
                new WidgetMethodTransformer(dataTypeTransformer, new WidgetActivityRegistry())),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private UiMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance ui metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the ui metamodel generator
   */
  public static UiMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new UiMetamodelGenerator(
        generationPath, rootPackageName, contextName, screenGenerator, widgetGenerator);
  }
}
