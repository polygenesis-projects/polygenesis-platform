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

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.core.AbstractModelRepository;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service model repository.
 *
 * @author Christos Tsakostas
 */
public class ServiceModelRepository extends AbstractModelRepository<Service>
    implements ModelRepository<Service> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service model repository.
   *
   * @param items the items
   */
  public ServiceModelRepository(Set<Service> items) {
    super(items);
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
    return getItems()
        .stream()
        .filter(service -> service.getThingName().equals(thingName))
        .collect(toCollection(LinkedHashSet::new));
  }
}
