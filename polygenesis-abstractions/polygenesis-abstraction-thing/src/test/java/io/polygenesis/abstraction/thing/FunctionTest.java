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

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.core.Goal;
import io.polygenesis.core.GoalType;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import java.util.LinkedHashSet;
import org.junit.Test;

/** @author Christos Tsakostas */
public class FunctionTest extends AbstractEqualityTest<Function> {

  @Test
  public void shouldCreateFunction() {
    Function function1 = createObject1();

    assertThat(function1).isNotNull();
    assertThat(function1.getThing()).isNotNull();
    assertThat(function1.getGoal()).isNotNull();
    assertThat(function1.getName()).isNotNull();
    assertThat(function1.getReturnValue()).isNotNull();
    assertThat(function1.getArguments()).isNotNull();
  }

  @Test
  public void shouldCreateFunctionWithReturnValue() {
    Function function1 = createFunctionWithReturnValueAndNoArguments();

    assertThat(function1).isNotNull();
    assertThat(function1.getThing()).isNotNull();
    assertThat(function1.getGoal()).isNotNull();
    assertThat(function1.getName()).isNotNull();
    assertThat(function1.getReturnValue()).isNotNull();

    assertThat(function1.getArguments()).isEmpty();
  }

  @Test
  public void shouldCreateFunctionWithArguments() {
    Function function1 = createFunctionWithArgumentsAndNoReturnValue();

    assertThat(function1).isNotNull();
    assertThat(function1.getThing()).isNotNull();
    assertThat(function1.getGoal()).isNotNull();
    assertThat(function1.getName()).isNotNull();
    assertThat(function1.getArguments()).isNotNull();

    assertThat(function1.getReturnValue()).isNull();
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Function createObject1() {
    return new Function(
        ThingBuilder.generic().setThingName(new ThingName("thingName")).createThing(),
        new Goal(GoalType.CREATE),
        new FunctionName("functionName"),
        new LinkedHashSet<>(),
        new ReturnValue(createDataPrimitive()));
  }

  @Override
  public Function createObject2() {
    return new Function(
        ThingBuilder.generic().setThingName(new ThingName("thingName")).createThing(),
        new Goal(GoalType.CREATE),
        new FunctionName("anotherFunctionName"),
        new LinkedHashSet<>(),
        new ReturnValue(createDataPrimitive()));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function createFunctionWithReturnValueAndNoArguments() {
    return new Function(
        ThingBuilder.generic().setThingName(new ThingName("thingName")).createThing(),
        new Goal(GoalType.CREATE),
        new FunctionName("functionName"),
        new ReturnValue(createDataPrimitive()));
  }

  private Function createFunctionWithArgumentsAndNoReturnValue() {
    return new Function(
        ThingBuilder.generic().setThingName(new ThingName("thingName")).createThing(),
        new Goal(GoalType.CREATE),
        new FunctionName("functionName"),
        new LinkedHashSet<>());
  }

  private DataPrimitive createDataPrimitive() {
    return new DataPrimitive(
        PrimitiveType.STRING,
        new VariableName("variableName"),
        new LinkedHashSet<>(),
        DataBusinessType.ANY);
  }
}
