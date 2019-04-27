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

package io.polygenesis.generators.java.exporters.domain.domainevent;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.DomainEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Domain event exporter.
 *
 * @author Christos Tsakostas
 */
public class DomainEventExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain event exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public DomainEventExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param domainEvent the domain event
   */
  public void export(Path generationPath, DomainEvent domainEvent) {
    // TODO
    if (domainEvent == null) {
      return;
    }
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("domainEvent", domainEvent);

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-domain/DomainEvent.java.ftl",
        makeFileName(generationPath, domainEvent));
  }

  private Path makeFileName(Path generationPath, DomainEvent domainEvent) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        domainEvent.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(domainEvent.getObjectName().getText()) + ".java");
  }
}
