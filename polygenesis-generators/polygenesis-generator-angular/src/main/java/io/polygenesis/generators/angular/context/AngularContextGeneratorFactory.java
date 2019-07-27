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

package io.polygenesis.generators.angular.context;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.MetamodelGenerator;
import io.polygenesis.generators.angular.AmgularFolderFileConstants;
import io.polygenesis.generators.angular.context.model.ModelMetamodelGenerator;
import io.polygenesis.generators.angular.context.model.ModelMetamodelGeneratorFactory;
import io.polygenesis.generators.angular.context.ui.UiMetamodelGenerator;
import io.polygenesis.generators.angular.context.ui.UiMetamodelGeneratorFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Angular context generator factory.
 *
 * @author Christos Tsakostas
 */
public class AngularContextGeneratorFactory {

  // ===============================================================================================
  // STATIC FINAL
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private AngularContextGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance angular context generator.
   *
   * @param generationPath the generation path
   * @param contextFolder the context folder
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the angular context generator
   */
  public static AngularContextGenerator newInstance(
      Path generationPath,
      String contextFolder,
      PackageName rootPackageName,
      ContextName contextName) {
    Set<MetamodelGenerator> metamodelGenerators = new LinkedHashSet<>();

    metamodelGenerators.add(
        modelMetamodelGenerator(
            generationPath.toString(), contextFolder, rootPackageName, contextName));

    metamodelGenerators.add(
        uiMetamodelGenerator(
            generationPath.toString(), contextFolder, rootPackageName, contextName));

    return new AngularContextGenerator(generationPath, metamodelGenerators);
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
        Paths.get(
            exportPath,
            AmgularFolderFileConstants.SRC,
            AmgularFolderFileConstants.APP,
            contextFolder),
        rootPackageName,
        contextName);
  }

  private static UiMetamodelGenerator uiMetamodelGenerator(
      String exportPath,
      String contextFolder,
      PackageName rootPackageName,
      ContextName contextName) {
    return UiMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            AmgularFolderFileConstants.SRC,
            AmgularFolderFileConstants.APP,
            contextFolder),
        rootPackageName,
        contextName);
  }
}
