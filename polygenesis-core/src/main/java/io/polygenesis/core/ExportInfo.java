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

package io.polygenesis.core;

import java.nio.file.Path;

public class ExportInfo {

  private Path generationPath;
  private String fileName;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Path export info.
   *
   * @param generationPath the generation path
   * @return the export info
   */
  public static ExportInfo path(Path generationPath) {
    return new ExportInfo(generationPath, null);
  }

  /**
   * File unit export info.
   *
   * @param generationPath the generation path
   * @param fileName the file name
   * @return the unit export info
   */
  public static ExportInfo file(Path generationPath, String fileName) {
    return new ExportInfo(generationPath, fileName);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ExportInfo(Path generationPath, String fileName) {
    this.generationPath = generationPath;
    this.fileName = fileName;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets generation path.
   *
   * @return the generation path
   */
  public Path getGenerationPath() {
    return generationPath;
  }

  /**
   * Gets file name.
   *
   * @return the file name
   */
  public String getFileName() {
    return fileName;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

}
