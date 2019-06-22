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
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.JavaDataTypeTransformer;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityExporter;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityIdExporter;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityIdLegacyClassTransformer;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityLegacyClassTransformer;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootIdExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootIdLegacyClassTransformer;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootLegacyClassTransformer;
import io.polygenesis.generators.java.domain.domainevent.DomainEventExporter;
import io.polygenesis.generators.java.domain.persistence.PersistenceExporter;
import io.polygenesis.generators.java.domain.persistence.PersistenceLegacyInterfaceTransformer;
import io.polygenesis.generators.java.domain.projection.exporter.ProjectionExporter;
import io.polygenesis.generators.java.domain.projection.exporter.ProjectionIdExporter;
import io.polygenesis.generators.java.domain.projection.exporter.ProjectionRepositoryExporter;
import io.polygenesis.generators.java.domain.projection.transformer.ProjectionIdLegacyClassTransformer;
import io.polygenesis.generators.java.domain.projection.transformer.ProjectionLegacyClassTransformer;
import io.polygenesis.generators.java.domain.projection.transformer.ProjectionRepositoryLegacyInterfaceTransformer;
import io.polygenesis.generators.java.domain.service.DomainServiceGenerator;
import io.polygenesis.generators.java.domain.service.DomainServiceMethodTransformer;
import io.polygenesis.generators.java.domain.service.DomainServiceTransformer;
import io.polygenesis.generators.java.domain.supportiveentity.SupportiveEntityExporter;
import io.polygenesis.generators.java.domain.supportiveentity.SupportiveEntityLegacyClassTransformer;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectExporter;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectLegacyClassTransformer;
import io.polygenesis.generators.java.implementations.domain.StateMutationMethodImplementorRegistry;
import io.polygenesis.generators.java.implementations.domain.constructor.ConstructorImplementorRegistry;
import io.polygenesis.generators.java.shared.transformer.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.shared.transformer.FunctionToLegacyMethodRepresentationTransformer;
import java.nio.file.Path;

/**
 * The Java Domain MetamodelGenerator Factory creates new instances of {@link
 * JavaDomainMetamodelGenerator}.
 *
 * @author Christos Tsakostas
 */
public final class JavaDomainGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private static AggregateRootExporter aggregateRootExporter;
  private static AggregateRootIdExporter aggregateRootIdExporter;
  private static AggregateEntityExporter aggregateEntityExporter;
  private static AggregateEntityIdExporter aggregateEntityIdExporter;
  private static ValueObjectExporter valueObjectExporter;
  private static DomainEventExporter domainEventExporter;
  private static PersistenceExporter persistenceExporter;
  private static DomainServiceGenerator domainServiceGenerator;
  private static SupportiveEntityExporter supportiveEntityExporter;
  private static ConstantsExporter constantsExporter;
  private static ProjectionExporter projectionExporter;
  private static ProjectionIdExporter projectionIdExporter;
  private static ProjectionRepositoryExporter projectionRepositoryExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter exporter = new ActiveFileExporter();
    final DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    StateMutationMethodImplementorRegistry stateMutationMethodImplementorRegistry =
        new StateMutationMethodImplementorRegistry(freemarkerService);

    StateMutationLegacyMethodTransformer stateMutationMethodRepresentable =
        new StateMutationLegacyMethodTransformer(
            fromDataTypeToJavaConverter, stateMutationMethodImplementorRegistry);

    AggregateRootLegacyClassTransformer aggregateRootClassRepresentable =
        new AggregateRootLegacyClassTransformer(
            fromDataTypeToJavaConverter, stateMutationMethodRepresentable);

    aggregateRootExporter =
        new AggregateRootExporter(freemarkerService, aggregateRootClassRepresentable);

    AggregateRootIdLegacyClassTransformer aggregateRootIdClassRepresentable =
        new AggregateRootIdLegacyClassTransformer(fromDataTypeToJavaConverter);

    aggregateRootIdExporter =
        new AggregateRootIdExporter(freemarkerService, aggregateRootIdClassRepresentable);

    AggregateEntityLegacyClassTransformer aggregateEntityClassRepresentable =
        new AggregateEntityLegacyClassTransformer(fromDataTypeToJavaConverter);
    aggregateEntityExporter =
        new AggregateEntityExporter(freemarkerService, aggregateEntityClassRepresentable);

    AggregateEntityIdLegacyClassTransformer aggregateEntityIdClassRepresentable =
        new AggregateEntityIdLegacyClassTransformer(fromDataTypeToJavaConverter);
    aggregateEntityIdExporter =
        new AggregateEntityIdExporter(freemarkerService, aggregateEntityIdClassRepresentable);

    ValueObjectLegacyClassTransformer valueObjectClassRepresentable =
        new ValueObjectLegacyClassTransformer(fromDataTypeToJavaConverter);

    valueObjectExporter = new ValueObjectExporter(freemarkerService, valueObjectClassRepresentable);

    domainEventExporter = new DomainEventExporter(freemarkerService);

    FunctionToLegacyMethodRepresentationTransformer functionToMethodRepresentationTransformer =
        new FunctionToLegacyMethodRepresentationTransformer(fromDataTypeToJavaConverter);

    PersistenceLegacyInterfaceTransformer persistenceInterfaceRepresentable =
        new PersistenceLegacyInterfaceTransformer(
            fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer);

    persistenceExporter =
        new PersistenceExporter(freemarkerService, persistenceInterfaceRepresentable);

    domainServiceGenerator =
        new DomainServiceGenerator(
            new DomainServiceTransformer(
                dataTypeTransformer, new DomainServiceMethodTransformer(dataTypeTransformer)),
            templateEngine,
            exporter);

    ConstructorImplementorRegistry constructorImplementorRegistry =
        new ConstructorImplementorRegistry(freemarkerService);
    ConstructorTransformerLegacy constructorRepresentable =
        new ConstructorTransformerLegacy(
            fromDataTypeToJavaConverter, constructorImplementorRegistry);

    SupportiveEntityLegacyClassTransformer supportiveEntityClassRepresentable =
        new SupportiveEntityLegacyClassTransformer(
            fromDataTypeToJavaConverter, constructorRepresentable);

    supportiveEntityExporter =
        new SupportiveEntityExporter(freemarkerService, supportiveEntityClassRepresentable);

    constantsExporter = new ConstantsExporter(freemarkerService);

    projectionExporter =
        new ProjectionExporter(
            freemarkerService, new ProjectionLegacyClassTransformer(fromDataTypeToJavaConverter));

    projectionIdExporter =
        new ProjectionIdExporter(
            freemarkerService, new ProjectionIdLegacyClassTransformer(fromDataTypeToJavaConverter));

    projectionRepositoryExporter =
        new ProjectionRepositoryExporter(
            freemarkerService,
            new ProjectionRepositoryLegacyInterfaceTransformer(
                fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private JavaDomainGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance java api generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param tablePrefix the table prefix
   * @return the java api generator
   */
  public static JavaDomainMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, String tablePrefix) {
    return new JavaDomainMetamodelGenerator(
        generationPath,
        tablePrefix,
        rootPackageName,
        aggregateRootExporter,
        aggregateRootIdExporter,
        aggregateEntityExporter,
        aggregateEntityIdExporter,
        valueObjectExporter,
        domainEventExporter,
        persistenceExporter,
        domainServiceGenerator,
        supportiveEntityExporter,
        constantsExporter,
        projectionExporter,
        projectionIdExporter,
        projectionRepositoryExporter);
  }
}
