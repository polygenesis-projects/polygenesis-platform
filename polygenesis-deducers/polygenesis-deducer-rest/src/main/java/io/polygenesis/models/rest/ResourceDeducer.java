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

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.Function;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.data.PackageName;
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
            thing ->
                resources.add(
                    new Resource(
                        makeResourcePackageName(rootPackageName, thing),
                        makeResourceName(thing),
                        makeEndpoints(thing, serviceModelRepository),
                        serviceModelRepository.getServicesBy(thing.getName()))));

    return resources;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private PackageName makeResourcePackageName(PackageName rootPackageName, Thing thing) {
    return new PackageName(
        rootPackageName.getText() + "." + thing.getName().getText().toLowerCase());
  }

  private Name makeResourceName(Thing thing) {
    return new Name(thing.getName().getText());
  }

  private Set<Endpoint> makeEndpoints(Thing thing, ServiceModelRepository serviceModelRepository) {
    Set<Endpoint> endpoints = new LinkedHashSet<>();

    thing
        .getFunctions()
        .forEach(
            function -> {
              Optional<Endpoint> optionalEndpoint =
                  endpointDeducer.deduceFromFunction(
                      function, getServiceFor(function, serviceModelRepository));
              if (optionalEndpoint.isPresent()) {
                endpoints.add(optionalEndpoint.get());
              }
            });

    return endpoints;
  }

  private Service getServiceFor(Function function, ServiceModelRepository serviceModelRepository) {
    if (serviceModelRepository.getServices().isEmpty()) {
      throw new IllegalStateException(
          "serviceModelRepository is create. Check the order of the decucers.");
    }

    Optional<Service> optionalService =
        serviceModelRepository
            .getServicesBy(function.getThing().getName())
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
