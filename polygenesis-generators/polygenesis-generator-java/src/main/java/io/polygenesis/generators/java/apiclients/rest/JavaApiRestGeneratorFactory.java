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
import io.polygenesis.generators.java.apiclients.rest.resource.EndpointLegacyMethodTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceExporter;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceLegacyClassTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.EndpointImplementationRegistry;
import io.polygenesis.generators.java.apiclients.rest.resourcetest.ResourceTestExporter;
import io.polygenesis.generators.java.apiclients.rest.resourcetest.ResourceTestLegacyClassTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

/**
 * The Java Domain MetamodelGenerator Factory creates new instances of {@link
 * JavaApiRestMetamodelGenerator}.
 *
 * @author Christos Tsakostas
 */
public final class JavaApiRestGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final ResourceExporter resourceExporter;
  private static final ResourceTestExporter resourceTestExporter;
  private static final RestConstantsProjectionExporter restConstantsProjectionExporter;
  private static final RestServiceAspectGenerator restServiceAspectGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter activeFileExporter = new ActiveFileExporter();
    final DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    EndpointImplementationRegistry endpointImplementationRegistry =
        new EndpointImplementationRegistry();

    EndpointLegacyMethodTransformer endpointMethodRepresentable =
        new EndpointLegacyMethodTransformer(
            dataTypeTransformer, freemarkerService, endpointImplementationRegistry);

    ResourceLegacyClassTransformer resourceClassRepresentable =
        new ResourceLegacyClassTransformer(dataTypeTransformer, endpointMethodRepresentable);

    resourceExporter = new ResourceExporter(freemarkerService, resourceClassRepresentable);

    ResourceTestLegacyClassTransformer resourceTestClassRepresentable =
        new ResourceTestLegacyClassTransformer(dataTypeTransformer, endpointMethodRepresentable);

    resourceTestExporter =
        new ResourceTestExporter(freemarkerService, resourceTestClassRepresentable);

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

  private JavaApiRestGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance java api generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the java api generator
   */
  public static JavaApiRestMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new JavaApiRestMetamodelGenerator(
        generationPath,
        rootPackageName,
        contextName,
        resourceExporter,
        resourceTestExporter,
        restConstantsProjectionExporter,
        restServiceAspectGenerator);
  }
}
