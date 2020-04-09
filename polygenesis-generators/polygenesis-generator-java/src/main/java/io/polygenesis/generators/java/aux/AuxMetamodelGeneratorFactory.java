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

package io.polygenesis.generators.java.aux;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.aux.propertyfile.PropertyFileGenerator;
import io.polygenesis.generators.java.aux.propertyfile.PropertyFileMethodTransformer;
import io.polygenesis.generators.java.aux.propertyfile.PropertyFileTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

public final class AuxMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static PropertyFileGenerator propertyFileGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter activeFileExporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    propertyFileGenerator =
        new PropertyFileGenerator(
            new PropertyFileTransformer(
                dataTypeTransformer, new PropertyFileMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private AuxMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance aux metamodel generator.
   *
   * @param generationPath the generation path
   * @param contextName the context name
   * @param rootPackageName the root package name
   * @return the aux metamodel generator
   */
  public static AuxMetamodelGenerator newInstance(
      Path generationPath, ContextName contextName, PackageName rootPackageName) {
    return new AuxMetamodelGenerator(
        generationPath, contextName, rootPackageName, propertyFileGenerator);
  }
}
