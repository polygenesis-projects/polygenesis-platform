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

package io.polygenesis.generators.java.domainservicedetail;

import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.PassiveFileExporter;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

public final class DomainServiceDetailMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final DomainServiceDetailGenerator domainServiceDetailGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter exporter = new PassiveFileExporter();
    final DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    domainServiceDetailGenerator =
        new DomainServiceDetailGenerator(
            new DomainServiceDetailTransformer(
                dataTypeTransformer, new DomainServiceDetailMethodTransformer(dataTypeTransformer)),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DomainServiceDetailMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance domain service detail metamodel generator.
   *
   * @param generationPath the generation path
   * @return the domain service detail metamodel generator
   */
  public static DomainServiceDetailMetamodelGenerator newInstance(Path generationPath) {
    return new DomainServiceDetailMetamodelGenerator(generationPath, domainServiceDetailGenerator);
  }
}
