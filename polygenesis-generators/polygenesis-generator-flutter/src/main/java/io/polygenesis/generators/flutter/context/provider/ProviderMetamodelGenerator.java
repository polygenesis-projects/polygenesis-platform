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

package io.polygenesis.generators.flutter.context.provider;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.flutter.DartFolderFileConstants;
import io.polygenesis.generators.flutter.context.provider.collection.ProviderCollectionGenerator;
import io.polygenesis.generators.flutter.context.provider.detail.ProviderDetailGenerator;
import io.polygenesis.metamodels.stateprovider.Provider;
import io.polygenesis.metamodels.stateprovider.ProviderMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Provider metamodel generator.
 *
 * @author Christos Tsakostas
 */
public class ProviderMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final ProviderCollectionGenerator providerCollectionGenerator;
  private final ProviderDetailGenerator providerDetailGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Provider metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param providerCollectionGenerator the provider collection generator
   * @param providerDetailGenerator the provider detail generator
   */
  public ProviderMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      ProviderCollectionGenerator providerCollectionGenerator,
      ProviderDetailGenerator providerDetailGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.providerCollectionGenerator = providerCollectionGenerator;
    this.providerDetailGenerator = providerDetailGenerator;
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
        .resolve(modelRepositories, ProviderMetamodelRepository.class)
        .getItems()
        .forEach(
            provider -> {
              switch (provider.getProviderType()) {
                case DETAIL:
                  providerDetailGenerator.generate(
                      provider, providerExportInfo(getGenerationPath(), provider));
                  break;
                case COLLECTION:
                  providerCollectionGenerator.generate(
                      provider, providerExportInfo(getGenerationPath(), provider));
                  break;
                default:
                  throw new IllegalStateException(
                      String.format("Unknown provider type=%s", provider.getProviderType()));
              }
            });
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ExportInfo providerExportInfo(Path generationPath, Provider provider) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            TextConverter.toLowerUnderscore(provider.getFeatureName().getText()),
            DartFolderFileConstants.PROVIDERS),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(provider.getName().getText()),
            DartFolderFileConstants.DART_POSTFIX));
  }
}
