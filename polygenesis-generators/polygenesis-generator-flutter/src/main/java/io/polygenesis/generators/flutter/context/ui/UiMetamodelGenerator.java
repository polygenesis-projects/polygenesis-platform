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

package io.polygenesis.generators.flutter.context.ui;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.flutter.DartFolderFileConstants;
import io.polygenesis.generators.flutter.context.ui.screen.ScreenGenerator;
import io.polygenesis.generators.flutter.context.ui.widget.WidgetGenerator;
import io.polygenesis.metamodels.ui.UiContextMetamodelRepository;
import io.polygenesis.metamodels.ui.feature.Feature;
import io.polygenesis.metamodels.ui.screen.Screen;
import io.polygenesis.metamodels.ui.widget.Widget;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UiMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final ScreenGenerator screenGenerator;
  private final WidgetGenerator widgetGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Ui metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param screenGenerator the screen generator
   * @param widgetGenerator the widget generator
   */
  public UiMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      ScreenGenerator screenGenerator,
      WidgetGenerator widgetGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.screenGenerator = screenGenerator;
    this.widgetGenerator = widgetGenerator;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ContextName getContextName() {
    return contextName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    Set<Feature> features =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, UiContextMetamodelRepository.class)
            .getItems()
            .stream()
            .flatMap(uiContext -> uiContext.getFeatures().stream())
            .collect(Collectors.toCollection(LinkedHashSet::new));

    features.stream()
        .flatMap(feature -> feature.getScreens().stream())
        .forEach(
            screen ->
                screenGenerator.generate(screen, screenExportInfo(getGenerationPath(), screen)));

    features.stream()
        .flatMap(feature -> feature.getWidgets().stream())
        .forEach(
            widget ->
                widgetGenerator.generate(widget, widgetExportInfo(getGenerationPath(), widget)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo screenExportInfo(Path generationPath, Screen screen) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            TextConverter.toLowerUnderscore(screen.getFeature().getFeatureName().getText()),
            DartFolderFileConstants.SCREENS),
        TextConverter.toLowerUnderscore(
            String.format(
                "%s%s",
                screen.getObjectName().getText(), DartFolderFileConstants.DART_SCREEN_POSTFIX)));
  }

  private ExportInfo widgetExportInfo(Path generationPath, Widget widget) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            TextConverter.toLowerUnderscore(widget.getFeature().getFeatureName().getText()),
            DartFolderFileConstants.WIDGETS),
        TextConverter.toLowerUnderscore(
            String.format(
                "%s%s",
                widget.getObjectName().getText(), DartFolderFileConstants.DART_WIDGET_POSTFIX)));
  }
}
