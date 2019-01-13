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

import io.polygenesis.generators.angular.state.StoreExporter;
import java.nio.file.Path;

/**
 * The type PolyGenesis angular generator service.
 *
 * @author Christos Tsakostas
 */
public class PolyGenesisAngularGeneratorService {

  // ===============================================================================================
  // SINGLETONS / STATIC
  // ===============================================================================================
  private static final StoreExporter storeExporter;

  static {
    storeExporter = new StoreExporter();
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
