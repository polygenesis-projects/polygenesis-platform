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

package io.polygenesis.generators.java.repository.inmemory;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.domain.supportiveentity.repository.SupportiveEntityRepository;
import io.polygenesis.generators.java.repository.inmemory.initialization.SupportiveEntityInitialization;
import io.polygenesis.generators.java.repository.inmemory.initialization.SupportiveEntityInitializationGenerator;
import io.polygenesis.generators.java.repository.inmemory.supportiveentity.SupportiveEntityRepositoryImpl;
import io.polygenesis.generators.java.repository.inmemory.supportiveentity.SupportiveEntityRepositoryImplGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.domain.SupportiveEntityMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class InMemoryMetamodelGenerator extends AbstractMetamodelGenerator {

  private final PackageName rootPackageName;
  private final ObjectName contextName;
  private final SupportiveEntityRepositoryImplGenerator supportiveEntityRepositoryImplGenerator;
  private final SupportiveEntityInitializationGenerator supportiveEntityInitializationGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new In memory metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param supportiveEntityRepositoryImplGenerator the supportive entity repository impl generator
   * @param supportiveEntityInitializationGenerator the supportive entity initialization generator
   */
  public InMemoryMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ObjectName contextName,
      SupportiveEntityRepositoryImplGenerator supportiveEntityRepositoryImplGenerator,
      SupportiveEntityInitializationGenerator supportiveEntityInitializationGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.supportiveEntityRepositoryImplGenerator = supportiveEntityRepositoryImplGenerator;
    this.supportiveEntityInitializationGenerator = supportiveEntityInitializationGenerator;
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
  public ObjectName getContextName() {
    return contextName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    supportiveEntities(modelRepositories);
  }

  private void supportiveEntities(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, SupportiveEntityMetamodelRepository.class)
        .getItems()
        .stream()
        .forEach(
            supportiveEntity -> {
              SupportiveEntityRepository supportiveEntityRepository =
                  new SupportiveEntityRepository(
                      new ObjectName(
                          String.format(
                              "%sRepository", supportiveEntity.getObjectName().getText())),
                      supportiveEntity.getPackageName(),
                      supportiveEntity);

              SupportiveEntityRepositoryImpl supportiveEntityRepositoryImpl =
                  new SupportiveEntityRepositoryImpl(
                      new ObjectName(
                          String.format(
                              "%sRepositoryImpl", supportiveEntity.getObjectName().getText())),
                      supportiveEntity.getPackageName(),
                      supportiveEntityRepository);

              supportiveEntityRepositoryImplGenerator.generate(
                  supportiveEntityRepositoryImpl,
                  supportiveEntityRepositoryImplExportInfo(
                      getGenerationPath(), supportiveEntityRepositoryImpl));

              SupportiveEntityInitialization supportiveEntityInitialization =
                  new SupportiveEntityInitialization(
                      new ObjectName(
                          String.format(
                              "%sInitialization", supportiveEntity.getObjectName().getText())),
                      supportiveEntityRepository.getPackageName(),
                      supportiveEntityRepository);
              supportiveEntityInitializationGenerator.generate(
                  supportiveEntityInitialization,
                  supportiveEntityInitializationExportInfo(
                      getGenerationPath(), supportiveEntityInitialization));
            });
  }

  private ExportInfo supportiveEntityRepositoryImplExportInfo(
      Path generationPath, SupportiveEntityRepositoryImpl supportiveEntityRepositoryImpl) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            supportiveEntityRepositoryImpl.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(supportiveEntityRepositoryImpl.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo supportiveEntityInitializationExportInfo(
      Path generationPath, SupportiveEntityInitialization supportiveEntityInitialization) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            supportiveEntityInitialization.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(supportiveEntityInitialization.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
