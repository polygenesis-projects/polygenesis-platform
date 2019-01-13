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

package io.polygenesis.models.state;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.text.Text;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ActionTest {

  @Test
  public void shouldSucceedToInstantiate() {
    Action action = new Action(ActionType.SUBMIT, new Text("xxx"));

    assertThat(action).isNotNull();
    assertThat(action.getActionType()).isEqualTo(ActionType.SUBMIT);
    assertThat(action.getName()).isEqualTo(new Text("xxx"));
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Test
  public void equalityShouldSucceedForTwoEqualObjects() {
    Action action1 = createAction1();
    Action action2 = createAction1();

    assertThat(action1.equals(action2)).isTrue();
  }

  @Test
  public void equalityShouldSucceedForSameObject() {
    Action action1 = createAction1();

    assertThat(action1.equals(action1)).isTrue();
  }

  @Test
  public void equalityShouldFailForNotEqualObjects() {
    Action action1 = createAction1();
    Action action2 = createAction2();

    assertThat(action1.equals(action2)).isFalse();
  }

  @Test
  public void equalityShouldFailForNullInput() {
    Action action1 = createAction1();

    assertThat(action1.equals(null)).isFalse();
  }

  @Test
  public void equalityShouldFailForDifferentObject() {
    Action action1 = createAction1();
    Object object = new Object();

    assertThat(action1.equals(object)).isFalse();
  }

  @Test
  public void hashCodeShouldNotBeNull() {
    Action action1 = createAction1();

    assertThat(action1.hashCode()).isNotNull();
  }

  private Action createAction1() {
    return new Action(ActionType.SUBMIT, new Text("xxx"));
  }

  private Action createAction2() {
    return new Action(ActionType.RESET, new Text("xxx"));
  }
}
