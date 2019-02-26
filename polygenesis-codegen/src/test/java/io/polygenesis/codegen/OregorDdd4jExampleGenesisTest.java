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
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.core.ThingRepositoryImpl;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.deducers.apiimpl.ApiImplDeducerFactory;
import io.polygenesis.generators.java.api.JavaApiGeneratorFactory;
import io.polygenesis.generators.java.apiimpl.JavaApiImplGeneratorFactory;
import io.polygenesis.generators.java.domain.JavaDomainGeneratorFactory;
import io.polygenesis.generators.java.rdbms.JavaRdbmsGeneratorFactory;
import io.polygenesis.generators.java.rest.JavaApiRestGeneratorFactory;
import io.polygenesis.models.api.ApiDeducerFactory;
import io.polygenesis.models.domain.DomainDeducerFactory;
import io.polygenesis.models.rest.RestDeducerFactory;
import io.polygenesis.scaffolders.javams.ProjectDescription;
import io.polygenesis.scaffolders.javams.ScaffolderJavaMicroservice;
import io.polygenesis.scaffolders.javams.ScaffolderJavaMicroserviceFactory;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class OregorDdd4jExampleGenesisTest {

  private static final String EXPORT_PATH =
      "/Users/tsakostas/work/repo/github/polygenesis/polygenesis-platform/polygenesis-codegen/tmp";
  private static final String PROJECT_FOLDER = "oregor-ddd4j-example";
  private static final String MODULE_PREFIX = "example";
  /** The constant ROOT_PACKAGE. */
  public static final String ROOT_PACKAGE = "com.oregor.ddd4j.example";

  public static final String CONTEXT = "example";

  @Test
  public void shouldGenerateForAnnotationsAndStateDeducer() {
    scaffold();
    generate();
  }

  private void scaffold() {
    ScaffolderJavaMicroservice scaffolderJavaMicroservice =
        ScaffolderJavaMicroserviceFactory.newInstance(Paths.get(EXPORT_PATH));

    ProjectDescription projectDescription = makeProjectDescription();

    scaffolderJavaMicroservice.scaffold(Paths.get(EXPORT_PATH, PROJECT_FOLDER), projectDescription);
  }

  private ProjectDescription makeProjectDescription() {
    ProjectDescription projectDescription = new ProjectDescription();
    projectDescription.setGroupId(ROOT_PACKAGE);
    projectDescription.setArtifactId("oregor-ddd4j-example");
    projectDescription.setModulePrefix(MODULE_PREFIX);
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

  private void generate() {
    Genesis genesis = new Genesis();

    Set<Deducer> deducers =
        new LinkedHashSet<>(
            Arrays.asList(
                ApiDeducerFactory.newInstance(new PackageName(ROOT_PACKAGE)),
                DomainDeducerFactory.newInstance(new PackageName(ROOT_PACKAGE)),
                ApiImplDeducerFactory.newInstance(new PackageName(ROOT_PACKAGE)),
                RestDeducerFactory.newInstance(new PackageName(ROOT_PACKAGE))));

    Set<Generator> generators =
        new LinkedHashSet<>(
            Arrays.asList(
                JavaApiGeneratorFactory.newInstance(
                    Paths.get(EXPORT_PATH, PROJECT_FOLDER, MODULE_PREFIX + "-api")),
                JavaApiImplGeneratorFactory.newInstance(
                    Paths.get(EXPORT_PATH, PROJECT_FOLDER, MODULE_PREFIX + "-api-impl"),
                    new PackageName(ROOT_PACKAGE)),
                JavaApiRestGeneratorFactory.newInstance(
                    Paths.get(
                        EXPORT_PATH,
                        PROJECT_FOLDER,
                        MODULE_PREFIX + "-primary-adapters",
                        MODULE_PREFIX + "-rest-spring"),
                    new PackageName(ROOT_PACKAGE),
                    new Name(CONTEXT)),
                JavaRdbmsGeneratorFactory.newInstance(
                    Paths.get(
                        EXPORT_PATH,
                        PROJECT_FOLDER,
                        MODULE_PREFIX + "-secondary-adapters",
                        MODULE_PREFIX + "-persistence-rdbms"),
                    new PackageName(ROOT_PACKAGE),
                    new Name(CONTEXT)),
                JavaDomainGeneratorFactory.newInstance(
                    Paths.get(EXPORT_PATH, PROJECT_FOLDER, MODULE_PREFIX + "-domain-model"),
                    new PackageName(ROOT_PACKAGE))));

    genesis.generate(
        new ThingRepositoryImpl(new LinkedHashSet<>(Arrays.asList(ThingBusiness.create()))),
        deducers,
        generators);
  }
}
