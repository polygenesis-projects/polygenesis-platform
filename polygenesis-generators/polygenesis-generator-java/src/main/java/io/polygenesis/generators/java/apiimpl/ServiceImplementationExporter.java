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
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootClassRepresentable;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.domain.AggregateRoot;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Service implementation exporter.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final ServiceImplementationClassRepresentable serviceImplementationClassRepresentable;
  private final AggregateRootClassRepresentable aggregateRootClassRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation exporter.
   *
   * @param freemarkerService the freemarker service
   * @param serviceImplementationClassRepresentable the service implementation class representable
   * @param aggregateRootClassRepresentable the aggregate root class representable
   */
  public ServiceImplementationExporter(
      FreemarkerService freemarkerService,
      ServiceImplementationClassRepresentable serviceImplementationClassRepresentable,
      AggregateRootClassRepresentable aggregateRootClassRepresentable) {
    this.freemarkerService = freemarkerService;
    this.serviceImplementationClassRepresentable = serviceImplementationClassRepresentable;
    this.aggregateRootClassRepresentable = aggregateRootClassRepresentable;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param serviceImplementation the service implementation
   * @param aggregateRoot the aggregate root
   */
  public void export(
      Path generationPath,
      PackageName rootPackageName,
      ServiceImplementation serviceImplementation,
      AggregateRoot aggregateRoot) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put(
        "representation", serviceImplementationClassRepresentable.create(serviceImplementation));
    dataModel.put(
        "aggregateRootProjection",
        aggregateRootClassRepresentable.create(aggregateRoot, rootPackageName));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, serviceImplementation.getService()));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Path makeFileName(Path generationPath, Service service) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        service.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(service.getServiceName().getText()) + "Impl.java");
  }
}