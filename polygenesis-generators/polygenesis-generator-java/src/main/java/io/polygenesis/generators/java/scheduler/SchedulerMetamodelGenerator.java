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

package io.polygenesis.generators.java.scheduler;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.scheduler.Scheduler;
import io.polygenesis.models.scheduler.SchedulerRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class SchedulerMetamodelGenerator extends AbstractMetamodelGenerator {

  private final SchedulerGenerator schedulerGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scheduler metamodel generator.
   *
   * @param generationPath the generation path
   * @param schedulerGenerator the scheduler generator
   */
  public SchedulerMetamodelGenerator(Path generationPath, SchedulerGenerator schedulerGenerator) {
    super(generationPath);
    this.schedulerGenerator = schedulerGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, SchedulerRepository.class)
        .getItems()
        .forEach(
            scheduler ->
                schedulerGenerator.generate(
                    scheduler, schedulerExportInfo(getGenerationPath(), scheduler)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo schedulerExportInfo(Path generationPath, Scheduler scheduler) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            scheduler.getPackageName().toPath().toString()),
        String.format(
            "%sRoute%s",
            TextConverter.toUpperCamel(scheduler.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
