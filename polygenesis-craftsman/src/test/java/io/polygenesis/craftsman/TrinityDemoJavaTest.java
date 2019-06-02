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

import static org.assertj.core.api.Assertions.assertThatCode;

import com.oregor.trinity.scaffolder.java.core.ProjectDescription;
import com.oregor.trinity.scaffolder.java.core.ProjectDescriptionBuilder;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJava;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJavaFactory;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/**
 * The type Trinity demo java scaffolder java test.
 *
 * @author Christos Tsakostas
 */
public class TrinityDemoJavaTest {

  private static final String JAVA_EXPORT_PATH = "tmp";
  private static final String JAVA_ROOT_PACKAGE = "com.oregor.trinity.demo.java";
  private static final String JAVA_PROJECT_FOLDER = "trinity-demo-java";
  private static final String JAVA_MODULE_PREFIX = "demo";
  private static final String JAVA_CONTEXT = "demo";
  private static final String TABLE_PREFIX = "dmo_";

  /**
   * Should scaffold and generate successfully.
   *
   * @throws IOException the io exception
   */
  @Test
  public void shouldScaffoldAndGenerateSuccessfully() throws IOException {
    //    FileUtils.deleteDirectory(new File(JAVA_EXPORT_PATH));

    TrinityScaffolderJava trinityScaffolderJava = TrinityScaffolderJavaFactory.newInstance();

    assertThatCode(
            () ->
                trinityScaffolderJava.scaffold(
                    Paths.get(JAVA_EXPORT_PATH), trinityDemoProjectDescription()))
        .doesNotThrowAnyException();

    assertThatCode(() -> generate()).doesNotThrowAnyException();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ProjectDescription trinityDemoProjectDescription() {
    return new ProjectDescriptionBuilder()
        .setContext("demo")
        .setGroupId(JAVA_ROOT_PACKAGE)
        .setArtifactId("trinity-demo-java")
        .setVersion("0.0.1-SNAPSHOT")
        .setModulePrefix(JAVA_MODULE_PREFIX)
        .setName("trinity-demo-java")
        .setDescription("trinity-demo-java")
        .setUrl("https://www.oregor.com")
        .setInceptionYear("2019")
        .setOrganizationName("Christos Tsakostas, OREGOR LTD")
        .setOrganizationUrl("https://www.oregor.com")
        .setLicenseName("The Apache License, Version 2.0")
        .setScmConnection("scm:git:git://github.com/oregor-projects/trinity-demo-java.git")
        .setScmDeveloperConnection("scm:git:git@github.com:oregor-projects/trinity-demo-java.git")
        .setScmUrl("http://github.com/oregor-projects/trinity-demo-java/tree/master")
        .setDistributionProfile("ossrh-oregor")
        .createProjectDescription();
  }

  @SuppressWarnings("rawtypes")
  private void generate() {
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

    allThings.add(createTodo(new PackageName(JAVA_ROOT_PACKAGE)));

    Set<AbstractionRepository> abstractionRepositories =
        new LinkedHashSet<>(Arrays.asList(new ThingRepository(allThings)));

    genesis.generate(abstractionRepositories, deducers, generators);
  }

  private Thing createTodo(PackageName rootPackageName) {
    Thing task = ThingBuilder.endToEnd().setThingName("task").setMultiTenant(false).createThing();

    task.addFunctions(
        PurposeFunctionBuilder.forThing(task, rootPackageName.getText())
            .withCrudFunction(data())
            .build());

    return task;
  }

  private Set<Data> data() {
    return DataBuilder.create()
        .withTextProperty("description")
        .build()
        .withBooleanProperty("done")
        .build()
        .build();
  }
}
