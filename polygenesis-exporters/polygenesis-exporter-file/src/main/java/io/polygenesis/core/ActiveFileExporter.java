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

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.path.PathService;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ActiveFileExporter extends AbstractExporter implements Exporter {

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void export(ByteArrayOutputStream byteArrayOutputStream, ExportInfo exportInfo) {
    Assertion.isNotNull(exportInfo, "exportInfo is required");
    Assertion.isNotNull(
        exportInfo.getGenerationPath(), "exportInfo.getGenerationPath() is required");
    Assertion.isNotNull(exportInfo.getFileName(), "exportInfo.getFileName() is required");

    PathService.ensurePath(exportInfo.getGenerationPath());

    try {
      Path filePath =
          Paths.get(exportInfo.getGenerationPath().toString(), exportInfo.getFileName());

      OutputStream outputStream = new FileOutputStream(filePath.toString());
      if (exportInfo.getFormatCode() && 1 == 1) {
        byteArrayOutputStream = formatCodeIfJava(byteArrayOutputStream, filePath.toString());
      }
      byteArrayOutputStream.writeTo(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ByteArrayOutputStream formatCodeIfJava(
      ByteArrayOutputStream byteArrayOutputStream, String fileName) {
    if (!fileName.toLowerCase().endsWith(".java")) {
      return byteArrayOutputStream;
    }

    String output = format(byteArrayOutputStream.toString(), fileName);

    ByteArrayOutputStream byteArrayOutputStreamFormatted = new ByteArrayOutputStream();
    byteArrayOutputStreamFormatted.writeBytes(output.getBytes());

    return byteArrayOutputStreamFormatted;
  }
}
