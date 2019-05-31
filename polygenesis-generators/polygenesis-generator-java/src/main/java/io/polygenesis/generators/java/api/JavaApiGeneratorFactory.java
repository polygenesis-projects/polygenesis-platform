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

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.generators.java.api.exporter.DtoExporter;
import io.polygenesis.generators.java.api.exporter.ServiceExporter;
import io.polygenesis.generators.java.api.transformer.DtoClassTransformer;
import io.polygenesis.generators.java.api.transformer.ServiceInterfaceTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import io.polygenesis.transformer.code.FunctionToMethodRepresentationTransformer;
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
  private static final ServiceExporter serviceExporter;
  private static final DtoExporter dtoExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    FunctionToMethodRepresentationTransformer functionToMethodRepresentationTransformer =
        new FunctionToMethodRepresentationTransformer(fromDataTypeToJavaConverter);

    ServiceInterfaceTransformer serviceInterfaceRepresentable =
        new ServiceInterfaceTransformer(
            fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer);

    serviceExporter = new ServiceExporter(freemarkerService, serviceInterfaceRepresentable);

    DtoClassTransformer dtoClassRepresentable =
        new DtoClassTransformer(fromDataTypeToJavaConverter);

    dtoExporter = new DtoExporter(freemarkerService, dtoClassRepresentable);
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
    return new JavaApiGenerator(generationPath, serviceExporter, dtoExporter);
  }
}
