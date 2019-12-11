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

package com.invoiceful.genesis.backend;

import com.invoiceful.genesis.contexts.access.ContextAccess;
import com.invoiceful.genesis.contexts.invoicing.ContextInvoicing;
import com.oregor.trinity.scaffolder.java.core.ContextDescription;
import com.oregor.trinity.scaffolder.java.core.ProjectDescription;
import com.oregor.trinity.scaffolder.java.core.ProjectDescriptionBuilder;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJava;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJavaFactory;
import io.polygenesis.commons.freemarker.FreemarkerAuthorService;
import io.polygenesis.commons.text.TextConverter;
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
import org.junit.Test;

/** @author Christos Tsakostas */
public class InvoicefulBackendCreator {

  private static final String JAVA_EXPORT_PATH = "/Users/tsakostas/work/repo/gitlab/invoiceful/xxx";
  private static final String JAVA_PROJECT_FOLDER = "invoiceful-backend";
  private static final String JAVA_ARTIFACT_ID = "invoiceful";
  private static final String JAVA_MODULE_PREFIX = "invoiceful";
  private static final String JAVA_CONTEXT = "invoiceful";
  private static final String JAVA_ROOT_PACKAGE = "com.invoiceful";

  @Test
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
        ProjectBuilder.of("invoiceful")
            .addContext(
                ContextAccess.get(
                    String.format("%s.%s", JAVA_ROOT_PACKAGE, "access"),
                    contextGenerator("access", "access", "acs_", "access"),
                    deducers("access")))
            .addContext(
                ContextInvoicing.get(
                    String.format("%s.%s", JAVA_ROOT_PACKAGE, "invoicing"),
                    contextGenerator("invoicing", "invoicing", "inv_", "invoicing"),
                    deducers("invoicing")))
            //            .addContext(
            //                ContextSupport.get(
            //                    String.format("%s.%s", JAVA_ROOT_PACKAGE, "support"),
            //                    contextGenerator("support", "support", "spt_", "support"),
            //                    deducers("support")))
            //            .addContext(
            //                ContextNotification.get(
            //                    String.format("%s.%s", JAVA_ROOT_PACKAGE, "notification"),
            //                    contextGenerator("notification", "notification", "ntf_",
            // "notification"),
            //                    deducers("notification")))
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
        .setDescription("Invoiceful")
        .setUrl("https://www.invoiceful.com")
        .setInceptionYear("2019")
        .setOrganizationName("INVOICEFUL")
        .setOrganizationUrl("https://www.invoiceful.com")
        .setLicenseName("The Apache License, Version 2.0")
        .setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt")
        .setDistributionProfile("ossrh-oregor")
        .setScmConnection("scm:git:git://gitlab.com/invoiceful/invoiceful-backend.git")
        .setScmDeveloperConnection("scm:git:git@gitlab.com:invoiceful/invoiceful-backend.git")
        .setScmUrl("https://gitlab.com/invoiceful/invoiceful-backend")
        .setContextDescriptions(contextDescriptions())
        .createProjectDescription();
  }

  private static Set<ContextDescription> contextDescriptions() {
    Set<ContextDescription> contextDescriptions = new LinkedHashSet<>();

    contextDescriptions.add(
        new ContextDescription("access", "access", "com.invoiceful.access", "access", "access"));

    contextDescriptions.add(
        new ContextDescription(
            "invoicing", "invoicing", "com.invoiceful.invoicing", "invoicing", "invoicing"));

    //    contextDescriptions.add(
    //        new ContextDescription(
    //            "support", "support", "com.invoiceful.support", "support", "support"));
    //
    //    contextDescriptions.add(
    //        new ContextDescription(
    //            "notification",
    //            "notification",
    //            "com.invoiceful.notification",
    //            "notification",
    //            "notification"));

    return contextDescriptions;
  }
}
