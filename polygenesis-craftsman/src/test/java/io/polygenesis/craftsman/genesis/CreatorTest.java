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

package io.polygenesis.craftsman.genesis;

import com.oregor.trinity.scaffolder.java.core.ProjectDescription;
import com.oregor.trinity.scaffolder.java.core.ProjectDescriptionBuilder;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJava;
import com.oregor.trinity.scaffolder.java.core.TrinityScaffolderJavaFactory;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Creator;
import io.polygenesis.core.Deducer;
import io.polygenesis.craftsman.GenesisDefault;
import io.polygenesis.generators.java.TrinityJavaContextGeneratorEnablement;
import io.polygenesis.generators.java.TrinityJavaContextGeneratorFactory;
import java.nio.file.Paths;
import java.util.Set;
import org.junit.Ignore;
import org.junit.Test;

/** @author Christos Tsakostas */
@Ignore
public class CreatorTest {

  // private static final String JAVA_EXPORT_PATH = "tmp";
  private static final String JAVA_EXPORT_PATH =
      "/Users/tsakostas/work/repo/gitlab/oregor/microservices";
  private static final String JAVA_ARTIFACT_ID = "oregor-microservice-genesis";
  private static final String JAVA_MODULE_PREFIX = "genesis";
  private static final String JAVA_ROOT_PACKAGE = "com.oregor.microservices.genesis";
  private static final String JAVA_CONTEXT = "genesis";
  private static final String TABLE_PREFIX = "gns_";

  @Test
  public void shouldGenerate() {
    String projectFolder = scaffoldJava();
    generateJava(projectFolder);
  }

  private String scaffoldJava() {
    TrinityScaffolderJava trinityScaffolderJava = TrinityScaffolderJavaFactory.newInstance();
    ProjectDescription projectDescription = makeProjectDescription();
    trinityScaffolderJava.scaffold(Paths.get(JAVA_EXPORT_PATH), projectDescription);
    return projectDescription.getArtifactId();
  }

  @SuppressWarnings("rawtypes")
  private void generateJava(String projectFolder) {
    Creator creator = new Creator();

    creator.generateByContext(
        GenesisContext.get(JAVA_ROOT_PACKAGE, architectureGenerator(projectFolder)), deducers());
  }

  @SuppressWarnings("rawtypes")
  private Set<Deducer> deducers() {
    return GenesisDefault.javaDeducers(JAVA_ROOT_PACKAGE);
  }

  private ContextGenerator architectureGenerator(String projectFolder) {
    TrinityJavaContextGeneratorEnablement trinityJavaContextGeneratorEnablement =
        new TrinityJavaContextGeneratorEnablement();
    trinityJavaContextGeneratorEnablement.setDomainServiceImplementationGenerator(false);

    return TrinityJavaContextGeneratorFactory.newInstance(
        Paths.get(JAVA_EXPORT_PATH),
        trinityJavaContextGeneratorEnablement,
        JAVA_EXPORT_PATH,
        projectFolder,
        JAVA_MODULE_PREFIX,
        JAVA_CONTEXT,
        TABLE_PREFIX,
        JAVA_ROOT_PACKAGE);
  }

  private ProjectDescription makeProjectDescription() {
    return new ProjectDescriptionBuilder()
        .setContext(TextConverter.toLowerHyphen(JAVA_CONTEXT))
        .setGroupId(JAVA_ROOT_PACKAGE)
        .setArtifactId(JAVA_ARTIFACT_ID)
        .setModulePrefix(JAVA_MODULE_PREFIX)
        .setVersion("0.0.1-SNAPSHOT")
        .setName(JAVA_ARTIFACT_ID)
        .setDescription("OREGOR Genesis microservice")
        .setUrl("https://www.oregor.com")
        .setInceptionYear("2019")
        .setOrganizationName("OREGOR LTD")
        .setOrganizationUrl("https://www.oregor.com")
        .setLicenseName("The Apache License, Version 2.0")
        .setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt")
        .setDistributionProfile("ossrh-oregor")
        .setScmConnection(
            "scm:git:git://gitlab.com/oregor/microservices/oregor-microservice-genesis.git")
        .setScmDeveloperConnection(
            "scm:git:git@gitlab.com:oregor/microservices/oregor-microservice-genesis.git")
        .setScmUrl("https://gitlab.com/oregor/microservices/oregor-microservice-genesis")
        .createProjectDescription();
  }
}
