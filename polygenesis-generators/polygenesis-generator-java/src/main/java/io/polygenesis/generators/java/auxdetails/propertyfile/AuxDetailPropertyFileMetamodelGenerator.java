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

package io.polygenesis.generators.java.auxdetails.propertyfile;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.auxdetails.propertyfile.propertyfile.PropertyFileImpl;
import io.polygenesis.generators.java.auxdetails.propertyfile.propertyfile.PropertyFileImplGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Aux detail property file metamodel generator.
 *
 * @author Christos Tsakostas
 */
public class AuxDetailPropertyFileMetamodelGenerator extends AbstractMetamodelGenerator {

  private final ContextName contextName;
  private final PackageName rootPackageName;
  private final PropertyFileImplGenerator propertyFileImplGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aux detail property file metamodel generator.
   *
   * @param generationPath the generation path
   * @param contextName the context name
   * @param rootPackageName the root package name
   * @param propertyFileImplGenerator the property file impl generator
   */
  public AuxDetailPropertyFileMetamodelGenerator(
      Path generationPath,
      ContextName contextName,
      PackageName rootPackageName,
      PropertyFileImplGenerator propertyFileImplGenerator) {
    super(generationPath);
    this.contextName = contextName;
    this.rootPackageName = rootPackageName;
    this.propertyFileImplGenerator = propertyFileImplGenerator;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ContextName getContextName() {
    return contextName;
  }

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> metamodelRepositories) {
    PropertyFileImpl propertyFileImpl =
        new PropertyFileImpl(getContextName(), getRootPackageName());

    propertyFileImplGenerator.generate(
        propertyFileImpl,
        propertyFileImplExportInfo(getGenerationPath(), propertyFileImpl),
        getRootPackageName(),
        getContextName());
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo propertyFileImplExportInfo(
      Path generationPath, PropertyFileImpl propertyFileImpl) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            propertyFileImpl.getRootPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(propertyFileImpl.getName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
