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

package io.polygenesis.models.api;

import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.datatype.PackageName;

/**
 * The type Api deducer.
 *
 * @author Christos Tsakostas
 */
public class ApiDeducerImpl implements ApiDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ServiceDeducer serviceDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api deducer.
   *
   * @param rootPackageName the package name
   * @param serviceDeducer the service deducer
   */
  public ApiDeducerImpl(PackageName rootPackageName, ServiceDeducer serviceDeducer) {
    this.rootPackageName = rootPackageName;
    this.serviceDeducer = serviceDeducer;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ServiceModelRepository deduce(ThingRepository thingRepository) {
    if (thingRepository.getThings().isEmpty()) {
      throw new IllegalStateException("thingRepository cannot be empty");
    }

    return new ServiceModelRepository(
        serviceDeducer.deduceFrom(thingRepository, getRootPackageName()));
  }
}
