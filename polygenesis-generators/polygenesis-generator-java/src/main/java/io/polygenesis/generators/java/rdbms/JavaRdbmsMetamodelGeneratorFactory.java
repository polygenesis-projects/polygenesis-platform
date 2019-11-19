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

package io.polygenesis.generators.java.rdbms;

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.PassiveFileExporter;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.rdbms.domainmessage.datarepositoryimpl.DomainMessageDataRepositoryImplGenerator;
import io.polygenesis.generators.java.rdbms.domainmessage.datarepositoryimpl.DomainMessageDataRepositoryImplMethodTransformer;
import io.polygenesis.generators.java.rdbms.domainmessage.datarepositoryimpl.DomainMessageDataRepositoryImplTransformer;
import io.polygenesis.generators.java.rdbms.domainmessage.domainmessagedataconverter.DomainMessageDataConverterExporter;
import io.polygenesis.generators.java.rdbms.domainmessage.publisheddatarepositoryimpl.DomainMessagePublishedDataRepositoryImplGenerator;
import io.polygenesis.generators.java.rdbms.domainmessage.publisheddatarepositoryimpl.DomainMessagePublishedDataRepositoryImplMethodTransformer;
import io.polygenesis.generators.java.rdbms.domainmessage.publisheddatarepositoryimpl.DomainMessagePublishedDataRepositoryImplTransformer;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagedatarepository.SpringDomainMessageDataRepositoryGenerator;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagedatarepository.SpringDomainMessageDataRepositoryMethodTransformer;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagedatarepository.SpringDomainMessageDataRepositoryTransformer;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagepublisheddatarepository.SpringDomainMessagePublishedDataRepositoryGenerator;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagepublisheddatarepository.SpringDomainMessagePublishedDataRepositoryMethodTransformer;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagepublisheddatarepository.SpringDomainMessagePublishedDataRepositoryTransformer;
import io.polygenesis.generators.java.rdbms.projection.ProjectionRepositoryImplExporter;
import io.polygenesis.generators.java.rdbms.projection.ProjectionRepositoryImplLegacyClassTransformer;
import io.polygenesis.generators.java.rdbms.projection.ProjectionSpringDataRepositoryExporter;
import io.polygenesis.generators.java.rdbms.projection.ProjectionSpringDataRepositoryLegacyInterfaceTransformer;
import io.polygenesis.generators.java.rdbms.projection.testing.ProjectionRepositoryImplTestExporter;
import io.polygenesis.generators.java.rdbms.projection.testing.ProjectionRepositoryImplTestLegacyClassTransformer;
import io.polygenesis.generators.java.rdbms.repositoryimpl.RepositoryImplGenerator;
import io.polygenesis.generators.java.rdbms.repositoryimpl.RepositoryImplMethodTransformer;
import io.polygenesis.generators.java.rdbms.repositoryimpl.RepositoryImplTransformer;
import io.polygenesis.generators.java.rdbms.repositoryimpl.spingdata.SpringDataRepositoryGenerator;
import io.polygenesis.generators.java.rdbms.repositoryimpl.spingdata.SpringDataRepositoryMethodTransformer;
import io.polygenesis.generators.java.rdbms.repositoryimpl.spingdata.SpringDataRepositoryTransformer;
import io.polygenesis.generators.java.rdbms.testing.PersistenceImplTestExporter;
import io.polygenesis.generators.java.rdbms.testing.PersistenceImplTestLegacyClassTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import io.polygenesis.transformers.java.legacy.FunctionToLegacyMethodRepresentationTransformer;
import java.nio.file.Path;

/**
 * The type Java rdbms metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public final class JavaRdbmsMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static RepositoryImplGenerator repositoryImplGenerator;
  private static PersistenceImplTestExporter persistenceImplTestExporter;
  private static DomainMessageDataConverterExporter domainMessageDataConverterExporter;
  private static SpringDataRepositoryGenerator springDataRepositoryGenerator;
  private static RdbmsTestExporter rdbmsTestExporter;
  private static RdbmsTestConfigExporter rdbmsTestConfigExporter;
  private static ApplicationCiRdbmsYmlExporter applicationCiRdbmsYmlExporter;
  private static ProjectionRepositoryImplExporter projectionRepositoryImplExporter;
  private static ProjectionSpringDataRepositoryExporter projectionSpringDataRepositoryExporter;
  private static ProjectionRepositoryImplTestExporter projectionRepositoryImplTestExporter;

  private static DomainMessageDataRepositoryImplGenerator domainMessageDataRepositoryImplGenerator;
  private static DomainMessagePublishedDataRepositoryImplGenerator
      domainMessagePublishedDataRepositoryImplGenerator;

  private static SpringDomainMessageDataRepositoryGenerator
      springDomainMessageDataRepositoryGenerator;
  private static SpringDomainMessagePublishedDataRepositoryGenerator
      springDomainMessagePublishedDataRepositoryGenerator;

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

    domainMessageDataConverterExporter = new DomainMessageDataConverterExporter(freemarkerService);

    repositoryImplGenerator =
        new RepositoryImplGenerator(
            new RepositoryImplTransformer(
                dataTypeTransformer, new RepositoryImplMethodTransformer(dataTypeTransformer)),
            templateEngine,
            passiveFileExporter);

    PersistenceImplTestLegacyClassTransformer persistenceImplTestClassRepresentable =
        new PersistenceImplTestLegacyClassTransformer(dataTypeTransformer);

    persistenceImplTestExporter =
        new PersistenceImplTestExporter(freemarkerService, persistenceImplTestClassRepresentable);

    springDataRepositoryGenerator =
        new SpringDataRepositoryGenerator(
            new SpringDataRepositoryTransformer(
                dataTypeTransformer,
                new SpringDataRepositoryMethodTransformer(dataTypeTransformer)),
            templateEngine,
            passiveFileExporter);

    rdbmsTestExporter = new RdbmsTestExporter(freemarkerService);
    rdbmsTestConfigExporter = new RdbmsTestConfigExporter(freemarkerService);

    applicationCiRdbmsYmlExporter = new ApplicationCiRdbmsYmlExporter(freemarkerService);

    projectionRepositoryImplExporter =
        new ProjectionRepositoryImplExporter(
            freemarkerService,
            new ProjectionRepositoryImplLegacyClassTransformer(dataTypeTransformer));

    FunctionToLegacyMethodRepresentationTransformer functionToMethodRepresentationTransformer =
        new FunctionToLegacyMethodRepresentationTransformer(dataTypeTransformer);

    projectionSpringDataRepositoryExporter =
        new ProjectionSpringDataRepositoryExporter(
            freemarkerService,
            new ProjectionSpringDataRepositoryLegacyInterfaceTransformer(
                dataTypeTransformer, functionToMethodRepresentationTransformer));

    projectionRepositoryImplTestExporter =
        new ProjectionRepositoryImplTestExporter(
            freemarkerService,
            new ProjectionRepositoryImplTestLegacyClassTransformer(dataTypeTransformer));

    domainMessageDataRepositoryImplGenerator =
        new DomainMessageDataRepositoryImplGenerator(
            new DomainMessageDataRepositoryImplTransformer(
                dataTypeTransformer,
                new DomainMessageDataRepositoryImplMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    domainMessagePublishedDataRepositoryImplGenerator =
        new DomainMessagePublishedDataRepositoryImplGenerator(
            new DomainMessagePublishedDataRepositoryImplTransformer(
                dataTypeTransformer,
                new DomainMessagePublishedDataRepositoryImplMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    springDomainMessageDataRepositoryGenerator =
        new SpringDomainMessageDataRepositoryGenerator(
            new SpringDomainMessageDataRepositoryTransformer(
                dataTypeTransformer,
                new SpringDomainMessageDataRepositoryMethodTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    springDomainMessagePublishedDataRepositoryGenerator =
        new SpringDomainMessagePublishedDataRepositoryGenerator(
            new SpringDomainMessagePublishedDataRepositoryTransformer(
                dataTypeTransformer,
                new SpringDomainMessagePublishedDataRepositoryMethodTransformer(
                    dataTypeTransformer)),
            templateEngine,
            activeFileExporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private JavaRdbmsMetamodelGeneratorFactory() {
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
        domainMessageDataConverterExporter,
        repositoryImplGenerator,
        persistenceImplTestExporter,
        springDataRepositoryGenerator,
        rdbmsTestExporter,
        rdbmsTestConfigExporter,
        applicationCiRdbmsYmlExporter,
        projectionRepositoryImplExporter,
        projectionSpringDataRepositoryExporter,
        projectionRepositoryImplTestExporter,
        domainMessageDataRepositoryImplGenerator,
        domainMessagePublishedDataRepositoryImplGenerator,
        springDomainMessageDataRepositoryGenerator,
        springDomainMessagePublishedDataRepositoryGenerator);
  }
}
