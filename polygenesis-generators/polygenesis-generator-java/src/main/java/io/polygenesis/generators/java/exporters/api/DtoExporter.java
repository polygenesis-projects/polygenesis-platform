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

package io.polygenesis.generators.java.exporters.api;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Dto exporter.
 *
 * @author Christos Tsakostas
 */
public class DtoExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final DtoClassRepresentable dtoClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto exporter.
   *
   * @param freemarkerService the freemarker service
   * @param dtoClassRepresentable the dto class representable
   */
  public DtoExporter(
      FreemarkerService freemarkerService, DtoClassRepresentable dtoClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.dtoClassRepresentable = dtoClassRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param service the service
   */
  public void export(Path generationPath, Service service) {
    service
        .getDtos()
        .forEach(
            dto -> {
              if (!dto.getVirtual()) {
                export(generationPath, dto);
              }
            });
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void export(Path generationPath, Dto dto) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", dtoClassRepresentable.create(dto));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, dto));
  }

  private Path makeFileName(Path generationPath, Dto dto) {
    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        dto.getDataGroup().getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(dto.getDataGroup().getObjectName().getText()) + ".java");
  }
}
