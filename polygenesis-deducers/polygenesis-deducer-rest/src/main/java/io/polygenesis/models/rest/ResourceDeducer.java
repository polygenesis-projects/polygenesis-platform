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

package io.polygenesis.models.rest;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Function;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceModelRepository;
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
      ServiceModelRepository serviceModelRepository,
      PackageName rootPackageName) {
    Set<Resource> resources = new LinkedHashSet<>();

    thingRepository
        .getApiThings()
        .forEach(
            thing -> {
              Set<Endpoint> endpoints = new LinkedHashSet<>();

              fillEndpoints(endpoints, thing, serviceModelRepository);

              resources.add(
                  new Resource(
                      thing.makePackageName(rootPackageName, thing),
                      new ObjectName(thing.getThingName().getText()),
                      endpoints,
                      serviceModelRepository.getServicesBy(thing.getThingName())));
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
   * @param thing the thing
   * @param serviceModelRepository the service model repository
   */
  private void fillEndpoints(
      Set<Endpoint> endpoints, Thing thing, ServiceModelRepository serviceModelRepository) {
    thing
        .getFunctions()
        .forEach(
            function ->
                fillEndpointForFunction(
                    endpoints, function, getServiceFor(function, serviceModelRepository)));

    // TODO: must implement service has function first!
    //    thing.getChildren()
    //        .forEach(thingChild -> fillEndpoints(endpoints, thingChild, serviceModelRepository));
    //
    //    thing.getVirtualChildren()
    //        .forEach(thingChild -> fillEndpoints(endpoints, thingChild, serviceModelRepository));
  }

  /**
   * Fill endpoint for function.
   *
   * @param endpoints the endpoints
   * @param function the function
   * @param service the service
   */
  private void fillEndpointForFunction(
      Set<Endpoint> endpoints, Function function, Service service) {
    Optional<Endpoint> optionalEndpoint = endpointDeducer.deduceFromFunction(function, service);
    if (optionalEndpoint.isPresent()) {
      endpoints.add(optionalEndpoint.get());
    }
  }

  /**
   * Gets service for.
   *
   * @param function the function
   * @param serviceModelRepository the service model repository
   * @return the service for
   */
  private Service getServiceFor(Function function, ServiceModelRepository serviceModelRepository) {
    if (serviceModelRepository.getServices().isEmpty()) {
      throw new IllegalStateException(
          "serviceModelRepository is create. Check the order of the decucers.");
    }

    Optional<Service> optionalService =
        serviceModelRepository
            .getServicesBy(function.getThing().getThingName())
            .stream()
            .filter(service -> service.contains(function))
            .findFirst();

    if (optionalService.isPresent()) {
      return optionalService.get();
    } else {
      throw new IllegalStateException("Could not find Service for Function");
    }
  }
}
