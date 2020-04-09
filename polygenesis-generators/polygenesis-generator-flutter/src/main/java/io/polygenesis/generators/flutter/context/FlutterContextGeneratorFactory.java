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

package io.polygenesis.generators.flutter.context;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.MetamodelGenerator;
import io.polygenesis.generators.flutter.DartFolderFileConstants;
import io.polygenesis.generators.flutter.context.model.ModelMetamodelGenerator;
import io.polygenesis.generators.flutter.context.model.ModelMetamodelGeneratorFactory;
import io.polygenesis.generators.flutter.context.provider.ProviderMetamodelGenerator;
import io.polygenesis.generators.flutter.context.provider.ProviderMetamodelGeneratorFactory;
import io.polygenesis.generators.flutter.context.ui.UiMetamodelGenerator;
import io.polygenesis.generators.flutter.context.ui.UiMetamodelGeneratorFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

public class FlutterContextGeneratorFactory {

  // ===============================================================================================
  // STATIC FINAL
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Flutter context generator factory. */
  private FlutterContextGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance flutter context generator.
   *
   * @param generationPath the generation path
   * @param contextFolder the context folder
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the flutter context generator
   */
  public static FlutterContextGenerator newInstance(
      Path generationPath,
      String contextFolder,
      PackageName rootPackageName,
      ContextName contextName) {
    Set<MetamodelGenerator> metamodelGenerators = new LinkedHashSet<>();

    metamodelGenerators.add(
        modelMetamodelGenerator(
            generationPath.toString(), contextFolder, rootPackageName, contextName));

    metamodelGenerators.add(
        providerMetamodelGenerator(
            generationPath.toString(), contextFolder, rootPackageName, contextName));

    metamodelGenerators.add(
        uiMetamodelGenerator(
            generationPath.toString(), contextFolder, rootPackageName, contextName));

    return new FlutterContextGenerator(generationPath, metamodelGenerators);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static ModelMetamodelGenerator modelMetamodelGenerator(
      String exportPath,
      String contextFolder,
      PackageName rootPackageName,
      ContextName contextName) {
    return ModelMetamodelGeneratorFactory.newInstance(
        Paths.get(exportPath, DartFolderFileConstants.LIB, contextFolder),
        rootPackageName,
        contextName);
  }

  private static ProviderMetamodelGenerator providerMetamodelGenerator(
      String exportPath,
      String contextFolder,
      PackageName rootPackageName,
      ContextName contextName) {
    return ProviderMetamodelGeneratorFactory.newInstance(
        Paths.get(exportPath, DartFolderFileConstants.LIB, contextFolder),
        rootPackageName,
        contextName);
  }

  private static UiMetamodelGenerator uiMetamodelGenerator(
      String exportPath,
      String contextFolder,
      PackageName rootPackageName,
      ContextName contextName) {
    return UiMetamodelGeneratorFactory.newInstance(
        Paths.get(exportPath, DartFolderFileConstants.LIB, contextFolder),
        rootPackageName,
        contextName);
  }
}
