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

package io.polygenesis.commons.freemarker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class FreemarkerServiceTest {

  private Template template;
  private Configuration configuration;
  private FreemarkerService freemarkerService;

  @Before
  public void setUp() {
    template = mock(Template.class);
    configuration = mock(Configuration.class);
    freemarkerService = new FreemarkerService(configuration);
  }

  @Test
  public void shouldSucceedToExport() throws IOException {
    FileUtils.deleteDirectory(new File("tmp"));

    Map<String, Object> dataModel = new HashMap<>();
    String ftlTemplate = "some.ftl";
    Path generationFilePath = Paths.get("tmp", "Export.java");

    given(configuration.getTemplate(ftlTemplate)).willReturn(template);

    freemarkerService.export(dataModel, ftlTemplate, generationFilePath);

    verify(configuration).getTemplate(eq(ftlTemplate));
  }

  @Test
  public void shouldFailToExport() throws IOException {
    FileUtils.deleteDirectory(new File("folder-no-file"));

    Map<String, Object> dataModel = new HashMap<>();
    String ftlTemplate = "some.ftl";
    Path generationFilePath = Paths.get("folder-no-file");

    assertThatThrownBy(() -> freemarkerService.export(dataModel, ftlTemplate, generationFilePath))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldSucceedToExportIfNotExists() throws IOException {
    FileUtils.deleteDirectory(new File("tmp"));

    Map<String, Object> dataModel = new HashMap<>();
    String ftlTemplate = "some.ftl";
    Path generationFilePath = Paths.get("tmp", "Export.java");

    given(configuration.getTemplate(ftlTemplate)).willReturn(template);

    freemarkerService.exportIfNotExists(dataModel, ftlTemplate, generationFilePath);
    verify(configuration).getTemplate(eq(ftlTemplate));
  }

  @Test
  public void shouldSucceedToExportToString() throws IOException {
    Map<String, Object> dataModel = new HashMap<>();
    String ftlTemplate = "some.ftl";
    given(configuration.getTemplate(ftlTemplate)).willReturn(template);

    String output = freemarkerService.exportToString(dataModel, ftlTemplate);

    verify(configuration).getTemplate(eq(ftlTemplate));

    assertThat(output).isNotNull();
  }
}
