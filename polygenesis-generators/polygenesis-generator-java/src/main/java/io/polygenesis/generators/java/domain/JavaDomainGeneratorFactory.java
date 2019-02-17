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
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.domain.aggregateentity.AggregateEntityExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootIdExporter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootIdProjectionConverter;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootProjectionConverter;
import io.polygenesis.generators.java.domain.domainevent.DomainEventExporter;
import io.polygenesis.generators.java.domain.persistence.PersistenceExporter;
import io.polygenesis.generators.java.domain.persistence.PersistenceProjectionConverter;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectExporter;
import io.polygenesis.generators.java.domain.valueobject.ValueObjectProjectionConverter;
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
  private static ValueObjectExporter valueObjectExporter;
  private static DomainEventExporter domainEventExporter;
  private static PersistenceExporter persistenceExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    AggregateRootProjectionConverter aggregateRootProjectionConverter =
        new AggregateRootProjectionConverter(fromDataTypeToJavaConverter);

    aggregateRootExporter =
        new AggregateRootExporter(freemarkerService, aggregateRootProjectionConverter);

    AggregateRootIdProjectionConverter aggregateRootIdProjectionConverter =
        new AggregateRootIdProjectionConverter(fromDataTypeToJavaConverter);

    aggregateRootIdExporter =
        new AggregateRootIdExporter(freemarkerService, aggregateRootIdProjectionConverter);

    aggregateEntityExporter = new AggregateEntityExporter(freemarkerService);

    ValueObjectProjectionConverter valueObjectProjectionConverter =
        new ValueObjectProjectionConverter(fromDataTypeToJavaConverter);

    valueObjectExporter =
        new ValueObjectExporter(freemarkerService, valueObjectProjectionConverter);

    domainEventExporter = new DomainEventExporter(freemarkerService);

    PersistenceProjectionConverter persistenceProjectionConverter =
        new PersistenceProjectionConverter(fromDataTypeToJavaConverter);

    persistenceExporter =
        new PersistenceExporter(freemarkerService, persistenceProjectionConverter);
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
        valueObjectExporter,
        domainEventExporter,
        persistenceExporter);
  }
}
