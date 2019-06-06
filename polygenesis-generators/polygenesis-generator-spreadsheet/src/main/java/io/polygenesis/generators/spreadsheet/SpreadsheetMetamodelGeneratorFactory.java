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

package io.polygenesis.generators.spreadsheet;

import io.polygenesis.core.Exporter;
import io.polygenesis.core.FileExporter;
import java.nio.file.Path;

/**
 * The type Spreadsheet metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public class SpreadsheetMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static SpreadsheetGenerator spreadsheetGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    Exporter exporter = new FileExporter();

    spreadsheetGenerator = new SpreadsheetGenerator(new SpreadsheetTransformer(), exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private SpreadsheetMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance spreadsheet metamodel generator.
   *
   * @param generationPath the generation path
   * @return the spreadsheet metamodel generator
   */
  public static SpreadsheetMetamodelGenerator newInstance(Path generationPath) {
    return new SpreadsheetMetamodelGenerator(generationPath, spreadsheetGenerator);
  }
}
