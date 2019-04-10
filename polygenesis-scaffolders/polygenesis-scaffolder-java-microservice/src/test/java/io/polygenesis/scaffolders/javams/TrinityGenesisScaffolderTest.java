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

import io.polygenesis.commons.text.TextConverter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import org.junit.Test;

/** @author Christos Tsakostas */
public class TrinityGenesisScaffolderTest {

  private static final String EXPORT_PATH = "tmp";
  // private static final String EXPORT_PATH = "/Users/tsakostas/work/repo/gitlab/oregor";
  private static final String JAVA_PROJECT_FOLDER = "oregor-trinity-template-java";
  private static final String JAVA_MODULE_PREFIX = "boundedcontext";
  private static final String JAVA_ROOT_PACKAGE = "com.oregor.trinity.template";
  private static final String JAVA_CONTEXT = "template";
  private static final String TABLE_PREFIX = "tpl_";

  @Test
  public void shouldGenerateForAnnotationsAndStateDeducer() {

    TrinityScaffolderJava trinityScaffolderJava =
        TrinityScaffolderJavaFactory.newInstance(Paths.get(EXPORT_PATH));

    ProjectDescription projectDescription = new ProjectDescription();

    projectDescription.setContext(TextConverter.toLowerHyphen(JAVA_CONTEXT));
    projectDescription.setTablePrefix(TABLE_PREFIX);
    projectDescription.setGroupId(JAVA_ROOT_PACKAGE);
    projectDescription.setArtifactId("oregor-trinity-template-java");
    projectDescription.setModulePrefix(JAVA_MODULE_PREFIX);
    projectDescription.setVersion("0.0.1-SNAPSHOT");
    projectDescription.setName("Trinity Architecture Template");
    projectDescription.setDescription("Trinity Architecture Template");
    projectDescription.setUrl("https://www.oregor.com");
    projectDescription.setInceptionYear("2019");
    projectDescription.setOrganizationName("OREGOR LTD");
    projectDescription.setOrganizationUrl("https://www.oregor.com");
    projectDescription.setLicenseName("The Apache License, Version 2.0");
    projectDescription.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt");
    projectDescription.setDistributionProfile("ossrh-oregor");

    projectDescription.setScmConnection(
        "scm:git:git://github.com/oregor-projects/trinity4j.git");
    projectDescription.setScmDeveloperConnection(
        "scm:git:git@github.com:oregor-projects/trinity4j.git");
    projectDescription.setScmUrl("http://github.com/oregor-projects/trinity4j/tree/master");

    projectDescription.setLayers(
        new LinkedHashSet<>(
            Arrays.asList(
                Layer.APP,
                Layer.API,
                Layer.API_IMPL,
                Layer.DOMAIN_MODEL,
                Layer.DOMAIN_SERVICES,
                Layer.PRIMARY_ADAPTER_REST_SPRING,
                Layer.PRIMARY_ADAPTER_SUBSCRIBER_ACTIVEMQ,
                Layer.SECONDARY_ADAPTER_PERSISTENCE_SPRING_DATA_JPA,
                Layer.SECONDARY_ADAPTER_PUBLISHER_ACTIVEMQ)));

    trinityScaffolderJava.scaffold(
        Paths.get(EXPORT_PATH, JAVA_PROJECT_FOLDER), projectDescription);
  }
}
