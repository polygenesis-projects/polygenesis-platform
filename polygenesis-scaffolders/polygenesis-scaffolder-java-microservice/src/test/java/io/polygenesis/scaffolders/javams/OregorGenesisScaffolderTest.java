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

package io.polygenesis.scaffolders.javams;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import org.junit.Test;

/** @author Christos Tsakostas */
public class OregorGenesisScaffolderTest {

  private static final String EXPORT_PATH = "tmp";

  @Test
  public void shouldGenerateForAnnotationsAndStateDeducer() {

    TrinityScaffolderJava trinityScaffolderJava =
        TrinityScaffolderJavaFactory.newInstance(Paths.get(EXPORT_PATH));

    String projectFolder = "oregor-genesis";

    ProjectDescription projectDescription = new ProjectDescription();
    projectDescription.setGroupId("com.oregor.genesis");
    projectDescription.setArtifactId("oregor-genesis");
    projectDescription.setModulePrefix("genesis");
    projectDescription.setVersion("0.0.1-SNAPSHOT");
    projectDescription.setName("OREGOR Genesis Projects");
    projectDescription.setDescription("PolyGenesis runners for OREGOR applications");
    projectDescription.setUrl("https://www.oregor.com");
    projectDescription.setInceptionYear("2019");
    projectDescription.setOrganizationName("OREGOR LTD");
    projectDescription.setOrganizationUrl("https://www.oregor.com");
    projectDescription.setLicenseName("The Apache License, Version 2.0");
    projectDescription.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt");
    projectDescription.setDistributionProfile("ossrh-oregor");
    projectDescription.setExtraModules(
        new LinkedHashSet<>(
            Arrays.asList(
                "app-invoiceful",
                "app-polyfono",
                "app-polygenesis",
                "backoffice-invoiceful",
                "backoffice-polyfono",
                "backoffice-polygenesis",
                "backoffice-shared",
                "ms-account",
                "ms-audio",
                "ms-communication",
                "ms-event",
                "ms-genesis",
                "ms-invoicing",
                "ms-networking",
                "ms-promotion",
                "ms-support",
                "webapp-invoiceful",
                "webapp-polyfono",
                "webapp-polygenesis",
                "webapp-shared")));
    projectDescription.setMicroservice(false);

    projectDescription.setScmConnection("scm:git:git://gitlab.com/oregor/oregor-genesis.git");
    projectDescription.setScmDeveloperConnection(
        "scm:git:git@gitlab.com:oregor/oregor-genesis.git");
    projectDescription.setScmUrl("https://gitlab.com/oregor/oregor-genesis");

    trinityScaffolderJava.scaffold(Paths.get(EXPORT_PATH, projectFolder), projectDescription);
  }
}
