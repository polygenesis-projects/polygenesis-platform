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

package io.polygenesis.generators.angular.project;

import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.angular.project.appmodule.AppModuleGenerator;
import io.polygenesis.generators.angular.project.appmodule.AppModuleTransformer;
import io.polygenesis.generators.angular.project.approutingmodule.AppRoutingModuleGenerator;
import io.polygenesis.generators.angular.project.approutingmodule.AppRoutingModuleTransformer;
import io.polygenesis.transformers.typescript.TypescriptDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type Angular project generator factory.
 *
 * @author Christos Tsakostas
 */
public class AngularProjectGeneratorFactory {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static AppModuleGenerator appModuleGenerator;
  private static AppRoutingModuleGenerator appRoutingModuleGenerator;

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new TypescriptDataTypeTransformer();

    appModuleGenerator =
        new AppModuleGenerator(
            new AppModuleTransformer(dataTypeTransformer), templateEngine, exporter);

    appRoutingModuleGenerator =
        new AppRoutingModuleGenerator(
            new AppRoutingModuleTransformer(dataTypeTransformer), templateEngine, exporter);
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance angular project generator.
   *
   * @param generationPath the generation path
   * @return the angular project generator
   */
  public static AngularProjectGenerator newInstance(Path generationPath) {
    return new AngularProjectGenerator(
        generationPath, appModuleGenerator, appRoutingModuleGenerator);
  }
}
