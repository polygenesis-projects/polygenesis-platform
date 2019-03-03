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

package io.polygenesis.generators.sql;

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import java.nio.file.Path;

/**
 * The type Sql generator factory.
 *
 * @author Christos Tsakostas
 */
public final class SqlGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final ScriptExporter scriptExporter;

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    ScriptRepresentable scriptRepresentable = new ScriptRepresentable();

    scriptExporter = new ScriptExporter(freemarkerService, scriptRepresentable);
  }

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private SqlGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance sql generator.
   *
   * @param generationPath the generation path
   * @param tablePrefix the table prefix
   * @return the sql generator
   */
  public static SqlGenerator newInstance(Path generationPath, String tablePrefix) {
    return new SqlGenerator(generationPath, scriptExporter, tablePrefix);
  }
}
