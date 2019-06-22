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

package io.polygenesis.generators.java.apidetail;

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.JavaDataTypeTransformer;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.apidetail.converter.DomainObjectConverterExporter;
import io.polygenesis.generators.java.apidetail.converter.DomainObjectConverterLegacyClassTransformer;
import io.polygenesis.generators.java.apidetail.converter.DomainObjectConverterLegacyMethodTransformer;
import io.polygenesis.generators.java.apidetail.converter.activity.ConverterMethodImplementationRegistry;
import io.polygenesis.generators.java.apidetail.service.ServiceDetailGenerator;
import io.polygenesis.generators.java.apidetail.service.ServiceDetailMethodTransformer;
import io.polygenesis.generators.java.apidetail.service.ServiceDetailTransformer;
import io.polygenesis.generators.java.apidetail.service.activity.ServiceMethodActivityRegistry;
import io.polygenesis.generators.java.shared.transformer.FromDataTypeToJavaConverter;
import java.nio.file.Path;

/**
 * The type Java api detail generator factory.
 *
 * @author Christos Tsakostas
 */
public final class JavaApiDetailMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static ServiceDetailGenerator serviceDetailGenerator;
  private static final DomainObjectConverterExporter domainObjectConverterExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    ConverterMethodImplementationRegistry converterMethodImplementationRegistry =
        new ConverterMethodImplementationRegistry();

    DomainObjectConverterLegacyMethodTransformer domainObjectConverterMethodRepresentable =
        new DomainObjectConverterLegacyMethodTransformer(
            fromDataTypeToJavaConverter, freemarkerService, converterMethodImplementationRegistry);

    DomainObjectConverterLegacyClassTransformer domainObjectConverterClassRepresentable =
        new DomainObjectConverterLegacyClassTransformer(
            fromDataTypeToJavaConverter, domainObjectConverterMethodRepresentable);

    domainObjectConverterExporter =
        new DomainObjectConverterExporter(
            freemarkerService, domainObjectConverterClassRepresentable);

    // =============================================================================================
    ServiceMethodActivityRegistry serviceMethodActivityRegistry =
        new ServiceMethodActivityRegistry();

    serviceDetailGenerator =
        new ServiceDetailGenerator(
            new ServiceDetailTransformer(
                dataTypeTransformer,
                new ServiceDetailMethodTransformer(
                    dataTypeTransformer, serviceMethodActivityRegistry)),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private JavaApiDetailMetamodelGeneratorFactory() {
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
  public static JavaApiDetailMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName) {
    return new JavaApiDetailMetamodelGenerator(
        generationPath, rootPackageName, serviceDetailGenerator, domainObjectConverterExporter);
  }
}
