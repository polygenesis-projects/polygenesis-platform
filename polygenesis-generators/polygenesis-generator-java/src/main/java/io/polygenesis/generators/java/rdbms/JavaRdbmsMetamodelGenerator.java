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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.rdbms.domainmessage.datarepositoryimpl.DomainMessageDataRepositoryImpl;
import io.polygenesis.generators.java.rdbms.domainmessage.datarepositoryimpl.DomainMessageDataRepositoryImplGenerator;
import io.polygenesis.generators.java.rdbms.domainmessage.domainmessagedataconverter.DomainMessageDataConverterExporter;
import io.polygenesis.generators.java.rdbms.domainmessage.publisheddatarepositoryimpl.DomainMessagePublishedDataRepositoryImpl;
import io.polygenesis.generators.java.rdbms.domainmessage.publisheddatarepositoryimpl.DomainMessagePublishedDataRepositoryImplGenerator;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagedatarepository.SpringDomainMessageDataRepository;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagedatarepository.SpringDomainMessageDataRepositoryGenerator;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagepublisheddatarepository.SpringDomainMessagePublishedDataRepository;
import io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagepublisheddatarepository.SpringDomainMessagePublishedDataRepositoryGenerator;
import io.polygenesis.generators.java.rdbms.projection.ProjectionRepositoryImplExporter;
import io.polygenesis.generators.java.rdbms.projection.ProjectionSpringDataRepositoryExporter;
import io.polygenesis.generators.java.rdbms.projection.testing.ProjectionRepositoryImplTestExporter;
import io.polygenesis.generators.java.rdbms.repositoryimpl.RepositoryImplGenerator;
import io.polygenesis.generators.java.rdbms.repositoryimpl.spingdata.SpringDataRepositoryGenerator;
import io.polygenesis.generators.java.rdbms.testing.PersistenceImplTestExporter;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.domain.AggregateRootMetamodelRepository;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.models.domain.ProjectionMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Java rdbms metamodel generator.
 *
 * @author Christos Tsakostas
 */
public class JavaRdbmsMetamodelGenerator extends AbstractMetamodelGenerator {

  private final PackageName rootPackageName;
  private final ObjectName contextName;
  private final DomainMessageDataConverterExporter domainMessageDataConverterExporter;
  private final RepositoryImplGenerator repositoryImplGenerator;
  private final PersistenceImplTestExporter persistenceImplTestExporter;
  private final SpringDataRepositoryGenerator springDataRepositoryGenerator;
  private final RdbmsTestExporter rdbmsTestExporter;
  private final RdbmsTestConfigExporter rdbmsTestConfigExporter;
  private final ApplicationCiRdbmsYmlExporter applicationCiRdbmsYmlExporter;
  private final ProjectionRepositoryImplExporter projectionRepositoryImplExporter;
  private final ProjectionSpringDataRepositoryExporter projectionSpringDataRepositoryExporter;
  private final ProjectionRepositoryImplTestExporter projectionRepositoryImplTestExporter;
  private final DomainMessageDataRepositoryImplGenerator domainMessageDataRepositoryImplGenerator;
  private final DomainMessagePublishedDataRepositoryImplGenerator
      domainMessagePublishedDataRepositoryImplGenerator;
  private final SpringDomainMessageDataRepositoryGenerator
      springDomainMessageDataRepositoryGenerator;
  private final SpringDomainMessagePublishedDataRepositoryGenerator
      springDomainMessagePublishedDataRepositoryGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public JavaRdbmsMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ObjectName contextName,
      DomainMessageDataConverterExporter domainMessageDataConverterExporter,
      RepositoryImplGenerator repositoryImplGenerator,
      PersistenceImplTestExporter persistenceImplTestExporter,
      SpringDataRepositoryGenerator springDataRepositoryGenerator,
      RdbmsTestExporter rdbmsTestExporter,
      RdbmsTestConfigExporter rdbmsTestConfigExporter,
      ApplicationCiRdbmsYmlExporter applicationCiRdbmsYmlExporter,
      ProjectionRepositoryImplExporter projectionRepositoryImplExporter,
      ProjectionSpringDataRepositoryExporter projectionSpringDataRepositoryExporter,
      ProjectionRepositoryImplTestExporter projectionRepositoryImplTestExporter,
      DomainMessageDataRepositoryImplGenerator domainMessageDataRepositoryImplGenerator,
      DomainMessagePublishedDataRepositoryImplGenerator
          domainMessagePublishedDataRepositoryImplGenerator,
      SpringDomainMessageDataRepositoryGenerator springDomainMessageDataRepositoryGenerator,
      SpringDomainMessagePublishedDataRepositoryGenerator
          springDomainMessagePublishedDataRepositoryGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.domainMessageDataConverterExporter = domainMessageDataConverterExporter;
    this.repositoryImplGenerator = repositoryImplGenerator;
    this.persistenceImplTestExporter = persistenceImplTestExporter;
    this.springDataRepositoryGenerator = springDataRepositoryGenerator;
    this.rdbmsTestExporter = rdbmsTestExporter;
    this.rdbmsTestConfigExporter = rdbmsTestConfigExporter;
    this.applicationCiRdbmsYmlExporter = applicationCiRdbmsYmlExporter;
    this.projectionRepositoryImplExporter = projectionRepositoryImplExporter;
    this.projectionSpringDataRepositoryExporter = projectionSpringDataRepositoryExporter;
    this.projectionRepositoryImplTestExporter = projectionRepositoryImplTestExporter;
    this.domainMessageDataRepositoryImplGenerator = domainMessageDataRepositoryImplGenerator;
    this.domainMessagePublishedDataRepositoryImplGenerator =
        domainMessagePublishedDataRepositoryImplGenerator;
    this.springDomainMessageDataRepositoryGenerator = springDomainMessageDataRepositoryGenerator;
    this.springDomainMessagePublishedDataRepositoryGenerator =
        springDomainMessagePublishedDataRepositoryGenerator;
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
   * Gets context name.
   *
   * @return the context name
   */
  public ObjectName getContextName() {
    return contextName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    domainMessageDataConverterExporter.export(
        getGenerationPath(), getRootPackageName(), getContextName());

    rdbmsTestExporter.export(getGenerationPath(), getRootPackageName());

    rdbmsTestConfigExporter.export(getGenerationPath(), getRootPackageName());

    applicationCiRdbmsYmlExporter.export(getGenerationPath());

    aggregateRoots(modelRepositories);
    projections(modelRepositories);

    DomainMessageDataRepositoryImpl domainMessageDataRepositoryImpl =
        makeDomainMessageDataRepositoryImpl();
    domainMessageDataRepositoryImplGenerator.generate(
        domainMessageDataRepositoryImpl,
        domainMessageDataRepositoryImplExportInfo(
            getGenerationPath(), domainMessageDataRepositoryImpl),
        contextName);

    DomainMessagePublishedDataRepositoryImpl domainMessagePublishedDataRepositoryImpl =
        makeDomainMessagePublishedDataRepositoryImpl();
    domainMessagePublishedDataRepositoryImplGenerator.generate(
        domainMessagePublishedDataRepositoryImpl,
        domainMessagePublishedDataRepositoryImplExportInfo(
            getGenerationPath(), domainMessagePublishedDataRepositoryImpl),
        contextName);

    SpringDomainMessageDataRepository springDomainMessageDataRepository =
        makeSpringDomainMessageDataRepository();
    springDomainMessageDataRepositoryGenerator.generate(
        springDomainMessageDataRepository,
        makeExportInfo(
            getGenerationPath(),
            springDomainMessageDataRepository.getPackageName(),
            springDomainMessageDataRepository.getObjectName().getText()),
        contextName);

    SpringDomainMessagePublishedDataRepository springDomainMessagePublishedDataRepository =
        makeSpringDomainMessagePublishedDataRepository();
    springDomainMessagePublishedDataRepositoryGenerator.generate(
        springDomainMessagePublishedDataRepository,
        makeExportInfo(
            getGenerationPath(),
            springDomainMessagePublishedDataRepository.getPackageName(),
            springDomainMessagePublishedDataRepository.getObjectName().getText()),
        contextName);
  }

  private void aggregateRoots(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, AggregateRootMetamodelRepository.class)
        .getItems()
        .stream()
        .filter(aggregateRoot -> aggregateRoot instanceof AggregateRootPersistable)
        .map(AggregateRootPersistable.class::cast)
        .forEach(
            aggregateRoot -> {
              if (aggregateRoot instanceof AggregateRootPersistable) {
                Persistence persistence = aggregateRoot.getPersistence();

                repositoryImplGenerator.generate(
                    persistence,
                    repositoryImplExportInfo(
                        getGenerationPath(),
                        persistence.getPackageName(),
                        persistence.getObjectName().getText()),
                    getRootPackageName(),
                    getContextName());

                persistenceImplTestExporter.export(
                    getGenerationPath(), persistence, getRootPackageName(), getContextName());

                springDataRepositoryGenerator.generate(
                    persistence,
                    repositorySpringDataExportInfo(
                        getGenerationPath(),
                        persistence.getPackageName(),
                        persistence.getAggregateRootObjectName().getText()));
              }
            });
  }

  private void projections(Set<MetamodelRepository<?>> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, ProjectionMetamodelRepository.class)
        .getItems()
        .stream()
        .forEach(
            projection -> {
              projectionRepositoryImplExporter.export(
                  getGenerationPath(),
                  projection.getPersistence(),
                  getRootPackageName(),
                  getContextName());

              projectionRepositoryImplTestExporter.export(
                  getGenerationPath(),
                  projection.getPersistence(),
                  getRootPackageName(),
                  getContextName());

              projectionSpringDataRepositoryExporter.export(
                  getGenerationPath(), projection.getPersistence());
            });
  }

  private DomainMessageDataRepositoryImpl makeDomainMessageDataRepositoryImpl() {
    return new DomainMessageDataRepositoryImpl(
        new ObjectName(
            String.format(
                "%sDomainMessageDataRepositoryImpl",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessagePublishedDataRepositoryImpl makeDomainMessagePublishedDataRepositoryImpl() {
    return new DomainMessagePublishedDataRepositoryImpl(
        new ObjectName(
            String.format(
                "%sDomainMessagePublishedDataRepositoryImpl",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private SpringDomainMessageDataRepository makeSpringDomainMessageDataRepository() {
    return new SpringDomainMessageDataRepository(
        new ObjectName(
            String.format(
                "%sSpringDomainMessageDataRepository",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private SpringDomainMessagePublishedDataRepository
      makeSpringDomainMessagePublishedDataRepository() {
    return new SpringDomainMessagePublishedDataRepository(
        new ObjectName(
            String.format(
                "%sSpringDomainMessagePublishedDataRepository",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private ExportInfo domainMessageDataRepositoryImplExportInfo(
      Path generationPath, DomainMessageDataRepositoryImpl domainMessageDataRepositoryImpl) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessageDataRepositoryImpl.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessageDataRepositoryImpl.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo domainMessagePublishedDataRepositoryImplExportInfo(
      Path generationPath,
      DomainMessagePublishedDataRepositoryImpl domainMessagePublishedDataRepositoryImpl) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessagePublishedDataRepositoryImpl.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(
                domainMessagePublishedDataRepositoryImpl.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo repositoryImplExportInfo(
      Path generationPath, PackageName packageName, String objectName) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            packageName.toPath().toString()),
        String.format(
            "%sImpl%s", TextConverter.toUpperCamel(objectName), FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo repositorySpringDataExportInfo(
      Path generationPath, PackageName packageName, String objectName) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            packageName.toPath().toString()),
        String.format(
            "%sSpringDataRepository%s",
            TextConverter.toUpperCamel(objectName), FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo makeExportInfo(
      Path generationPath, PackageName packageName, String objectName) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            packageName.toPath().toString()),
        String.format(
            "%s%s", TextConverter.toUpperCamel(objectName), FolderFileConstants.JAVA_POSTFIX));
  }
}
