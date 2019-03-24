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

package io.polygenesis.models.apiimpl;

import io.polygenesis.core.ModelRepository;
import java.util.Set;

/**
 * The type Service implementation model repository.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationModelRepository implements ModelRepository {

  private Set<ServiceImplementation> serviceImplementations;
  private Set<DomainEntityConverter> domainEntityConverters;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public ServiceImplementationModelRepository(
      Set<ServiceImplementation> serviceImplementations,
      Set<DomainEntityConverter> domainEntityConverters) {
    setServiceImplementations(serviceImplementations);
    setDomainEntityConverters(domainEntityConverters);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets service implementations.
   *
   * @return the service implementations
   */
  public Set<ServiceImplementation> getServiceImplementations() {
    return serviceImplementations;
  }

  /**
   * Gets aggregate root converters.
   *
   * @return the aggregate root converters
   */
  public Set<DomainEntityConverter> getDomainEntityConverters() {
    return domainEntityConverters;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets service implementations.
   *
   * @param serviceImplementations the service implementations
   */
  private void setServiceImplementations(Set<ServiceImplementation> serviceImplementations) {
    this.serviceImplementations = serviceImplementations;
  }

  /**
   * Sets aggregate root converters.
   *
   * @param domainEntityConverters the aggregate root converters
   */
  private void setDomainEntityConverters(Set<DomainEntityConverter> domainEntityConverters) {
    this.domainEntityConverters = domainEntityConverters;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

}
