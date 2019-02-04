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

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootProjectionConverter;
import java.nio.file.Path;

/**
 * The Java API Implementation Generator Factory creates new instances of {@link
 * JavaApiImplGenerator}*.
 *
 * @author Christos Tsakostas
 */
public final class JavaApiImplGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final ApiImplServiceExporter apiImplServiceExporter;
  private static final ApiImplServiceTestExporter apiImplServiceTestExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    ApiImplMethodProjectionMaker apiImplMethodProjectionMaker =
        new ApiImplMethodProjectionMaker(fromDataTypeToJavaConverter);

    ApiImplServiceProjectionConverter apiImplServiceProjectionConverter =
        new ApiImplServiceProjectionConverter(apiImplMethodProjectionMaker);

    AggregateRootProjectionConverter aggregateRootProjectionConverter =
        new AggregateRootProjectionConverter(fromDataTypeToJavaConverter);

    apiImplServiceExporter =
        new ApiImplServiceExporter(
            freemarkerService, apiImplServiceProjectionConverter, aggregateRootProjectionConverter);

    ApiImplServiceTestProjectionConverter apiImplServiceTestProjectionConverter =
        new ApiImplServiceTestProjectionConverter(apiImplMethodProjectionMaker);

    apiImplServiceTestExporter =
        new ApiImplServiceTestExporter(freemarkerService, apiImplServiceTestProjectionConverter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private JavaApiImplGeneratorFactory() {
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
  public static JavaApiImplGenerator newInstance(Path generationPath, PackageName rootPackageName) {
    return new JavaApiImplGenerator(
        generationPath, rootPackageName, apiImplServiceExporter, apiImplServiceTestExporter);
  }
}