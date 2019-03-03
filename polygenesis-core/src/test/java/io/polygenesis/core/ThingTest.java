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
import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.core.datatype.PrimitiveDataType;
import io.polygenesis.core.datatype.PrimitiveType;
import io.polygenesis.core.iomodel.DataBusinessType;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ThingTest extends AbstractEqualityTest<Thing> {

  @Test
  public void shouldCreateThingWithName() {
    Thing thing = new Thing(new ThingName("someThingName"));

    assertThat(thing).isNotNull();

    Function function1 = createGoal1();
    Function function2 = createGoal2();
    Function function3 = createGoal3();

    Set<Function> functionSet = new LinkedHashSet<>();
    functionSet.add(function2);
    functionSet.add(function3);

    thing.appendFunction(function1);
    thing.appendFunctions(functionSet);

    assertThat(thing.getName()).isEqualTo(new ThingName("someThingName"));
    assertThat(thing.getParent()).isNull();
    assertThat(thing.getFunctions().size()).isEqualTo(3);
  }

  @Test
  public void shouldCreateThingWithNameAndParent() {
    Thing parentThing = new Thing(new ThingName("someParentThingName"));
    Thing thing = new Thing(new ThingName("someThingName"), parentThing);

    assertThat(thing).isNotNull();
    assertThat(thing.getName()).isEqualTo(new ThingName("someThingName"));
    assertThat(thing.getParent()).isEqualTo(parentThing);
    assertThat(thing.getParent()).isEqualTo(new Thing(new ThingName("someParentThingName")));
  }

  private Function createGoal1() {
    return new Function(
        new Thing(new ThingName("thingName")),
        new Goal(GoalType.MODIFY),
        new FunctionName("functionName"),
        new LinkedHashSet<>(),
        new ReturnValue(createIoModelPrimitive()));
  }

  private Function createGoal2() {
    return new Function(
        new Thing(new ThingName("thingName")),
        new Goal(GoalType.MODIFY),
        new FunctionName("anotherFunctionName"),
        new LinkedHashSet<>(),
        new ReturnValue(createIoModelPrimitive()));
  }

  private Function createGoal3() {
    return new Function(
        new Thing(new ThingName("thingName")),
        new Goal(GoalType.MODIFY),
        new FunctionName("someAnotherNewFunctionName"),
        new LinkedHashSet<>(),
        new ReturnValue(createIoModelPrimitive()));
  }

  private IoModelPrimitive createIoModelPrimitive() {
    return new IoModelPrimitive(
        new PrimitiveDataType(PrimitiveType.STRING),
        new VariableName("variableName"),
        new LinkedHashSet<>(),
        DataBusinessType.ANY);
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Override
  public Thing createObject1() {
    return new Thing(new ThingName("xxx"));
  }

  @Override
  public Thing createObject2() {
    return new Thing(new ThingName("yyy"));
  }
}
