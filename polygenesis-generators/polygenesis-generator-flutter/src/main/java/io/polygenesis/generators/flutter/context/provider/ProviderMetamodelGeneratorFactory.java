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

package io.polygenesis.generators.flutter.context.provider;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.flutter.context.provider.collection.ProviderCollectionActivityRegistry;
import io.polygenesis.generators.flutter.context.provider.collection.ProviderCollectionGenerator;
import io.polygenesis.generators.flutter.context.provider.collection.ProviderCollectionMethodTransformer;
import io.polygenesis.generators.flutter.context.provider.collection.ProviderCollectionTransformer;
import io.polygenesis.generators.flutter.context.provider.detail.ProviderDetailActivityRegistry;
import io.polygenesis.generators.flutter.context.provider.detail.ProviderDetailGenerator;
import io.polygenesis.generators.flutter.context.provider.detail.ProviderDetailMethodTransformer;
import io.polygenesis.generators.flutter.context.provider.detail.ProviderDetailTransformer;
import io.polygenesis.transformers.dart.DartDataTypeTransformer;
import java.nio.file.Path;

public class ProviderMetamodelGeneratorFactory {
  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private static ProviderCollectionGenerator providerCollectionGenerator;
  private static ProviderDetailGenerator providerDetailGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new DartDataTypeTransformer();

    providerCollectionGenerator =
        new ProviderCollectionGenerator(
            new ProviderCollectionTransformer(
                dataTypeTransformer,
                new ProviderCollectionMethodTransformer(
                    dataTypeTransformer, new ProviderCollectionActivityRegistry())),
            templateEngine,
            exporter);

    providerDetailGenerator =
        new ProviderDetailGenerator(
            new ProviderDetailTransformer(
                dataTypeTransformer,
                new ProviderDetailMethodTransformer(
                    dataTypeTransformer, new ProviderDetailActivityRegistry())),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ProviderMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance provider metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the provider metamodel generator
   */
  public static ProviderMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new ProviderMetamodelGenerator(
        generationPath,
        rootPackageName,
        contextName,
        providerCollectionGenerator,
        providerDetailGenerator);
  }
}
