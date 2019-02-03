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
  private final DtoProjectionMaker dtoProjectionMaker;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model group exporter.
   *
   * @param freemarkerService the freemarker service
   * @param dtoProjectionMaker the io model group projection maker
   */
  public DtoExporter(FreemarkerService freemarkerService, DtoProjectionMaker dtoProjectionMaker) {
    this.freemarkerService = freemarkerService;
    this.dtoProjectionMaker = dtoProjectionMaker;
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
    dataModel.put("projection", dtoProjectionMaker.make(ioModelGroup));

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-api/Dto.java.ftl",
        makeFileName(generationPath, ioModelGroup));
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
