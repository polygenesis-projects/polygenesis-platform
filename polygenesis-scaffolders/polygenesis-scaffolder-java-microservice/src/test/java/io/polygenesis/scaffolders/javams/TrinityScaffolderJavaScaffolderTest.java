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
public class TrinityScaffolderJavaScaffolderTest {

  // private static final String EXPORT_PATH = "tmp";
  private static final String EXPORT_PATH = "/Users/tsakostas/work/repo/github/oregor";

  @Test
  public void shouldScaffoldDdd4j() {
    TrinityScaffolderJava trinityScaffolderJava =
        TrinityScaffolderJavaFactory.newInstance(Paths.get(EXPORT_PATH));

    String projectFolder = "trinity-scaffolder-java";

    ProjectDescription projectDescription = new ProjectDescription();
    projectDescription.setGroupId("com.oregor.trinity.scaffolder.java");
    projectDescription.setArtifactId("trinity-scaffolder-java");
    projectDescription.setModulePrefix("trinity-scaffolder-java");
    projectDescription.setVersion("0.0.1-SNAPSHOT");
    projectDescription.setName("trinity-scaffolder-java");
    projectDescription.setDescription(
        "Scaffolder for Java Applications using the Trinity Architecture");
    projectDescription.setUrl("https://www.oregor.com");
    projectDescription.setInceptionYear("2013");
    projectDescription.setOrganizationName("OREGOR LTD");
    projectDescription.setOrganizationUrl("https://www.oregor.com");
    projectDescription.setLicenseName("The Apache License, Version 2.0");
    projectDescription.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt");
    projectDescription.setDistributionProfile("ossrh-oregor");

    projectDescription.setScmConnection(
        "scm:git:git://github.com/oregor-projects/trinity-scaffolder-java.git");
    projectDescription.setScmDeveloperConnection(
        "scm:git:git@github.com:oregor-projects/trinity-scaffolder-java.git");
    projectDescription.setScmUrl(
        "http://github.com/oregor-projects/trinity-scaffolder-java/tree/master");

    projectDescription.setExtraModules(new LinkedHashSet<>(Arrays.asList("core", "cli")));

    projectDescription.setMicroservice(false);

    trinityScaffolderJava.scaffold(Paths.get(EXPORT_PATH, projectFolder), projectDescription);
  }
}
