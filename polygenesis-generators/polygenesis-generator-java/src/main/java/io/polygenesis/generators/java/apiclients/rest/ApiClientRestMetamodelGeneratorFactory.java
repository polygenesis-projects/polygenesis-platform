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

package io.polygenesis.generators.java.apiclients.rest;

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.apiclients.rest.aspect.RestServiceAspectActivityRegistry;
import io.polygenesis.generators.java.apiclients.rest.aspect.RestServiceAspectGenerator;
import io.polygenesis.generators.java.apiclients.rest.aspect.RestServiceAspectMethodTransformer;
import io.polygenesis.generators.java.apiclients.rest.aspect.RestServiceAspectTransformer;
import io.polygenesis.generators.java.apiclients.rest.constants.RestConstantsProjectionExporter;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceActivityRegistry;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceGenerator;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceMethodParameterRepresentationService;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceMethodTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type Api client rest metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public final class ApiClientRestMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private static ResourceGenerator resourceGenerator;
  private static RestConstantsProjectionExporter restConstantsProjectionExporter;
  private static RestServiceAspectGenerator restServiceAspectGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter activeFileExporter = new ActiveFileExporter();
    final DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    resourceGenerator =
        new ResourceGenerator(
            new ResourceTransformer(
                dataTypeTransformer,
                new ResourceMethodTransformer(
                    dataTypeTransformer,
                    new ResourceActivityRegistry(),
                    new ResourceMethodParameterRepresentationService(dataTypeTransformer))),
            templateEngine,
            activeFileExporter);

    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    restConstantsProjectionExporter = new RestConstantsProjectionExporter(freemarkerService);

    restServiceAspectGenerator =
        new RestServiceAspectGenerator(
            new RestServiceAspectTransformer(
                dataTypeTransformer,
                new RestServiceAspectMethodTransformer(
                    dataTypeTransformer, new RestServiceAspectActivityRegistry())),
            templateEngine,
            activeFileExporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ApiClientRestMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance api client rest metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the api client rest metamodel generator
   */
  public static ApiClientRestMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new ApiClientRestMetamodelGenerator(
        generationPath,
        rootPackageName,
        contextName,
        resourceGenerator,
        restConstantsProjectionExporter,
        restServiceAspectGenerator);
  }
}
