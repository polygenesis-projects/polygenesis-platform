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

package io.polygenesis.generators.java.aux;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.aux.propertyfile.PropertyFile;
import io.polygenesis.generators.java.aux.propertyfile.PropertyFileGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Aux metamodel generator.
 *
 * @author Christos Tsakostas
 */
public class AuxMetamodelGenerator extends AbstractMetamodelGenerator {

  private final ContextName contextName;
  private final PackageName rootPackageName;
  private final PropertyFileGenerator propertyFileGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aux metamodel generator.
   *
   * @param generationPath the generation path
   * @param contextName the context name
   * @param rootPackageName the root package name
   * @param propertyFileGenerator the property file generator
   */
  public AuxMetamodelGenerator(
      Path generationPath,
      ContextName contextName,
      PackageName rootPackageName,
      PropertyFileGenerator propertyFileGenerator) {
    super(generationPath);
    this.contextName = contextName;
    this.rootPackageName = rootPackageName;
    this.propertyFileGenerator = propertyFileGenerator;
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

    // SERVICE ASPECT
    PropertyFile propertyFile = new PropertyFile(getContextName(), getRootPackageName());

    propertyFileGenerator.generate(
        propertyFile,
        serviceAspectExportInfo(getGenerationPath(), propertyFile),
        getRootPackageName());
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo serviceAspectExportInfo(Path generationPath, PropertyFile propertyFile) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            propertyFile.getRootPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(propertyFile.getName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
