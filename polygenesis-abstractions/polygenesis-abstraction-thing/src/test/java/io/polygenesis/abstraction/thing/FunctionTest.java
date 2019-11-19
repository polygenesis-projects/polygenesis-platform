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

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.VariableName;
import org.junit.Test;

/** @author Christos Tsakostas */
public class FunctionTest extends AbstractEqualityTest<Function> {

  @Test
  public void shouldCreateFunction() {
    Function function1 = createObject1();

    assertThat(function1).isNotNull();
    assertThat(function1.getThing()).isNotNull();
    assertThat(function1.getPurpose()).isNotNull();
    assertThat(function1.getName()).isNotNull();
    assertThat(function1.getReturnValue()).isNotNull();
    assertThat(function1.getArguments()).isNotNull();
  }

  @Test
  public void shouldCreateFunctionWithReturnValue() {
    Function function1 = createFunctionWithReturnValueAndNoArguments();

    assertThat(function1).isNotNull();
    assertThat(function1.getThing()).isNotNull();
    assertThat(function1.getPurpose()).isNotNull();
    assertThat(function1.getName()).isNotNull();
    assertThat(function1.getReturnValue()).isNotNull();

    assertThat(function1.getArguments()).isEmpty();
  }

  @Test
  public void shouldCreateFunctionWithArguments() {
    Function function1 = createFunctionWithArgumentsAndNoReturnValue();

    assertThat(function1).isNotNull();
    assertThat(function1.getThing()).isNotNull();
    assertThat(function1.getPurpose()).isNotNull();
    assertThat(function1.getName()).isNotNull();
    assertThat(function1.getArguments()).isNotNull();

    assertThat(function1.getReturnValue()).isNull();
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Function createObject1() {
    return FunctionBuilder.of(
            ThingBuilder.endToEnd().setThingName("thingName").createThing(),
            "functionName",
            Purpose.create())
        .setReturnValue(createDataPrimitive())
        .build();
  }

  @Override
  public Function createObject2() {
    return FunctionBuilder.of(
            ThingBuilder.endToEnd().setThingName("thingName").createThing(),
            "anotherFunctionName",
            Purpose.create())
        .setReturnValue(createDataPrimitive())
        .build();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function createFunctionWithReturnValueAndNoArguments() {
    return FunctionBuilder.of(
            ThingBuilder.endToEnd().setThingName("thingName").createThing(),
            "functionName",
            Purpose.create())
        .setReturnValue(createDataPrimitive())
        .build();
  }

  private Function createFunctionWithArgumentsAndNoReturnValue() {
    return FunctionBuilder.of(
            ThingBuilder.endToEnd().setThingName("thingName").createThing(),
            "functionName",
            Purpose.create())
        .addArgument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("jsonMessage")))
        .build();
  }

  private DataPrimitive createDataPrimitive() {
    return DataPrimitive.of(PrimitiveType.STRING, new VariableName("variableName"));
  }
}
