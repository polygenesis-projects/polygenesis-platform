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

package io.polygenesis.generators.java.domainserviceimpl;

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import java.nio.file.Path;

/**
 * The type Domain service implementation generator factory.
 *
 * @author Christos Tsakostas
 */
public final class DomainServiceImplementationGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final DomainServiceImplementationExporter domainServiceImplementationExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    DomainServiceImplementationClassRepresentable domainServiceImplementationClassRepresentable =
        new DomainServiceImplementationClassRepresentable(fromDataTypeToJavaConverter);

    domainServiceImplementationExporter =
        new DomainServiceImplementationExporter(
            freemarkerService, domainServiceImplementationClassRepresentable);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DomainServiceImplementationGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance domain service implementation generator.
   *
   * @param generationPath the generation path
   * @return the domain service implementation generator
   */
  public static DomainServiceImplementationGenerator newInstance(Path generationPath) {
    return new DomainServiceImplementationGenerator(
        generationPath, domainServiceImplementationExporter);
  }
}
