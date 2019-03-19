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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.ModelRepository;
import java.util.Set;

/**
 * The type Domain service repository.
 *
 * @author Christos Tsakostas
 */
public class DomainServiceRepository implements ModelRepository {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<DomainService> domainServices;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service repository.
   *
   * @param domainServices the domain services
   */
  public DomainServiceRepository(Set<DomainService> domainServices) {
    setDomainServices(domainServices);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets domain services.
   *
   * @return the domain services
   */
  public Set<DomainService> getDomainServices() {
    return domainServices;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets domain services.
   *
   * @param domainServices the domain services
   */
  private void setDomainServices(Set<DomainService> domainServices) {
    Assertion.isNotNull(domainServices, "domainServices is required");
    this.domainServices = domainServices;
  }
}
