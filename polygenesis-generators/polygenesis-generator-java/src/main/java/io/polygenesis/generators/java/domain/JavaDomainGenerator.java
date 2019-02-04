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

package io.polygenesis.generators.java.domain;

import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootIdExporter;
import io.polygenesis.generators.java.domain.domainevent.DomainEventExporter;
import io.polygenesis.generators.java.domain.persistence.PersistenceExporter;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectExporter;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.DomainModelRepository;
import io.polygenesis.models.domain.ValueObject;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Java domain generator.
 *
 * @author Christos Tsakostas
 */
public class JavaDomainGenerator extends AbstractGenerator {

  private final PackageName rootPackageName;
  private final AggregateRootExporter aggregateRootExporter;
  private final AggregateRootIdExporter aggregateRootIdExporter;
  private final AggregateEntityExporter aggregateEntityExporter;
  private final ValueObjectExporter valueObjectExporter;
  private final DomainEventExporter domainEventExporter;
  private final PersistenceExporter persistenceExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java domain generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param aggregateRootExporter the aggregate root exporter
   * @param aggregateRootIdExporter the aggregate root id exporter
   * @param aggregateEntityExporter the aggregate entity exporter
   * @param valueObjectExporter the value object exporter
   * @param domainEventExporter the domain event exporter
   * @param persistenceExporter the persistence exporter
   */
  public JavaDomainGenerator(
      Path generationPath,
      PackageName rootPackageName,
      AggregateRootExporter aggregateRootExporter,
      AggregateRootIdExporter aggregateRootIdExporter,
      AggregateEntityExporter aggregateEntityExporter,
      ValueObjectExporter valueObjectExporter,
      DomainEventExporter domainEventExporter,
      PersistenceExporter persistenceExporter) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.aggregateRootExporter = aggregateRootExporter;
    this.aggregateRootIdExporter = aggregateRootIdExporter;
    this.aggregateEntityExporter = aggregateEntityExporter;
    this.valueObjectExporter = valueObjectExporter;
    this.domainEventExporter = domainEventExporter;
    this.persistenceExporter = persistenceExporter;
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

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<ModelRepository> modelRepositories) {
    CoreRegistry.getModelRepositoryResolver()
        .resolve(modelRepositories, DomainModelRepository.class)
        .getAggregateRoots()
        .forEach(
            aggregateRoot -> {
              aggregateRootExporter.export(
                  getGenerationPath(), aggregateRoot, getRootPackageName());
              aggregateRootIdExporter.export(getGenerationPath(), aggregateRoot);
              persistenceExporter.export(getGenerationPath(), aggregateRoot.getPersistence());
              domainEventExporter.export(getGenerationPath(), null);

              aggregateRoot
                  .getProperties()
                  .forEach(
                      property -> {
                        if (property instanceof ValueObject) {
                          valueObjectExporter.export(getGenerationPath(), (ValueObject) property);
                        } else if (property instanceof AggregateEntity) {
                          aggregateEntityExporter.export(
                              getGenerationPath(), (AggregateEntity) property);
                        }
                      });
            });
  }
}
