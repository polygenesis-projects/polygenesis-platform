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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingName;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Service model repository.
 *
 * @author Christos Tsakostas
 */
public class ServiceModelRepository implements ModelRepository {

  private Set<Service> services;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service model repository.
   *
   * @param services the services
   */
  public ServiceModelRepository(Set<Service> services) {
    setServices(services);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets services by.
   *
   * @param thingName the thing name
   * @return the services by
   */
  public Set<Service> getServicesBy(ThingName thingName) {
    return services
        .stream()
        .filter(service -> service.getThingName().equals(thingName))
        .collect(Collectors.toSet());
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets services.
   *
   * @return the services
   */
  public Set<Service> getServices() {
    return services;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets services.
   *
   * @param services the services
   */
  private void setServices(Set<Service> services) {
    Assertion.isNotNull(services, "services is required");
    this.services = services;
  }
}
