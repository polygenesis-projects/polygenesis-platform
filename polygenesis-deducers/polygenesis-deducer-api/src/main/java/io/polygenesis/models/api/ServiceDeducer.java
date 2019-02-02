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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.ThingRepository;
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
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thingRepository the thing repository
   * @param rootPackageName the package name
   * @return the set
   */
  public Set<Service> deduceFrom(ThingRepository thingRepository, PackageName rootPackageName) {
    Set<Service> services = new LinkedHashSet<>();

    thingRepository
        .getThings()
        .forEach(
            thing ->
                services.add(
                    new Service(
                        makeServicePackageName(rootPackageName, thing.getName()),
                        makeServiceName(thing.getName()),
                        getCommandMethods(thing),
                        ServiceCqrsType.COMMAND,
                        thing.getName())));

    return services;
  }

  private Set<Method> getCommandMethods(Thing thing) {
    Set<Method> methods = new LinkedHashSet<>();

    thing
        .getFunctions()
        .forEach(
            function ->
                methods.add(
                    new Method(
                        new MethodName(function.getName().getText()),
                        function.getReturnValue(),
                        function.getArguments())));

    return methods;
  }

  private PackageName makeServicePackageName(PackageName rootPackageName, ThingName thingName) {
    return new PackageName(
        rootPackageName.getText() + "." + TextConverter.toLowerCase(thingName.getText()));
  }

  private ServiceName makeServiceName(ThingName thingName) {
    return new ServiceName(thingName.getText() + "Service");
  }
}
