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

package io.polygenesis.models.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Thing;
import io.polygenesis.core.test.ThingForTesting;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DtoDeducerTest {

  private Thing thing;
  private DtoDeducer dtoDeducer;

  @Before
  public void setUp() {
    thing = ThingForTesting.create();
    dtoDeducer = new DtoDeducer();
  }

  @Test
  public void shouldSuccessfullyDeduceRequestDto() {
    Function function = getThingFunction("create");

    Dto dtoRequest = dtoDeducer.deduceRequestDto(function);
    assertThat(dtoRequest).isNotNull();
  }

  @Test
  public void shouldFailToDeduceRequestDtoForNoArguments() {
    Function function = getThingFunction("functionWithNoArguments");

    assertThatThrownBy(() -> dtoDeducer.deduceRequestDto(function))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldFailToDeduceRequestDtoForMoreThanOneArguments() {
    Function function = getThingFunction("functionWithNoReturnValueAndManyArguments");

    assertThatThrownBy(() -> dtoDeducer.deduceRequestDto(function))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldFailToDeduceRequestDtoForOneArgumentWhichIsNotIoModelGroup() {
    Function function = getThingFunction("functionWithPrimitives");

    assertThatThrownBy(() -> dtoDeducer.deduceRequestDto(function))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldSuccessfullyDeduceResponseDto() {
    Function function =
        thing
            .getFunctions()
            .stream()
            .filter(function1 -> function1.getName().equals(new FunctionName("create")))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

    Dto dtoResponse = dtoDeducer.deduceResponseDto(function);
    assertThat(dtoResponse).isNotNull();
  }

  @Test
  public void shouldFailToDeduceResponseDtoForNullReturnValue() {
    Function function = getThingFunction("functionWithNoReturnValueAndManyArguments");

    assertThatThrownBy(() -> dtoDeducer.deduceResponseDto(function))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldFailToDeduceResponseDtoForReturnValueWhichIsNotIoModelGroup() {
    Function function = getThingFunction("functionWithPrimitives");

    assertThatThrownBy(() -> dtoDeducer.deduceResponseDto(function))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldDeduceAllDtosInMethods() {}

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function getThingFunction(String functionName) {
    return thing
        .getFunctions()
        .stream()
        .filter(function -> function.getName().equals(new FunctionName(functionName)))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
