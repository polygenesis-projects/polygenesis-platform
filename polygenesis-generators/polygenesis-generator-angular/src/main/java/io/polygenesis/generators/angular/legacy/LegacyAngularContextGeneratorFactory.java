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

package io.polygenesis.generators.angular.legacy;

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.MetamodelGenerator;
import io.polygenesis.generators.angular.legacy.exporters.once.OnceAppModuleExporter;
import io.polygenesis.generators.angular.legacy.exporters.once.OnceCoreModuleExporter;
import io.polygenesis.generators.angular.legacy.exporters.once.OnceEnvironmentsExporter;
import io.polygenesis.generators.angular.legacy.exporters.once.OnceExporter;
import io.polygenesis.generators.angular.legacy.exporters.once.OnceLandingModuleExporter;
import io.polygenesis.generators.angular.legacy.exporters.once.OnceSharedModuleExporter;
import io.polygenesis.generators.angular.legacy.exporters.once.OnceSourceRootExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.EffectExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.IndexReducerExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.ModuleExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.ReducerExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.ServiceExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.StoreExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.action.ActionGroupExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.action.ActionGroupRepresentable;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.action.ActionIndexExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.action.ActionIndexRepresentable;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.model.ModelExporter;
import io.polygenesis.generators.angular.legacy.exporters.reactivestate.model.ModelRepresentable;
import io.polygenesis.generators.angular.legacy.exporters.ui.UiExporter;
import io.polygenesis.generators.angular.legacy.exporters.ui.UiModuleExporter;
import io.polygenesis.generators.angular.legacy.exporters.ui.container.UiContainerExporter;
import io.polygenesis.generators.angular.legacy.exporters.ui.container.UiContainerHtmlExporter;
import io.polygenesis.generators.angular.legacy.exporters.ui.container.UiContainerScssExporter;
import io.polygenesis.generators.angular.legacy.exporters.ui.container.UiContainerTypescriptExporter;
import io.polygenesis.generators.angular.legacy.exporters.ui.container.UiContainerTypescriptSpecExporter;
import io.polygenesis.generators.angular.legacy.skeletons.FromDataTypeToTypescriptConverter;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

public class LegacyAngularContextGeneratorFactory {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private LegacyAngularContextGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // SINGLETONS / STATIC
  // ===============================================================================================

  private static final OnceExporter onceExporter;
  private static final StoreExporter storeExporter;
  private static final UiExporter uiExporter;

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());
    FromDataTypeToTypescriptConverter fromDataTypeToTypescriptConverter =
        new FromDataTypeToTypescriptConverter();

    ActionGroupRepresentable actionGroupRepresentable =
        new ActionGroupRepresentable(fromDataTypeToTypescriptConverter);
    ActionGroupExporter actionGroupExporter =
        new ActionGroupExporter(freemarkerService, actionGroupRepresentable);

    ActionIndexRepresentable actionIndexRepresentable = new ActionIndexRepresentable();
    ActionIndexExporter actionIndexExporter =
        new ActionIndexExporter(freemarkerService, actionIndexRepresentable);

    ReducerExporter reducerExporter = new ReducerExporter(freemarkerService);
    IndexReducerExporter indexReducerExporter = new IndexReducerExporter(freemarkerService);
    EffectExporter effectExporter = new EffectExporter(freemarkerService);
    ServiceExporter serviceExporter = new ServiceExporter(freemarkerService);

    ModelRepresentable modelRepresentable =
        new ModelRepresentable(fromDataTypeToTypescriptConverter);
    ModelExporter modelExporter = new ModelExporter(freemarkerService, modelRepresentable);

    ModuleExporter moduleExporter = new ModuleExporter(freemarkerService);

    storeExporter =
        new StoreExporter(
            actionGroupExporter,
            actionIndexExporter,
            reducerExporter,
            indexReducerExporter,
            effectExporter,
            serviceExporter,
            modelExporter,
            moduleExporter);

    UiModuleExporter uiModuleExporter = new UiModuleExporter(freemarkerService);
    UiContainerHtmlExporter uiContainerHtmlExporter =
        new UiContainerHtmlExporter(freemarkerService);
    UiContainerScssExporter uiContainerScssExporter =
        new UiContainerScssExporter(freemarkerService);
    UiContainerTypescriptExporter uiContainerTypescriptExporter =
        new UiContainerTypescriptExporter(freemarkerService);
    UiContainerTypescriptSpecExporter uiContainerTypescriptSpecExporter =
        new UiContainerTypescriptSpecExporter(freemarkerService);
    UiContainerExporter uiContainerExporter =
        new UiContainerExporter(
            uiContainerHtmlExporter,
            uiContainerScssExporter,
            uiContainerTypescriptExporter,
            uiContainerTypescriptSpecExporter);

    uiExporter = new UiExporter(uiModuleExporter, uiContainerExporter);

    OnceSourceRootExporter onceSourceRootExporter = new OnceSourceRootExporter(freemarkerService);
    OnceEnvironmentsExporter onceEnvironmentsExporter =
        new OnceEnvironmentsExporter(freemarkerService);
    OnceCoreModuleExporter onceCoreModuleExporter = new OnceCoreModuleExporter(freemarkerService);
    OnceSharedModuleExporter onceSharedModuleExporter =
        new OnceSharedModuleExporter(freemarkerService, uiContainerExporter);
    OnceAppModuleExporter onceAppModuleExporter = new OnceAppModuleExporter(freemarkerService);
    OnceLandingModuleExporter onceLandingModuleExporter =
        new OnceLandingModuleExporter(freemarkerService);

    onceExporter =
        new OnceExporter(
            onceSourceRootExporter,
            onceEnvironmentsExporter,
            onceCoreModuleExporter,
            onceSharedModuleExporter,
            onceAppModuleExporter,
            onceLandingModuleExporter);
  }

  /**
   * New instance angular context generator.
   *
   * @param generationPath the generation path
   * @return the angular context generator
   */
  public static LegacyAngularContextGenerator newInstance(Path generationPath) {
    Set<MetamodelGenerator> metamodelGenerators = new LinkedHashSet<>();

    return new LegacyAngularContextGenerator(
        generationPath, metamodelGenerators, onceExporter, storeExporter, uiExporter);
  }
}
