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
import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;

/**
 * The type Passive file exporter.
 *
 * @author Christos Tsakostas
 */
public class PassiveFileExporter extends ActiveFileExporter {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final Boolean enforceOverwrite;

  // ===============================================================================================
  // CONSTRUCTOR
  // ===============================================================================================

  /** Instantiates a new Passive file exporter. */
  public PassiveFileExporter() {
    if (System.getenv().get("enforceOverwrite") != null
        && System.getenv().get("enforceOverwrite") == "true") {
      this.enforceOverwrite = true;
    } else {
      this.enforceOverwrite = false;
    }
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void export(ByteArrayOutputStream byteArrayOutputStream, ExportInfo exportInfo) {
    Assertion.isNotNull(exportInfo, "exportInfo is required");
    Assertion.isNotNull(
        exportInfo.getGenerationPath(), "exportInfo.getGenerationPath() is required");
    Assertion.isNotNull(exportInfo.getFileName(), "exportInfo.getFileName() is required");

    if (!Paths.get(exportInfo.getGenerationPath().toString(), exportInfo.getFileName())
            .toFile()
            .exists()
        || enforceOverwrite) {
      super.export(byteArrayOutputStream, exportInfo);
    }
  }
}
