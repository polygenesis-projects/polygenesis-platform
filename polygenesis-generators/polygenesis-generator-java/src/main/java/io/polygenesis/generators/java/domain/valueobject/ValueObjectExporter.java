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

package io.polygenesis.generators.java.domain.valueobject;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.ValueObject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Value object exporter.
 *
 * @author Christos Tsakostas
 */
public class ValueObjectExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final FreemarkerService freemarkerService;
  private final ValueObjectProjectionConverter valueObjectProjectionConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object exporter.
   *
   * @param freemarkerService the freemarker service
   * @param valueObjectProjectionConverter the value object projection converter
   */
  public ValueObjectExporter(
      FreemarkerService freemarkerService,
      ValueObjectProjectionConverter valueObjectProjectionConverter) {
    this.freemarkerService = freemarkerService;
    this.valueObjectProjectionConverter = valueObjectProjectionConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param valueObject the value object
   */
  public void export(Path generationPath, ValueObject valueObject) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("projection", valueObjectProjectionConverter.convert(valueObject));

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-domain/ValueObject.java.ftl",
        makeFileName(generationPath, valueObject));
  }

  private Path makeFileName(Path generationPath, ValueObject valueObject) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        valueObject
            .getIoModelGroup()
            .getClassDataType()
            .getOptionalPackageName()
            .get()
            .toPath()
            .toString(),
        TextConverter.toUpperCamel(valueObject.getVariableName().getText()) + ".java");
  }
}
