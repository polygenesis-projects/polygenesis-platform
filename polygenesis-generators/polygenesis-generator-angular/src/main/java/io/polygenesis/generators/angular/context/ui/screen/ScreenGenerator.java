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

package io.polygenesis.generators.angular.context.ui.screen;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.generators.angular.AmgularFolderFileConstants;
import io.polygenesis.generators.angular.context.ui.screen.html.ScreenHtmlGenerator;
import io.polygenesis.generators.angular.context.ui.screen.scss.ScreenScssGenerator;
import io.polygenesis.generators.angular.context.ui.screen.spec.ScreenSpecTypescriptGenerator;
import io.polygenesis.generators.angular.context.ui.screen.ts.ScreenTypescriptGenerator;
import io.polygenesis.metamodels.ui.screen.Screen;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Screen generator.
 *
 * @author Christos Tsakostas
 */
public class ScreenGenerator {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final ScreenHtmlGenerator screenHtmlGenerator;
  private final ScreenScssGenerator screenScssGenerator;
  private final ScreenTypescriptGenerator screenTypescriptGenerator;
  private final ScreenSpecTypescriptGenerator screenSpecTypescriptGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Screen generator.
   *
   * @param screenHtmlGenerator the screen html generator
   * @param screenScssGenerator the screen scss generator
   * @param screenTypescriptGenerator the screen typescript generator
   * @param screenSpecTypescriptGenerator the screen test typescript generator
   */
  public ScreenGenerator(
      ScreenHtmlGenerator screenHtmlGenerator,
      ScreenScssGenerator screenScssGenerator,
      ScreenTypescriptGenerator screenTypescriptGenerator,
      ScreenSpecTypescriptGenerator screenSpecTypescriptGenerator) {
    this.screenHtmlGenerator = screenHtmlGenerator;
    this.screenScssGenerator = screenScssGenerator;
    this.screenTypescriptGenerator = screenTypescriptGenerator;
    this.screenSpecTypescriptGenerator = screenSpecTypescriptGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  /**
   * Generate.
   *
   * @param generationPath the generation path
   * @param screen the screen
   */
  public void generate(Path generationPath, Screen screen) {
    screenHtmlGenerator.generate(
        screen,
        ExportInfo.file(
            Paths.get(generationPath.toString(), AmgularFolderFileConstants.SCREENS),
            String.format(
                "%s%s",
                TextConverter.toUpperCamel(screen.getObjectName().getText()),
                AmgularFolderFileConstants.HTML_POSTFIX)));

    screenScssGenerator.generate(
        screen,
        ExportInfo.file(
            Paths.get(generationPath.toString(), AmgularFolderFileConstants.SCREENS),
            String.format(
                "%s%s",
                TextConverter.toUpperCamel(screen.getObjectName().getText()),
                AmgularFolderFileConstants.SCSS_POSTFIX)));

    screenTypescriptGenerator.generate(
        screen,
        ExportInfo.file(
            Paths.get(generationPath.toString(), AmgularFolderFileConstants.SCREENS),
            String.format(
                "%s%s",
                TextConverter.toUpperCamel(screen.getObjectName().getText()),
                AmgularFolderFileConstants.TYPESCRIPT_POSTFIX)));

    screenSpecTypescriptGenerator.generate(
        screen,
        ExportInfo.file(
            Paths.get(generationPath.toString(), AmgularFolderFileConstants.SCREENS),
            String.format(
                "%s%s",
                TextConverter.toUpperCamel(screen.getObjectName().getText()),
                AmgularFolderFileConstants.TYPESCRIPT_SPEC_POSTFIX)));
  }
}
