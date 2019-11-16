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

package io.polygenesis.generators.flutter.context.model;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.flutter.context.model.dto.ModelActivityRegistry;
import io.polygenesis.generators.flutter.context.model.dto.ModelGenerator;
import io.polygenesis.generators.flutter.context.model.dto.ModelMethodTransformer;
import io.polygenesis.generators.flutter.context.model.dto.ModelTransformer;
import io.polygenesis.transformers.dart.DartDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type Model metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public class ModelMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private static ModelGenerator modelGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new DartDataTypeTransformer();

    modelGenerator =
        new ModelGenerator(
            new ModelTransformer(
                dataTypeTransformer,
                new ModelMethodTransformer(dataTypeTransformer, new ModelActivityRegistry())),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ModelMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance java api metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the java api metamodel generator
   */
  public static ModelMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new ModelMetamodelGenerator(
        generationPath, rootPackageName, contextName, modelGenerator);
  }
}
