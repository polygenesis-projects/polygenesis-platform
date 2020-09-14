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

package com.eventiac.genesis.backend;

import com.eventiac.genesis.contexts.auth.ContextAuth;
import com.eventiac.genesis.contexts.notification.ContextNotification;
import com.eventiac.genesis.contexts.payment.ContextPayment;
import com.eventiac.genesis.contexts.planning.ContextPlanning;
import com.eventiac.genesis.contexts.profile.ContextProfile;
import com.eventiac.genesis.contexts.ticketing.ContextTicketing;
import com.oregor.trinity.scaffolder.java.core.AppConfigLocationType;
import com.oregor.trinity.scaffolder.java.core.ContextDescription;
import com.oregor.trinity.scaffolder.java.core.Enablement;
import com.oregor.trinity.scaffolder.java.core.ProjectDescription;
import com.oregor.trinity.scaffolder.java.core.ProjectDescriptionBuilder;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJava;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJavaFactory;
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
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class EventiacBackendCreator {

  private static final String JAVA_EXPORT_PATH = "/Users/tsakostas/work/repo/gitlab/eventiac/tmp";
  private static final String JAVA_PROJECT_FOLDER = "eventiac-backend";
  private static final String JAVA_ARTIFACT_ID = "eventiac-backend";
  private static final String JAVA_MODULE_PREFIX = "eventiac";
  private static final String JAVA_CONTEXT = "eventiac";
  private static final String JAVA_ROOT_PACKAGE = "com.eventiac";

  @Test
  public void test() {
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
        ProjectBuilder.of("eventiac")
            .addContext(
                ContextAuth.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "auth")),
                    contextGenerator("auth", "auth", "ath_", "auth"),
                    deducers("auth")))
            .addContext(
                ContextProfile.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "profile")),
                    contextGenerator(
                        "profile", "profile", "pfl_", "profile"),
                    deducers("profile")))
            .addContext(
                ContextPlanning.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "planning")),
                    contextGenerator(
                        "planning", "planning", "pln_", "planning"),
                    deducers("planning")))
            .addContext(
                ContextTicketing.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "ticketing")),
                    contextGenerator("ticketing", "ticketing", "tck_", "ticketing"),
                    deducers("ticketing")))
            .addContext(
                ContextPayment.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "payment")),
                    contextGenerator("payment", "payment", "pmt_", "payment"),
                    deducers("payment")))
            .addContext(
                ContextNotification.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "notification")),
                    contextGenerator("notification", "notification", "ntf_", "notification"),
                    deducers("notification")))

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
    return new ProjectDescriptionBuilder()
        .setProjectFolder(JAVA_PROJECT_FOLDER)
        .setContext(TextConverter.toLowerHyphen(JAVA_CONTEXT))
        .setGroupId(JAVA_ROOT_PACKAGE)
        .setArtifactId(JAVA_ARTIFACT_ID)
        .setModulePrefix(JAVA_MODULE_PREFIX)
        .setVersion("0.0.1-SNAPSHOT")
        .setName(JAVA_ARTIFACT_ID)
        .setDescription("eventiac")
        .setUrl("https://www.eventiac.com")
        .setInceptionYear("2019")
        .setOrganizationName("eventiac")
        .setOrganizationUrl("https://www.eventiac.com")
        .setLicenseName("The Apache License, Version 2.0")
        .setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt")
        .setDistributionProfile("ossrh-oregor")
        .setScmConnection("scm:git:git://gitlab.com/eventiac/eventiac-backend.git")
        .setScmDeveloperConnection("scm:git:git@gitlab.com:eventiac/eventiac-backend.git")
        .setScmUrl("https://gitlab.com/eventiac/eventiac-backend")
        .setContextDescriptions(contextDescriptions())
        .setEnablement(new Enablement())
        .setAppConfigLocationType(AppConfigLocationType.OUTSIDE)
        .createProjectDescription();
  }

  private static Set<ContextDescription> contextDescriptions() {
    Set<ContextDescription> contextDescriptions = new LinkedHashSet<>();
    Enablement enablement = new Enablement();

    //@formatter:off
    contextDescriptions.add(
        new ContextDescription("auth",
            "auth",
            "com.eventiac.auth",
            "auth",
            "auth",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "profile",
            "profile",
            "com.eventiac.profile",
            "profile",
            "profile",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "planning",
            "planning",
            "com.eventiac.planning",
            "planning",
            "planning",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "ticketing",
            "ticketing",
            "com.eventiac.ticketing",
            "ticketing",
            "ticketing",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "payment",
            "payment",
            "com.eventiac.payment",
            "payment",
            "payment",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "notification",
            "notification",
            "com.eventiac.notification",
            "notification",
            "notification",
            enablement));
    //@formatter:on

    return contextDescriptions;
  }
}
