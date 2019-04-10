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

package io.polygenesis.models.domain;

import io.polygenesis.commons.valueobjects.PackageName;

/**
 * The type Supportive entity deducer factory.
 *
 * @author Christos Tsakostas
 */
public final class SupportiveEntityDeducerFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static DomainObjectConstructorDeducer domainObjectConstructorDeducer;
  private static SupportiveEntityPropertyDeducer supportiveEntityPropertyDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    domainObjectConstructorDeducer = new DomainObjectConstructorDeducer();
    supportiveEntityPropertyDeducer = new SupportiveEntityPropertyDeducer();
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  private SupportiveEntityDeducerFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * New instance supportive entity deducer.
   *
   * @param packageName the package name
   * @return the domain service deducer
   */
  public static SupportiveEntityDeducer newInstance(PackageName packageName) {
    return new SupportiveEntityDeducer(
        packageName, domainObjectConstructorDeducer, supportiveEntityPropertyDeducer);
  }
}
