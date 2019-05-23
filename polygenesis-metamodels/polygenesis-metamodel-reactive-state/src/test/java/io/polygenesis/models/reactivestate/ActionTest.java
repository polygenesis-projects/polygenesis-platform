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

package io.polygenesis.models.reactivestate;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.data.VariableName;
import io.polygenesis.commons.test.AbstractEqualityTest;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ActionTest extends AbstractEqualityTest<Action> {

  @Test
  public void shouldSucceedToInstantiate() {
    Action action =
        new Action(
            ActionType.SUBMIT,
            new ActionName("xxx"),
            new Model(DataPrimitive.of(PrimitiveType.STRING, new VariableName("name"))));

    assertThat(action).isNotNull();
    assertThat(action.getActionType()).isEqualTo(ActionType.SUBMIT);
    assertThat(action.getName()).isEqualTo(new ActionName("xxx"));
  }

  @Override
  public Action createObject1() {
    return new Action(
        ActionType.SUBMIT,
        new ActionName("xxx"),
        new Model(DataPrimitive.of(PrimitiveType.STRING, new VariableName("name"))));
  }

  @Override
  public Action createObject2() {
    return new Action(
        ActionType.RESET,
        new ActionName("xxx"),
        new Model(DataPrimitive.of(PrimitiveType.STRING, new VariableName("name"))));
  }
}
