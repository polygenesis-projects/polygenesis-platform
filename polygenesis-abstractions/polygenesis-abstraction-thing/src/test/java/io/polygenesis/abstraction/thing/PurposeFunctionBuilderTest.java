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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/** @author Christos Tsakostas */
public class PurposeFunctionBuilderTest {

  @Test
  public void build() {
    Set<Function> functions =
        PurposeFunctionBuilder.forThing(ThingForTesting.create(), "com.oregor").build();

    Assertions.assertThat(functions).isNotNull();
    assertThat(functions.size()).isEqualTo(0);
  }

  @Test
  public void shouldCreateModify() {
    Set<Function> functions =
        PurposeFunctionBuilder.forThing(ThingForTesting.create(), "com.oregor")
            .withFunctionModify("modify", dataModifyDescription())
            .build();

    Assertions.assertThat(functions).isNotNull();
    assertThat(functions.size()).isEqualTo(1);

    Function function = functions.stream().findFirst().orElseThrow(IllegalStateException::new);
    assertThat(function.getName()).isEqualTo(new FunctionName("modify"));
    assertThat(function.getReturnValue().getData().getAsDataObject().getObjectName().getText())
        .isEqualTo("modifyBusinessResponse");
  }

  @Test
  public void shouldCreateModifyWithCustomName() {
    Set<Function> functions =
        PurposeFunctionBuilder.forThing(ThingForTesting.create(), "com.oregor")
            .withFunctionModify("modifyDescription", dataModifyDescription())
            .build();

    Assertions.assertThat(functions).isNotNull();
    assertThat(functions.size()).isEqualTo(1);

    Function function = functions.stream().findFirst().orElseThrow(IllegalStateException::new);
    assertThat(function.getName().getText())
        .isEqualTo(new FunctionName("modifyDescription").getText());
    assertThat(function.getReturnValue().getData().getAsDataObject().getObjectName().getText())
        .isEqualTo("modifyDescriptionBusinessResponse");
  }

  private static Set<Data> dataModifyDescription() {
    return DataBuilder.create().withTextProperty("description").build().build();
  }

  private static Set<Data> dataModifyDone() {
    return DataBuilder.create().withBooleanProperty("done").build().build();
  }
}
