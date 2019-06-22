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

package io.polygenesis.generators.flutter;

import io.polygenesis.core.MetamodelGenerator;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Flutter context generator factory.
 *
 * @author Christos Tsakostas
 */
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
   * @return the flutter context generator
   */
  public static FlutterContextGenerator newInstance(Path generationPath) {
    Set<MetamodelGenerator> metamodelGenerators = new LinkedHashSet<>();

    return new FlutterContextGenerator(generationPath, metamodelGenerators);
  }
}
