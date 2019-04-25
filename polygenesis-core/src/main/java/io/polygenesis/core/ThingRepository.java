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

package io.polygenesis.core;

import java.util.Optional;
import java.util.Set;

/**
 * The interface Thing repository.
 *
 * @author Christos Tsakostas
 */
public interface ThingRepository extends MetamodelRepository<Thing> {

  /**
   * Gets api things.
   *
   * @return the api things
   */
  Set<Thing> getApiThings();

  /**
   * Gets domain service things.
   *
   * @return the domain service things
   */
  Set<Thing> getDomainServiceThings();

  /**
   * Gets domain model things.
   *
   * @return the domain model things
   */
  Set<Thing> getDomainModelThings();

  /**
   * Gets thing by name.
   *
   * @param thingName the thing name
   * @return the thing by name
   */
  Optional<Thing> getThingByName(ThingName thingName);

  /**
   * Gets a thing's function.
   *
   * @param thingName the thing name
   * @param functionName the function name
   * @return the thing function
   */
  Optional<Function> getThingFunction(ThingName thingName, FunctionName functionName);

  /**
   * Is virtual child boolean.
   *
   * @param thingToCheck the thing to check
   * @return the boolean
   */
  Boolean isVirtualChild(Thing thingToCheck);
}
