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

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import org.junit.Test;

/** @author Christos Tsakostas */
public class TrinityScaffolderJavaTest {

  @Test
  public void scaffold() {
    TrinityScaffolderJava trinityScaffolderJava =
        TrinityScaffolderJavaFactory.newInstance(Paths.get("tmp"));

    assertThat(trinityScaffolderJava).isNotNull();

    ProjectDescription projectDescription = new ProjectDescription();

    projectDescription.setContext("account");
    projectDescription.setTablePrefix("ddd");
    projectDescription.setGroupId("com.oregor.microservices.account");
    projectDescription.setArtifactId("oregor-microservice-account");
    projectDescription.setModulePrefix("account");
    projectDescription.setVersion("0.0.1-SNAPSHOT");
    projectDescription.setName("OREGOR Account Microservice");
    projectDescription.setDescription("Microservice for handling accounts in OREGOR applications");
    projectDescription.setUrl("https://www.oregor.com");
    projectDescription.setInceptionYear("2019");
    projectDescription.setOrganizationName("OREGOR LTD");
    projectDescription.setOrganizationUrl("https://www.oregor.com");
    projectDescription.setLicenseName("The Apache License, Version 2.0");
    projectDescription.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt");
    projectDescription.setDistributionProfile("ossrh-oregor");

    projectDescription.setScmConnection(
        "scm:git:git://gitlab.com/oregor/microservices/oregor-microservice-account.git");
    projectDescription.setScmDeveloperConnection(
        "scm:git:git@gitlab.com:oregor/microservices/oregor-microservice-account.git");
    projectDescription.setScmUrl(
        "https://gitlab.com/oregor/microservices/oregor-microservice-account");

    trinityScaffolderJava.scaffold(Paths.get("tmp/some-microservice"), projectDescription);
  }
}
