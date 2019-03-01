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

package io.polygenesis.core;

import com.oregor.ddd4j.check.assertion.Assertion;
import java.util.Optional;
import java.util.Set;

/**
 * In-memory repository for Things.
 *
 * <p>It also offers convenience query methods.
 *
 * @author Christos Tsakostas
 */
public class ThingRepositoryImpl implements ThingRepository {

  private Set<Thing> things;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Thing repository.
   *
   * @param things the things
   */
  public ThingRepositoryImpl(Set<Thing> things) {
    setThings(things);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /** {@inheritDoc} */
  @Override
  public Set<Thing> getThings() {
    return things;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Thing> getThingByName(ThingName thingName) {
    return things.stream().filter(thing -> thing.getName().equals(thingName)).findFirst();
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Function> getThingFunction(ThingName thingName, FunctionName functionName) {
    Optional<Thing> optionalThing = getThingByName(thingName);

    return optionalThing.flatMap(
        thing ->
            thing
                .getFunctions()
                .stream()
                .filter(function -> function.getName().equals(functionName))
                .findFirst());
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setThings(Set<Thing> things) {
    Assertion.isNotNull(things, "things is required");
    this.things = things;
  }
}
