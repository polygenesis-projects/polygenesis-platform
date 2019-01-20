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

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.text.Text;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ThingTest extends AbstractEqualityTest<Thing> {

  @Test
  public void shouldCreateThingWithName() {
    Thing thing = new Thing(new Text("someThingName"));

    assertThat(thing).isNotNull();

    Function function1 = createGoal1();
    Function function2 = createGoal2();
    Function function3 = createGoal3();

    Set<Function> functionSet = new LinkedHashSet<>();
    functionSet.add(function2);
    functionSet.add(function3);

    thing.appendFunction(function1);
    thing.appendFunctions(functionSet);

    assertThat(thing.getName()).isEqualTo(new Text("someThingName"));
    assertThat(thing.getParent()).isNull();
    assertThat(thing.getFunctions().size()).isEqualTo(3);
  }

  @Test
  public void shouldCreateThingWithNameAndParent() {
    Thing parentThing = new Thing(new Text("someParentThingName"));
    Thing thing = new Thing(new Text("someThingName"), parentThing);

    assertThat(thing).isNotNull();
    assertThat(thing.getName()).isEqualTo(new Text("someThingName"));
    assertThat(thing.getParent()).isEqualTo(parentThing);
    assertThat(thing.getParent()).isEqualTo(new Thing(new Text("someParentThingName")));
  }

  private Function createGoal1() {
    return new Function(
        new Thing(new Text("thingName")),
        new Goal("someGoal"),
        new Text("goalName"),
        new LinkedHashSet<>(),
        new ReturnValue(createIoModelPrimitive()));
  }

  private Function createGoal2() {
    return new Function(
        new Thing(new Text("thingName")),
        new Goal("someGoal"),
        new Text("anotherGoalName"),
        new LinkedHashSet<>(),
        new ReturnValue(createIoModelPrimitive()));
  }

  private Function createGoal3() {
    return new Function(
        new Thing(new Text("thingName")),
        new Goal("someGoal"),
        new Text("someAnotherNewGoalName"),
        new LinkedHashSet<>(),
        new ReturnValue(createIoModelPrimitive()));
  }

  private IoModelPrimitive createIoModelPrimitive() {
    return new IoModelPrimitive(
        new Text("datatype"), new Text("variableName"), new LinkedHashSet<>());
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Override
  public Thing createObject1() {
    return new Thing(new Text("xxx"));
  }

  @Override
  public Thing createObject2() {
    return new Thing(new Text("yyy"));
  }
}