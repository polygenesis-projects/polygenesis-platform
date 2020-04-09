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

package io.polygenesis.commons.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.polygenesis.commons.path.PathService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Map;

/**
 * The type Freemarker service.
 *
 * @author Christos Tsakostas
 */
public class FreemarkerService {

  private final Configuration configuration;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Freemarker service.
   *
   * @param configuration the configuration
   */
  public FreemarkerService(Configuration configuration) {
    this.configuration = configuration;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param dataModel the data model
   * @param ftlTemplate the ftl template
   * @param generationFilePath the generation file path
   */
  public void export(Map<String, Object> dataModel, String ftlTemplate, Path generationFilePath) {
    try {
      PathService.ensurePath(generationFilePath.getParent());

      Template template = configuration.getTemplate(ftlTemplate);
      Writer file = new FileWriter(new File(generationFilePath.toString()));
      template.process(dataModel, file);
      file.flush();
      file.close();
    } catch (IOException | TemplateException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }

  /**
   * Export if not exists.
   *
   * @param dataModel the data model
   * @param ftlTemplate the ftl template
   * @param generationFilePath the generation file path
   */
  public void exportIfNotExists(
      Map<String, Object> dataModel, String ftlTemplate, Path generationFilePath) {
    if (!generationFilePath.toFile().exists()) {
      export(dataModel, ftlTemplate, generationFilePath);
    }
  }

  /**
   * Export to byte array output stream byte array output stream.
   *
   * @param dataModel the data model
   * @param ftlTemplate the ftl template
   * @return the byte array output stream
   */
  public ByteArrayOutputStream exportToByteArrayOutputStream(
      Map<String, Object> dataModel, String ftlTemplate) {
    try {
      Template template = configuration.getTemplate(ftlTemplate);

      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream);

      template.process(dataModel, writer);

      writer.flush();
      writer.close();

      return byteArrayOutputStream;
    } catch (IOException | TemplateException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }

  /**
   * Export to memory string.
   *
   * @param dataModel the data model
   * @param ftlTemplate the ftl template
   * @return the string
   */
  public String exportToString(Map<String, Object> dataModel, String ftlTemplate) {
    return exportToByteArrayOutputStream(dataModel, ftlTemplate).toString();
  }
}
