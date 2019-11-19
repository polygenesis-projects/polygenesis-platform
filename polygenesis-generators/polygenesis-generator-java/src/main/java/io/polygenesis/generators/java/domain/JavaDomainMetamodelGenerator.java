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

package io.polygenesis.generators.java.domain;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityExporter;
import io.polygenesis.generators.java.domain.aggregateentity.id.AggregateEntityIdExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootGenerator;
import io.polygenesis.generators.java.domain.aggregateroot.id.AggregateRootIdExporter;
import io.polygenesis.generators.java.domain.domainevent.DomainEventExporter;
import io.polygenesis.generators.java.domain.domainmessage.data.DomainMessageData;
import io.polygenesis.generators.java.domain.domainmessage.data.DomainMessageDataGenerator;
import io.polygenesis.generators.java.domain.domainmessage.datarepository.DomainMessageDataRepository;
import io.polygenesis.generators.java.domain.domainmessage.datarepository.DomainMessageDataRepositoryGenerator;
import io.polygenesis.generators.java.domain.domainmessage.publisheddata.DomainMessagePublishedData;
import io.polygenesis.generators.java.domain.domainmessage.publisheddata.DomainMessagePublishedDataGenerator;
import io.polygenesis.generators.java.domain.domainmessage.publisheddatarepository.DomainMessagePublishedDataRepository;
import io.polygenesis.generators.java.domain.domainmessage.publisheddatarepository.DomainMessagePublishedDataRepositoryGenerator;
import io.polygenesis.generators.java.domain.persistence.PersistenceExporter;
import io.polygenesis.generators.java.domain.projection.exporter.ProjectionExporter;
import io.polygenesis.generators.java.domain.projection.exporter.ProjectionIdExporter;
import io.polygenesis.generators.java.domain.projection.exporter.ProjectionRepositoryExporter;
import io.polygenesis.generators.java.domain.service.DomainServiceGenerator;
import io.polygenesis.generators.java.domain.supportiveentity.entity.SupportiveEntityGenerator;
import io.polygenesis.generators.java.domain.supportiveentity.id.SupportiveEntityId;
import io.polygenesis.generators.java.domain.supportiveentity.id.SupportiveEntityIdGenerator;
import io.polygenesis.generators.java.domain.supportiveentity.repository.SupportiveEntityRepository;
import io.polygenesis.generators.java.domain.supportiveentity.repository.SupportiveEntityRepositoryGenerator;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.BaseDomainEntity;
import io.polygenesis.models.domain.DomainMetamodelRepository;
import io.polygenesis.models.domain.DomainService;
import io.polygenesis.models.domain.DomainServiceRepository;
import io.polygenesis.models.domain.ProjectionMetamodelRepository;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.models.domain.SupportiveEntity;
import io.polygenesis.models.domain.SupportiveEntityMetamodelRepository;
import io.polygenesis.models.domain.ValueObject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Java domain generator.
 *
 * @author Christos Tsakostas
 */
public class JavaDomainMetamodelGenerator extends AbstractMetamodelGenerator {

  private final String tablePrefix;

  private final PackageName rootPackageName;
  private final ContextName contextName;

  private final AggregateRootGenerator aggregateRootGenerator;
  private final AggregateRootIdExporter aggregateRootIdExporter;
  private final AggregateEntityExporter aggregateEntityExporter;
  private final AggregateEntityIdExporter aggregateEntityIdExporter;
  private final ValueObjectGenerator valueObjectGenerator;
  private final DomainEventExporter domainEventExporter;
  private final PersistenceExporter persistenceExporter;
  private final DomainServiceGenerator domainServiceGenerator;
  private final SupportiveEntityGenerator supportiveEntityGenerator;
  private final SupportiveEntityIdGenerator supportiveEntityIdGenerator;
  private final SupportiveEntityRepositoryGenerator supportiveEntityRepositoryGenerator;
  private final ConstantsExporter constantsExporter;
  private final ProjectionExporter projectionExporter;
  private final ProjectionIdExporter projectionIdExporter;
  private final ProjectionRepositoryExporter projectionRepositoryExporter;

  private final DomainMessageDataGenerator domainMessageDataGenerator;
  private final DomainMessageDataRepositoryGenerator domainMessageDataRepositoryGenerator;
  private final DomainMessagePublishedDataGenerator domainMessagePublishedDataGenerator;
  private final DomainMessagePublishedDataRepositoryGenerator
      domainMessagePublishedDataRepositoryGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java domain metamodel generator.
   *
   * @param generationPath the generation path
   * @param tablePrefix the table prefix
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param aggregateRootGenerator the aggregate root exporter
   * @param aggregateRootIdExporter the aggregate root id exporter
   * @param aggregateEntityExporter the aggregate entity exporter
   * @param aggregateEntityIdExporter the aggregate entity id exporter
   * @param valueObjectGenerator the value object generator
   * @param domainEventExporter the domain event exporter
   * @param persistenceExporter the persistence exporter
   * @param domainServiceGenerator the domain service generator
   * @param supportiveEntityGenerator the supportive entity exporter
   * @param supportiveEntityIdGenerator the supportive entity id exporter
   * @param supportiveEntityRepositoryGenerator the supportive entity repository generator
   * @param constantsExporter the constants exporter
   * @param projectionExporter the projection exporter
   * @param projectionIdExporter the projection id exporter
   * @param projectionRepositoryExporter the projection repository exporter
   * @param domainMessageDataGenerator the domain message data generator
   * @param domainMessageDataRepositoryGenerator the domain message data repository generator
   * @param domainMessagePublishedDataGenerator the domain message published data generator
   * @param domainMessagePublishedDataRepositoryGenerator the domain message published data
   *     repository generator
   */
  public JavaDomainMetamodelGenerator(
      Path generationPath,
      String tablePrefix,
      PackageName rootPackageName,
      ContextName contextName,
      AggregateRootGenerator aggregateRootGenerator,
      AggregateRootIdExporter aggregateRootIdExporter,
      AggregateEntityExporter aggregateEntityExporter,
      AggregateEntityIdExporter aggregateEntityIdExporter,
      ValueObjectGenerator valueObjectGenerator,
      DomainEventExporter domainEventExporter,
      PersistenceExporter persistenceExporter,
      DomainServiceGenerator domainServiceGenerator,
      SupportiveEntityGenerator supportiveEntityGenerator,
      SupportiveEntityIdGenerator supportiveEntityIdGenerator,
      SupportiveEntityRepositoryGenerator supportiveEntityRepositoryGenerator,
      ConstantsExporter constantsExporter,
      ProjectionExporter projectionExporter,
      ProjectionIdExporter projectionIdExporter,
      ProjectionRepositoryExporter projectionRepositoryExporter,
      DomainMessageDataGenerator domainMessageDataGenerator,
      DomainMessageDataRepositoryGenerator domainMessageDataRepositoryGenerator,
      DomainMessagePublishedDataGenerator domainMessagePublishedDataGenerator,
      DomainMessagePublishedDataRepositoryGenerator domainMessagePublishedDataRepositoryGenerator) {
    super(generationPath);
    this.tablePrefix = tablePrefix;
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.aggregateRootGenerator = aggregateRootGenerator;
    this.aggregateRootIdExporter = aggregateRootIdExporter;
    this.aggregateEntityExporter = aggregateEntityExporter;
    this.aggregateEntityIdExporter = aggregateEntityIdExporter;
    this.valueObjectGenerator = valueObjectGenerator;
    this.domainEventExporter = domainEventExporter;
    this.persistenceExporter = persistenceExporter;
    this.domainServiceGenerator = domainServiceGenerator;
    this.supportiveEntityGenerator = supportiveEntityGenerator;
    this.supportiveEntityIdGenerator = supportiveEntityIdGenerator;
    this.supportiveEntityRepositoryGenerator = supportiveEntityRepositoryGenerator;
    this.constantsExporter = constantsExporter;
    this.projectionExporter = projectionExporter;
    this.projectionIdExporter = projectionIdExporter;
    this.projectionRepositoryExporter = projectionRepositoryExporter;
    this.domainMessageDataGenerator = domainMessageDataGenerator;
    this.domainMessageDataRepositoryGenerator = domainMessageDataRepositoryGenerator;
    this.domainMessagePublishedDataGenerator = domainMessagePublishedDataGenerator;
    this.domainMessagePublishedDataRepositoryGenerator =
        domainMessagePublishedDataRepositoryGenerator;
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

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, DomainMetamodelRepository.class)
        .getItems()
        .forEach(
            aggregateRoot -> {
              aggregateRootGenerator.generate(
                  aggregateRoot,
                  aggregateRootExportInfo(getGenerationPath(), aggregateRoot),
                  getRootPackageName());

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

                        // Value Objects
                        aggregateEntity
                            .getProperties()
                            .forEach(
                                aggregateEntityProperty -> {
                                  if (aggregateEntityProperty
                                      .getPropertyType()
                                      .equals(PropertyType.VALUE_OBJECT)) {
                                    valueObjectGenerator.generate(
                                        (ValueObject) aggregateEntityProperty,
                                        valueObjectExportInfo(
                                            getGenerationPath(),
                                            (ValueObject) aggregateEntityProperty));
                                  }
                                });
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

                        // Value Objects
                        aggregateEntity
                            .getProperties()
                            .forEach(
                                aggregateEntityProperty -> {
                                  if (aggregateEntityProperty
                                      .getPropertyType()
                                      .equals(PropertyType.VALUE_OBJECT)) {
                                    valueObjectGenerator.generate(
                                        (ValueObject) aggregateEntityProperty,
                                        valueObjectExportInfo(
                                            getGenerationPath(),
                                            (ValueObject) aggregateEntityProperty));
                                  }
                                });
                      });

              // Value Objects
              aggregateRoot
                  .getProperties()
                  .forEach(
                      property -> {
                        if (property.getPropertyType().equals(PropertyType.VALUE_OBJECT)) {
                          valueObjectGenerator.generate(
                              (ValueObject) property,
                              valueObjectExportInfo(getGenerationPath(), (ValueObject) property));
                        }
                      });
            });

    // DOMAIN SERVICES
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, DomainServiceRepository.class)
        .getItems()
        .forEach(
            domainService -> {
              domainServiceGenerator.generate(
                  domainService, domainServiceExportInfo(getGenerationPath(), domainService));

              // Argument Value Objects
              Set<Data> allArguments =
                  domainService
                      .getDomainServiceMethods()
                      .stream()
                      .flatMap(
                          domainServiceMethod ->
                              domainServiceMethod.getFunction().getArguments().stream())
                      .map(argument -> argument.getData())
                      .collect(Collectors.toSet());

              allArguments.forEach(
                  data -> {
                    if (data.isDataGroup()) {
                      ValueObject valueObject = new ValueObject(data.getAsDataObject());
                      valueObjectGenerator.generate(
                          valueObject, valueObjectExportInfo(getGenerationPath(), valueObject));
                    }
                  });
            });

    // SUPPORTIVE ENTITIES
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, SupportiveEntityMetamodelRepository.class)
        .getItems()
        .forEach(
            supportiveEntity -> {
              supportiveEntityGenerator.generate(
                  supportiveEntity,
                  supportiveEntityExportInfo(getGenerationPath(), supportiveEntity));

              SupportiveEntityId supportiveEntityId =
                  new SupportiveEntityId(
                      new ObjectName(
                          String.format("%sId", supportiveEntity.getObjectName().getText())),
                      supportiveEntity.getPackageName());
              supportiveEntityIdGenerator.generate(
                  supportiveEntityId,
                  supportiveEntityIdExportInfo(getGenerationPath(), supportiveEntityId));

              SupportiveEntityRepository supportiveEntityRepository =
                  new SupportiveEntityRepository(
                      new ObjectName(
                          String.format(
                              "%sRepository", supportiveEntity.getObjectName().getText())),
                      supportiveEntity.getPackageName(),
                      supportiveEntity);

              supportiveEntityRepositoryGenerator.generate(
                  supportiveEntityRepository,
                  supportiveEntityRepositoryExportInfo(
                      getGenerationPath(), supportiveEntityRepository));

              // Value Objects
              supportiveEntity
                  .getProperties()
                  .forEach(
                      supportiveEntityProperty -> {
                        if (supportiveEntityProperty
                            .getPropertyType()
                            .equals(PropertyType.VALUE_OBJECT)) {
                          valueObjectGenerator.generate(
                              (ValueObject) supportiveEntityProperty,
                              valueObjectExportInfo(
                                  getGenerationPath(), (ValueObject) supportiveEntityProperty));
                        }
                      });
            });

    constantsExporter.export(getGenerationPath(), getRootPackageName(), getTablePrefix());

    generateProjections(modelRepositories);

    // =============================================================================================
    // DOMAIN MESSAGE

    DomainMessageData domainMessageData = makeDomainMessageData();
    domainMessageDataGenerator.generate(
        domainMessageData,
        domainMessageDataExportInfo(getGenerationPath(), domainMessageData),
        contextName);

    DomainMessageDataRepository domainMessageDataRepository = makeDomainMessageDataRepository();
    domainMessageDataRepositoryGenerator.generate(
        domainMessageDataRepository,
        domainMessageDataRepositoryExportInfo(getGenerationPath(), domainMessageDataRepository),
        contextName);

    DomainMessagePublishedData domainMessagePublishedData = makeDomainMessagePublishedData();
    domainMessagePublishedDataGenerator.generate(
        domainMessagePublishedData,
        domainMessagePublishedDataExportInfo(getGenerationPath(), domainMessagePublishedData),
        contextName);

    DomainMessagePublishedDataRepository domainMessagePublishedDataRepository =
        makeDomainMessagePublishedDataRepository();
    domainMessagePublishedDataRepositoryGenerator.generate(
        domainMessagePublishedDataRepository,
        domainMessagePublishedDataRepositoryExportInfo(
            getGenerationPath(), domainMessagePublishedDataRepository),
        contextName);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void generateProjections(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, ProjectionMetamodelRepository.class)
        .getItems()
        .forEach(
            projection -> {
              projectionIdExporter.export(getGenerationPath(), projection);
              projectionExporter.export(getGenerationPath(), projection, getRootPackageName());
              if (projection.getPersistence() != null) {
                projectionRepositoryExporter.export(
                    getGenerationPath(), projection.getPersistence());
              }
              exportValueObjects(projection);
            });
  }

  private void exportValueObjects(BaseDomainEntity baseDomainEntity) {
    baseDomainEntity
        .getProperties()
        .forEach(
            property -> {
              if (property.getPropertyType().equals(PropertyType.VALUE_OBJECT)) {
                valueObjectGenerator.generate(
                    (ValueObject) property,
                    valueObjectExportInfo(getGenerationPath(), (ValueObject) property));
              }
            });
  }

  private ExportInfo domainServiceExportInfo(Path generationPath, DomainService domainService) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainService.getPackageName().toPath().toString()),
        TextConverter.toUpperCamel(domainService.getObjectName().getText())
            + FolderFileConstants.JAVA_POSTFIX);
  }

  // ===============================================================================================
  // PRIVATE MESSAGE DATA
  // ===============================================================================================

  private DomainMessageData makeDomainMessageData() {
    return new DomainMessageData(
        new ObjectName(
            String.format(
                "%sDomainMessageData", TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessageDataRepository makeDomainMessageDataRepository() {
    return new DomainMessageDataRepository(
        new ObjectName(
            String.format(
                "%sDomainMessageDataRepository",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessagePublishedData makeDomainMessagePublishedData() {
    return new DomainMessagePublishedData(
        new ObjectName(
            String.format(
                "%sDomainMessagePublishedData", TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessagePublishedDataRepository makeDomainMessagePublishedDataRepository() {
    return new DomainMessagePublishedDataRepository(
        new ObjectName(
            String.format(
                "%sDomainMessagePublishedDataRepository",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private ExportInfo domainMessageDataExportInfo(
      Path generationPath, DomainMessageData domainMessageData) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessageData.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessageData.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo domainMessageDataRepositoryExportInfo(
      Path generationPath, DomainMessageDataRepository domainMessageDataRepository) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessageDataRepository.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessageDataRepository.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo domainMessagePublishedDataExportInfo(
      Path generationPath, DomainMessagePublishedData domainMessagePublishedData) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessagePublishedData.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessagePublishedData.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo domainMessagePublishedDataRepositoryExportInfo(
      Path generationPath,
      DomainMessagePublishedDataRepository domainMessagePublishedDataRepository) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessagePublishedDataRepository.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(
                domainMessagePublishedDataRepository.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo valueObjectExportInfo(Path generationPath, ValueObject valueObject) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            valueObject.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(valueObject.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo supportiveEntityExportInfo(
      Path generationPath, SupportiveEntity supportiveEntity) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            supportiveEntity.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(supportiveEntity.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo aggregateRootExportInfo(Path generationPath, AggregateRoot aggregateRoot) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            aggregateRoot.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(aggregateRoot.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo supportiveEntityIdExportInfo(
      Path generationPath, SupportiveEntityId supportiveEntityId) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            supportiveEntityId.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(supportiveEntityId.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo supportiveEntityRepositoryExportInfo(
      Path generationPath, SupportiveEntityRepository supportiveEntityRepository) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            supportiveEntityRepository.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(supportiveEntityRepository.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
