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

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.annotations.core.GoalType;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ThingRepositoryTest {

  @Test
  public void shouldSucceedToMakeDeducer() {
    ThingRepository repository = new ThingRepositoryImpl(new LinkedHashSet<>());

    assertThat(repository).isNotNull();
    assertThat(repository.getApiThings().size()).isEqualTo(0);
  }

  @Test
  public void shouldSucceedToGetThingByName() {
    ThingRepository repository = new ThingRepositoryImpl(createThings());

    assertThat(repository.getThingByName(new ThingName("someThing"))).isPresent();
  }

  @Test
  public void shouldSucceedToGetThingFunction() {
    ThingRepository repository = new ThingRepositoryImpl(createThings());

    assertThat(
            repository.getThingFunction(
                new ThingName("someThing"), new FunctionName("someFunction")))
        .isPresent();
  }

  @Test
  public void shouldFailToGetThingFunction() {
    ThingRepository repository = new ThingRepositoryImpl(createThings());

    assertThat(
            repository.getThingFunction(
                new ThingName("someThing"), new FunctionName("someNonExistingGoal")))
        .isEmpty();
  }

  @Test
  public void shouldFailToGetThingGoalForNonExistingThing() {
    ThingRepository repository = new ThingRepositoryImpl(createThings());

    assertThat(
            repository.getThingFunction(
                new ThingName("someNonExistingThing"), new FunctionName("someNonExistingGoal")))
        .isEmpty();
  }

  private Set<Thing> createThings() {
    Set<Thing> things = new LinkedHashSet<>();

    Thing someThing = new Thing(new ThingName("someThing"));

    Function someFunction =
        new Function(
            someThing,
            new Goal(GoalType.CREATE),
            new FunctionName("someFunction"),
            new LinkedHashSet<>());

    someThing.appendFunction(someFunction);

    things.add(someThing);
    return things;
  }
}
