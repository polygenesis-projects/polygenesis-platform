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

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.PassiveFileExporter;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.common.ParameterRepresentationsService;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityGenerator;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityMethodTransformer;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityTransformer;
import io.polygenesis.generators.java.domain.aggregateentity.activity.statemutation.AggregateEntityStateMutationActivityRegistry;
import io.polygenesis.generators.java.domain.aggregateentity.activity.statemutation.AggregateEntityStateMutationMethodTransformer;
import io.polygenesis.generators.java.domain.aggregateentityid.AggregateEntityIdExporter;
import io.polygenesis.generators.java.domain.aggregateentityid.AggregateEntityIdLegacyClassTransformer;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootActivityRegistry;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootGenerator;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootMethodTransformer;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootTransformer;
import io.polygenesis.generators.java.domain.aggregateroot.activity.statemutation.AggregateRootStateMutationActivityRegistry;
import io.polygenesis.generators.java.domain.aggregateroot.activity.statemutation.AggregateRootStateMutationMethodTransformer;
import io.polygenesis.generators.java.domain.aggregaterootid.AggregateRootIdExporter;
import io.polygenesis.generators.java.domain.aggregaterootid.AggregateRootIdLegacyClassTransformer;
import io.polygenesis.generators.java.domain.domainevent.DomainEventGenerator;
import io.polygenesis.generators.java.domain.domainevent.DomainEventMethodTransformer;
import io.polygenesis.generators.java.domain.domainevent.DomainEventTransformer;
import io.polygenesis.generators.java.domain.domainmessage.data.DomainMessageDataGenerator;
import io.polygenesis.generators.java.domain.domainmessage.data.DomainMessageDataMethodTransformer;
import io.polygenesis.generators.java.domain.domainmessage.data.DomainMessageDataTransformer;
import io.polygenesis.generators.java.domain.domainmessage.datarepository.DomainMessageDataRepositoryGenerator;
import io.polygenesis.generators.java.domain.domainmessage.datarepository.DomainMessageDataRepositoryMethodTransformer;
import io.polygenesis.generators.java.domain.domainmessage.datarepository.DomainMessageDataRepositoryTransformer;
import io.polygenesis.generators.java.domain.domainmessage.publisheddata.DomainMessagePublishedDataGenerator;
import io.polygenesis.generators.java.domain.domainmessage.publisheddata.DomainMessagePublishedDataMethodTransformer;
import io.polygenesis.generators.java.domain.domainmessage.publisheddata.DomainMessagePublishedDataTransformer;
import io.polygenesis.generators.java.domain.domainmessage.publisheddatarepository.DomainMessagePublishedDataRepositoryGenerator;
import io.polygenesis.generators.java.domain.domainmessage.publisheddatarepository.DomainMessagePublishedDataRepositoryMethodTransformer;
import io.polygenesis.generators.java.domain.domainmessage.publisheddatarepository.DomainMessagePublishedDataRepositoryTransformer;
import io.polygenesis.generators.java.domain.projection.exporter.ProjectionExporter;
import io.polygenesis.generators.java.domain.projection.exporter.ProjectionIdExporter;
import io.polygenesis.generators.java.domain.projection.exporter.ProjectionRepositoryExporter;
import io.polygenesis.generators.java.domain.projection.transformer.ProjectionIdLegacyClassTransformer;
import io.polygenesis.generators.java.domain.projection.transformer.ProjectionLegacyClassTransformer;
import io.polygenesis.generators.java.domain.projection.transformer.ProjectionRepositoryLegacyInterfaceTransformer;
import io.polygenesis.generators.java.domain.repository.RepositoryGenerator;
import io.polygenesis.generators.java.domain.repository.RepositoryMethodTransformer;
import io.polygenesis.generators.java.domain.repository.RepositoryTransformer;
import io.polygenesis.generators.java.domain.service.DomainServiceGenerator;
import io.polygenesis.generators.java.domain.service.DomainServiceMethodTransformer;
import io.polygenesis.generators.java.domain.service.DomainServiceTransformer;
import io.polygenesis.generators.java.domain.supportiveentity.entity.SupportiveEntityGenerator;
import io.polygenesis.generators.java.domain.supportiveentity.entity.SupportiveEntityMethodTransformer;
import io.polygenesis.generators.java.domain.supportiveentity.entity.SupportiveEntityTransformer;
import io.polygenesis.generators.java.domain.supportiveentity.id.SupportiveEntityIdGenerator;
import io.polygenesis.generators.java.domain.supportiveentity.id.SupportiveEntityIdMethodTransformer;
import io.polygenesis.generators.java.domain.supportiveentity.id.SupportiveEntityIdTransformer;
import io.polygenesis.generators.java.domain.supportiveentity.repository.SupportiveEntityRepositoryGenerator;
import io.polygenesis.generators.java.domain.supportiveentity.repository.SupportiveEntityRepositoryMethodTransformer;
import io.polygenesis.generators.java.domain.supportiveentity.repository.SupportiveEntityRepositoryTransformer;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectGenerator;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectMethodTransformer;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import io.polygenesis.transformers.java.legacy.FunctionToLegacyMethodRepresentationTransformer;
import java.nio.file.Path;

/**
 * The Java Domain MetamodelGenerator Factory creates new instances of {@link
 * JavaDomainMetamodelGenerator}***.
 *
 * @author Christos Tsakostas
 */
public final class JavaDomainMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private static AggregateRootGenerator aggregateRootGenerator;
  private static AggregateRootIdExporter aggregateRootIdExporter;
  private static AggregateEntityGenerator aggregateEntityGenerator;
  private static AggregateEntityIdExporter aggregateEntityIdExporter;
  private static ValueObjectGenerator valueObjectGenerator;
  private static DomainEventGenerator domainEventGenerator;
  private static RepositoryGenerator repositoryGenerator;
  private static DomainServiceGenerator domainServiceGenerator;
  private static SupportiveEntityGenerator supportiveEntityGenerator;
  private static SupportiveEntityIdGenerator supportiveEntityIdGenerator;
  private static SupportiveEntityRepositoryGenerator supportiveEntityRepositoryGenerator;
  private static ConstantsExporter constantsExporter;
  private static ProjectionExporter projectionExporter;
  private static ProjectionIdExporter projectionIdExporter;
  private static ProjectionRepositoryExporter projectionRepositoryExporter;

  private static DomainMessageDataGenerator domainMessageDataGenerator;
  private static DomainMessageDataRepositoryGenerator domainMessageDataRepositoryGenerator;
  private static DomainMessagePublishedDataGenerator domainMessagePublishedDataGenerator;
  private static DomainMessagePublishedDataRepositoryGenerator
      domainMessagePublishedDataRepositoryGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter activeFileExporter = new ActiveFileExporter();
    final Exporter passiveFileExporter = new PassiveFileExporter();
    final DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    aggregateRootGenerator =
        new AggregateRootGenerator(
            new AggregateRootTransformer(
                dataTypeTransformer,
                new AggregateRootMethodTransformer(
                    dataTypeTransformer, new AggregateRootActivityRegistry()),
                new AggregateRootStateMutationMethodTransformer(
                    dataTypeTransformer,
                    new AggregateRootStateMutationActivityRegistry(),
                    new ParameterRepresentationsService(dataTypeTransformer))),
            templateEngine,
            passiveFileExporter);

    AggregateRootIdLegacyClassTransformer aggregateRootIdClassRepresentable =
        new AggregateRootIdLegacyClassTransformer(dataTypeTransformer);

    aggregateRootIdExporter =
        new AggregateRootIdExporter(freemarkerService, aggregateRootIdClassRepresentable);

    aggregateEntityGenerator =
        new AggregateEntityGenerator(
            new AggregateEntityTransformer(
                dataTypeTransformer,
                new AggregateEntityMethodTransformer(dataTypeTransformer),
                new AggregateEntityStateMutationMethodTransformer(
                    dataTypeTransformer,
                    new AggregateEntityStateMutationActivityRegistry(),
                    new ParameterRepresentationsService(dataTypeTransformer))),
            templateEngine,
            passiveFileExporter);

    AggregateEntityIdLegacyClassTransformer aggregateEntityIdClassRepresentable =
        new AggregateEntityIdLegacyClassTransformer(dataTypeTransformer);
    aggregateEntityIdExporter =
        new AggregateEntityIdExporter(freemarkerService, aggregateEntityIdClassRepresentable);

    valueObjectGenerator =
        new ValueObjectGenerator(
            new ValueObjectTransformer(
                dataTypeTransformer, new ValueObjectMethodTransformer(dataTypeTransformer)),
            templateEngine,
            passiveFileExporter);

    domainEventGenerator =
        new DomainEventGenerator(
            new DomainEventTransformer(
                dataTypeTransformer, new DomainEventMethodTransformer(dataTypeTransformer)),
            templateEngine,
            passiveFileExporter);

    repositoryGenerator =
        new RepositoryGenerator(
            new RepositoryTransformer(
                dataTypeTransformer, new RepositoryMethodTransformer(dataTypeTransformer)),
            templateEngine,
            passiveFileExporter);

    domainServiceGenerator =
        new DomainServiceGenerator(
            new DomainServiceTransformer(
                dataTypeTransformer, new DomainServiceMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    supportiveEntityGenerator =
        new SupportiveEntityGenerator(
            new SupportiveEntityTransformer(
                dataTypeTransformer, new SupportiveEntityMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    supportiveEntityIdGenerator =
        new SupportiveEntityIdGenerator(
            new SupportiveEntityIdTransformer(
                dataTypeTransformer, new SupportiveEntityIdMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    supportiveEntityRepositoryGenerator =
        new SupportiveEntityRepositoryGenerator(
            new SupportiveEntityRepositoryTransformer(
                dataTypeTransformer,
                new SupportiveEntityRepositoryMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    constantsExporter = new ConstantsExporter(freemarkerService);

    projectionExporter =
        new ProjectionExporter(
            freemarkerService, new ProjectionLegacyClassTransformer(dataTypeTransformer));

    projectionIdExporter =
        new ProjectionIdExporter(
            freemarkerService, new ProjectionIdLegacyClassTransformer(dataTypeTransformer));

    FunctionToLegacyMethodRepresentationTransformer functionToMethodRepresentationTransformer =
        new FunctionToLegacyMethodRepresentationTransformer(dataTypeTransformer);

    projectionRepositoryExporter =
        new ProjectionRepositoryExporter(
            freemarkerService,
            new ProjectionRepositoryLegacyInterfaceTransformer(
                dataTypeTransformer, functionToMethodRepresentationTransformer));

    domainMessageDataGenerator =
        new DomainMessageDataGenerator(
            new DomainMessageDataTransformer(
                dataTypeTransformer, new DomainMessageDataMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    domainMessageDataRepositoryGenerator =
        new DomainMessageDataRepositoryGenerator(
            new DomainMessageDataRepositoryTransformer(
                dataTypeTransformer,
                new DomainMessageDataRepositoryMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    domainMessagePublishedDataGenerator =
        new DomainMessagePublishedDataGenerator(
            new DomainMessagePublishedDataTransformer(
                dataTypeTransformer,
                new DomainMessagePublishedDataMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    domainMessagePublishedDataRepositoryGenerator =
        new DomainMessagePublishedDataRepositoryGenerator(
            new DomainMessagePublishedDataRepositoryTransformer(
                dataTypeTransformer,
                new DomainMessagePublishedDataRepositoryMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private JavaDomainMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance java api generator.
   *
   * @param generationPath the generation path
   * @param contextName the context name
   * @param rootPackageName the root package name
   * @param tablePrefix the table prefix
   * @return the java api generator
   */
  public static JavaDomainMetamodelGenerator newInstance(
      Path generationPath,
      ContextName contextName,
      PackageName rootPackageName,
      String tablePrefix) {
    return new JavaDomainMetamodelGenerator(
        generationPath,
        tablePrefix,
        rootPackageName,
        contextName,
        aggregateRootGenerator,
        aggregateRootIdExporter,
        aggregateEntityGenerator,
        aggregateEntityIdExporter,
        valueObjectGenerator,
        domainEventGenerator,
        repositoryGenerator,
        domainServiceGenerator,
        supportiveEntityGenerator,
        supportiveEntityIdGenerator,
        supportiveEntityRepositoryGenerator,
        constantsExporter,
        projectionExporter,
        projectionIdExporter,
        projectionRepositoryExporter,
        domainMessageDataGenerator,
        domainMessageDataRepositoryGenerator,
        domainMessagePublishedDataGenerator,
        domainMessagePublishedDataRepositoryGenerator);
  }
}
