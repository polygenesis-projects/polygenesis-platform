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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.AbstractProjectGenerator;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.generators.angular.AmgularFolderFileConstants;
import io.polygenesis.generators.angular.project.appmodule.AppModuleGenerator;
import io.polygenesis.generators.angular.project.appmodule.source.AppModule;
import io.polygenesis.generators.angular.project.approutingmodule.AppRoutingModuleGenerator;
import io.polygenesis.generators.angular.project.approutingmodule.source.AppRoutingModule;
import io.polygenesis.metamodels.appangular.AngularProject;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Angular project generator.
 *
 * @author Christos Tsakostas
 */
public class AngularProjectGenerator extends AbstractProjectGenerator<AngularProject> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final AppModuleGenerator appModuleGenerator;
  private final AppRoutingModuleGenerator appRoutingModuleGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Angular project generator.
   *
   * @param generationPath the generation path
   * @param appModuleGenerator the app module generator
   * @param appRoutingModuleGenerator the app routing module module generator
   */
  public AngularProjectGenerator(
      Path generationPath,
      AppModuleGenerator appModuleGenerator,
      AppRoutingModuleGenerator appRoutingModuleGenerator) {
    super(generationPath);
    this.appModuleGenerator = appModuleGenerator;
    this.appRoutingModuleGenerator = appRoutingModuleGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  protected void generateBootstrap(AngularProject project) {
    AppModule appModule = new AppModule();
    appModuleGenerator.generate(
        appModule,
        ExportInfo.file(
            Paths.get(getGenerationPath().toString(), AmgularFolderFileConstants.SRC),
            String.format(
                "%s%s",
                TextConverter.toLowerHyphen(appModule.getObjectName().getText()),
                AmgularFolderFileConstants.TYPESCRIPT_MODULE_POSTFIX)),
        project);

    AppRoutingModule appRoutingModule = new AppRoutingModule();
    appRoutingModuleGenerator.generate(
        appRoutingModule,
        ExportInfo.file(
            Paths.get(getGenerationPath().toString(), AmgularFolderFileConstants.SRC),
            String.format(
                "%s%s",
                TextConverter.toLowerHyphen(appRoutingModule.getObjectName().getText()),
                AmgularFolderFileConstants.TYPESCRIPT_MODULE_POSTFIX)),
        project);
  }
}
