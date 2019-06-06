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

package io.polygenesis.generators.java.exporters.domain.service;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.transformers.domain.service.DomainServiceLegacyInterfaceTransformer;
import io.polygenesis.models.domain.DomainService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Domain service exporter.
 *
 * @author Christos Tsakostas
 */
public class DomainServiceExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final DomainServiceLegacyInterfaceTransformer domainServiceInterfaceRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service exporter.
   *
   * @param freemarkerService the freemarker service
   * @param domainServiceInterfaceRepresentable the domain service interface representable
   */
  public DomainServiceExporter(
      FreemarkerService freemarkerService,
      DomainServiceLegacyInterfaceTransformer domainServiceInterfaceRepresentable) {
    this.freemarkerService = freemarkerService;
    this.domainServiceInterfaceRepresentable = domainServiceInterfaceRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param domainService the domain service
   */
  public void export(Path generationPath, DomainService domainService) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", domainServiceInterfaceRepresentable.create(domainService));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Interface.java.ftl",
        makeFileName(generationPath, domainService));
  }

  private Path makeFileName(Path generationPath, DomainService domainService) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        domainService.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(domainService.getObjectName().getText()) + "DomainService.java");
  }
}
