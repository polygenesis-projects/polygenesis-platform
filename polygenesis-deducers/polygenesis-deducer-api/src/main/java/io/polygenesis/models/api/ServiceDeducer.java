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

import io.polygenesis.abstraction.thing.CqsType;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionScope;
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

  private final DtoDeducer dtoDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service deducer.
   *
   * @param dtoDeducer the dto deducer
   */
  public ServiceDeducer(DtoDeducer dtoDeducer) {
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
    if (numberOfCommandFunctions(thing) > 0) {
      Service service =
          new Service(
              thing.makePackageName(rootPackageName, thing),
              new ServiceName(thing.getThingName().getText() + "CommandService"),
              CqsType.COMMAND,
              thing.getThingName());

      appendCommandMethods(service, thing, rootPackageName);

      // CHILDREN
      thing
          .getChildren()
          .forEach(childThing -> appendCommandMethods(service, childThing, rootPackageName));

      services.add(service);
    }

    if (numberOfQueryFunctions(thing) > 0) {
      Service service =
          new Service(
              thing.makePackageName(rootPackageName, thing),
              new ServiceName(thing.getThingName().getText() + "QueryService"),
              CqsType.QUERY,
              thing.getThingName());

      appendQueryMethods(service, thing, rootPackageName);

      // CHILDREN
      thing
          .getChildren()
          .forEach(childThing -> appendQueryMethods(service, childThing, rootPackageName));

      services.add(service);
    }
  }

  private long numberOfCommandFunctions(Thing thing) {
    return thing
        .getFunctions()
        .stream()
        .filter(function -> function.supportsAbstractionScope(AbstractionScope.api()))
        .filter(function -> function.getPurpose().isCommand())
        .count();
  }

  private long numberOfQueryFunctions(Thing thing) {
    return thing
        .getFunctions()
        .stream()
        .filter(function -> function.supportsAbstractionScope(AbstractionScope.api()))
        .filter(function -> function.getPurpose().isQuery())
        .count();
  }

  private void appendCommandMethods(Service service, Thing thing, PackageName rootPackageName) {
    thing
        .getFunctions()
        .stream()
        .filter(function -> function.supportsAbstractionScope(AbstractionScope.api()))
        .filter(function -> function.getPurpose().isCommand())
        .forEach(
            function ->
                service.appendServiceMethod(
                    function,
                    dtoDeducer.deduceRequestDto(function, rootPackageName),
                    dtoDeducer.deduceResponseDto(function, rootPackageName)));
  }

  private void appendQueryMethods(Service service, Thing thing, PackageName rootPackageName) {
    thing
        .getFunctions()
        .stream()
        .filter(function -> function.supportsAbstractionScope(AbstractionScope.api()))
        .filter(function -> function.getPurpose().isQuery())
        .forEach(
            function ->
                service.appendServiceMethod(
                    function,
                    dtoDeducer.deduceRequestDto(function, rootPackageName),
                    dtoDeducer.deduceResponseDto(function, rootPackageName)));
  }
}
