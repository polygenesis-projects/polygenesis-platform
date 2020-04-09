/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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
import com.oregor.trinity4j.Trinity4jAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingContext;
import io.polygenesis.abstraction.thing.ThingContextBuilder;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.freemarker.FreemarkerAuthorService;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.MetamodelGenerator;
import io.polygenesis.generators.java.TrinityJavaContextGeneratorEnablement;
import io.polygenesis.generators.java.TrinityJavaContextGeneratorFactory;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

public class ToptalJavaTest {

  private static final String JAVA_EXPORT_PATH = "tmp";
  private static final String JAVA_ROOT_PACKAGE = "com.oregor.trinity.demo.java";
  private static final String JAVA_PROJECT_FOLDER = "toptal-assessment";
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
    FreemarkerAuthorService.setAuthor("Christos Tsakostas");

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
        .setProjectFolder(JAVA_PROJECT_FOLDER)
        .setContext("demo")
        .setGroupId(JAVA_ROOT_PACKAGE)
        .setArtifactId("toptal-assessment")
        .setVersion("0.0.1-SNAPSHOT")
        .setModulePrefix(JAVA_MODULE_PREFIX)
        .setName("toptal-assessment")
        .setDescription("toptal-assessment")
        .setUrl("https://www.oregor.com")
        .setInceptionYear("2020")
        .setOrganizationName("Christos Tsakostas, OREGOR LP")
        .setOrganizationUrl("https://www.oregor.com")
        .setLicenseName("The Apache License, Version 2.0")
        .setScmConnection("scm:git:git://github.com/oregor-projects/toptal-assessment.git")
        .setScmDeveloperConnection("scm:git:git@github.com:oregor-projects/toptal-assessment.git")
        .setScmUrl("http://github.com/oregor-projects/toptal-assessment/tree/master")
        .setDistributionProfile("ossrh-oregor")
        .createProjectDescription();
  }

  private void generate() {
    //    TrinityJavaContextGeneratorEnablement trinityJavaContextGeneratorEnablement =
    //        new TrinityJavaContextGeneratorEnablement();
    //
    //    TrinityJavaContextGenerator trinityJavaContextGenerator =
    // TrinityJavaContextGeneratorFactory
    //        .newInstance(
    //
    //            Paths.get(JAVA_EXPORT_PATH),
    //            trinityJavaContextGeneratorEnablement,
    //            JAVA_EXPORT_PATH,
    //            JAVA_PROJECT_FOLDER,
    //            contextFolder,
    //            modulePrefix,
    //            context,
    //            tablePrefix,
    //            String.format("%s.%s", JAVA_ROOT_PACKAGE, context)
    //        );

    Set<MetamodelGenerator> metamodelGenerators =
        GenesisDefault.javaGenerators(
            JAVA_EXPORT_PATH,
            JAVA_PROJECT_FOLDER,
            JAVA_MODULE_PREFIX,
            JAVA_CONTEXT,
            TABLE_PREFIX,
            JAVA_ROOT_PACKAGE);

    Set<Thing> allThings = new LinkedHashSet<>();

    allThings.add(createTodo(new PackageName(JAVA_ROOT_PACKAGE)));

    ThingContext thingContext =
        ThingContextBuilder.of(
                "trinity-demo",
                TrinityJavaContextGeneratorFactory.newInstance(
                    Paths.get(JAVA_EXPORT_PATH),
                    new TrinityJavaContextGeneratorEnablement(),
                    JAVA_EXPORT_PATH,
                    JAVA_PROJECT_FOLDER,
                    "",
                    JAVA_MODULE_PREFIX,
                    JAVA_CONTEXT,
                    TABLE_PREFIX,
                    JAVA_ROOT_PACKAGE))
            .addThing(Trinity4jAggregateRoot.create(new PackageName(JAVA_ROOT_PACKAGE)))
            .addThing(createTodo(new PackageName(JAVA_ROOT_PACKAGE)))
            .withDeducers(GenesisDefault.javaDeducers(JAVA_ROOT_PACKAGE))
            .build();

    metamodelGenerators.forEach(
        generator -> generator.generate(thingContext.getMetamodelRepositories()));
  }

  private Thing createTodo(PackageName rootPackageName) {
    Thing task =
        ThingBuilder.endToEnd("task")
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

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
