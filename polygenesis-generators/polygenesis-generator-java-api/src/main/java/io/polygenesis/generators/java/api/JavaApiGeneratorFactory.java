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

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import java.nio.file.Path;

/**
 * The Java API Generator Factory creates new instances of {@link JavaApiGenerator}.
 *
 * @author Christos Tsakostas
 */
public final class JavaApiGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final ApiServiceExporter apiServiceExporter;
  private static final DtoExporter dtoExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    ApiMethodProjectionMaker apiMethodProjectionMaker =
        new ApiMethodProjectionMaker(fromDataTypeToJavaConverter);

    ApiServiceProjectionMaker apiServiceProjectionMaker =
        new ApiServiceProjectionMaker(apiMethodProjectionMaker);

    apiServiceExporter = new ApiServiceExporter(freemarkerService, apiServiceProjectionMaker);

    DtoProjectionMaker dtoProjectionMaker = new DtoProjectionMaker(fromDataTypeToJavaConverter);

    dtoExporter = new DtoExporter(freemarkerService, dtoProjectionMaker);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private JavaApiGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance java api generator.
   *
   * @param generationPath the generation path
   * @return the java api generator
   */
  public static JavaApiGenerator newInstance(Path generationPath) {
    return new JavaApiGenerator(generationPath, apiServiceExporter, dtoExporter);
  }
}
