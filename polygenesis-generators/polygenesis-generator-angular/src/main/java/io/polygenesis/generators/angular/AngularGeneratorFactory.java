/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.generators.angular;

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.generators.angular.once.OnceAppModuleExporter;
import io.polygenesis.generators.angular.once.OnceCoreModuleExporter;
import io.polygenesis.generators.angular.once.OnceEnvironmentsExporter;
import io.polygenesis.generators.angular.once.OnceExporter;
import io.polygenesis.generators.angular.once.OnceLandingModuleExporter;
import io.polygenesis.generators.angular.once.OnceSharedModuleExporter;
import io.polygenesis.generators.angular.once.OnceSourceRootExporter;
import io.polygenesis.generators.angular.reactivestate.ActionExporter;
import io.polygenesis.generators.angular.reactivestate.EffectExporter;
import io.polygenesis.generators.angular.reactivestate.IndexReducerExporter;
import io.polygenesis.generators.angular.reactivestate.ModelExporter;
import io.polygenesis.generators.angular.reactivestate.ModuleExporter;
import io.polygenesis.generators.angular.reactivestate.ReducerExporter;
import io.polygenesis.generators.angular.reactivestate.ServiceExporter;
import io.polygenesis.generators.angular.reactivestate.StoreExporter;
import io.polygenesis.generators.angular.ui.UiExporter;
import io.polygenesis.generators.angular.ui.UiModuleExporter;
import io.polygenesis.generators.angular.ui.container.UiContainerExporter;
import io.polygenesis.generators.angular.ui.container.UiContainerHtmlExporter;
import io.polygenesis.generators.angular.ui.container.UiContainerScssExporter;
import io.polygenesis.generators.angular.ui.container.UiContainerTypescriptExporter;
import io.polygenesis.generators.angular.ui.container.UiContainerTypescriptSpecExporter;
import java.nio.file.Path;

/**
 * The type PolyGenesis angular generator factory.
 *
 * @author Christos Tsakostas
 */
public class AngularGeneratorFactory {

  // ===============================================================================================
  // SINGLETONS / STATIC
  // ===============================================================================================

  private static final OnceExporter onceExporter;
  private static final StoreExporter storeExporter;
  private static final UiExporter uiExporter;

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    ActionExporter actionExporter = new ActionExporter(freemarkerService);
    ReducerExporter reducerExporter = new ReducerExporter(freemarkerService);
    IndexReducerExporter indexReducerExporter = new IndexReducerExporter(freemarkerService);
    EffectExporter effectExporter = new EffectExporter(freemarkerService);
    ServiceExporter serviceExporter = new ServiceExporter(freemarkerService);
    ModelExporter modelExporter = new ModelExporter(freemarkerService);
    ModuleExporter moduleExporter = new ModuleExporter(freemarkerService);

    storeExporter =
        new StoreExporter(
            actionExporter,
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
   * New instance PolyGenesis angular generator.
   *
   * @param generationPath the generation path
   * @return the poly genesis angular generator
   */
  public static AngularGenerator newInstance(Path generationPath) {
    return new AngularGenerator(generationPath, onceExporter, storeExporter, uiExporter);
  }
}
