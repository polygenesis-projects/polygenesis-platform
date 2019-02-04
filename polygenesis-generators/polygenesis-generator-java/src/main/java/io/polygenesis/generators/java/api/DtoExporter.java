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

package io.polygenesis.generators.java.api;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.models.api.Service;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Io model group exporter.
 *
 * @author Christos Tsakostas
 */
public class DtoExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final DtoObjectProjectionMaker dtoObjectProjectionMaker;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto exporter.
   *
   * @param freemarkerService the freemarker service
   * @param dtoObjectProjectionMaker the dto object projection maker
   */
  public DtoExporter(
      FreemarkerService freemarkerService, DtoObjectProjectionMaker dtoObjectProjectionMaker) {
    this.freemarkerService = freemarkerService;
    this.dtoObjectProjectionMaker = dtoObjectProjectionMaker;
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
        .getMethods()
        .forEach(
            method -> {
              if (method.getReturnValue().getModel().isIoModelGroup()) {
                export(generationPath, (IoModelGroup) method.getReturnValue().getModel());
              }

              method
                  .getArguments()
                  .forEach(
                      argument -> {
                        if (argument.getModel().isIoModelGroup()) {
                          export(generationPath, (IoModelGroup) argument.getModel());
                        }
                      });
            });
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void export(Path generationPath, IoModelGroup ioModelGroup) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("projection", dtoObjectProjectionMaker.make(ioModelGroup));

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-api/Dto.java.ftl",
        makeFileName(generationPath, ioModelGroup));

    // Export of model group children of ioModelGroup recursively
    ioModelGroup
        .getModels()
        .forEach(
            model -> {
              if (model.isIoModelGroup()) {
                export(generationPath, (IoModelGroup) model);
              }
            });
  }

  private Path makeFileName(Path generationPath, IoModelGroup ioModelGroup) {
    PackageName servicePackageName =
        ioModelGroup
            .getDataType()
            .getOptionalPackageName()
            .orElseThrow(IllegalArgumentException::new);

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        servicePackageName.toPath().toString(),
        TextConverter.toUpperCamel(ioModelGroup.getDataType().getDataTypeName().getText())
            + ".java");
  }
}
