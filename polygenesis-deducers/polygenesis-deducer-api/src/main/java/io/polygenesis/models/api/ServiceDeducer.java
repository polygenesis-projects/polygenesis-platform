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

import io.polygenesis.annotations.core.CqsType;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service deducer.
 *
 * @author Christos Tsakostas
 */
public class ServiceDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ServiceMethodDeducer serviceMethodDeducer;
  private final DtoDeducer dtoDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service deducer.
   *
   * @param serviceMethodDeducer the service method deducer
   * @param dtoDeducer the dto deducer
   */
  public ServiceDeducer(ServiceMethodDeducer serviceMethodDeducer, DtoDeducer dtoDeducer) {
    this.serviceMethodDeducer = serviceMethodDeducer;
    this.dtoDeducer = dtoDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thing the thing
   * @param rootPackageName the package name
   * @return the set
   */
  public Set<Service> deduceFrom(Thing thing, PackageName rootPackageName) {
    Set<Service> services = new LinkedHashSet<>();

    fillServices(services, thing, rootPackageName);

    return services;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Fill services.
   *
   * @param services the services
   * @param thing the thing
   * @param rootPackageName the root package name
   */
  private void fillServices(Set<Service> services, Thing thing, PackageName rootPackageName) {
    Set<Method> commandMethods = new LinkedHashSet<>();
    Set<Method> queryMethods = new LinkedHashSet<>();

    serviceMethodDeducer.deduceCommandMethods(commandMethods, thing, rootPackageName);
    serviceMethodDeducer.deduceQueryMethods(queryMethods, thing, rootPackageName);

    Set<Method> allMethods = new LinkedHashSet<>();
    allMethods.addAll(commandMethods);
    allMethods.addAll(queryMethods);

    Set<Dto> dtos = dtoDeducer.deduceAllDtosInMethods(allMethods, rootPackageName);

    if (!commandMethods.isEmpty()) {
      services.add(
          new Service(
              thing.makePackageName(rootPackageName, thing),
              makeCommandServiceName(thing.getThingName()),
              commandMethods,
              CqsType.COMMAND,
              thing.getThingName(),
              dtos));
    }

    if (!queryMethods.isEmpty()) {
      services.add(
          new Service(
              thing.makePackageName(rootPackageName, thing),
              makeQueryServiceName(thing.getThingName()),
              queryMethods,
              CqsType.QUERY,
              thing.getThingName(),
              dtos));
    }

    thing.getChildren().forEach(thingChild -> fillServices(services, thingChild, rootPackageName));

    thing
        .getVirtualChildren()
        .forEach(thingVirtualChild -> fillServices(services, thingVirtualChild, rootPackageName));
  }

  private ServiceName makeCommandServiceName(ThingName thingName) {
    return new ServiceName(thingName.getText() + "Service");
  }

  private ServiceName makeQueryServiceName(ThingName thingName) {
    return new ServiceName(thingName.getText() + "QueryService");
  }
}
