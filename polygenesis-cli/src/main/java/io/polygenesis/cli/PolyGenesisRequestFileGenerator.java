/*-
 * ==========================LICENSE_START=================================
 * STATICFY
 * ========================================================================
 * Copyright (C) 2020 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.polygenesis.commons.assertion.Assertion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Poly genesis request file generator.
 *
 * @author Christos Tsakostas
 */
public class PolyGenesisRequestFileGenerator {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static final String JSON_FILE_EXTENSION = "json";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private ObjectMapper objectMapper;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Staticfy request file generator.
   *
   * @param objectMapper the object mapper
   */
  public PolyGenesisRequestFileGenerator(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  // ===============================================================================================
  // METHODS
  // ===============================================================================================

  /**
   * Create.
   *
   * @param generationPath the generation path
   * @param fileName the file name
   * @param request the request
   */
  public void create(Path generationPath, String fileName, PolyGenesisRequest request) {
    Assertion.isNotNull(request, "request is required");

    try {
      String jsonFileName =
          fileName.toLowerCase().endsWith(JSON_FILE_EXTENSION)
              ? fileName
              : String.format("%s.%s", fileName, JSON_FILE_EXTENSION);

      Files.write(
          Paths.get(generationPath.toString(), jsonFileName),
          objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request).getBytes());
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }
}
