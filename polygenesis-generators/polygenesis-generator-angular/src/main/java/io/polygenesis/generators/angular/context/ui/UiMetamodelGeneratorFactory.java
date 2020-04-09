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

package io.polygenesis.generators.angular.context.ui;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.angular.context.ui.screen.ScreenGenerator;
import io.polygenesis.generators.angular.context.ui.screen.html.ScreenHtmlGenerator;
import io.polygenesis.generators.angular.context.ui.screen.html.ScreenHtmlTransformer;
import io.polygenesis.generators.angular.context.ui.screen.scss.ScreenScssGenerator;
import io.polygenesis.generators.angular.context.ui.screen.scss.ScreenScssTransformer;
import io.polygenesis.generators.angular.context.ui.screen.spec.ScreenSpecTypescriptActivityRegistry;
import io.polygenesis.generators.angular.context.ui.screen.spec.ScreenSpecTypescriptGenerator;
import io.polygenesis.generators.angular.context.ui.screen.spec.ScreenSpecTypescriptMethodTransformer;
import io.polygenesis.generators.angular.context.ui.screen.spec.ScreenSpecTypescriptTransformer;
import io.polygenesis.generators.angular.context.ui.screen.ts.ScreenTypescriptActivityRegistry;
import io.polygenesis.generators.angular.context.ui.screen.ts.ScreenTypescriptGenerator;
import io.polygenesis.generators.angular.context.ui.screen.ts.ScreenTypescriptMethodTransformer;
import io.polygenesis.generators.angular.context.ui.screen.ts.ScreenTypescriptTransformer;
import io.polygenesis.generators.angular.context.ui.widget.WidgetActivityRegistry;
import io.polygenesis.generators.angular.context.ui.widget.WidgetGenerator;
import io.polygenesis.generators.angular.context.ui.widget.WidgetMethodTransformer;
import io.polygenesis.generators.angular.context.ui.widget.WidgetTransformer;
import io.polygenesis.transformers.typescript.TypescriptDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type Ui metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
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
    DataTypeTransformer dataTypeTransformer = new TypescriptDataTypeTransformer();

    ScreenHtmlGenerator screenHtmlGenerator =
        new ScreenHtmlGenerator(
            new ScreenHtmlTransformer(dataTypeTransformer), templateEngine, exporter);

    ScreenScssGenerator screenScssGenerator =
        new ScreenScssGenerator(
            new ScreenScssTransformer(dataTypeTransformer), templateEngine, exporter);

    ScreenTypescriptGenerator screenTypescriptGenerator =
        new ScreenTypescriptGenerator(
            new ScreenTypescriptTransformer(
                dataTypeTransformer,
                new ScreenTypescriptMethodTransformer(
                    dataTypeTransformer, new ScreenTypescriptActivityRegistry())),
            templateEngine,
            exporter);

    ScreenSpecTypescriptGenerator screenSpecTypescriptGenerator =
        new ScreenSpecTypescriptGenerator(
            new ScreenSpecTypescriptTransformer(
                dataTypeTransformer,
                new ScreenSpecTypescriptMethodTransformer(
                    dataTypeTransformer, new ScreenSpecTypescriptActivityRegistry())),
            templateEngine,
            exporter);

    screenGenerator =
        new ScreenGenerator(
            screenHtmlGenerator,
            screenScssGenerator,
            screenTypescriptGenerator,
            screenSpecTypescriptGenerator);

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
