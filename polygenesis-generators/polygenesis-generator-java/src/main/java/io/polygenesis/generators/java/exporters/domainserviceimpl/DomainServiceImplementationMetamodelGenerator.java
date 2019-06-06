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

package io.polygenesis.generators.java.exporters.domainserviceimpl;

import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.domain.DomainServiceRepository;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Java api rest generator.
 *
 * @author Christos Tsakostas
 */
public class DomainServiceImplementationMetamodelGenerator extends AbstractMetamodelGenerator {

  private final DomainServiceImplementationExporter domainServiceImplementationExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service implementation generator.
   *
   * @param generationPath the generation path
   * @param domainServiceImplementationExporter the domain service implementation exporter
   */
  public DomainServiceImplementationMetamodelGenerator(
      Path generationPath,
      DomainServiceImplementationExporter domainServiceImplementationExporter) {
    super(generationPath);
    this.domainServiceImplementationExporter = domainServiceImplementationExporter;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public void generate(Set<MetamodelRepository> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, DomainServiceRepository.class)
        .getItems()
        .forEach(
            domainService ->
                domainServiceImplementationExporter.export(getGenerationPath(), domainService));
  }
}
