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

package io.polygenesis.generators.java.batchprocessscheduler;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.batchprocessscheduler.scheduler.BatchProcessSchedulerGenerator;
import io.polygenesis.generators.java.batchprocessscheduler.scheduler.BatchProcessSchedulerRoute;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodel;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class BatchProcessSchedulerMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final BatchProcessSchedulerGenerator batchProcessSchedulerGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process scheduler metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param batchProcessSchedulerGenerator the batch process scheduler generator
   */
  public BatchProcessSchedulerMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      BatchProcessSchedulerGenerator batchProcessSchedulerGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.batchProcessSchedulerGenerator = batchProcessSchedulerGenerator;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ContextName getContextName() {
    return contextName;
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
            batchProcessMetamodel -> {
              batchProcessSchedulerGenerator.generate(
                  makeBatchProcessSchedulerRoute(batchProcessMetamodel),
                  batchProcessSchedulerGeneratorExportInfo(
                      getGenerationPath(), batchProcessMetamodel));
            });
  }

  // ===============================================================================================
  // PRIVATE - Objects
  // ===============================================================================================

  /**
   * Make batch process scheduler route batch process scheduler route.
   *
   * @return the batch process scheduler route
   */
  private BatchProcessSchedulerRoute makeBatchProcessSchedulerRoute(
      BatchProcessMetamodel batchProcessMetamodel) {
    return new BatchProcessSchedulerRoute(
        batchProcessMetamodel.getObjectName(),
        batchProcessMetamodel.getPackageName(),
        batchProcessMetamodel.getCommandServiceMethod(),
        batchProcessMetamodel.getQueryServiceMethod(),
        batchProcessMetamodel.getQueryCollectionItem());
  }

  // ===============================================================================================
  // PRIVATE - ExportInfo
  // ===============================================================================================

  private ExportInfo batchProcessSchedulerGeneratorExportInfo(
      Path generationPath, BatchProcessMetamodel batchProcessMetamodel) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            batchProcessMetamodel.getPackageName().toPath().toString()),
        String.format(
            "%sSchedulerRoute%s",
            TextConverter.toUpperCamel(batchProcessMetamodel.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
