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

package com.arcaiv.backend;

import com.arcaiv.contexts.archiving.ContextArchiving;
import com.arcaiv.contexts.auth.ContextAuth;
import com.arcaiv.contexts.notification.ContextNotification;
import com.arcaiv.contexts.support.ContextSupport;
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
public class ArcaivBackendCreator {

  private static final String JAVA_EXPORT_PATH = "/Users/tsakostas/work/repo/gitlab/arcaiv";
  private static final String JAVA_PROJECT_FOLDER = "arcaiv-backend";
  private static final String JAVA_ARTIFACT_ID = "arcaiv";
  private static final String JAVA_MODULE_PREFIX = "arcaiv";
  private static final String JAVA_CONTEXT = "arcaiv";
  private static final String JAVA_ROOT_PACKAGE = "com.arcaiv";

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
        ProjectBuilder.of("arcaiv")
            .addContext(
                ContextAuth.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "auth")),
                    contextGenerator("auth", "auth", "ath_", "auth"),
                    deducers("auth")))
            .addContext(
                ContextArchiving.get(
                    new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "archiving")),
                    contextGenerator("archiving", "archiving", "arc_", "archiving"),
                    deducers("archiving")))
            .addContext(
                ContextNotification.get(
                    String.format("%s.%s", JAVA_ROOT_PACKAGE, "notification"),
                    contextGenerator("notification", "notification", "ntf_",
                        "notification"),
                    deducers("notification")))
            .addContext(
                ContextSupport.get(
                    String.format("%s.%s", JAVA_ROOT_PACKAGE, "support"),
                    contextGenerator("support", "support", "spt_",
                        "support"),
                    deducers("support")))
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
        .setDescription("arcaiv")
        .setUrl("https://www.arcaiv.com")
        .setInceptionYear("2021")
        .setOrganizationName("arcaiv")
        .setOrganizationUrl("https://www.arcaiv.com")
        .setLicenseName("The Apache License, Version 2.0")
        .setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt")
        .setDistributionProfile("ossrh-oregor")
        .setScmConnection("scm:git:git://gitlab.com/arcaiv/arcaiv-backend.git")
        .setScmDeveloperConnection("scm:git:git@gitlab.com:arcaiv/arcaiv-backend.git")
        .setScmUrl("https://gitlab.com/arcaiv/arcaiv-backend")
        .setContextDescriptions(contextDescriptions())
        .setEnablement(new Enablement())
        .createProjectDescription();
  }

  private static Set<ContextDescription> contextDescriptions() {
    Set<ContextDescription> contextDescriptions = new LinkedHashSet<>();
    Enablement enablement = new Enablement();

    contextDescriptions.add(
        new ContextDescription(
            "auth",
            "auth",
            "com.arcaiv.auth",
            "auth",
            "auth",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "archiving",
            "archiving",
            "com.arcaiv.archiving",
            "archiving",
            "archiving",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "notification",
            "notification",
            "com.arcaiv.notification",
            "notification",
            "notification",
            enablement));

    contextDescriptions.add(
        new ContextDescription(
            "support",
            "support",
            "com.arcaiv.support",
            "support",
            "support",
            enablement));

    return contextDescriptions;
  }
}
