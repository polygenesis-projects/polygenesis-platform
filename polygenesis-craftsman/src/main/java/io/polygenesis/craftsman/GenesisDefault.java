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

package io.polygenesis.craftsman;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelGenerator;
import io.polygenesis.deducers.apiimpl.DomainObjectConverterDeducerFactory;
import io.polygenesis.deducers.apiimpl.ServiceImplementationDeducerFactory;
import io.polygenesis.deducers.batchprocess.BatchProcessDeducerFactory;
import io.polygenesis.deducers.messaging.subscriber.SubscriberDeducerFactory;
import io.polygenesis.deducers.scheduler.SchedulerDeducerFactory;
import io.polygenesis.deducers.sql.SqlIndexDeducerFactory;
import io.polygenesis.deducers.sql.SqlTableDeducerFactory;
import io.polygenesis.deducers.stateprovider.ProviderDeducerFactory;
import io.polygenesis.deducers.stateredux.StateReduxDeducerFactory;
import io.polygenesis.deducers.ui.UiContextDeducerFactory;
import io.polygenesis.generators.java.api.JavaApiGeneratorFactory;
import io.polygenesis.generators.java.api.JavaApiMetamodelGenerator;
import io.polygenesis.generators.java.apiclients.rest.JavaApiRestGeneratorFactory;
import io.polygenesis.generators.java.apidetail.JavaApiDetailMetamodelGenerator;
import io.polygenesis.generators.java.apidetail.JavaApiDetailMetamodelGeneratorFactory;
import io.polygenesis.generators.java.domain.JavaDomainMetamodelGeneratorFactory;
import io.polygenesis.generators.java.domainservicedetail.DomainServiceDetailMetamodelGeneratorFactory;
import io.polygenesis.generators.java.rdbms.JavaRdbmsMetamodelGeneratorFactory;
import io.polygenesis.generators.sql.SqlGeneratorFactory;
import io.polygenesis.models.api.ApiDeducerFactory;
import io.polygenesis.models.domain.DomainDeducerFactory;
import io.polygenesis.models.domain.DomainServiceDeducerFactory;
import io.polygenesis.models.domain.projection.ProjectionDeducerFactory;
import io.polygenesis.models.domain.supportiveentity.SupportiveEntityDeducerFactory;
import io.polygenesis.models.rest.RestDeducerFactory;
import io.polygenesis.models.ui.UiFeatureDeducerFactory;
import io.polygenesis.models.ui.UiLayoutContainerDeducerFactory;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Creator default.
 *
 * @author Christos Tsakostas
 */
public class GenesisDefault {

  private static final String API = "api";
  private static final String API_DETAIL = "api-detail";
  private static final String API_CLIENTS = "api-clients";
  private static final String API_CLIENT_REST_SPRING = "api-client-rest-spring";
  private static final String DOMAIN = "domain";
  private static final String DOMAIN_DETAILS = "domain-details";
  private static final String DOMAIN_DETAIL_SERVICES = "domain-detail-services";
  private static final String DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA =
      "domain-detail-repository-springdatajpa";

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private GenesisDefault() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // METHODS
  // ===============================================================================================

  /**
   * Java deducers proper generics set.
   *
   * @param rootPackageName the root package name
   * @return the set
   */
  public static Set<Deducer<?>> javaDeducers(String rootPackageName) {
    PackageName packageName = new PackageName(rootPackageName);

    return new LinkedHashSet<>(
        Arrays.asList(
            ApiDeducerFactory.newInstance(packageName),
            DomainDeducerFactory.newInstance(packageName),
            ProjectionDeducerFactory.newInstance(packageName),
            DomainServiceDeducerFactory.newInstance(packageName),
            DomainObjectConverterDeducerFactory.newInstance(),
            SupportiveEntityDeducerFactory.newInstance(packageName),
            ServiceImplementationDeducerFactory.newInstance(),
            RestDeducerFactory.newInstance(packageName),
            SqlTableDeducerFactory.newInstance(),
            SqlIndexDeducerFactory.newInstance(),
            SubscriberDeducerFactory.newInstance(packageName),
            SchedulerDeducerFactory.newInstance(packageName),
            BatchProcessDeducerFactory.newInstance(packageName)));
  }

  /**
   * Angular deducers set.
   *
   * @return the set
   */
  public static Set<Deducer<?>> angularDeducers() {
    PackageName packageName = new PackageName("com.oregor.dummy");

    return new LinkedHashSet<>(
        Arrays.asList(
            ApiDeducerFactory.newInstance(packageName),
            StateReduxDeducerFactory.newInstance(),
            UiContextDeducerFactory.newInstance(),
            UiFeatureDeducerFactory.newInstance(),
            UiLayoutContainerDeducerFactory.newInstance()));
  }

  /**
   * Flutter deducers proper generics set.
   *
   * @param rootPackageName the root package name
   * @return the set
   */
  public static Set<Deducer<?>> flutterDeducers(String rootPackageName) {
    PackageName packageName = new PackageName(rootPackageName);

    return new LinkedHashSet<>(
        Arrays.asList(
            ApiDeducerFactory.newInstance(packageName),
            ProviderDeducerFactory.newInstance(),
            UiContextDeducerFactory.newInstance(),
            UiFeatureDeducerFactory.newInstance(),
            UiLayoutContainerDeducerFactory.newInstance()));
  }

  /**
   * Java generators set.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param context the context
   * @param tablePrefix the table prefix
   * @param rootPackageName the root package name
   * @return the set
   */
  public static Set<MetamodelGenerator> javaGenerators(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String tablePrefix,
      String rootPackageName) {
    PackageName packageName = new PackageName(rootPackageName);

    return new LinkedHashSet<>(
        Arrays.asList(
            javaApiGenerator(exportPath, projectFolder, modulePrefix + "-" + API),
            javaApiDetailGenerator(
                exportPath,
                projectFolder,
                modulePrefix + "-" + API_DETAIL,
                context,
                rootPackageName),
            JavaApiRestGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-" + API_CLIENTS,
                    modulePrefix + "-" + API_CLIENT_REST_SPRING),
                packageName,
                new ContextName(context)),
            JavaRdbmsMetamodelGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-" + DOMAIN_DETAILS,
                    modulePrefix + "-" + DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA),
                packageName,
                new ObjectName(context)),
            JavaDomainMetamodelGeneratorFactory.newInstance(
                Paths.get(exportPath, projectFolder, modulePrefix + "-" + DOMAIN),
                new ContextName(context),
                packageName,
                tablePrefix),
            DomainServiceDetailMetamodelGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-" + DOMAIN_DETAILS,
                    modulePrefix + "-" + DOMAIN_DETAIL_SERVICES)),
            SqlGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-" + DOMAIN_DETAILS,
                    modulePrefix + "-" + DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA),
                new ContextName(context),
                tablePrefix)));
  }

  /**
   * Java api generator java api generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @return the java api generator
   */
  public static JavaApiMetamodelGenerator javaApiGenerator(
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
  public static JavaApiDetailMetamodelGenerator javaApiDetailGenerator(
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

  //  public static Set<MetamodelGenerator> angularGenerators(String angularExportPath) {
  //    return new LinkedHashSet<>(
  //
  // Arrays.asList(LegacyAngularContextGeneratorFactory.newInstance(Paths.get(angularExportPath))));
  //  }
}
