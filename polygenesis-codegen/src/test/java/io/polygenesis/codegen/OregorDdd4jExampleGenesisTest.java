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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepositoryImpl;
import io.polygenesis.scaffolders.javams.ProjectDescription;
import io.polygenesis.scaffolders.javams.ScaffolderJavaMicroservice;
import io.polygenesis.scaffolders.javams.ScaffolderJavaMicroserviceFactory;
import java.nio.file.Paths;
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

    Set<Deducer> deducers = GenesisDefault.javaDeducers(JAVA_ROOT_PACKAGE);

    Set<Generator> generators =
        GenesisDefault.javaGenerators(
            JAVA_EXPORT_PATH,
            JAVA_PROJECT_FOLDER,
            JAVA_MODULE_PREFIX,
            JAVA_CONTEXT,
            TABLE_PREFIX,
            JAVA_ROOT_PACKAGE);

    Set<Thing> allThings = new LinkedHashSet<>();
    allThings.addAll(OregorDdd4jThings.get(JAVA_ROOT_PACKAGE));

    genesis.generate(new ThingRepositoryImpl(allThings), deducers, generators);
  }

  private void generateAngular() {
    String rootPackageName = "com.oregor.dummy";

    Genesis genesis = new Genesis();

    Set<Deducer> deducers = GenesisDefault.angularDeducers();
    Set<Generator> generators = GenesisDefault.angularGenerators(ANGULAR_EXPORT_PATH);

    Set<Thing> allThings = new LinkedHashSet<>();
    allThings.addAll(OregorDdd4jThings.get(rootPackageName));

    genesis.generate(new ThingRepositoryImpl(allThings), deducers, generators);
  }
}
