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

import io.polygenesis.generators.angular.reactivestate.ActionExporter;
import io.polygenesis.generators.angular.reactivestate.EffectExporter;
import io.polygenesis.generators.angular.reactivestate.IndexReducerExporter;
import io.polygenesis.generators.angular.reactivestate.ModelExporter;
import io.polygenesis.generators.angular.reactivestate.ModuleExporter;
import io.polygenesis.generators.angular.reactivestate.ReducerExporter;
import io.polygenesis.generators.angular.reactivestate.ServiceExporter;
import io.polygenesis.generators.angular.reactivestate.StoreExporter;
import java.nio.file.Path;

/**
 * The type PolyGenesis angular generator factory.
 *
 * @author Christos Tsakostas
 */
public class PolyGenesisAngularGeneratorFactory {

  // ===============================================================================================
  // SINGLETONS / STATIC
  // ===============================================================================================
  private static final StoreExporter storeExporter;

  static {
    ActionExporter actionExporter = new ActionExporter();
    ReducerExporter reducerExporter = new ReducerExporter();
    IndexReducerExporter indexReducerExporter = new IndexReducerExporter();
    EffectExporter effectExporter = new EffectExporter();
    ServiceExporter serviceExporter = new ServiceExporter();
    ModelExporter modelExporter = new ModelExporter();
    ModuleExporter moduleExporter = new ModuleExporter();

    storeExporter =
        new StoreExporter(
            actionExporter,
            reducerExporter,
            indexReducerExporter,
            effectExporter,
            serviceExporter,
            modelExporter,
            moduleExporter);
  }

  /**
   * New instance PolyGenesis angular generator.
   *
   * @param generationPath the generation path
   * @return the poly genesis angular generator
   */
  public static PolyGenesisAngularGenerator newInstance(Path generationPath) {
    return new PolyGenesisAngularGenerator(generationPath, storeExporter);
  }
}
