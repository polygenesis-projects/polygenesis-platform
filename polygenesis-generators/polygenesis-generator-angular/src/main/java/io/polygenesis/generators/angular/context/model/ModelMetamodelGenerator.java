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

package io.polygenesis.generators.angular.context.model;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.angular.AmgularFolderFileConstants;
import io.polygenesis.generators.angular.context.model.dto.ModelGenerator;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class ModelMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final ModelGenerator modelGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Model metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param modelGenerator the model generator
   */
  public ModelMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      ModelGenerator modelGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.modelGenerator = modelGenerator;
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
        .resolve(modelRepositories, ServiceMetamodelRepository.class)
        .getItems()
        .forEach(
            service -> {
              service
                  .getDtos()
                  .forEach(
                      dto -> {
                        if (!dto.getVirtual()) {
                          modelGenerator.generate(dto, modelExportInfo(getGenerationPath(), dto));
                        }
                      });
            });
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo modelExportInfo(Path generationPath, Dto dto) {
    return ExportInfo.file(
        Paths.get(generationPath.toString(), AmgularFolderFileConstants.MODELS),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(dto.getDataObject().getObjectName().getText()),
            AmgularFolderFileConstants.TYPESCRIPT_POSTFIX));
  }
}
