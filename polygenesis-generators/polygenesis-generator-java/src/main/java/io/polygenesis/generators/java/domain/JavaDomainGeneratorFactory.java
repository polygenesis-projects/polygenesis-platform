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

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityClassRepresentable;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityExporter;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityIdClassRepresentable;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityIdExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootClassRepresentable;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootIdClassRepresentable;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootIdExporter;
import io.polygenesis.generators.java.domain.domainevent.DomainEventExporter;
import io.polygenesis.generators.java.domain.persistence.PersistenceExporter;
import io.polygenesis.generators.java.domain.persistence.PersistenceInterfaceRepresentable;
import io.polygenesis.generators.java.domain.service.DomainServiceExporter;
import io.polygenesis.generators.java.domain.service.DomainServiceInterfaceRepresentable;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectClassRepresentable;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectExporter;
import io.polygenesis.implementations.java.domain.StateMutationMethodImplementorRegistry;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.FunctionToMethodRepresentationConverter;
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

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    StateMutationMethodImplementorRegistry stateMutationMethodImplementorRegistry =
        new StateMutationMethodImplementorRegistry(freemarkerService);

    StateMutationMethodRepresentable stateMutationMethodRepresentable =
        new StateMutationMethodRepresentable(
            fromDataTypeToJavaConverter, stateMutationMethodImplementorRegistry);

    AggregateRootClassRepresentable aggregateRootClassRepresentable =
        new AggregateRootClassRepresentable(
            fromDataTypeToJavaConverter, stateMutationMethodRepresentable);

    aggregateRootExporter =
        new AggregateRootExporter(freemarkerService, aggregateRootClassRepresentable);

    AggregateRootIdClassRepresentable aggregateRootIdClassRepresentable =
        new AggregateRootIdClassRepresentable(fromDataTypeToJavaConverter);

    aggregateRootIdExporter =
        new AggregateRootIdExporter(freemarkerService, aggregateRootIdClassRepresentable);

    AggregateEntityClassRepresentable aggregateEntityClassRepresentable =
        new AggregateEntityClassRepresentable(fromDataTypeToJavaConverter);
    aggregateEntityExporter =
        new AggregateEntityExporter(freemarkerService, aggregateEntityClassRepresentable);

    AggregateEntityIdClassRepresentable aggregateEntityIdClassRepresentable =
        new AggregateEntityIdClassRepresentable(fromDataTypeToJavaConverter);
    aggregateEntityIdExporter =
        new AggregateEntityIdExporter(freemarkerService, aggregateEntityIdClassRepresentable);

    ValueObjectClassRepresentable valueObjectClassRepresentable =
        new ValueObjectClassRepresentable(fromDataTypeToJavaConverter);

    valueObjectExporter = new ValueObjectExporter(freemarkerService, valueObjectClassRepresentable);

    domainEventExporter = new DomainEventExporter(freemarkerService);

    FunctionToMethodRepresentationConverter functionToMethodRepresentationConverter =
        new FunctionToMethodRepresentationConverter(fromDataTypeToJavaConverter);

    PersistenceInterfaceRepresentable persistenceInterfaceRepresentable =
        new PersistenceInterfaceRepresentable(
            fromDataTypeToJavaConverter, functionToMethodRepresentationConverter);

    persistenceExporter =
        new PersistenceExporter(freemarkerService, persistenceInterfaceRepresentable);

    DomainServiceInterfaceRepresentable domainServiceInterfaceRepresentable =
        new DomainServiceInterfaceRepresentable(
            fromDataTypeToJavaConverter, functionToMethodRepresentationConverter);

    domainServiceExporter =
        new DomainServiceExporter(freemarkerService, domainServiceInterfaceRepresentable);
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
   * @return the java api generator
   */
  public static JavaDomainGenerator newInstance(Path generationPath, PackageName rootPackageName) {
    return new JavaDomainGenerator(
        generationPath,
        rootPackageName,
        aggregateRootExporter,
        aggregateRootIdExporter,
        aggregateEntityExporter,
        aggregateEntityIdExporter,
        valueObjectExporter,
        domainEventExporter,
        persistenceExporter,
        domainServiceExporter);
  }
}
