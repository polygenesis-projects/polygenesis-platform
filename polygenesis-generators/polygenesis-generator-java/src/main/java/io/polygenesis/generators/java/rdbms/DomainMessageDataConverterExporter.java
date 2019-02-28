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

package io.polygenesis.generators.java.rdbms;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.Name;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.PackageName;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Domain message data converter exporter.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageDataConverterExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message data converter exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public DomainMessageDataConverterExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param context the context
   */
  public void export(Path generationPath, PackageName rootPackageName, Name context) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("packageName", rootPackageName.getText());
    dataModel.put("context", context.getText());
    dataModel.put(
        "description",
        "The " + TextConverter.toUpperCamel(context.getText()) + "Domain Message Data Converter.");
    dataModel.put(
        "simpleObjectName",
        TextConverter.toUpperCamel(context.getText() + "DomainMessageDataConverter"));
    dataModel.put(
        "fullObjectName",
        TextConverter.toUpperCamel(context.getText() + "DomainMessageDataConverter")
            + " extends \n\t\tAbstractDomainMessageDataConverter<"
            + TextConverter.toUpperCamel(context.getText())
            + "DomainMessageData>");

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-persistence-rdbms/"
            + "message/ContextDomainMessageDataConverter.java.ftl",
        makeDomainMessageDataConverterFileName(generationPath, rootPackageName, context));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Path makeDomainMessageDataConverterFileName(
      Path generationPath, PackageName rootPackageName, Name context) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        rootPackageName.toPath().toString(),
        TextConverter.toUpperCamel(context.getText()) + "DomainMessageDataConverter.java");
  }
}
