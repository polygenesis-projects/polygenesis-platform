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

package io.polygenesis.generators.java;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.MetamodelGenerator;
import io.polygenesis.generators.java.api.JavaApiGeneratorFactory;
import io.polygenesis.generators.java.api.JavaApiMetamodelGenerator;
import io.polygenesis.generators.java.apiclients.rest.ApiClientRestMetamodelGenerator;
import io.polygenesis.generators.java.apiclients.rest.ApiClientRestMetamodelGeneratorFactory;
import io.polygenesis.generators.java.apidetail.JavaApiDetailMetamodelGenerator;
import io.polygenesis.generators.java.apidetail.JavaApiDetailMetamodelGeneratorFactory;
import io.polygenesis.generators.java.aux.AuxMetamodelGenerator;
import io.polygenesis.generators.java.aux.AuxMetamodelGeneratorFactory;
import io.polygenesis.generators.java.auxdetails.propertyfile.AuxDetailPropertyFileMetamodelGenerator;
import io.polygenesis.generators.java.auxdetails.propertyfile.AuxDetailPropertyFileMetamodelGeneratorFactory;
import io.polygenesis.generators.java.batchprocess.BatchProcessMetamodelGenerator;
import io.polygenesis.generators.java.batchprocess.BatchProcessMetamodelGeneratorFactory;
import io.polygenesis.generators.java.batchprocessactivemq.BatchProcessActiveMqMetamodelGenerator;
import io.polygenesis.generators.java.batchprocessactivemq.BatchProcessActiveMqMetamodelGeneratorFactory;
import io.polygenesis.generators.java.batchprocessscheduler.BatchProcessSchedulerMetamodelGenerator;
import io.polygenesis.generators.java.batchprocessscheduler.BatchProcessSchedulerMetamodelGeneratorFactory;
import io.polygenesis.generators.java.batchprocesssubscriber.BatchProcessSubscriberMetamodelGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.BatchProcessSubscriberMetamodelGeneratorFactory;
import io.polygenesis.generators.java.domain.JavaDomainMetamodelGenerator;
import io.polygenesis.generators.java.domain.JavaDomainMetamodelGeneratorFactory;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.DomainMessagePublisherMetamodelGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.DomainMessagePublisherMetamodelGeneratorFactory;
import io.polygenesis.generators.java.domainmessageactivemq.DomainMessageActiveMqMetamodelGenerator;
import io.polygenesis.generators.java.domainmessageactivemq.DomainMessageActiveMqMetamodelGeneratorFactory;
import io.polygenesis.generators.java.domainmessagesubscriber.DomainMessageSubscriberMetamodelGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.DomainMessageSubscriberMetamodelGeneratorFactory;
import io.polygenesis.generators.java.domainservicedetail.DomainServiceDetailMetamodelGenerator;
import io.polygenesis.generators.java.domainservicedetail.DomainServiceDetailMetamodelGeneratorFactory;
import io.polygenesis.generators.java.rdbms.JavaRdbmsMetamodelGenerator;
import io.polygenesis.generators.java.rdbms.JavaRdbmsMetamodelGeneratorFactory;
import io.polygenesis.generators.java.repository.inmemory.InMemoryMetamodelGenerator;
import io.polygenesis.generators.java.repository.inmemory.InMemoryMetamodelGeneratorFactory;
import io.polygenesis.generators.sql.SqlGeneratorFactory;
import io.polygenesis.generators.sql.SqlMetamodelGenerator;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Trinity java context generator factory.
 *
 * @author Christos Tsakostas
 */
public final class TrinityJavaContextGeneratorFactory {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static final String API = "api";
  private static final String API_DETAIL = "api-detail";
  private static final String API_CLIENTS = "api-clients";
  private static final String API_CLIENT_REST_SPRING = "api-client-rest-spring";

  // AUX
  private static final String AUX = "aux";
  private static final String AUX_DETAILS = "aux-details";
  private static final String AUX_DETAIL_PROPERTY_FILE = "aux-detail-property-file";

  // BATCH PROCESS
  private static final String API_CLIENT_BATCH_PROCESS_SUBSCRIBER =
      "api-client-batch-process-subscriber";
  private static final String API_CLIENT_BATCH_PROCESS_MESSAGING_ACTIVEMQ =
      "api-client-batch-process-subscriber-activemq";
  private static final String API_CLIENT_BATCH_PROCESS_SCHEDULER_CAMEL =
      "api-client-batch-process-subscriber-activemq";

  // DOMAIN MESSAGE
  private static final String API_CLIENT_DOMAIN_MESSAGE_SUBSCRIBER =
      "api-client-domain-message-subscriber";
  private static final String API_CLIENT_DOMAIN_MESSAGE_SUBSCRIBER_ACTIVEMQ =
      "api-client-domain-message-subscriber-activemq";

  private static final String DOMAIN = "domain";
  private static final String DOMAIN_DETAILS = "domain-details";
  private static final String DOMAIN_DETAIL_SERVICES = "domain-detail-services";
  private static final String DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA =
      "domain-detail-repository-springdatajpa";

  private static final String DOMAIN_DETAIL_REPOSITORY_IN_MEMORY =
      "domain-detail-repository-inmemory";

  private static final String DOMAIN_DETAIL_DOMAIN_MESSAGE_PUBLISHER_ACTIVEMQ =
      "domain-detail-domain-message-publisher-activemq";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private TrinityJavaContextGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance trinity java context generator.
   *
   * @param generationPath the generation path
   * @param trinityJavaContextGeneratorEnablement the trinity java context generator enablement
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param contextFolder the context folder
   * @param modulePrefix the module prefix
   * @param context the context
   * @param tablePrefix the table prefix
   * @param rootPackageName the root package name
   * @return the trinity java context generator
   */
  public static TrinityJavaContextGenerator newInstance(
      Path generationPath,
      TrinityJavaContextGeneratorEnablement trinityJavaContextGeneratorEnablement,
      String exportPath,
      String projectFolder,
      String contextFolder,
      String modulePrefix,
      String context,
      String tablePrefix,
      String rootPackageName) {
    Set<MetamodelGenerator> metamodelGenerators = new LinkedHashSet<>();

    projectFolder = Paths.get(projectFolder, contextFolder).toString();
    ContextName contextName = new ContextName(context);
    PackageName rootPackageNameVo = new PackageName(rootPackageName);

    if (trinityJavaContextGeneratorEnablement.isJavaApiGenerator()) {
      metamodelGenerators.add(javaApiGenerator(exportPath, projectFolder, modulePrefix));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaApiDetailGenerator()) {
      metamodelGenerators.add(
          javaApiDetailGenerator(
              exportPath, projectFolder, modulePrefix, context, rootPackageName));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaApiRestGenerator()) {
      metamodelGenerators.add(
          javaApiRestGenerator(
              exportPath, projectFolder, modulePrefix, contextName, rootPackageName));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaDomainGenerator()) {
      metamodelGenerators.add(
          javaDomainGenerator(
              exportPath, projectFolder, modulePrefix, tablePrefix, rootPackageName, context));
    }

    if (trinityJavaContextGeneratorEnablement.isDomainServiceImplementationGenerator()) {
      metamodelGenerators.add(
          domainServiceImplementationGenerator(exportPath, projectFolder, modulePrefix));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaRdbmsGenerator()) {
      metamodelGenerators.add(
          javaRdbmsGenerator(exportPath, projectFolder, modulePrefix, context, rootPackageName));
    }

    if (trinityJavaContextGeneratorEnablement.isDomainDetailRepositoryInMemory()) {
      metamodelGenerators.add(
          inMemoryMetamodelGenerator(
              exportPath, projectFolder, modulePrefix, context, rootPackageName));
    }

    if (trinityJavaContextGeneratorEnablement.isApiClientDomainMessageSubscriber()) {
      metamodelGenerators.add(
          apiClientDomainMessageSubscriber(
              exportPath, projectFolder, modulePrefix, rootPackageName, context));
    }

    if (trinityJavaContextGeneratorEnablement.isApiClientDomainMessageSubscriberActiveMq()) {
      metamodelGenerators.add(
          apiClientDomainMessageSubscriberActiveMq(
              exportPath, projectFolder, modulePrefix, rootPackageName, context));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaDomainSqlGenerator()) {
      metamodelGenerators.add(
          sqlMetamodelGenerator(exportPath, projectFolder, modulePrefix, tablePrefix, context));
    }

    if (trinityJavaContextGeneratorEnablement.isApiClientBatchProcess()) {
      metamodelGenerators.add(
          apiClientBatchProcess(exportPath, projectFolder, modulePrefix, context));
    }

    if (trinityJavaContextGeneratorEnablement.isApiClientBatchProcessMessageSubscriber()) {
      metamodelGenerators.add(
          apiClientBatchProcessSubscriber(
              exportPath, projectFolder, modulePrefix, rootPackageName, context));
    }

    if (trinityJavaContextGeneratorEnablement.isApiClientBatchProcessMessagingActivemq()) {
      metamodelGenerators.add(
          apiClientBatchProcessMessagingActiveMq(
              exportPath, projectFolder, modulePrefix, rootPackageName, context));
    }

    if (trinityJavaContextGeneratorEnablement.isApiClientBatchProcessSchedulerCamel()) {
      metamodelGenerators.add(
          apiClientBatchProcessSchedulerCamel(
              exportPath, projectFolder, modulePrefix, rootPackageName, context));
    }

    // =============================================================================================
    // AUX & DETAILS
    if (trinityJavaContextGeneratorEnablement.isAux()) {
      metamodelGenerators.add(
          auxMetamodelGenerator(
              exportPath, projectFolder, modulePrefix, contextName, rootPackageNameVo));
    }

    if (trinityJavaContextGeneratorEnablement.isAuxDetailPropertyFile()) {
      metamodelGenerators.add(
          auxDetailPropertyFileMetamodelGenerator(
              exportPath, projectFolder, modulePrefix, contextName, rootPackageNameVo));
    }

    // =============================================================================================
    // DOMAIN DETAILS
    if (trinityJavaContextGeneratorEnablement.isDomainDetailDomainMessagePublisher()) {
      metamodelGenerators.add(
          domainDetailDomainMessagePublisher(
              exportPath, projectFolder, modulePrefix, rootPackageName, context));
    }

    return new TrinityJavaContextGenerator(generationPath, metamodelGenerators);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static JavaApiMetamodelGenerator javaApiGenerator(
      String exportPath, String projectFolder, String modulePrefix) {
    return JavaApiGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + API));
  }

  private static JavaApiDetailMetamodelGenerator javaApiDetailGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String rootPackageName) {
    return JavaApiDetailMetamodelGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + API_DETAIL),
        new ContextName(context),
        new PackageName(rootPackageName));
  }

  private static ApiClientRestMetamodelGenerator javaApiRestGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      ContextName contextName,
      String rootPackageName) {
    return ApiClientRestMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_REST_SPRING),
        new PackageName(rootPackageName),
        contextName);
  }

  // AUX
  private static AuxMetamodelGenerator auxMetamodelGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      ContextName contextName,
      PackageName rootPackageName) {
    return AuxMetamodelGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + AUX),
        contextName,
        rootPackageName);
  }

  private static AuxDetailPropertyFileMetamodelGenerator auxDetailPropertyFileMetamodelGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      ContextName contextName,
      PackageName rootPackageName) {
    return AuxDetailPropertyFileMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + AUX_DETAILS,
            modulePrefix + "-" + AUX_DETAIL_PROPERTY_FILE),
        contextName,
        rootPackageName);
  }

  private static JavaRdbmsMetamodelGenerator javaRdbmsGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String rootPackageName) {
    return JavaRdbmsMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + DOMAIN_DETAILS,
            modulePrefix + "-" + DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA),
        new PackageName(rootPackageName),
        new ObjectName(context));
  }

  private static InMemoryMetamodelGenerator inMemoryMetamodelGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String rootPackageName) {
    return InMemoryMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + DOMAIN_DETAILS,
            modulePrefix + "-" + DOMAIN_DETAIL_REPOSITORY_IN_MEMORY),
        new PackageName(rootPackageName),
        new ObjectName(context));
  }

  private static JavaDomainMetamodelGenerator javaDomainGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String tablePrefix,
      String rootPackageName,
      String context) {
    return JavaDomainMetamodelGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + DOMAIN),
        new ContextName(context),
        new PackageName(rootPackageName),
        tablePrefix);
  }

  private static DomainServiceDetailMetamodelGenerator domainServiceImplementationGenerator(
      String exportPath, String projectFolder, String modulePrefix) {
    return DomainServiceDetailMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + DOMAIN_DETAILS,
            modulePrefix + "-" + DOMAIN_DETAIL_SERVICES));
  }

  private static DomainMessageSubscriberMetamodelGenerator apiClientDomainMessageSubscriber(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String rootPackageName,
      String context) {
    return DomainMessageSubscriberMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_DOMAIN_MESSAGE_SUBSCRIBER),
        new PackageName(rootPackageName),
        new ContextName(context));
  }

  private static DomainMessageActiveMqMetamodelGenerator apiClientDomainMessageSubscriberActiveMq(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String rootPackageName,
      String context) {
    return DomainMessageActiveMqMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_DOMAIN_MESSAGE_SUBSCRIBER_ACTIVEMQ),
        new PackageName(rootPackageName),
        new ContextName(context));
  }

  private static BatchProcessMetamodelGenerator apiClientBatchProcess(
      String exportPath, String projectFolder, String modulePrefix, String context) {
    return BatchProcessMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_BATCH_PROCESS_SUBSCRIBER),
        new ContextName(context));
  }

  private static BatchProcessSubscriberMetamodelGenerator apiClientBatchProcessSubscriber(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String rootPackageName,
      String context) {
    return BatchProcessSubscriberMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_BATCH_PROCESS_SUBSCRIBER),
        new PackageName(rootPackageName),
        new ContextName(context));
  }

  private static BatchProcessActiveMqMetamodelGenerator apiClientBatchProcessMessagingActiveMq(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String rootPackageName,
      String context) {
    return BatchProcessActiveMqMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_BATCH_PROCESS_MESSAGING_ACTIVEMQ),
        new PackageName(rootPackageName),
        new ContextName(context));
  }

  private static BatchProcessSchedulerMetamodelGenerator apiClientBatchProcessSchedulerCamel(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String rootPackageName,
      String context) {
    return BatchProcessSchedulerMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_BATCH_PROCESS_SCHEDULER_CAMEL),
        new PackageName(rootPackageName),
        new ContextName(context));
  }

  private static SqlMetamodelGenerator sqlMetamodelGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String tablePrefix,
      String context) {
    return SqlGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + "domain-details",
            modulePrefix + "-" + "domain-detail-repository-springdatajpa"),
        new ContextName(context),
        tablePrefix);
  }

  private static DomainMessagePublisherMetamodelGenerator domainDetailDomainMessagePublisher(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String rootPackageName,
      String context) {
    return DomainMessagePublisherMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + DOMAIN_DETAILS,
            modulePrefix + "-" + DOMAIN_DETAIL_DOMAIN_MESSAGE_PUBLISHER_ACTIVEMQ),
        new PackageName(rootPackageName),
        new ContextName(context));
  }
}
