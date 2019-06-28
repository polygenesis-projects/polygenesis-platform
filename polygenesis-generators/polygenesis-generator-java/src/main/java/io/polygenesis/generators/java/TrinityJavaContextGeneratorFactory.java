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
import io.polygenesis.generators.java.apidetail.JavaApiDetailMetamodelGenerator;
import io.polygenesis.generators.java.apidetail.JavaApiDetailMetamodelGeneratorFactory;
import io.polygenesis.generators.java.batchprocess.BatchProcessMetamodelGenerator;
import io.polygenesis.generators.java.batchprocess.BatchProcessMetamodelGeneratorFactory;
import io.polygenesis.generators.java.batchprocessactivemq.BatchProcessActiveMqMetamodelGenerator;
import io.polygenesis.generators.java.batchprocessactivemq.BatchProcessActiveMqMetamodelGeneratorFactory;
import io.polygenesis.generators.java.batchprocessscheduler.BatchProcessSchedulerMetamodelGenerator;
import io.polygenesis.generators.java.batchprocessscheduler.BatchProcessSchedulerMetamodelGeneratorFactory;
import io.polygenesis.generators.java.batchprocesssubscriber.BatchProcessSubscriberMetamodelGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.BatchProcessSubscriberMetamodelGeneratorFactory;
import io.polygenesis.generators.java.domain.JavaDomainGeneratorFactory;
import io.polygenesis.generators.java.domain.JavaDomainMetamodelGenerator;
import io.polygenesis.generators.java.domainservicedetail.DomainServiceDetailMetamodelGenerator;
import io.polygenesis.generators.java.domainservicedetail.DomainServiceDetailMetamodelGeneratorFactory;
import io.polygenesis.generators.java.messaging.MessagingGeneratorFactory;
import io.polygenesis.generators.java.messaging.MessagingMetamodelGenerator;
import io.polygenesis.generators.java.rdbms.JavaRdbmsGeneratorFactory;
import io.polygenesis.generators.java.rdbms.JavaRdbmsMetamodelGenerator;
import io.polygenesis.generators.java.rest.JavaApiRestGeneratorFactory;
import io.polygenesis.generators.java.rest.JavaApiRestMetamodelGenerator;
import io.polygenesis.generators.java.scheduler.SchedulerMetamodelGenerator;
import io.polygenesis.generators.java.scheduler.SchedulerMetamodelGeneratorFactory;
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
  // TODO
  // private static final String API_CLIENT_MESSAGING = "api-client-messaging";
  // private static final String API_CLIENT_MESSAGING = "api-client-subscriber-activemq";
  // BATCH PROCESS
  private static final String API_CLIENT_BATCH_PROCESS = "api-client-batch-process";
  private static final String API_CLIENT_BATCH_PROCESS_MESSAGE_SUBSCRIBER =
      "api-client-batch-process-message-subscriber";
  private static final String API_CLIENT_BATCH_PROCESS_MESSAGING_ACTIVEMQ =
      "api-client-batch-process-messaging-activemq";
  private static final String API_CLIENT_BATCH_PROCESS_SCHEDULER_CAMEL =
      "api-client-batch-process-scheduler-camel";

  private static final String API_CLIENT_MESSAGING = "api-client-subscriber";
  private static final String API_CLIENT_SCHEDULER = "api-client-scheduler-camel";

  private static final String DOMAIN = "domain";
  private static final String DOMAIN_DETAILS = "domain-details";
  private static final String DOMAIN_DETAIL_SERVICES = "domain-detail-services";
  private static final String DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA =
      "domain-detail-repository-springdatajpa";

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
      String modulePrefix,
      String context,
      String tablePrefix,
      String rootPackageName) {
    Set<MetamodelGenerator> metamodelGenerators = new LinkedHashSet<>();

    if (trinityJavaContextGeneratorEnablement.isJavaApiGenerator()) {
      metamodelGenerators.add(javaApiGenerator(exportPath, projectFolder, modulePrefix));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaApiDetailGenerator()) {
      metamodelGenerators.add(
          javaApiDetailGenerator(exportPath, projectFolder, modulePrefix, rootPackageName));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaApiRestGenerator()) {
      metamodelGenerators.add(
          javaApiRestGenerator(exportPath, projectFolder, modulePrefix, context, rootPackageName));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaDomainGenerator()) {
      metamodelGenerators.add(
          javaDomainGenerator(
              exportPath, projectFolder, modulePrefix, tablePrefix, rootPackageName));
    }

    if (trinityJavaContextGeneratorEnablement.isDomainServiceImplementationGenerator()) {
      metamodelGenerators.add(
          domainServiceImplementationGenerator(exportPath, projectFolder, modulePrefix));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaRdbmsGenerator()) {
      metamodelGenerators.add(
          javaRdbmsGenerator(exportPath, projectFolder, modulePrefix, context, rootPackageName));
    }

    if (trinityJavaContextGeneratorEnablement.isApiClientMessaging()) {
      metamodelGenerators.add(apiClientMessaging(exportPath, projectFolder, modulePrefix));
    }

    if (trinityJavaContextGeneratorEnablement.isJavaDomainSqlGenerator()) {
      metamodelGenerators.add(
          sqlMetamodelGenerator(exportPath, projectFolder, modulePrefix, tablePrefix));
    }

    if (trinityJavaContextGeneratorEnablement.isApiClientScheduler()) {
      metamodelGenerators.add(apiClientScheduler(exportPath, projectFolder, modulePrefix));
    }

    if (trinityJavaContextGeneratorEnablement.isApiClientBatchProcess()) {
      metamodelGenerators.add(apiClientBatchProcess(exportPath, projectFolder, modulePrefix));
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

    return new TrinityJavaContextGenerator(generationPath, metamodelGenerators);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Java api generator java api generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @return the java api generator
   */
  private static JavaApiMetamodelGenerator javaApiGenerator(
      String exportPath, String projectFolder, String modulePrefix) {
    return JavaApiGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + API));
  }

  /**
   * Java api detail generator java api detail generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param rootPackageName the root package name
   * @return the java api detail generator
   */
  private static JavaApiDetailMetamodelGenerator javaApiDetailGenerator(
      String exportPath, String projectFolder, String modulePrefix, String rootPackageName) {
    return JavaApiDetailMetamodelGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + API_DETAIL),
        new PackageName(rootPackageName));
  }

  /**
   * Java api rest generator java api rest generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param context the context
   * @param rootPackageName the root package name
   * @return the java api rest generator
   */
  private static JavaApiRestMetamodelGenerator javaApiRestGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String rootPackageName) {
    return JavaApiRestGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_REST_SPRING),
        new PackageName(rootPackageName),
        new ObjectName(context));
  }

  /**
   * Java rdbms generator java rdbms generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param context the context
   * @param rootPackageName the root package name
   * @return the java rdbms generator
   */
  private static JavaRdbmsMetamodelGenerator javaRdbmsGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String rootPackageName) {
    return JavaRdbmsGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + DOMAIN_DETAILS,
            modulePrefix + "-" + DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA),
        new PackageName(rootPackageName),
        new ObjectName(context));
  }

  /**
   * Java domain generator java domain generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param tablePrefix the table prefix
   * @param rootPackageName the root package name
   * @return the java domain generator
   */
  private static JavaDomainMetamodelGenerator javaDomainGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String tablePrefix,
      String rootPackageName) {
    return JavaDomainGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + DOMAIN),
        new PackageName(rootPackageName),
        tablePrefix);
  }

  /**
   * Domain service implementation generator domain service implementation generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @return the domain service implementation generator
   */
  private static DomainServiceDetailMetamodelGenerator domainServiceImplementationGenerator(
      String exportPath, String projectFolder, String modulePrefix) {
    return DomainServiceDetailMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + DOMAIN_DETAILS,
            modulePrefix + "-" + DOMAIN_DETAIL_SERVICES));
  }

  /**
   * Api client messaging messaging generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @return the messaging generator
   */
  private static MessagingMetamodelGenerator apiClientMessaging(
      String exportPath, String projectFolder, String modulePrefix) {
    return MessagingGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_MESSAGING));
  }

  /**
   * Api client scheduler scheduler metamodel generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @return the scheduler metamodel generator
   */
  private static SchedulerMetamodelGenerator apiClientScheduler(
      String exportPath, String projectFolder, String modulePrefix) {
    return SchedulerMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_SCHEDULER));
  }

  /**
   * Api client periodic process periodic process metamodel generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @return the periodic process metamodel generator
   */
  private static BatchProcessMetamodelGenerator apiClientBatchProcess(
      String exportPath, String projectFolder, String modulePrefix) {
    return BatchProcessMetamodelGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_BATCH_PROCESS));
  }

  /**
   * Api client batch process subscriber batch process subscriber metamodel generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @return the batch process subscriber metamodel generator
   */
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
            modulePrefix + "-" + API_CLIENT_BATCH_PROCESS_MESSAGE_SUBSCRIBER),
        new PackageName(rootPackageName),
        new ContextName(context));
  }

  /**
   * Api client batch process messaging active mq batch process active mq metamodel generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param rootPackageName the root package name
   * @param context the context
   * @return the batch process active mq metamodel generator
   */
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

  /**
   * Api client batch process scheduler camelq batch process scheduler metamodel generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param rootPackageName the root package name
   * @param context the context
   * @return the batch process scheduler metamodel generator
   */
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

  /**
   * Sql metamodel generator sql metamodel generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param tablePrefix the table prefix
   * @return the sql metamodel generator
   */
  private static SqlMetamodelGenerator sqlMetamodelGenerator(
      String exportPath, String projectFolder, String modulePrefix, String tablePrefix) {
    return SqlGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + "domain-details",
            modulePrefix + "-" + "domain-detail-repository-springdatajpa"),
        tablePrefix);
  }
}
