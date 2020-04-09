/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ThingRepository implements AbstractionRepository<Thing> {

  private Set<Thing> things;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  public static ThingRepository empty() {
    return new ThingRepository(new LinkedHashSet<>());
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Thing repository.
   *
   * @param things the things
   */
  public ThingRepository(Set<Thing> things) {
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
  public Set<Thing> getAbstractionItemsByScope(AbstractionScope abstractionScope) {
    return things.stream()
        .filter(thing -> thing.getAbstractionsScopes().contains(abstractionScope))
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Thing> getAbstractionItemsByScopes(Set<AbstractionScope> abstractionScopes) {
    Set<Thing> things = new LinkedHashSet<>();

    abstractionScopes.forEach(
        abstractionScope -> things.addAll(getAbstractionItemsByScope(abstractionScope)));

    return things;
  }

  @Override
  public void addAbstractionItem(Thing abstraction) {
    things.add(abstraction);
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
  }
}
