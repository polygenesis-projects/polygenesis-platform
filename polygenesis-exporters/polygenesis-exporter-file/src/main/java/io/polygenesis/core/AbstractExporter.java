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

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.JavaFormatterOptions;
import com.google.googlejavaformat.java.JavaFormatterOptions.Style;

public abstract class AbstractExporter {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Formatter formatter =
      new Formatter(JavaFormatterOptions.builder().style(Style.GOOGLE).build());

  // ===============================================================================================
  // PROTECTED FUNCTIONALITY
  // ===============================================================================================

  /**
   * Format string.
   *
   * @param sourceString the source string
   * @param fileName the file name
   * @return the string
   */
  protected String format(String sourceString, String fileName) {
    if (!fileName.endsWith(".java")) {
      return sourceString;
    }

    try {
      return formatter.formatSourceAndFixImports(sourceString);
    } catch (FormatterException e) {
      return sourceString;
    }
  }
}
