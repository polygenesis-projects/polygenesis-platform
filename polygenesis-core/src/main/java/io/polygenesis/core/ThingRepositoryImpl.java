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

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Set<Thing> getItems() {
    return things;
  }

  @Override
  public Optional<Thing> getItemByObjectName(ObjectName objectName) {
    return Optional.empty();
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  @Override
  public Set<Thing> getApiThings() {
    return things
        .stream()
        .filter(
            thing ->
                thing.getThingType().equals(ThingType.DOMAIN_AGGREGATE_ROOT)
                    || thing.getThingType().equals(ThingType.DOMAIN_AGGREGATE_ENTITY)
                    || thing.getThingType().equals(ThingType.DOMAIN_SUPPORTIVE_ENTITY))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public Set<Thing> getDomainServiceThings() {
    return things
        .stream()
        .filter(thing -> thing.getThingType().equals(ThingType.DOMAIN_SERVICE))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public Set<Thing> getDomainModelThings() {
    return things
        .stream()
        .filter(
            thing ->
                thing.getThingType().equals(ThingType.DOMAIN_AGGREGATE_ROOT)
                    || thing.getThingType().equals(ThingType.DOMAIN_AGGREGATE_ENTITY)
                    || thing.getThingType().equals(ThingType.DOMAIN_SUPPORTIVE_ENTITY))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public Optional<Thing> getThingByName(ThingName thingName) {
    return things.stream().filter(thing -> thing.getThingName().equals(thingName)).findFirst();
  }

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

  @Override
  public Boolean isVirtualChild(Thing thingToCheck) {
    return things
        .stream()
        .flatMap(thing -> thing.getVirtualChildren().stream())
        .filter(thing -> thing.equals(thingToCheck))
        .anyMatch(Objects::nonNull);
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setThings(Set<Thing> things) {
    Assertion.isNotNull(things, "things is required");
    Set<Thing> thingsAll = new LinkedHashSet<>();

    things.forEach(thing -> fillThings(thingsAll, thing));

    this.things = thingsAll;
  }

  private void fillThings(Set<Thing> thingsAll, Thing thing) {
    thingsAll.add(thing);

    thing.getChildren().forEach(thingChild -> fillThings(thingsAll, thingChild));
    thing
        .getVirtualChildren()
        .forEach(thingVirtualChild -> fillThings(thingsAll, thingVirtualChild));
  }
}
