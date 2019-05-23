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

package io.polygenesis.models.rest;

import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.api.ServiceMethod;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Resource deducer.
 *
 * @author Christos Tsakostas
 */
public class ResourceDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final EndpointDeducer endpointDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource deducer.
   *
   * @param endpointDeducer the endpoint deducer
   */
  public ResourceDeducer(EndpointDeducer endpointDeducer) {
    this.endpointDeducer = endpointDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thingRepository the thing repository
   * @param serviceModelRepository the service model repository
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<Resource> deduceFrom(
      ThingRepository thingRepository,
      ServiceMetamodelRepository serviceModelRepository,
      PackageName rootPackageName) {
    Set<Resource> resources = new LinkedHashSet<>();

    thingRepository
        .getAbstractionItemsByScope(AbstractionScope.api())
        .forEach(
            thing -> {
              Set<Endpoint> endpoints = new LinkedHashSet<>();
              Set<Service> services = serviceModelRepository.getServicesBy(thing.getThingName());

              fillEndpoints(endpoints, services);

              resources.add(
                  new Resource(
                      thing.makePackageName(rootPackageName, thing),
                      new ObjectName(thing.getThingName().getText()),
                      endpoints,
                      services));
            });

    return resources;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Fill endpoints.
   *
   * @param endpoints the endpoints
   * @param services the services
   */
  private void fillEndpoints(Set<Endpoint> endpoints, Set<Service> services) {
    services.forEach(
        service ->
            service
                .getServiceMethods()
                .forEach(
                    serviceMethod ->
                        fillEndpointForServiceMethod(endpoints, service, serviceMethod)));
  }

  /**
   * Fill endpoint for service method.
   *
   * @param endpoints the endpoints
   * @param service the service
   * @param serviceMethod the service method
   */
  private void fillEndpointForServiceMethod(
      Set<Endpoint> endpoints, Service service, ServiceMethod serviceMethod) {
    Optional<Endpoint> optionalEndpoint =
        endpointDeducer.deduceFromServiceMethod(serviceMethod, service);
    if (optionalEndpoint.isPresent()) {
      endpoints.add(optionalEndpoint.get());
    }
  }
}
