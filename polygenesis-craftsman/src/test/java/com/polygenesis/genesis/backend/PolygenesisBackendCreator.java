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

package com.polygenesis.genesis.backend;

import com.oregor.trinity.scaffolder.java.core.AppConfigLocationType;
import com.oregor.trinity.scaffolder.java.core.ContextDescription;
import com.oregor.trinity.scaffolder.java.core.Enablement;
import com.oregor.trinity.scaffolder.java.core.ProjectDescription;
import com.oregor.trinity.scaffolder.java.core.ProjectDescriptionBuilder;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJava;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJavaFactory;
import com.polygenesis.genesis.contexts.auth.ContextAuth;
import com.polygenesis.genesis.contexts.project.ContextProject;
import io.polygenesis.commons.freemarker.FreemarkerAuthorService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Project;
import io.polygenesis.core.ProjectBuilder;
import io.polygenesis.craftsman.GenesisDefault;
import io.polygenesis.generators.java.TrinityJavaContextGeneratorEnablement;
import io.polygenesis.generators.java.TrinityJavaContextGeneratorFactory;
import io.polygenesis.metamodels.apptrinity.TrinityProject;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

public class PolygenesisBackendCreator {

  private static final String JAVA_EXPORT_PATH = "/Users/tsakostas/work/repo/gitlab/polygenesis";
  private static final String JAVA_PROJECT_FOLDER = "polygenesis-backend";
  private static final String JAVA_ARTIFACT_ID = "polygenesis-backend";
  private static final String JAVA_MODULE_PREFIX = "polygenesis";
  private static final String JAVA_CONTEXT = "polygenesis";
  private static final String JAVA_ROOT_PACKAGE = "com.polygenesis";

  @Test
  public void test() throws IOException {
    main(null);
  }

  public static void main(String[] args) {
    FreemarkerAuthorService.setAuthor("Christos Tsakostas");

    scaffoldJava();
    generateJava();
  }

  private static void scaffoldJava() {
    TrinityScaffolderJava trinityScaffolderJava = TrinityScaffolderJavaFactory.newInstance();
    ProjectDescription projectDescription = makeProjectDescription();
    trinityScaffolderJava.scaffold(Paths.get(JAVA_EXPORT_PATH), projectDescription);
  }

  @SuppressWarnings("rawtypes")
  private static void generateJava() {
    Project project =
        ProjectBuilder.of("polygenesis")
            .addContext(
                ContextAuth.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "auth")),
                    contextGenerator("auth", "auth", "ath_", "auth"),
                    deducers("auth")))
            .addContext(
                ContextProject.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "project")),
                    contextGenerator("project", "project", "prj_", "project"),
                    deducers("project")))
            .build(TrinityProject.class);

    project.getContexts().forEach(context -> context.getContextGenerator().generate(context));
  }

  private static Set<Deducer<?>> deducers(String context) {
    Set<Deducer<?>> deducers =
        GenesisDefault.javaDeducers(String.format("%s.%s", JAVA_ROOT_PACKAGE, context));
    return deducers;
  }

  private static ContextGenerator contextGenerator(
      String contextFolder, String modulePrefix, String tablePrefix, String context) {
    TrinityJavaContextGeneratorEnablement trinityJavaContextGeneratorEnablement =
        new TrinityJavaContextGeneratorEnablement();

    trinityJavaContextGeneratorEnablement.setAuxDetailPropertyFile(true);
    trinityJavaContextGeneratorEnablement.setDomainDetailRepositoryInMemory(true);
    trinityJavaContextGeneratorEnablement.setApiClientBatchProcess(true);
    trinityJavaContextGeneratorEnablement.setApiClientBatchProcessMessageSubscriber(true);
    trinityJavaContextGeneratorEnablement.setApiClientBatchProcessMessagingActivemq(true);
    trinityJavaContextGeneratorEnablement.setApiClientBatchProcessSchedulerCamel(true);

    return TrinityJavaContextGeneratorFactory.newInstance(
        Paths.get(JAVA_EXPORT_PATH),
        trinityJavaContextGeneratorEnablement,
        JAVA_EXPORT_PATH,
        JAVA_PROJECT_FOLDER,
        contextFolder,
        modulePrefix,
        context,
        tablePrefix,
        String.format("%s.%s", JAVA_ROOT_PACKAGE, context));
  }

  private static ProjectDescription makeProjectDescription() {
    Enablement enablement = new Enablement();
    enablement.setApiClientBatchProcessScaffolder(true);
    enablement.setApiClientBatchProcessActiveMqScaffolder(true);
    enablement.setAuxDetailAlertSlackScaffolder(true);
    enablement.setDomainDetailRepositoryInMemoryScaffolder(true);

    return new ProjectDescriptionBuilder()
        .setProjectFolder(JAVA_PROJECT_FOLDER)
        .setContext(TextConverter.toLowerHyphen(JAVA_CONTEXT))
        .setGroupId(JAVA_ROOT_PACKAGE)
        .setArtifactId(JAVA_ARTIFACT_ID)
        .setModulePrefix(JAVA_MODULE_PREFIX)
        .setVersion("0.0.1-SNAPSHOT")
        .setName(JAVA_ARTIFACT_ID)
        .setDescription("Polygenesis")
        .setUrl("https://www.polygenesis.io")
        .setInceptionYear("2019")
        .setOrganizationName("Christos Tsakostas, OREGOR")
        .setOrganizationUrl("https://www.oregor.com")
        .setLicenseName("The Apache License, Version 2.0")
        .setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt")
        .setDistributionProfile("ossrh-oregor")
        .setScmConnection("scm:git:git://git.toptal.com/screening/christos-tsakostas.git")
        .setScmDeveloperConnection("scm:git:git@git.toptal.com:screening/christos-tsakostas.git")
        .setScmUrl("https://git.toptal.com/screening/christos-tsakostas")
        .setContextDescriptions(contextDescriptions(enablement))
        .setAppConfigLocationType(AppConfigLocationType.OUTSIDE)
        .setEnablement(enablement)
        .createProjectDescription();
  }

  private static Set<ContextDescription> contextDescriptions(Enablement enablement) {
    Set<ContextDescription> contextDescriptions = new LinkedHashSet<>();

    contextDescriptions.add(
        new ContextDescription("auth", "auth", "com.polygenesis.auth", "auth", "auth", enablement));

    contextDescriptions.add(
        new ContextDescription(
            "project", "project", "com.polygenesis.project", "project", "project", enablement));

    return contextDescriptions;
  }
}
