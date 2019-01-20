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

package io.polygenesis.generators.angular.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Map;

/**
 * The type Freemarker service.
 *
 * @author Christos Tsakostas
 */
public class FreemarkerService {

  /**
   * Export.
   *
   * @param configuration the configuration
   * @param dataModel the data model
   * @param ftlTemplate the ftl template
   * @param generationFilePath the generation file path
   */
  public static void export(
      Configuration configuration,
      Map<String, Object> dataModel,
      String ftlTemplate,
      Path generationFilePath) {

    try {
      Template template = configuration.getTemplate(ftlTemplate);
      Writer file = new FileWriter(new File(generationFilePath.toString()));
      template.process(dataModel, file);
      file.flush();
      file.close();
    } catch (IOException | TemplateException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }
}