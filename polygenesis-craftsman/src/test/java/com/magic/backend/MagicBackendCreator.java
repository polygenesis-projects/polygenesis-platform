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

package com.magic.backend;

import com.oregor.trinity.scaffolder.java.core.ContextDescription;
import com.oregor.trinity.scaffolder.java.core.Enablement;
import com.oregor.trinity.scaffolder.java.core.ProjectDescription;
import com.oregor.trinity.scaffolder.java.core.ProjectDescriptionBuilder;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJava;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJavaFactory;
import com.magic.contexts.account.ContextAccount;
import com.magic.contexts.audio.ContextAudio;
import com.magic.contexts.marketing.ContextMarketing;
import com.magic.contexts.notification.ContextNotification;
import com.magic.contexts.purchasing.ContextPurchasing;
import com.magic.contexts.support.ContextSupport;
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
public class MagicBackendCreator {

  private static final String JAVA_EXPORT_PATH = "/Users/tsakostas/work/repo/gitlab/magic/";
  private static final String JAVA_PROJECT_FOLDER = "magic-backend";
  private static final String JAVA_ARTIFACT_ID = "magic-backend";
  private static final String JAVA_MODULE_PREFIX = "magic";
  private static final String JAVA_CONTEXT = "magic";
  private static final String JAVA_ROOT_PACKAGE = "com.magic";

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
        ProjectBuilder.of("magic")
            .addContext(
                ContextAccount.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "account")),
                    contextGenerator("account", "account", "acn_", "account"),
                    deducers("account")))
            .addContext(
                ContextNotification.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "notification")),
                    contextGenerator("notification", "notification", "ntf_",
                        "notification"),
                    deducers("notification")))
            .addContext(
                ContextSupport.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "support")),
                    contextGenerator("support", "support", "spt_",
                        "support"),
                    deducers("support")))
            .addContext(
                ContextMarketing.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "marketing")),
                    contextGenerator("marketing", "marketing", "mkt_",
                        "marketing"),
                    deducers("marketing")))
            .addContext(
                ContextAudio.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "audio")),
                    contextGenerator("audio", "audio", "aud_",
                        "audio"),
                    deducers("audio")))
            .addContext(
                ContextPurchasing.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "purchasing")),
                    contextGenerator("purchasing", "purchasing", "prc_",
                        "purchasing"),
                    deducers("purchasing")))
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
        .setDescription("magic")
        .setUrl("https://www.magic.audio")
        .setInceptionYear("2021")
        .setOrganizationName("magic")
        .setOrganizationUrl("https://www.magic.audio")
        .setLicenseName("The Apache License, Version 2.0")
        .setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt")
        .setDistributionProfile("ossrh-oregor")
        .setScmConnection("scm:git:git@gitlab.devops.iptiq.com:training/magic-backend.git")
        .setScmDeveloperConnection(
            "scm:git:git@gitlab.devops.iptiq.com:training/magic-backend.git")
        .setScmUrl("https://gitlab.devops.iptiq.com/training/magic-backend")
        .setContextDescriptions(contextDescriptions())
        .setEnablement(new Enablement())
        .createProjectDescription();
  }

  private static Set<ContextDescription> contextDescriptions() {
    Set<ContextDescription> contextDescriptions = new LinkedHashSet<>();
    Enablement enablement = new Enablement();

    contextDescriptions.add(
        new ContextDescription(
            "account",
            "account",
            "com.magic.account",
            "account",
            "account",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "audio",
            "audio",
            "com.magic.audio",
            "audio",
            "audio",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "notification",
            "notification",
            "com.magic.notification",
            "notification",
            "notification",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "marketing",
            "marketing",
            "com.magic.marketing",
            "marketing",
            "marketing",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "support",
            "support",
            "com.magic.support",
            "support",
            "support",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "purchasing",
            "purchasing",
            "com.magic.purchasing",
            "purchasing",
            "purchasing",
            enablement));

    return contextDescriptions;
  }
}
