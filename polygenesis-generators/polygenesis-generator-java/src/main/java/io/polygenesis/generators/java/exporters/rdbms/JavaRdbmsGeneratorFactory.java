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
import io.polygenesis.generators.java.skeletons.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.skeletons.FunctionToMethodRepresentationConverter;
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
  private static final PersistenceImplExporter persistenceImplExporter;
  private static final PersistenceImplTestExporter persistenceImplTestExporter;
  private static final DomainMessageDataExporter domainMessageDataExporter;
  private static final DomainMessageDataConverterExporter domainMessageDataConverterExporter;
  private static final DomainMessageDataRepositoryExporter domainMessageDataRepositoryExporter;
  private static final SpringDataRepositoryExporter springDataRepositoryExporter;
  private static final RdbmsTestExporter rdbmsTestExporter;
  private static final RdbmsTestConfigExporter rdbmsTestConfigExporter;
  private static final ApplicationCiRdbmsYmlExporter applicationCiRdbmsYmlExporter;

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

    PersistenceImplClassRepresentable persistenceImplClassRepresentable =
        new PersistenceImplClassRepresentable(fromDataTypeToJavaConverter);

    persistenceImplExporter =
        new PersistenceImplExporter(freemarkerService, persistenceImplClassRepresentable);

    PersistenceImplTestClassRepresentable persistenceImplTestClassRepresentable =
        new PersistenceImplTestClassRepresentable(fromDataTypeToJavaConverter);

    persistenceImplTestExporter =
        new PersistenceImplTestExporter(freemarkerService, persistenceImplTestClassRepresentable);

    FunctionToMethodRepresentationConverter functionToMethodRepresentationConverter =
        new FunctionToMethodRepresentationConverter(fromDataTypeToJavaConverter);

    SpringDataRepositoryInterfaceRepresentable springDataRepositoryInterfaceRepresentable =
        new SpringDataRepositoryInterfaceRepresentable(
            fromDataTypeToJavaConverter, functionToMethodRepresentationConverter);

    springDataRepositoryExporter =
        new SpringDataRepositoryExporter(
            freemarkerService, springDataRepositoryInterfaceRepresentable);

    rdbmsTestExporter = new RdbmsTestExporter(freemarkerService);
    rdbmsTestConfigExporter = new RdbmsTestConfigExporter(freemarkerService);

    applicationCiRdbmsYmlExporter = new ApplicationCiRdbmsYmlExporter(freemarkerService);
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
        applicationCiRdbmsYmlExporter);
  }
}
