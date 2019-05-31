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

package io.polygenesis.generators.java.exporters.rdbms;

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.exporters.rdbms.projection.ProjectionRepositoryImplExporter;
import io.polygenesis.generators.java.exporters.rdbms.projection.ProjectionSpringDataRepositoryExporter;
import io.polygenesis.generators.java.exporters.rdbms.projection.testing.ProjectionRepositoryImplTestExporter;
import io.polygenesis.generators.java.exporters.rdbms.testing.PersistenceImplTestExporter;
import io.polygenesis.generators.java.transformers.rdbms.PersistenceImplClassTransformer;
import io.polygenesis.generators.java.transformers.rdbms.SpringDataRepositoryInterfaceTransformer;
import io.polygenesis.generators.java.transformers.rdbms.projection.ProjectionRepositoryImplClassTransformer;
import io.polygenesis.generators.java.transformers.rdbms.projection.ProjectionSpringDataRepositoryInterfaceTransformer;
import io.polygenesis.generators.java.transformers.rdbms.projection.testing.ProjectionRepositoryImplTestClassTransformer;
import io.polygenesis.generators.java.transformers.rdbms.testing.PersistenceImplTestClassTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import io.polygenesis.transformer.code.FunctionToMethodRepresentationTransformer;
import java.nio.file.Path;

/**
 * The Java Domain Generator Factory creates new instances of {@link JavaRdbmsGenerator}.
 *
 * @author Christos Tsakostas
 */
public final class JavaRdbmsGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static PersistenceImplExporter persistenceImplExporter;
  private static PersistenceImplTestExporter persistenceImplTestExporter;
  private static DomainMessageDataExporter domainMessageDataExporter;
  private static DomainMessageDataConverterExporter domainMessageDataConverterExporter;
  private static DomainMessageDataRepositoryExporter domainMessageDataRepositoryExporter;
  private static SpringDataRepositoryExporter springDataRepositoryExporter;
  private static RdbmsTestExporter rdbmsTestExporter;
  private static RdbmsTestConfigExporter rdbmsTestConfigExporter;
  private static ApplicationCiRdbmsYmlExporter applicationCiRdbmsYmlExporter;
  private static ProjectionRepositoryImplExporter projectionRepositoryImplExporter;
  private static ProjectionSpringDataRepositoryExporter projectionSpringDataRepositoryExporter;
  private static ProjectionRepositoryImplTestExporter projectionRepositoryImplTestExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    domainMessageDataExporter = new DomainMessageDataExporter(freemarkerService);
    domainMessageDataConverterExporter = new DomainMessageDataConverterExporter(freemarkerService);
    domainMessageDataRepositoryExporter =
        new DomainMessageDataRepositoryExporter(freemarkerService);

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    PersistenceImplClassTransformer persistenceImplClassRepresentable =
        new PersistenceImplClassTransformer(fromDataTypeToJavaConverter);

    persistenceImplExporter =
        new PersistenceImplExporter(freemarkerService, persistenceImplClassRepresentable);

    PersistenceImplTestClassTransformer persistenceImplTestClassRepresentable =
        new PersistenceImplTestClassTransformer(fromDataTypeToJavaConverter);

    persistenceImplTestExporter =
        new PersistenceImplTestExporter(freemarkerService, persistenceImplTestClassRepresentable);

    FunctionToMethodRepresentationTransformer functionToMethodRepresentationTransformer =
        new FunctionToMethodRepresentationTransformer(fromDataTypeToJavaConverter);

    SpringDataRepositoryInterfaceTransformer springDataRepositoryInterfaceRepresentable =
        new SpringDataRepositoryInterfaceTransformer(
            fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer);

    springDataRepositoryExporter =
        new SpringDataRepositoryExporter(
            freemarkerService, springDataRepositoryInterfaceRepresentable);

    rdbmsTestExporter = new RdbmsTestExporter(freemarkerService);
    rdbmsTestConfigExporter = new RdbmsTestConfigExporter(freemarkerService);

    applicationCiRdbmsYmlExporter = new ApplicationCiRdbmsYmlExporter(freemarkerService);

    projectionRepositoryImplExporter =
        new ProjectionRepositoryImplExporter(
            freemarkerService,
            new ProjectionRepositoryImplClassTransformer(fromDataTypeToJavaConverter));

    projectionSpringDataRepositoryExporter =
        new ProjectionSpringDataRepositoryExporter(
            freemarkerService,
            new ProjectionSpringDataRepositoryInterfaceTransformer(
                fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer));

    projectionRepositoryImplTestExporter =
        new ProjectionRepositoryImplTestExporter(
            freemarkerService,
            new ProjectionRepositoryImplTestClassTransformer(fromDataTypeToJavaConverter));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private JavaRdbmsGeneratorFactory() {
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
   * @param contextName the context name
   * @return the java api generator
   */
  public static JavaRdbmsGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ObjectName contextName) {
    return new JavaRdbmsGenerator(
        generationPath,
        rootPackageName,
        contextName,
        domainMessageDataExporter,
        domainMessageDataConverterExporter,
        domainMessageDataRepositoryExporter,
        persistenceImplExporter,
        persistenceImplTestExporter,
        springDataRepositoryExporter,
        rdbmsTestExporter,
        rdbmsTestConfigExporter,
        applicationCiRdbmsYmlExporter,
        projectionRepositoryImplExporter,
        projectionSpringDataRepositoryExporter,
        projectionRepositoryImplTestExporter);
  }
}
