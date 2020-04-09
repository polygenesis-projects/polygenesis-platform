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

package io.polygenesis.models.api;

import io.polygenesis.commons.valueobjects.PackageName;

/**
 * The type Api deducer factory.
 *
 * @author Christos Tsakostas
 */
public final class ApiDeducerFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final ServiceDeducer serviceDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    DtoDeducer dtoDeducer = new DtoDeducer();
    serviceDeducer = new ServiceDeducer(dtoDeducer);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  private ApiDeducerFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * New instance api deducer.
   *
   * @param packageName the package name
   * @return the api deducer
   */
  public static ApiDeducer newInstance(PackageName packageName) {
    return new ApiDeducer(packageName, serviceDeducer);
  }
}
