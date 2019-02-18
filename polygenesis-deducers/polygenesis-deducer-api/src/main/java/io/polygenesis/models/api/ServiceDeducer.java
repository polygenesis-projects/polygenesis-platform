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
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.datatype.PackageName;
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

    Set<Dto> dtos = dtoDeducer.deduce(thing.getFunctions());

    Set<Method> commandMethods = getCommandMethods(thing);
    if (!commandMethods.isEmpty()) {
      services.add(
          new Service(
              makeServicePackageName(rootPackageName, thing.getName()),
              makeCommandServiceName(thing.getName()),
              commandMethods,
              CqsType.COMMAND,
              thing.getName(),
              dtos));
    }

    Set<Method> queryMethods = getQueryMethods(thing);
    if (!queryMethods.isEmpty()) {
      services.add(
          new Service(
              makeServicePackageName(rootPackageName, thing.getName()),
              makeQueryServiceName(thing.getName()),
              queryMethods,
              CqsType.QUERY,
              thing.getName(),
              dtos));
    }

    return services;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<Method> getCommandMethods(Thing thing) {
    Set<Method> methods = new LinkedHashSet<>();

    thing
        .getFunctions()
        .stream()
        .filter(function -> function.getGoal().isCommand())
        .forEach(function -> methods.add(new Method(function)));

    return methods;
  }

  private Set<Method> getQueryMethods(Thing thing) {
    Set<Method> methods = new LinkedHashSet<>();

    thing
        .getFunctions()
        .stream()
        .filter(function -> !function.getGoal().isCommand())
        .forEach(function -> methods.add(new Method(function)));

    return methods;
  }

  private PackageName makeServicePackageName(PackageName rootPackageName, ThingName thingName) {
    return new PackageName(
        rootPackageName.getText() + "." + TextConverter.toLowerCase(thingName.getText()));
  }

  private ServiceName makeCommandServiceName(ThingName thingName) {
    return new ServiceName(thingName.getText() + "Service");
  }

  private ServiceName makeQueryServiceName(ThingName thingName) {
    return new ServiceName(thingName.getText() + "QueryService");
  }
}
