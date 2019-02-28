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
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootClassRepresentable;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import java.nio.file.Path;

/**
 * The Java API Implementation Generator Factory creates new instances of {@link
 * JavaApiImplGenerator}**.
 *
 * @author Christos Tsakostas
 */
public final class JavaApiImplGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final ServiceImplementationExporter serviceImplementationExporter;
  private static final ServiceImplementationTestExporter serviceImplementationTestExporter;
  private static final AggregateRootConverterExporter aggregateRootConverterExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    ServiceImplementationMethodCommand serviceImplementationMethodCommandRepresentable =
        new ServiceImplementationMethodCommand();

    ServiceImplementationMethodQuery serviceImplementationMethodQueryRepresentable =
        new ServiceImplementationMethodQuery();

    ServiceImplementationMethodRepresentable apiImplMethodProjectionMaker =
        new ServiceImplementationMethodRepresentable(
            fromDataTypeToJavaConverter,
            serviceImplementationMethodCommandRepresentable,
            serviceImplementationMethodQueryRepresentable);

    ServiceImplementationClassRepresentable serviceImplementationClassRepresentable =
        new ServiceImplementationClassRepresentable(
            fromDataTypeToJavaConverter, apiImplMethodProjectionMaker);

    AggregateRootClassRepresentable aggregateRootClassRepresentable =
        new AggregateRootClassRepresentable(fromDataTypeToJavaConverter);

    serviceImplementationExporter =
        new ServiceImplementationExporter(
            freemarkerService,
            serviceImplementationClassRepresentable,
            aggregateRootClassRepresentable);

    ServiceImplementationTestClassRepresentable serviceImplementationTestClassRepresentable =
        new ServiceImplementationTestClassRepresentable(
            fromDataTypeToJavaConverter, apiImplMethodProjectionMaker);

    serviceImplementationTestExporter =
        new ServiceImplementationTestExporter(
            freemarkerService, serviceImplementationTestClassRepresentable);

    AggregateRootConverterMethodRepresentable aggregateRootConverterMethodRepresentable =
        new AggregateRootConverterMethodRepresentable(fromDataTypeToJavaConverter);

    AggregateRootConverterClassRepresentable aggregateRootConverterClassRepresentable =
        new AggregateRootConverterClassRepresentable(
            fromDataTypeToJavaConverter, aggregateRootConverterMethodRepresentable);

    aggregateRootConverterExporter =
        new AggregateRootConverterExporter(
            freemarkerService, aggregateRootConverterClassRepresentable);
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
   * @param rootPackageName the root package name
   * @return the java api generator
   */
  public static JavaApiImplGenerator newInstance(Path generationPath, PackageName rootPackageName) {
    return new JavaApiImplGenerator(
        generationPath,
        rootPackageName,
        serviceImplementationExporter,
        serviceImplementationTestExporter,
        aggregateRootConverterExporter);
  }
}
