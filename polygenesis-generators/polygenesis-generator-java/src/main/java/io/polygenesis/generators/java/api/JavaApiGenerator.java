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

package io.polygenesis.generators.java.api;

import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.api.exporter.DtoExporter;
import io.polygenesis.generators.java.api.exporter.ServiceExporter;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Java api generator.
 *
 * @author Christos Tsakostas
 */
public class JavaApiGenerator extends AbstractGenerator {

  private final ServiceExporter serviceExporter;
  private final DtoExporter dtoExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java api generator.
   *
   * @param generationPath the generation path
   * @param serviceExporter the service exporter
   * @param dtoExporter the data group exporter
   */
  public JavaApiGenerator(
      Path generationPath, ServiceExporter serviceExporter, DtoExporter dtoExporter) {
    super(generationPath);
    this.serviceExporter = serviceExporter;
    this.dtoExporter = dtoExporter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public void generate(Set<MetamodelRepository> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, ServiceMetamodelRepository.class)
        .getItems()
        .forEach(
            service -> {
              serviceExporter.export(getGenerationPath(), service);
              dtoExporter.export(getGenerationPath(), service);
            });
  }
}
