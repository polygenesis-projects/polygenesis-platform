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
import io.polygenesis.generators.java.shared.transformer.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.shared.transformer.FunctionToLegacyMethodRepresentationTransformer;
import io.polygenesis.generators.java.transformers.rdbms.PersistenceImplLegacyClassTransformer;
import io.polygenesis.generators.java.transformers.rdbms.SpringDataRepositoryLegacyInterfaceTransformer;
import io.polygenesis.generators.java.transformers.rdbms.projection.ProjectionRepositoryImplLegacyClassTransformer;
import io.polygenesis.generators.java.transformers.rdbms.projection.ProjectionSpringDataRepositoryLegacyInterfaceTransformer;
import io.polygenesis.generators.java.transformers.rdbms.projection.testing.ProjectionRepositoryImplTestLegacyClassTransformer;
import io.polygenesis.generators.java.transformers.rdbms.testing.PersistenceImplTestLegacyClassTransformer;
import java.nio.file.Path;

/**
 * The Java Domain MetamodelGenerator Factory creates new instances of {@link
 * JavaRdbmsMetamodelGenerator}.
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

    PersistenceImplLegacyClassTransformer persistenceImplClassRepresentable =
        new PersistenceImplLegacyClassTransformer(fromDataTypeToJavaConverter);

    persistenceImplExporter =
        new PersistenceImplExporter(freemarkerService, persistenceImplClassRepresentable);

    PersistenceImplTestLegacyClassTransformer persistenceImplTestClassRepresentable =
        new PersistenceImplTestLegacyClassTransformer(fromDataTypeToJavaConverter);

    persistenceImplTestExporter =
        new PersistenceImplTestExporter(freemarkerService, persistenceImplTestClassRepresentable);

    FunctionToLegacyMethodRepresentationTransformer functionToMethodRepresentationTransformer =
        new FunctionToLegacyMethodRepresentationTransformer(fromDataTypeToJavaConverter);

    SpringDataRepositoryLegacyInterfaceTransformer springDataRepositoryInterfaceRepresentable =
        new SpringDataRepositoryLegacyInterfaceTransformer(
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
            new ProjectionRepositoryImplLegacyClassTransformer(fromDataTypeToJavaConverter));

    projectionSpringDataRepositoryExporter =
        new ProjectionSpringDataRepositoryExporter(
            freemarkerService,
            new ProjectionSpringDataRepositoryLegacyInterfaceTransformer(
                fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer));

    projectionRepositoryImplTestExporter =
        new ProjectionRepositoryImplTestExporter(
            freemarkerService,
            new ProjectionRepositoryImplTestLegacyClassTransformer(fromDataTypeToJavaConverter));
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
  public static JavaRdbmsMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ObjectName contextName) {
    return new JavaRdbmsMetamodelGenerator(
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
