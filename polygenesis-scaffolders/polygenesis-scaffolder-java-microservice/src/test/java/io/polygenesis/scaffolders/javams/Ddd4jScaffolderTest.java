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
public class Ddd4jScaffolderTest {

  private static final String EXPORT_PATH = "tmp";

  @Test
  public void shouldScaffoldDdd4j() {
    ScaffolderJavaMicroservice scaffolderJavaMicroservice =
        ScaffolderJavaMicroserviceFactory.newInstance(Paths.get(EXPORT_PATH));

    String projectFolder = "oregor-ddd4j";

    ProjectDescription projectDescription = new ProjectDescription();
    projectDescription.setGroupId("com.oregor.ddd4j");
    projectDescription.setArtifactId("oregor-ddd4j");
    projectDescription.setModulePrefix("ddd4j");
    projectDescription.setVersion("0.0.1-SNAPSHOT");
    projectDescription.setName("OREGOR Domain Driven Design (DDD)");
    projectDescription.setDescription(
        "Domain Driven Design (DDD) base classes used in OREGOR applications");
    projectDescription.setUrl("https://www.oregor.com");
    projectDescription.setInceptionYear("2013");
    projectDescription.setOrganizationName("OREGOR LTD");
    projectDescription.setOrganizationUrl("https://www.oregor.com");
    projectDescription.setLicenseName("The Apache License, Version 2.0");
    projectDescription.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt");
    projectDescription.setDistributionProfile("ossrh-oregor");

    projectDescription.setScmConnection(
        "scm:git:git://github.com/oregor-projects/oregor-ddd4j.git");
    projectDescription.setScmDeveloperConnection(
        "scm:git:git@github.com:oregor-projects/oregor-ddd4j.git");
    projectDescription.setScmUrl("http://github.com/oregor-projects/oregor-ddd4j/tree/master");

    projectDescription.setExtraModules(
        new LinkedHashSet<>(Arrays.asList("core", "spring-data-jpa")));

    projectDescription.setMicroservice(false);

    scaffolderJavaMicroservice.scaffold(Paths.get(EXPORT_PATH, projectFolder), projectDescription);
  }
}
