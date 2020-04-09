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

package io.polygenesis.generators.java.auxdetails.propertyfile;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.auxdetails.propertyfile.propertyfile.PropertyFileImplGenerator;
import io.polygenesis.generators.java.auxdetails.propertyfile.propertyfile.PropertyFileImplMethodTransformer;
import io.polygenesis.generators.java.auxdetails.propertyfile.propertyfile.PropertyFileImplTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

public final class AuxDetailPropertyFileMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static PropertyFileImplGenerator propertyFileImplGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter activeFileExporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    propertyFileImplGenerator =
        new PropertyFileImplGenerator(
            new PropertyFileImplTransformer(
                dataTypeTransformer, new PropertyFileImplMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private AuxDetailPropertyFileMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance aux detail property file metamodel generator.
   *
   * @param generationPath the generation path
   * @param contextName the context name
   * @param rootPackageName the root package name
   * @return the aux detail property file metamodel generator
   */
  public static AuxDetailPropertyFileMetamodelGenerator newInstance(
      Path generationPath, ContextName contextName, PackageName rootPackageName) {
    return new AuxDetailPropertyFileMetamodelGenerator(
        generationPath, contextName, rootPackageName, propertyFileImplGenerator);
  }
}
