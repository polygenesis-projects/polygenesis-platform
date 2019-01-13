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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

/** @author Christos Tsakostas */
public class FunctionTypeTest {

  @Test
  public void shouldSucceed() {
    Goal goal = new Goal("someGoal");

    assertThat(goal).isNotNull();
    assertThat(goal.getOriginal()).isEqualTo("someGoal");
  }

  @Test
  public void shouldThrowExceptionForNull() {
    assertThatThrownBy(() -> new Goal(null)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldThrowExceptionForEmpty() {
    assertThatThrownBy(() -> new Goal("")).isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Test
  public void equalityShouldSucceedForTwoEqualObjects() {
    Goal goal1 = new Goal("xxx");
    Goal goal2 = new Goal("xxx");

    assertThat(goal1.equals(goal2)).isTrue();
  }

  @Test
  public void equalityShouldSucceedForSameObject() {
    Goal goal1 = new Goal("xxx");

    assertThat(goal1.equals(goal1)).isTrue();
  }

  @Test
  public void equalityShouldFailForNotEqualObjects() {
    Goal goal1 = new Goal("xxx");
    Goal goal2 = new Goal("yyy");

    assertThat(goal1.equals(goal2)).isFalse();
  }

  @Test
  public void equalityShouldFailForNullInput() {
    Goal goal1 = new Goal("xxx");

    assertThat(goal1.equals(null)).isFalse();
  }

  @Test
  public void equalityShouldFailForDifferentObject() {
    Goal goal1 = new Goal("xxx");
    Object object = new Object();

    assertThat(goal1.equals(object)).isFalse();
  }

  @Test
  public void hashCodeShouldNotBeNull() {
    Goal goal1 = new Goal("xxx");

    assertThat(goal1.hashCode()).isNotNull();
  }
}
