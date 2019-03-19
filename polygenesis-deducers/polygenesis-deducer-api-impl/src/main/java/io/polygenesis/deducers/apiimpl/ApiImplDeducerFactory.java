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

package io.polygenesis.deducers.apiimpl;

import io.polygenesis.commons.valueobjects.PackageName;

/**
 * The type Api impl deducer factory.
 *
 * @author Christos Tsakostas
 */
public class ApiImplDeducerFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private static final DomainObjectConverterDeducer domainObjectConverterDeducer;
  private static final ServiceImplementationDeducer serviceImplementationDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    domainObjectConverterDeducer = new DomainObjectConverterDeducer();

    ServiceMethodImplementationDeducer serviceMethodImplementationDeducer =
        new ServiceMethodImplementationDeducer();

    serviceImplementationDeducer =
        new ServiceImplementationDeducer(serviceMethodImplementationDeducer);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  private ApiImplDeducerFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * New instance api impl deducer.
   *
   * @param packageName the package name
   * @return the api impl deducer
   */
  public static ApiImplDeducer newInstance(PackageName packageName) {
    return new ApiImplDeducer(domainObjectConverterDeducer, serviceImplementationDeducer);
  }
}
