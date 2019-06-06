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

package io.polygenesis.core;

import io.polygenesis.commons.assertion.Assertion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The type Abstract generator.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractMetamodelGenerator implements MetamodelGenerator {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Path generationPath;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract generator.
   *
   * @param generationPath the generation path
   */
  protected AbstractMetamodelGenerator(Path generationPath) {
    setGenerationPath(generationPath);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets generation path.
   *
   * @return the generation path
   */
  protected Path getGenerationPath() {
    return generationPath;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================
  private void setGenerationPath(Path generationPath) {
    Assertion.isNotNull(generationPath, "Generation Path is required");
    try {
      if (!generationPath.toFile().exists()) {
        Files.createDirectories(generationPath);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format(
              "Cannot create directories for '%s'. Error message=%s",
              generationPath.toString(), e.getMessage()),
          e);
    }
    this.generationPath = generationPath;
  }
}
