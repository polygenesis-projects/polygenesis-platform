/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.deducers.stateredux;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.metamodels.stateredux.Model;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

public class ModelDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce set.
   *
   * @param thing the thing
   * @param serviceModelRepository the service model repository
   * @return the set
   */
  public Set<Model> deduce(Thing thing, ServiceMetamodelRepository serviceModelRepository) {
    Set<Model> models = new LinkedHashSet<>();

    Set<Service> services = serviceModelRepository.getServicesBy(thing.getThingName());

    services.forEach(
        service -> service.getDtos().forEach(dto -> models.add(new Model(dto.getDataObject()))));

    return models;
  }
}
