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

import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.models.sql.SqlModelRepository;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Sql generator.
 *
 * @author Christos Tsakostas
 */
public class SqlGenerator extends AbstractGenerator {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ScriptExporter scriptExporter;
  private final String tablePrefix;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Sql generator.
   *
   * @param generationPath the generation path
   * @param scriptExporter the script exporter
   * @param tablePrefix the table prefix
   */
  public SqlGenerator(Path generationPath, ScriptExporter scriptExporter, String tablePrefix) {
    super(generationPath);
    this.scriptExporter = scriptExporter;
    this.tablePrefix = tablePrefix;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets table prefix.
   *
   * @return the table prefix
   */
  public String getTablePrefix() {
    return tablePrefix;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<ModelRepository> modelRepositories) {
    SqlModelRepository sqlModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, SqlModelRepository.class);

    scriptExporter.export(getGenerationPath(), sqlModelRepository, getTablePrefix());
  }
}
