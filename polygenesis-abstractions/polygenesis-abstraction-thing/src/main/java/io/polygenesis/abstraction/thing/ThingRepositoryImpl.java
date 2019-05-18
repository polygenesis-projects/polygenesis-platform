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

package io.polygenesis.abstraction.thing;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.MetamodelType;
import java.util.LinkedHashSet;
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
  public Set<Thing> getAllAbstractionItems() {
    return things;
  }

  @Override
  public Optional<Thing> getAbstractionItemByObjectName(ObjectName objectName) {
    return things.stream().filter(thing -> thing.getObjectName().equals(objectName)).findFirst();
  }

  @Override
  public Set<Thing> getAbstractionItemsByMetamodelType(MetamodelType metamodelType) {
    switch (metamodelType) {
      case API:
        return getApiThings();
      case DOMAIN:
        return getDomainModelThings();
      case DOMAIN_SERVICE:
        return getDomainServiceThings();
      default:
        throw new IllegalStateException();
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Gets api things.
   *
   * @return the api things
   */
  private Set<Thing> getApiThings() {
    return things
        .stream()
        .filter(
            thing ->
                thing.getThingType().equals(ThingType.DOMAIN_AGGREGATE_ROOT)
                    || thing.getThingType().equals(ThingType.DOMAIN_AGGREGATE_ENTITY)
                    || thing.getThingType().equals(ThingType.DOMAIN_SUPPORTIVE_ENTITY))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Gets domain model things.
   *
   * @return the domain model things
   */
  private Set<Thing> getDomainModelThings() {
    return things
        .stream()
        .filter(
            thing ->
                thing.getThingType().equals(ThingType.DOMAIN_AGGREGATE_ROOT)
                    || thing.getThingType().equals(ThingType.DOMAIN_AGGREGATE_ENTITY)
                    || thing.getThingType().equals(ThingType.DOMAIN_SUPPORTIVE_ENTITY))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Gets domain service things.
   *
   * @return the domain service things
   */
  private Set<Thing> getDomainServiceThings() {
    return things
        .stream()
        .filter(thing -> thing.getThingType().equals(ThingType.DOMAIN_SERVICE))
        .collect(Collectors.toCollection(LinkedHashSet::new));
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
