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

package io.polygenesis.generators.java.exporters.domain;

import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.exporters.domain.aggregateentity.AggregateEntityExporter;
import io.polygenesis.generators.java.exporters.domain.aggregateentity.AggregateEntityIdExporter;
import io.polygenesis.generators.java.exporters.domain.aggregateroot.AggregateRootExporter;
import io.polygenesis.generators.java.exporters.domain.aggregateroot.AggregateRootIdExporter;
import io.polygenesis.generators.java.exporters.domain.domainevent.DomainEventExporter;
import io.polygenesis.generators.java.exporters.domain.persistence.PersistenceExporter;
import io.polygenesis.generators.java.exporters.domain.service.DomainServiceExporter;
import io.polygenesis.generators.java.exporters.domain.supportiveentity.SupportiveEntityExporter;
import io.polygenesis.generators.java.exporters.domain.valueobject.ValueObjectExporter;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.DomainMetamodelRepository;
import io.polygenesis.models.domain.DomainServiceRepository;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.models.domain.SupportiveEntityMetamodelRepository;
import io.polygenesis.models.domain.ValueObject;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Java domain generator.
 *
 * @author Christos Tsakostas
 */
public class JavaDomainGenerator extends AbstractGenerator {

  private final String tablePrefix;

  private final PackageName rootPackageName;
  private final AggregateRootExporter aggregateRootExporter;
  private final AggregateRootIdExporter aggregateRootIdExporter;
  private final AggregateEntityExporter aggregateEntityExporter;
  private final AggregateEntityIdExporter aggregateEntityIdExporter;
  private final ValueObjectExporter valueObjectExporter;
  private final DomainEventExporter domainEventExporter;
  private final PersistenceExporter persistenceExporter;
  private final DomainServiceExporter domainServiceExporter;
  private final SupportiveEntityExporter supportiveEntityExporter;
  private final ConstantsExporter constantsExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java domain generator.
   *
   * @param generationPath the generation path
   * @param tablePrefix the table prefix
   * @param rootPackageName the root package name
   * @param aggregateRootExporter the aggregate root exporter
   * @param aggregateRootIdExporter the aggregate root id exporter
   * @param aggregateEntityExporter the aggregate entity exporter
   * @param aggregateEntityIdExporter the aggregate entity id exporter
   * @param valueObjectExporter the value object exporter
   * @param domainEventExporter the domain event exporter
   * @param persistenceExporter the persistence exporter
   * @param domainServiceExporter the domain service exporter
   * @param supportiveEntityExporter the supportive entity exporter
   * @param constantsExporter the constants exporter
   */
  public JavaDomainGenerator(
      Path generationPath,
      String tablePrefix,
      PackageName rootPackageName,
      AggregateRootExporter aggregateRootExporter,
      AggregateRootIdExporter aggregateRootIdExporter,
      AggregateEntityExporter aggregateEntityExporter,
      AggregateEntityIdExporter aggregateEntityIdExporter,
      ValueObjectExporter valueObjectExporter,
      DomainEventExporter domainEventExporter,
      PersistenceExporter persistenceExporter,
      DomainServiceExporter domainServiceExporter,
      SupportiveEntityExporter supportiveEntityExporter,
      ConstantsExporter constantsExporter) {
    super(generationPath);
    this.tablePrefix = tablePrefix;
    this.rootPackageName = rootPackageName;
    this.aggregateRootExporter = aggregateRootExporter;
    this.aggregateRootIdExporter = aggregateRootIdExporter;
    this.aggregateEntityExporter = aggregateEntityExporter;
    this.aggregateEntityIdExporter = aggregateEntityIdExporter;
    this.valueObjectExporter = valueObjectExporter;
    this.domainEventExporter = domainEventExporter;
    this.persistenceExporter = persistenceExporter;
    this.domainServiceExporter = domainServiceExporter;
    this.supportiveEntityExporter = supportiveEntityExporter;
    this.constantsExporter = constantsExporter;
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
   * Gets table prefix.
   *
   * @return the table prefix
   */
  public String getTablePrefix() {
    return tablePrefix;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public void generate(Set<MetamodelRepository> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, DomainMetamodelRepository.class)
        .getItems()
        .forEach(
            aggregateRoot -> {
              aggregateRootExporter.export(
                  getGenerationPath(), aggregateRoot, getRootPackageName());

              aggregateRootIdExporter.export(getGenerationPath(), aggregateRoot);

              if (aggregateRoot instanceof AggregateRootPersistable) {
                persistenceExporter.export(
                    getGenerationPath(),
                    ((AggregateRootPersistable) aggregateRoot).getPersistence());
              }

              domainEventExporter.export(getGenerationPath(), null);

              // Aggregate Entities
              aggregateRoot
                  .getProperties()
                  .stream()
                  .filter(
                      property -> property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY))
                  .forEach(
                      property -> {
                        AggregateEntity aggregateEntity = (AggregateEntity) property;
                        aggregateEntityIdExporter.export(getGenerationPath(), aggregateEntity);

                        aggregateEntityExporter.export(
                            getGenerationPath(),
                            (AggregateEntity) property,
                            getRootPackageName(),
                            aggregateRoot);
                      });

              // Aggregate Entities From Collections
              aggregateRoot
                  .getProperties()
                  .stream()
                  .filter(
                      property ->
                          property
                              .getPropertyType()
                              .equals(PropertyType.AGGREGATE_ENTITY_COLLECTION))
                  .forEach(
                      property -> {
                        AggregateEntity aggregateEntity =
                            ((AggregateEntityCollection) property).getAggregateEntity();
                        aggregateEntityIdExporter.export(getGenerationPath(), aggregateEntity);

                        aggregateEntityExporter.export(
                            getGenerationPath(),
                            aggregateEntity,
                            getRootPackageName(),
                            aggregateRoot);
                      });

              aggregateRoot
                  .getProperties()
                  .forEach(
                      property -> {
                        if (property.getPropertyType().equals(PropertyType.VALUE_OBJECT)) {
                          valueObjectExporter.export(getGenerationPath(), (ValueObject) property);
                        }
                      });
            });

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, DomainServiceRepository.class)
        .getItems()
        .forEach(
            domainService -> {
              domainServiceExporter.export(getGenerationPath(), domainService);
            });

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, SupportiveEntityMetamodelRepository.class)
        .getItems()
        .forEach(
            helperEntity -> {
              supportiveEntityExporter.export(
                  getGenerationPath(), helperEntity, getRootPackageName());
            });

    constantsExporter.export(getGenerationPath(), getRootPackageName(), getTablePrefix());
  }
}
