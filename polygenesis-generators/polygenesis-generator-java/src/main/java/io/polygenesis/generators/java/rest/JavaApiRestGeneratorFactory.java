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

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import java.nio.file.Path;

/**
 * The Java Domain Generator Factory creates new instances of {@link JavaApiRestGenerator}.
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

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    EndpointMethodRepresentable endpointMethodRepresentable =
        new EndpointMethodRepresentable(fromDataTypeToJavaConverter);

    ResourceClassRepresentable resourceClassRepresentable =
        new ResourceClassRepresentable(fromDataTypeToJavaConverter, endpointMethodRepresentable);

    resourceExporter = new ResourceExporter(freemarkerService, resourceClassRepresentable);

    ResourceTestClassRepresentable resourceTestClassRepresentable =
        new ResourceTestClassRepresentable(
            fromDataTypeToJavaConverter, endpointMethodRepresentable);

    resourceTestExporter =
        new ResourceTestExporter(freemarkerService, resourceTestClassRepresentable);

    restConstantsProjectionExporter = new RestConstantsProjectionExporter(freemarkerService);
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
  public static JavaApiRestGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ObjectName contextName) {
    return new JavaApiRestGenerator(
        generationPath,
        rootPackageName,
        contextName,
        resourceExporter,
        resourceTestExporter,
        restConstantsProjectionExporter);
  }
}
