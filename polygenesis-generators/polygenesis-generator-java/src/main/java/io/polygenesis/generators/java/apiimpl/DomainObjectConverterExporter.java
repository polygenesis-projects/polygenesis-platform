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

package io.polygenesis.generators.java.apiimpl;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Aggregate root converter exporter.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverterExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final DomainObjectConverterClassRepresentable domainObjectConverterClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter exporter.
   *
   * @param freemarkerService the freemarker service
   * @param domainObjectConverterClassRepresentable the aggregate root converter converter
   */
  public DomainObjectConverterExporter(
      FreemarkerService freemarkerService,
      DomainObjectConverterClassRepresentable domainObjectConverterClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.domainObjectConverterClassRepresentable = domainObjectConverterClassRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param domainObjectConverter the aggregate root converter
   */
  public void export(Path generationPath, DomainObjectConverter domainObjectConverter) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put(
        "representation", domainObjectConverterClassRepresentable.create(domainObjectConverter));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, domainObjectConverter));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Path makeFileName(Path generationPath, DomainObjectConverter domainObjectConverter) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        domainObjectConverter.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(domainObjectConverter.getVariableName().getText()) + ".java");
  }
}
