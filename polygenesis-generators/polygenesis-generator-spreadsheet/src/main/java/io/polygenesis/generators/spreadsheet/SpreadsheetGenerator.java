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

import io.polygenesis.commons.path.PathService;
import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.metamodels.spreadsheet.SpreadsheetMetamodelRepository;
import io.polygenesis.transformers.spreadsheet.SpreadsheetTransformer;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Spreadsheet generator.
 *
 * @author Christos Tsakostas
 */
public class SpreadsheetGenerator extends AbstractGenerator {

  private static final String FORMAT = "xls";

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Spreadsheet generator.
   *
   * @param generationPath the generation path
   */
  public SpreadsheetGenerator(Path generationPath) {
    super(generationPath);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public void generate(Set<MetamodelRepository> modelRepositories) {
    SpreadsheetTransformer spreadsheetTransformer = new SpreadsheetTransformer();

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, SpreadsheetMetamodelRepository.class)
        .getItems()
        .forEach(
            spreadsheet -> {
              ByteArrayOutputStream byteArrayOutputStream =
                  spreadsheetTransformer.transform(spreadsheet);

              export(
                  byteArrayOutputStream,
                  Paths.get(
                      getGenerationPath().toString(),
                      String.format("%s.%s", spreadsheet.getObjectName().getText(), FORMAT)));
            });
  }

  private void export(ByteArrayOutputStream byteArrayOutputStream, Path filePath) {
    PathService.ensurePath(filePath.getParent());

    FileOutputStream fileOutputStream;
    try {
      fileOutputStream = new FileOutputStream(filePath.toFile());

      byteArrayOutputStream.writeTo(fileOutputStream);

      fileOutputStream.close();
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }
}
