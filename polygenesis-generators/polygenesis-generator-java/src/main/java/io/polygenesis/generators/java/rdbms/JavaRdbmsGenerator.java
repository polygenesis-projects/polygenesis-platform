/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.generators.java.rdbms;

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.data.PackageName;
import io.polygenesis.models.domain.DomainModelRepository;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Java domain generator.
 *
 * @author Christos Tsakostas
 */
public class JavaRdbmsGenerator extends AbstractGenerator {

  private final PackageName rootPackageName;
  private final Name contextName;
  private final DomainMessageDataExporter domainMessageDataExporter;
  private final DomainMessageDataConverterExporter domainMessageDataConverterExporter;
  private final DomainMessageDataRepositoryExporter domainMessageDataRepositoryExporter;
  private final PersistenceImplExporter persistenceImplExporter;
  private final SpringDataRepositoryExporter springDataRepositoryExporter;
  private final RdbmsTestExporter rdbmsTestExporter;
  private final RdbmsTestConfigExporter rdbmsTestConfigExporter;

  private DomainModelRepository domainModelRepository;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java rdbms generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param domainMessageDataExporter the domain message data exporter
   * @param domainMessageDataConverterExporter the domain message data converter exporter
   * @param domainMessageDataRepositoryExporter the domain message data repository exporter
   * @param persistenceImplExporter the persistence impl exporter
   * @param springDataRepositoryExporter the spring data repository exporter
   * @param rdbmsTestExporter the rdbms test exporter
   * @param rdbmsTestConfigExporter the rdbms test config exporter
   */
  public JavaRdbmsGenerator(
      Path generationPath,
      PackageName rootPackageName,
      Name contextName,
      DomainMessageDataExporter domainMessageDataExporter,
      DomainMessageDataConverterExporter domainMessageDataConverterExporter,
      DomainMessageDataRepositoryExporter domainMessageDataRepositoryExporter,
      PersistenceImplExporter persistenceImplExporter,
      SpringDataRepositoryExporter springDataRepositoryExporter,
      RdbmsTestExporter rdbmsTestExporter,
      RdbmsTestConfigExporter rdbmsTestConfigExporter) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.domainMessageDataExporter = domainMessageDataExporter;
    this.domainMessageDataConverterExporter = domainMessageDataConverterExporter;
    this.domainMessageDataRepositoryExporter = domainMessageDataRepositoryExporter;
    this.persistenceImplExporter = persistenceImplExporter;
    this.springDataRepositoryExporter = springDataRepositoryExporter;
    this.rdbmsTestExporter = rdbmsTestExporter;
    this.rdbmsTestConfigExporter = rdbmsTestConfigExporter;
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
  public Name getContextName() {
    return contextName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<ModelRepository> modelRepositories) {
    initializeModelRepositories(modelRepositories);

    domainMessageDataExporter.export(getGenerationPath(), getRootPackageName(), getContextName());
    domainMessageDataConverterExporter.export(
        getGenerationPath(), getRootPackageName(), getContextName());
    domainMessageDataRepositoryExporter.export(
        getGenerationPath(), getRootPackageName(), getContextName());
    rdbmsTestExporter.export(getGenerationPath(), getRootPackageName());
    rdbmsTestConfigExporter.export(getGenerationPath(), getRootPackageName());

    domainModelRepository
        .getAggregateRoots()
        .forEach(
            aggregateRoot -> {
              persistenceImplExporter.export(
                  getGenerationPath(),
                  aggregateRoot.getPersistence(),
                  getRootPackageName(),
                  getContextName());

              springDataRepositoryExporter.export(
                  getGenerationPath(), aggregateRoot.getPersistence());
            });
  }

  /**
   * Initialize model repositories.
   *
   * @param modelRepositories the model repositories
   */
  private void initializeModelRepositories(Set<ModelRepository> modelRepositories) {
    domainModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, DomainModelRepository.class);
  }
}
