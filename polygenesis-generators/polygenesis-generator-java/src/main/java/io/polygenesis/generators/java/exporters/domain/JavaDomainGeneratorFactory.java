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

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.exporters.domain.aggregateentity.AggregateEntityExporter;
import io.polygenesis.generators.java.exporters.domain.aggregateentity.AggregateEntityIdExporter;
import io.polygenesis.generators.java.exporters.domain.aggregateroot.AggregateRootExporter;
import io.polygenesis.generators.java.exporters.domain.aggregateroot.AggregateRootIdExporter;
import io.polygenesis.generators.java.exporters.domain.domainevent.DomainEventExporter;
import io.polygenesis.generators.java.exporters.domain.persistence.PersistenceExporter;
import io.polygenesis.generators.java.exporters.domain.projection.ProjectionExporter;
import io.polygenesis.generators.java.exporters.domain.projection.ProjectionIdExporter;
import io.polygenesis.generators.java.exporters.domain.projection.ProjectionRepositoryExporter;
import io.polygenesis.generators.java.exporters.domain.service.DomainServiceExporter;
import io.polygenesis.generators.java.exporters.domain.supportiveentity.SupportiveEntityExporter;
import io.polygenesis.generators.java.exporters.domain.valueobject.ValueObjectExporter;
import io.polygenesis.generators.java.implementations.domain.StateMutationMethodImplementorRegistry;
import io.polygenesis.generators.java.implementations.domain.constructor.ConstructorImplementorRegistry;
import io.polygenesis.generators.java.transformers.domain.ConstructorTransformer;
import io.polygenesis.generators.java.transformers.domain.StateMutationMethodTransformer;
import io.polygenesis.generators.java.transformers.domain.aggregateentity.AggregateEntityClassTransformer;
import io.polygenesis.generators.java.transformers.domain.aggregateentity.AggregateEntityIdClassTransformer;
import io.polygenesis.generators.java.transformers.domain.aggregateroot.AggregateRootClassTransformer;
import io.polygenesis.generators.java.transformers.domain.aggregateroot.AggregateRootIdClassTransformer;
import io.polygenesis.generators.java.transformers.domain.persistence.PersistenceInterfaceTransformer;
import io.polygenesis.generators.java.transformers.domain.projection.ProjectionClassTransformer;
import io.polygenesis.generators.java.transformers.domain.projection.ProjectionIdClassTransformer;
import io.polygenesis.generators.java.transformers.domain.projection.ProjectionRepositoryInterfaceTransformer;
import io.polygenesis.generators.java.transformers.domain.service.DomainServiceInterfaceTransformer;
import io.polygenesis.generators.java.transformers.domain.supportiveentity.SupportiveEntityClassTransformer;
import io.polygenesis.generators.java.transformers.domain.valueobject.ValueObjectClassTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import io.polygenesis.transformer.code.FunctionToMethodRepresentationTransformer;
import java.nio.file.Path;

/**
 * The Java Domain Generator Factory creates new instances of {@link JavaDomainGenerator}.
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
  private static DomainServiceExporter domainServiceExporter;
  private static SupportiveEntityExporter supportiveEntityExporter;
  private static ConstantsExporter constantsExporter;
  private static ProjectionExporter projectionExporter;
  private static ProjectionIdExporter projectionIdExporter;
  private static ProjectionRepositoryExporter projectionRepositoryExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    StateMutationMethodImplementorRegistry stateMutationMethodImplementorRegistry =
        new StateMutationMethodImplementorRegistry(freemarkerService);

    StateMutationMethodTransformer stateMutationMethodRepresentable =
        new StateMutationMethodTransformer(
            fromDataTypeToJavaConverter, stateMutationMethodImplementorRegistry);

    AggregateRootClassTransformer aggregateRootClassRepresentable =
        new AggregateRootClassTransformer(
            fromDataTypeToJavaConverter, stateMutationMethodRepresentable);

    aggregateRootExporter =
        new AggregateRootExporter(freemarkerService, aggregateRootClassRepresentable);

    AggregateRootIdClassTransformer aggregateRootIdClassRepresentable =
        new AggregateRootIdClassTransformer(fromDataTypeToJavaConverter);

    aggregateRootIdExporter =
        new AggregateRootIdExporter(freemarkerService, aggregateRootIdClassRepresentable);

    AggregateEntityClassTransformer aggregateEntityClassRepresentable =
        new AggregateEntityClassTransformer(fromDataTypeToJavaConverter);
    aggregateEntityExporter =
        new AggregateEntityExporter(freemarkerService, aggregateEntityClassRepresentable);

    AggregateEntityIdClassTransformer aggregateEntityIdClassRepresentable =
        new AggregateEntityIdClassTransformer(fromDataTypeToJavaConverter);
    aggregateEntityIdExporter =
        new AggregateEntityIdExporter(freemarkerService, aggregateEntityIdClassRepresentable);

    ValueObjectClassTransformer valueObjectClassRepresentable =
        new ValueObjectClassTransformer(fromDataTypeToJavaConverter);

    valueObjectExporter = new ValueObjectExporter(freemarkerService, valueObjectClassRepresentable);

    domainEventExporter = new DomainEventExporter(freemarkerService);

    FunctionToMethodRepresentationTransformer functionToMethodRepresentationTransformer =
        new FunctionToMethodRepresentationTransformer(fromDataTypeToJavaConverter);

    PersistenceInterfaceTransformer persistenceInterfaceRepresentable =
        new PersistenceInterfaceTransformer(
            fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer);

    persistenceExporter =
        new PersistenceExporter(freemarkerService, persistenceInterfaceRepresentable);

    DomainServiceInterfaceTransformer domainServiceInterfaceRepresentable =
        new DomainServiceInterfaceTransformer(
            fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer);

    domainServiceExporter =
        new DomainServiceExporter(freemarkerService, domainServiceInterfaceRepresentable);

    ConstructorImplementorRegistry constructorImplementorRegistry =
        new ConstructorImplementorRegistry(freemarkerService);
    ConstructorTransformer constructorRepresentable =
        new ConstructorTransformer(fromDataTypeToJavaConverter, constructorImplementorRegistry);

    SupportiveEntityClassTransformer supportiveEntityClassRepresentable =
        new SupportiveEntityClassTransformer(fromDataTypeToJavaConverter, constructorRepresentable);

    supportiveEntityExporter =
        new SupportiveEntityExporter(freemarkerService, supportiveEntityClassRepresentable);

    constantsExporter = new ConstantsExporter(freemarkerService);

    projectionExporter =
        new ProjectionExporter(
            freemarkerService, new ProjectionClassTransformer(fromDataTypeToJavaConverter));

    projectionIdExporter =
        new ProjectionIdExporter(
            freemarkerService, new ProjectionIdClassTransformer(fromDataTypeToJavaConverter));

    projectionRepositoryExporter =
        new ProjectionRepositoryExporter(
            freemarkerService,
            new ProjectionRepositoryInterfaceTransformer(
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
  public static JavaDomainGenerator newInstance(
      Path generationPath, PackageName rootPackageName, String tablePrefix) {
    return new JavaDomainGenerator(
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
        domainServiceExporter,
        supportiveEntityExporter,
        constantsExporter,
        projectionExporter,
        projectionIdExporter,
        projectionRepositoryExporter);
  }
}
