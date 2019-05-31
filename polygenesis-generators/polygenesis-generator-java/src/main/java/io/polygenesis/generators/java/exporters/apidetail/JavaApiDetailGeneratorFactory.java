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

package io.polygenesis.generators.java.exporters.apidetail;

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.exporters.apidetail.testing.ServiceImplementationTestExporter;
import io.polygenesis.generators.java.transformers.apidetail.DomainObjectConverterClassTransformer;
import io.polygenesis.generators.java.transformers.apidetail.DomainObjectConverterMethodTransformer;
import io.polygenesis.generators.java.transformers.apidetail.ServiceImplementationClassTransformer;
import io.polygenesis.generators.java.transformers.apidetail.ServiceImplementationTestClassTransformer;
import io.polygenesis.generators.java.transformers.apidetail.ServiceMethodImplementationTransformer;
import io.polygenesis.generators.java.transformers.apidetail.activities.ConverterMethodImplementationRegistry;
import io.polygenesis.generators.java.transformers.apidetail.activities.ServiceMethodImplementationRegistry;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import java.nio.file.Path;

/**
 * The type Java api detail generator factory.
 *
 * @author Christos Tsakostas
 */
public final class JavaApiDetailGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final ServiceImplementationExporter serviceImplementationExporter;
  private static final ServiceImplementationTestExporter serviceImplementationTestExporter;
  private static final DomainObjectConverterExporter domainObjectConverterExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    ServiceMethodImplementationRegistry serviceMethodImplementationRegistry =
        new ServiceMethodImplementationRegistry();

    ServiceMethodImplementationTransformer apiImplMethodProjectionMaker =
        new ServiceMethodImplementationTransformer(
            fromDataTypeToJavaConverter, serviceMethodImplementationRegistry, freemarkerService);

    ServiceImplementationClassTransformer serviceImplementationClassRepresentable =
        new ServiceImplementationClassTransformer(
            fromDataTypeToJavaConverter, apiImplMethodProjectionMaker);

    serviceImplementationExporter =
        new ServiceImplementationExporter(
            freemarkerService, serviceImplementationClassRepresentable);

    ServiceImplementationTestClassTransformer serviceImplementationTestClassRepresentable =
        new ServiceImplementationTestClassTransformer(
            fromDataTypeToJavaConverter, apiImplMethodProjectionMaker);

    serviceImplementationTestExporter =
        new ServiceImplementationTestExporter(
            freemarkerService, serviceImplementationTestClassRepresentable);

    ConverterMethodImplementationRegistry converterMethodImplementationRegistry =
        new ConverterMethodImplementationRegistry();

    DomainObjectConverterMethodTransformer domainObjectConverterMethodRepresentable =
        new DomainObjectConverterMethodTransformer(
            fromDataTypeToJavaConverter, freemarkerService, converterMethodImplementationRegistry);

    DomainObjectConverterClassTransformer domainObjectConverterClassRepresentable =
        new DomainObjectConverterClassTransformer(
            fromDataTypeToJavaConverter, domainObjectConverterMethodRepresentable);

    domainObjectConverterExporter =
        new DomainObjectConverterExporter(
            freemarkerService, domainObjectConverterClassRepresentable);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private JavaApiDetailGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance java api impl generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @return the java api impl generator
   */
  public static JavaApiDetailGenerator newInstance(
      Path generationPath, PackageName rootPackageName) {
    return new JavaApiDetailGenerator(
        generationPath,
        rootPackageName,
        serviceImplementationExporter,
        serviceImplementationTestExporter,
        domainObjectConverterExporter);
  }
}
