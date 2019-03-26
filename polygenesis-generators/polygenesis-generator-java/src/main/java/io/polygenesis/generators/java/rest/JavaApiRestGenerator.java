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

package io.polygenesis.generators.java.rest;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.models.rest.RestModelRepository;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Java api rest generator.
 *
 * @author Christos Tsakostas
 */
public class JavaApiRestGenerator extends AbstractGenerator {

  private final PackageName rootPackageName;
  private final ObjectName contextName;
  private final ResourceExporter resourceExporter;
  private final ResourceTestExporter resourceTestExporter;
  private final RestConstantsProjectionExporter restConstantsProjectionExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Java api rest generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param resourceExporter the resource exporter
   * @param resourceTestExporter the resource test exporter
   * @param restConstantsProjectionExporter the rest constants projection exporter
   */
  public JavaApiRestGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ObjectName contextName,
      ResourceExporter resourceExporter,
      ResourceTestExporter resourceTestExporter,
      RestConstantsProjectionExporter restConstantsProjectionExporter) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.resourceExporter = resourceExporter;
    this.resourceTestExporter = resourceTestExporter;
    this.restConstantsProjectionExporter = restConstantsProjectionExporter;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ObjectName getContextName() {
    return contextName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<ModelRepository> modelRepositories) {
    CoreRegistry.getModelRepositoryResolver()
        .resolve(modelRepositories, RestModelRepository.class)
        .getItems()
        .forEach(
            resource -> {
              resourceExporter.export(getGenerationPath(), resource, getRootPackageName());
              resourceTestExporter.export(getGenerationPath(), resource, getRootPackageName());
            });

    restConstantsProjectionExporter.export(
        getGenerationPath(),
        new RestConstantsProjection(getRootPackageName().getText(), getContextName().getText()));
  }
}
