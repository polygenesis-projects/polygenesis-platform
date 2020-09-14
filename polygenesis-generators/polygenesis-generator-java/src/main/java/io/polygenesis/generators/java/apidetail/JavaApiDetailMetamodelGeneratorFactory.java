/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.PassiveFileExporter;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.apidetail.aspect.ServiceAspectActivityRegistry;
import io.polygenesis.generators.java.apidetail.aspect.ServiceAspectGenerator;
import io.polygenesis.generators.java.apidetail.aspect.ServiceAspectMethodTransformer;
import io.polygenesis.generators.java.apidetail.aspect.ServiceAspectTransformer;
import io.polygenesis.generators.java.apidetail.converter.DomainObjectConverterActivityRegistry;
import io.polygenesis.generators.java.apidetail.converter.DomainObjectConverterGenerator;
import io.polygenesis.generators.java.apidetail.converter.DomainObjectConverterMethodTransformer;
import io.polygenesis.generators.java.apidetail.converter.DomainObjectConverterTransformer;
import io.polygenesis.generators.java.apidetail.service.ServiceDetailGenerator;
import io.polygenesis.generators.java.apidetail.service.ServiceDetailMethodActivityRegistry;
import io.polygenesis.generators.java.apidetail.service.ServiceDetailMethodTransformer;
import io.polygenesis.generators.java.apidetail.service.ServiceDetailTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

public final class JavaApiDetailMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static ServiceDetailGenerator serviceDetailGenerator;
  private static DomainObjectConverterGenerator domainObjectConverterGenerator;
  private static ServiceAspectGenerator serviceAspectGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter activeFileExporter = new ActiveFileExporter();
    final Exporter passiveFileExporter = new PassiveFileExporter();
    final DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    domainObjectConverterGenerator =
        new DomainObjectConverterGenerator(
            new DomainObjectConverterTransformer(
                dataTypeTransformer,
                new DomainObjectConverterMethodTransformer(
                    dataTypeTransformer, new DomainObjectConverterActivityRegistry())),
            templateEngine,
            passiveFileExporter);

    // =============================================================================================

    serviceDetailGenerator =
        new ServiceDetailGenerator(
            new ServiceDetailTransformer(
                dataTypeTransformer,
                new ServiceDetailMethodTransformer(
                    dataTypeTransformer, new ServiceDetailMethodActivityRegistry())),
            templateEngine,
            passiveFileExporter);

    // =============================================================================================

    serviceAspectGenerator =
        new ServiceAspectGenerator(
            new ServiceAspectTransformer(
                dataTypeTransformer,
                new ServiceAspectMethodTransformer(
                    dataTypeTransformer, new ServiceAspectActivityRegistry())),
            templateEngine,
            activeFileExporter);
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
   * @param contextName the context name
   * @param rootPackageName the root package name
   * @return the java api impl generator
   */
  public static JavaApiDetailMetamodelGenerator newInstance(
      Path generationPath, ContextName contextName, PackageName rootPackageName) {
    return new JavaApiDetailMetamodelGenerator(
        generationPath,
        contextName,
        rootPackageName,
        serviceDetailGenerator,
        domainObjectConverterGenerator,
        serviceAspectGenerator);
  }
}
