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

package io.polygenesis.generators.spreadsheet;

import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.metamodels.spreadsheet.Cell;
import io.polygenesis.metamodels.spreadsheet.ColumnIndex;
import io.polygenesis.metamodels.spreadsheet.RowIndex;
import io.polygenesis.metamodels.spreadsheet.Sheet;
import io.polygenesis.metamodels.spreadsheet.Spreadsheet;
import io.polygenesis.metamodels.spreadsheet.SpreadsheetMetamodelRepository;
import io.polygenesis.metamodels.spreadsheet.Value;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.IntStream;
import org.junit.Test;

public class SpreadsheetGeneratorIntegrationTest {

  /** Should generate spreadsheet. */
  @Test
  public void shouldGenerateSpreadsheet() {
    final Path exportPath = Paths.get(System.getProperty("user.dir"), "target");

    // SPREADSHEET
    Set<Spreadsheet> spreadsheets = new LinkedHashSet<>();

    Set<Sheet> sheets = new LinkedHashSet<>();

    Set<Cell> cells = new LinkedHashSet<>();
    IntStream.range(0, 5)
        .forEach(
            rowIndex -> {
              cells.add(new Cell(new RowIndex(rowIndex), new ColumnIndex(0), new Value(rowIndex)));
            });

    Sheet sheet = new Sheet(new Name("no alias"), cells);

    sheets.add(sheet);

    Spreadsheet spreadsheet = new Spreadsheet(new ObjectName("redirects"), sheets);
    spreadsheets.add(spreadsheet);

    SpreadsheetMetamodelRepository spreadsheetMetamodelRepository =
        new SpreadsheetMetamodelRepository(spreadsheets);

    Set<MetamodelRepository<?>> modelRepositories = new LinkedHashSet<>();
    modelRepositories.add(spreadsheetMetamodelRepository);

    SpreadsheetMetamodelGenerator spreadsheetMetamodelGenerator =
        SpreadsheetMetamodelGeneratorFactory.newInstance(exportPath);

    spreadsheetMetamodelGenerator.generate(modelRepositories);
  }
}
