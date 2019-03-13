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
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ThingTest extends AbstractEqualityTest<Thing> {

  @Test
  public void shouldCreateThingWithName() {
    Thing thing = new ThingBuilder().setThingName(new ThingName("someThingName")).createThing();

    assertThat(thing).isNotNull();

    Function function1 = createFunction1();
    Function function2 = createFunction2();
    Function function3 = createFunction3();

    Set<Function> functionSet = new LinkedHashSet<>();
    functionSet.add(function2);
    functionSet.add(function3);

    thing.addFunction(function1);
    thing.addFunctions(functionSet);

    assertThat(thing.getThingName()).isEqualTo(new ThingName("someThingName"));
    assertThat(thing.getChildren()).isEmpty();
    assertThat(thing.getFunctions().size()).isEqualTo(3);
  }

  private Function createFunction1() {
    return new Function(
        new ThingBuilder().setThingName(new ThingName("thingName")).createThing(),
        new Goal(GoalType.MODIFY),
        new FunctionName("functionName"),
        new LinkedHashSet<>(),
        new ReturnValue(createDataPrimitive()));
  }

  private Function createFunction2() {
    return new Function(
        new ThingBuilder().setThingName(new ThingName("thingName")).createThing(),
        new Goal(GoalType.MODIFY),
        new FunctionName("anotherFunctionName"),
        new LinkedHashSet<>(),
        new ReturnValue(createDataPrimitive()));
  }

  private Function createFunction3() {
    return new Function(
        new ThingBuilder().setThingName(new ThingName("thingName")).createThing(),
        new Goal(GoalType.MODIFY),
        new FunctionName("someAnotherNewFunctionName"),
        new LinkedHashSet<>(),
        new ReturnValue(createDataPrimitive()));
  }

  private DataPrimitive createDataPrimitive() {
    return new DataPrimitive(
        PrimitiveType.STRING,
        new VariableName("variableName"),
        new LinkedHashSet<>(),
        DataBusinessType.ANY);
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Override
  public Thing createObject1() {
    return new ThingBuilder().setThingName(new ThingName("xxx")).createThing();
  }

  @Override
  public Thing createObject2() {
    return new ThingBuilder().setThingName(new ThingName("yyy")).createThing();
  }
}
