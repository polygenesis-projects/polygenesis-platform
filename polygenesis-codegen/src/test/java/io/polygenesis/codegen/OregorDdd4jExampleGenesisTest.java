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

package io.polygenesis.codegen;

import com.oregor.trinity.scaffolder.java.core.ProjectDescription;
import com.oregor.trinity.scaffolder.java.core.ProjectDescriptionBuilder;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJava;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJavaFactory;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.core.Model;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepositoryImpl;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Angular:
 *
 * <p>ng new --routing=true --style=scss trinity4j-example-angular && cd trinity4j-example-angular
 *
 * @author Christos Tsakostas
 */
@Ignore
public class OregorDdd4jExampleGenesisTest {

  private static final String ANGULAR_EXPORT_PATH =
      "/Users/tsakostas/work/repo/github/polygenesis/polygenesis-platform/polygenesis-codegen/"
          + "tmp/trinity4j-example-angular/src";

  private static final String JAVA_EXPORT_PATH =
      "/Users/tsakostas/work/repo/github/polygenesis/polygenesis-platform/polygenesis-codegen/tmp";
  private static final String JAVA_PROJECT_FOLDER = "trinity4j-example";
  private static final String JAVA_MODULE_PREFIX = "example";
  /** The constant JAVA_ROOT_PACKAGE. */
  public static final String JAVA_ROOT_PACKAGE = "com.oregor.trinity4j.example";

  public static final String JAVA_CONTEXT = "example";

  private static final String TABLE_PREFIX = "ddd_";

  @Test
  public void shouldGenerateForAnnotationsAndStateDeducer() {
    scaffoldJava();
    generateJava();

    // Scaffold Angular:
    // ng new --routing=true --style=scss trinity4j-example-angular && cd
    // trinity4j-example-angular
    generateAngular();
  }

  private void scaffoldJava() {
    TrinityScaffolderJava trinityScaffolderJava = TrinityScaffolderJavaFactory.newInstance();

    ProjectDescription projectDescription = makeProjectDescription();

    trinityScaffolderJava.scaffold(
        Paths.get(JAVA_EXPORT_PATH, JAVA_PROJECT_FOLDER), projectDescription);
  }

  private ProjectDescription makeProjectDescription() {
    ProjectDescription projectDescription =
        new ProjectDescriptionBuilder()
            .setContext(TextConverter.toLowerHyphen("trinity4j-example"))
            .setTablePrefix(TABLE_PREFIX)
            .setGroupId(JAVA_ROOT_PACKAGE)
            .setArtifactId("trinity4j-example")
            .setModulePrefix(JAVA_MODULE_PREFIX)
            .setVersion("0.0.1-SNAPSHOT")
            .setName("OREGOR TRINITY4J Example Microservice")
            .setDescription("Example microservice demonstrating the usage of TRINITY4J")
            .setUrl("https://www.oregor.com")
            .setInceptionYear("2019")
            .setOrganizationName("OREGOR LTD")
            .setOrganizationUrl("https://www.oregor.com")
            .setLicenseName("The Apache License, Version 2.0")
            .setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt")
            .setDistributionProfile("ossrh-oregor")
            .setScmConnection("scm:git:git://github.com/oregor-projects/trinity4j-example.git")
            .setScmDeveloperConnection(
                "scm:git:git@github.com:oregor-projects/trinity4j-example.git")
            .setScmUrl("https://github.com/oregor-projects/trinity4j-example")
            .createProjectDescription();

    return projectDescription;
  }

  private void generateJava() {
    Genesis genesis = new Genesis();

    Set<Deducer<? extends ModelRepository<? extends Model>>> deducers =
        GenesisDefault.javaDeducers(JAVA_ROOT_PACKAGE);

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

    Set<Deducer<? extends ModelRepository<? extends Model>>> deducers =
        GenesisDefault.angularDeducers();
    Set<Generator> generators = GenesisDefault.angularGenerators(ANGULAR_EXPORT_PATH);

    Set<Thing> allThings = new LinkedHashSet<>();
    allThings.addAll(OregorDdd4jThings.get(rootPackageName));

    genesis.generate(new ThingRepositoryImpl(allThings), deducers, generators);
  }
}
