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

package io.polygenesis.generators.java.batchprocess;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.batchprocess.command.BatchProcessCommandGenerator;
import io.polygenesis.generators.java.batchprocess.process.BatchProcessGenerator;
import io.polygenesis.generators.java.batchprocess.query.BatchProcessQueryGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodel;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Batch process metamodel generator.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final ContextName contextName;
  private final BatchProcessGenerator batchProcessGenerator;
  private final BatchProcessCommandGenerator batchProcessCommandGenerator;
  private final BatchProcessQueryGenerator batchProcessQueryGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process metamodel generator.
   *
   * @param generationPath the generation path
   * @param contextName the context name
   * @param batchProcessGenerator the batch process generator
   * @param batchProcessCommandGenerator the batch process command generator
   * @param batchProcessQueryGenerator the batch process query generator
   */
  public BatchProcessMetamodelGenerator(
      Path generationPath,
      ContextName contextName,
      BatchProcessGenerator batchProcessGenerator,
      BatchProcessCommandGenerator batchProcessCommandGenerator,
      BatchProcessQueryGenerator batchProcessQueryGenerator) {
    super(generationPath);
    this.contextName = contextName;
    this.batchProcessGenerator = batchProcessGenerator;
    this.batchProcessCommandGenerator = batchProcessCommandGenerator;
    this.batchProcessQueryGenerator = batchProcessQueryGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, BatchProcessMetamodelRepository.class)
        .getItems()
        .forEach(
            periodicProcessMetamodel -> {
              batchProcessGenerator.generate(
                  periodicProcessMetamodel,
                  processExportInfo(getGenerationPath(), periodicProcessMetamodel),
                  contextName);

              batchProcessCommandGenerator.generate(
                  periodicProcessMetamodel,
                  processCommandExportInfo(getGenerationPath(), periodicProcessMetamodel));

              batchProcessQueryGenerator.generate(
                  periodicProcessMetamodel,
                  processQueryExportInfo(getGenerationPath(), periodicProcessMetamodel));
            });
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo processExportInfo(
      Path generationPath, BatchProcessMetamodel batchProcessMetamodel) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            batchProcessMetamodel.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(batchProcessMetamodel.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo processCommandExportInfo(
      Path generationPath, BatchProcessMetamodel batchProcessMetamodel) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            batchProcessMetamodel.getPackageName().toPath().toString()),
        String.format(
            "%sCommand%s",
            TextConverter.toUpperCamel(batchProcessMetamodel.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo processQueryExportInfo(
      Path generationPath, BatchProcessMetamodel batchProcessMetamodel) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            batchProcessMetamodel.getPackageName().toPath().toString()),
        String.format(
            "%sQuery%s",
            TextConverter.toUpperCamel(batchProcessMetamodel.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
