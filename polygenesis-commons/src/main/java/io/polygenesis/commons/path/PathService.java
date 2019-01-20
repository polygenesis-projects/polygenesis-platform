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

package io.polygenesis.commons.path;

import io.polygenesis.commons.assertions.Assertion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The type Path service.
 *
 * @author Christos Tsakostas
 */
public final class PathService {

  private PathService() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Ensures the provided path is valid by creating missing directories, if any.
   *
   * @param path the path
   */
  public static void ensurePath(Path path) {
    Assertion.isNotNull(path, "Path is required");
    try {
      if (!Files.exists(path)) {
        Files.createDirectories(path);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format(
              "Cannot create directories for '%s'. Error message=%s",
              path.toString(), e.getMessage()),
          e);
    }
  }
}
