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

package io.polygenesis.models.domain;

import io.polygenesis.commons.valueobjects.PackageName;

/**
 * The type Domain service deducer factory.
 *
 * @author Christos Tsakostas
 */
public final class HelperEntityDeducerFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static DomainObjectConstructorDeducer domainObjectConstructorDeducer;
  private static HelperEntityPropertyDeducer helperEntityPropertyDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    domainObjectConstructorDeducer = new DomainObjectConstructorDeducer();
    helperEntityPropertyDeducer = new HelperEntityPropertyDeducer();
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  private HelperEntityDeducerFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * New instance domain service deducer.
   *
   * @param packageName the package name
   * @return the domain service deducer
   */
  public static HelperEntityDeducer newInstance(PackageName packageName) {
    return new HelperEntityDeducer(domainObjectConstructorDeducer, helperEntityPropertyDeducer);
  }
}
