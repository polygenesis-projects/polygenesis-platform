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

package io.polygenesis.models.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.PackageName;
import org.junit.Before;
import org.junit.Test;

public class DtoDeducerTest {

  private PackageName rootPackageName;
  private Thing thing;
  private DtoDeducer dtoDeducer;

  @Before
  public void setUp() {
    rootPackageName = new PackageName("com.oregor");
    thing = ThingForTesting.create();
    dtoDeducer = new DtoDeducer();
  }

  @Test
  public void shouldSuccessfullyDeduceRequestDto() {
    Function function = getThingFunction("create");

    Dto dtoRequest = dtoDeducer.deduceRequestDto(function, rootPackageName);
    assertThat(dtoRequest).isNotNull();
  }

  @Test
  public void shouldSuccessfullyDeduceResponseDto() {
    Function function =
        thing.getFunctions().stream()
            .filter(function1 -> function1.getName().equals(FunctionName.ofVerbOnly("create")))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

    Dto dtoResponse = dtoDeducer.deduceResponseDto(function, rootPackageName);
    assertThat(dtoResponse).isNotNull();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function getThingFunction(String functionName) {
    return thing.getFunctions().stream()
        .filter(function -> function.getName().equals(FunctionName.ofVerbOnly(functionName)))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
