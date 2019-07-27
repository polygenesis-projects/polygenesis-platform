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

package io.polygenesis.generators.java.domainservicedetail;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.domain.DomainService;
import io.polygenesis.models.domain.DomainServiceRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Java api rest generator.
 *
 * @author Christos Tsakostas
 */
public class DomainServiceDetailMetamodelGenerator extends AbstractMetamodelGenerator {

  private final DomainServiceDetailGenerator domainServiceDetailGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public DomainServiceDetailMetamodelGenerator(
      Path generationPath, DomainServiceDetailGenerator domainServiceDetailGenerator) {
    super(generationPath);
    this.domainServiceDetailGenerator = domainServiceDetailGenerator;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, DomainServiceRepository.class)
        .getItems()
        .forEach(
            domainService ->
                domainServiceDetailGenerator.generate(
                    domainService, domainServiceExportInfo(getGenerationPath(), domainService)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo domainServiceExportInfo(Path generationPath, DomainService domainService) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainService.getPackageName().toPath().toString()),
        String.format(
            "%sImpl%s",
            TextConverter.toUpperCamel(domainService.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
