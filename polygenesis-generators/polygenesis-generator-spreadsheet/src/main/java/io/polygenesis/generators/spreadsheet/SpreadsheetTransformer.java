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

import io.polygenesis.core.Transformer;
import io.polygenesis.metamodels.spreadsheet.Sheet;
import io.polygenesis.metamodels.spreadsheet.Spreadsheet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The type Spreadsheet unit transformer.
 *
 * @author Christos Tsakostas
 */
public class SpreadsheetTransformer implements Transformer<Spreadsheet> {

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ByteArrayOutputStream transform(Spreadsheet source) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      source.getSheets().forEach(sheet -> createXssfSheet(workbook, sheet));
      workbook.write(byteArrayOutputStream);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }

    return byteArrayOutputStream;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Create xssf sheet xssf sheet.
   *
   * @param workbook the workbook
   * @param sheet the sheet
   * @return the xssf sheet
   */
  private XSSFSheet createXssfSheet(XSSFWorkbook workbook, Sheet sheet) {
    XSSFSheet xssfSheet = workbook.createSheet(sheet.getName().getText());

    IntStream.range(0, sheet.getNumberOfRows())
        .forEach(
            rowIndex -> {
              Row row = xssfSheet.createRow(rowIndex);

              sheet
                  .getCellsByRowIndex(rowIndex)
                  .stream()
                  .forEach(
                      sheetCell -> {
                        Cell cell = row.createCell(sheetCell.getColumnIndex().getIndex());
                        cell.setCellValue(sheetCell.getValue().getValue().toString());
                      });
            });

    return xssfSheet;
  }
}
