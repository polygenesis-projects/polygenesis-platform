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

package io.polygenesis.generators.java.api;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.api.dto.DtoGenerator;
import io.polygenesis.generators.java.api.service.ServiceGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Java api generator.
 *
 * @author Christos Tsakostas
 */
public class JavaApiMetamodelGenerator extends AbstractMetamodelGenerator {

  private final ServiceGenerator serviceGenerator;
  private final DtoGenerator dtoGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java api metamodel generator.
   *
   * @param generationPath the generation path
   * @param serviceGenerator the service generator
   * @param dtoGenerator the dto generator
   */
  public JavaApiMetamodelGenerator(
      Path generationPath, ServiceGenerator serviceGenerator, DtoGenerator dtoGenerator) {
    super(generationPath);
    this.serviceGenerator = serviceGenerator;
    this.dtoGenerator = dtoGenerator;
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
              serviceGenerator.generate(service, serviceExportInfo(getGenerationPath(), service));

              service
                  .getDtos()
                  .forEach(
                      dto -> {
                        if (!dto.getVirtual()) {
                          dtoGenerator.generate(dto, dtoExportInfo(getGenerationPath(), dto));
                        }
                      });
            });
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo serviceExportInfo(Path generationPath, Service service) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            service.getPackageName().toPath().toString()),
        TextConverter.toUpperCamel(service.getServiceName().getText())
            + FolderFileConstants.JAVA_POSTFIX);
  }

  private ExportInfo dtoExportInfo(Path generationPath, Dto dto) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            dto.getDataObject().getPackageName().toPath().toString()),
        TextConverter.toUpperCamel(dto.getDataObject().getObjectName().getText())
            + FolderFileConstants.JAVA_POSTFIX);
  }
}
