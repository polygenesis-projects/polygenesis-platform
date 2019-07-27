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

import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.metamodels.spreadsheet.Spreadsheet;
import io.polygenesis.metamodels.spreadsheet.SpreadsheetMetamodelRepository;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Spreadsheet metamodel generator.
 *
 * @author Christos Tsakostas
 */
public class SpreadsheetMetamodelGenerator extends AbstractMetamodelGenerator {

  private static final String SPREADSHEET_POSTFIX = "xls";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final SpreadsheetGenerator spreadsheetGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Spreadsheet metamodel generator.
   *
   * @param generationPath the generation path
   * @param spreadsheetGenerator the spreadsheet generator
   */
  public SpreadsheetMetamodelGenerator(
      Path generationPath, SpreadsheetGenerator spreadsheetGenerator) {
    super(generationPath);
    this.spreadsheetGenerator = spreadsheetGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, SpreadsheetMetamodelRepository.class)
        .getItems()
        .forEach(
            spreadsheet ->
                spreadsheetGenerator.generate(
                    spreadsheet, serviceExportInfo(getGenerationPath(), spreadsheet)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo serviceExportInfo(Path generationPath, Spreadsheet spreadsheet) {
    return ExportInfo.file(
        generationPath,
        String.format("%s.%s", spreadsheet.getObjectName().getText(), SPREADSHEET_POSTFIX));
  }
}
