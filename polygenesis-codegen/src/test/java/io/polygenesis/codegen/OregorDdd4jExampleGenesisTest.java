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

package io.polygenesis.codegen;

import io.polygenesis.commons.text.Name;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.core.ThingRepositoryImpl;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.deducers.apiimpl.ApiImplDeducerFactory;
import io.polygenesis.deducers.sql.SqlDeducerFactory;
import io.polygenesis.generators.angular.AngularGeneratorFactory;
import io.polygenesis.generators.java.api.JavaApiGeneratorFactory;
import io.polygenesis.generators.java.apiimpl.JavaApiImplGeneratorFactory;
import io.polygenesis.generators.java.domain.JavaDomainGeneratorFactory;
import io.polygenesis.generators.java.rdbms.JavaRdbmsGeneratorFactory;
import io.polygenesis.generators.java.rest.JavaApiRestGeneratorFactory;
import io.polygenesis.generators.sql.SqlGeneratorFactory;
import io.polygenesis.models.api.ApiDeducerFactory;
import io.polygenesis.models.domain.DomainDeducerFactory;
import io.polygenesis.models.reactivestate.ReactiveStateFactory;
import io.polygenesis.models.rest.RestDeducerFactory;
import io.polygenesis.models.ui.UiDeducerFactory;
import io.polygenesis.scaffolders.javams.ProjectDescription;
import io.polygenesis.scaffolders.javams.ScaffolderJavaMicroservice;
import io.polygenesis.scaffolders.javams.ScaffolderJavaMicroserviceFactory;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/**
 * Angular:
 *
 * <p>ng new --routing=true --style=scss oregor-ddd4j-example-angular && cd
 * oregor-ddd4j-example-angular
 *
 * @author Christos Tsakostas
 */
public class OregorDdd4jExampleGenesisTest {

  private static final String ANGULAR_EXPORT_PATH =
      "/Users/tsakostas/work/repo/github/polygenesis/polygenesis-platform/polygenesis-codegen/"
          + "tmp/oregor-ddd4j-example-angular/src";

  private static final String JAVA_EXPORT_PATH =
      "/Users/tsakostas/work/repo/github/polygenesis/polygenesis-platform/polygenesis-codegen/tmp";
  private static final String JAVA_PROJECT_FOLDER = "oregor-ddd4j-example";
  private static final String JAVA_MODULE_PREFIX = "example";
  /** The constant JAVA_ROOT_PACKAGE. */
  public static final String JAVA_ROOT_PACKAGE = "com.oregor.ddd4j.example";

  public static final String JAVA_CONTEXT = "example";

  private static final String TABLE_PREFIX = "ddd_";

  @Test
  public void shouldGenerateForAnnotationsAndStateDeducer() {
    scaffoldJava();
    generateJava();

    // Scaffold Angular:
    // ng new --routing=true --style=scss oregor-ddd4j-example-angular && cd
    // oregor-ddd4j-example-angular
    generateAngular();
  }

  private void scaffoldJava() {
    ScaffolderJavaMicroservice scaffolderJavaMicroservice =
        ScaffolderJavaMicroserviceFactory.newInstance(Paths.get(JAVA_EXPORT_PATH));

    ProjectDescription projectDescription = makeProjectDescription();

    scaffolderJavaMicroservice.scaffold(
        Paths.get(JAVA_EXPORT_PATH, JAVA_PROJECT_FOLDER), projectDescription);
  }

  private ProjectDescription makeProjectDescription() {
    ProjectDescription projectDescription = new ProjectDescription();

    projectDescription.setContext(TextConverter.toLowerHyphen("ddd4j-example"));
    projectDescription.setTablePrefix(TABLE_PREFIX);
    projectDescription.setGroupId(JAVA_ROOT_PACKAGE);
    projectDescription.setArtifactId("oregor-ddd4j-example");
    projectDescription.setModulePrefix(JAVA_MODULE_PREFIX);
    projectDescription.setVersion("0.0.1-SNAPSHOT");
    projectDescription.setName("OREGOR DDD4J Example Microservice");
    projectDescription.setDescription("Example microservice demonstrating the usage of DDD4J");
    projectDescription.setUrl("https://www.oregor.com");
    projectDescription.setInceptionYear("2019");
    projectDescription.setOrganizationName("OREGOR LTD");
    projectDescription.setOrganizationUrl("https://www.oregor.com");
    projectDescription.setLicenseName("The Apache License, Version 2.0");
    projectDescription.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt");
    projectDescription.setDistributionProfile("ossrh-oregor");

    projectDescription.setScmConnection(
        "scm:git:git://github.com/oregor-projects/oregor-ddd4j-example.git");
    projectDescription.setScmDeveloperConnection(
        "scm:git:git@github.com:oregor-projects/oregor-ddd4j-example.git");
    projectDescription.setScmUrl("https://github.com/oregor-projects/oregor-ddd4j-example");

    return projectDescription;
  }

  private void generateJava() {
    Genesis genesis = new Genesis();

    Set<Deducer> deducers =
        new LinkedHashSet<>(
            Arrays.asList(
                ApiDeducerFactory.newInstance(new PackageName(JAVA_ROOT_PACKAGE)),
                DomainDeducerFactory.newInstance(new PackageName(JAVA_ROOT_PACKAGE)),
                ApiImplDeducerFactory.newInstance(new PackageName(JAVA_ROOT_PACKAGE)),
                RestDeducerFactory.newInstance(new PackageName(JAVA_ROOT_PACKAGE)),
                SqlDeducerFactory.newInstance()));

    Set<Generator> generators =
        new LinkedHashSet<>(
            Arrays.asList(
                JavaApiGeneratorFactory.newInstance(
                    Paths.get(JAVA_EXPORT_PATH, JAVA_PROJECT_FOLDER, JAVA_MODULE_PREFIX + "-api")),
                JavaApiImplGeneratorFactory.newInstance(
                    Paths.get(
                        JAVA_EXPORT_PATH, JAVA_PROJECT_FOLDER, JAVA_MODULE_PREFIX + "-api-impl"),
                    new PackageName(JAVA_ROOT_PACKAGE)),
                JavaApiRestGeneratorFactory.newInstance(
                    Paths.get(
                        JAVA_EXPORT_PATH,
                        JAVA_PROJECT_FOLDER,
                        JAVA_MODULE_PREFIX + "-primary-adapters",
                        JAVA_MODULE_PREFIX + "-rest-spring"),
                    new PackageName(JAVA_ROOT_PACKAGE),
                    new Name(JAVA_CONTEXT)),
                JavaRdbmsGeneratorFactory.newInstance(
                    Paths.get(
                        JAVA_EXPORT_PATH,
                        JAVA_PROJECT_FOLDER,
                        JAVA_MODULE_PREFIX + "-secondary-adapters",
                        JAVA_MODULE_PREFIX + "-persistence-rdbms"),
                    new PackageName(JAVA_ROOT_PACKAGE),
                    new Name(JAVA_CONTEXT)),
                JavaDomainGeneratorFactory.newInstance(
                    Paths.get(
                        JAVA_EXPORT_PATH,
                        JAVA_PROJECT_FOLDER,
                        JAVA_MODULE_PREFIX + "-domain-model"),
                    new PackageName(JAVA_ROOT_PACKAGE)),
                SqlGeneratorFactory.newInstance(
                    Paths.get(
                        JAVA_EXPORT_PATH,
                        JAVA_PROJECT_FOLDER,
                        JAVA_MODULE_PREFIX + "-secondary-adapters",
                        JAVA_MODULE_PREFIX + "-persistence-rdbms"),
                    TABLE_PREFIX)));

    genesis.generate(
        new ThingRepositoryImpl(
            new LinkedHashSet<>(Arrays.asList(ThingTodo.create(), ThingBusiness.create()))),
        deducers,
        generators);
  }

  private void generateAngular() {
    Genesis genesis = new Genesis();

    Set<Deducer> deducers =
        new LinkedHashSet<>(
            Arrays.asList(
                ApiDeducerFactory.newInstance(new PackageName(JAVA_ROOT_PACKAGE)),
                ReactiveStateFactory.newInstance(),
                UiDeducerFactory.newInstance()));

    Set<Generator> generators =
        new LinkedHashSet<>(
            Arrays.asList(AngularGeneratorFactory.newInstance(Paths.get(ANGULAR_EXPORT_PATH))));

    genesis.generate(
        new ThingRepositoryImpl(
            new LinkedHashSet<>(Arrays.asList(ThingTodo.create(), ThingBusiness.create()))),
        deducers,
        generators);
  }
}
