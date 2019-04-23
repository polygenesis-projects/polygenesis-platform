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

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.deducers.apiimpl.DomainEntityConverterDeducerFactory;
import io.polygenesis.deducers.apiimpl.ServiceImplementationDeducerFactory;
import io.polygenesis.deducers.sql.SqlIndexDeducerFactory;
import io.polygenesis.deducers.sql.SqlTableDeducerFactory;
import io.polygenesis.generators.angular.AngularGeneratorFactory;
import io.polygenesis.generators.java.api.JavaApiGeneratorFactory;
import io.polygenesis.generators.java.apidetail.JavaApiDetailGeneratorFactory;
import io.polygenesis.generators.java.domain.JavaDomainGeneratorFactory;
import io.polygenesis.generators.java.domainserviceimpl.DomainServiceImplementationGeneratorFactory;
import io.polygenesis.generators.java.rdbms.JavaRdbmsGeneratorFactory;
import io.polygenesis.generators.java.rest.JavaApiRestGeneratorFactory;
import io.polygenesis.generators.sql.SqlGeneratorFactory;
import io.polygenesis.models.api.ApiDeducerFactory;
import io.polygenesis.models.domain.DomainDeducerFactory;
import io.polygenesis.models.domain.DomainServiceDeducerFactory;
import io.polygenesis.models.domain.SupportiveEntityDeducerFactory;
import io.polygenesis.models.reactivestate.ReactiveStateFactory;
import io.polygenesis.models.rest.RestDeducerFactory;
import io.polygenesis.models.ui.UiFeatureDeducerFactory;
import io.polygenesis.models.ui.UiLayoutContainerDeducerFactory;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Genesis default.
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
   * Java deducers set.
   *
   * @param rootPackageName the root package name
   * @return the set
   */
  @SuppressWarnings("rawtypes")
  public static Set<Deducer> javaDeducers(String rootPackageName) {
    PackageName packageName = new PackageName(rootPackageName);

    return new LinkedHashSet<>(
        Arrays.asList(
            ApiDeducerFactory.newInstance(packageName),
            DomainDeducerFactory.newInstance(packageName),
            DomainServiceDeducerFactory.newInstance(packageName),
            DomainEntityConverterDeducerFactory.newInstance(),
            SupportiveEntityDeducerFactory.newInstance(packageName),
            ServiceImplementationDeducerFactory.newInstance(),
            RestDeducerFactory.newInstance(packageName),
            SqlTableDeducerFactory.newInstance(),
            SqlIndexDeducerFactory.newInstance()));
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
  public static Set<Generator> javaGenerators(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String tablePrefix,
      String rootPackageName) {
    PackageName packageName = new PackageName(rootPackageName);

    return new LinkedHashSet<>(
        Arrays.asList(
            JavaApiGeneratorFactory.newInstance(
                Paths.get(exportPath, projectFolder, modulePrefix + "-" + API)),
            JavaApiDetailGeneratorFactory.newInstance(
                Paths.get(exportPath, projectFolder, modulePrefix + "-" + API_DETAIL), packageName),
            JavaApiRestGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-" + API_CLIENTS,
                    modulePrefix + "-" + API_CLIENT_REST_SPRING),
                packageName,
                new ObjectName(context)),
            JavaRdbmsGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-" + DOMAIN_DETAILS,
                    modulePrefix + "-" + DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA),
                packageName,
                new ObjectName(context)),
            JavaDomainGeneratorFactory.newInstance(
                Paths.get(exportPath, projectFolder, modulePrefix + "-" + DOMAIN),
                packageName,
                tablePrefix),
            DomainServiceImplementationGeneratorFactory.newInstance(
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
                tablePrefix)));
  }

  /**
   * Angular deducers set.
   *
   * @return the set
   */
  @SuppressWarnings("rawtypes")
  public static Set<Deducer> angularDeducers() {
    PackageName packageName = new PackageName("com.oregor.dummy");

    return new LinkedHashSet<>(
        Arrays.asList(
            ApiDeducerFactory.newInstance(packageName),
            ReactiveStateFactory.newInstance(),
            UiFeatureDeducerFactory.newInstance(),
            UiLayoutContainerDeducerFactory.newInstance()));
  }

  /**
   * Angular generators set.
   *
   * @param angularExportPath the angular export path
   * @return the set
   */
  public static Set<Generator> angularGenerators(String angularExportPath) {
    return new LinkedHashSet<>(
        Arrays.asList(AngularGeneratorFactory.newInstance(Paths.get(angularExportPath))));
  }
}
