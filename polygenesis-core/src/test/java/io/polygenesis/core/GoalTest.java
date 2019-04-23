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

package io.polygenesis.core;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import org.junit.Test;

/** @author Christos Tsakostas */
public class GoalTest extends AbstractEqualityTest<Goal> {

  @Test
  public void shouldInitialize() {
    Goal goal = new Goal(GoalType.CREATE);
    assertThat(goal).isNotNull();
  }

  @Test
  public void shouldInitializeWithCustomGoal() {
    Goal goal = new Goal("someCustomGoal");
    assertThat(goal).isNotNull();
  }

  @Test
  public void shouldBeCreate() {
    Goal goal = new Goal(GoalType.CREATE);

    assertThat(goal.isCommand()).isTrue();
    assertThat(goal.isCreate()).isTrue();
  }

  @Test
  public void shouldBeModify() {
    Goal goal = new Goal(GoalType.MODIFY);

    assertThat(goal.isCommand()).isTrue();
    assertThat(goal.isModify()).isTrue();
  }

  @Test
  public void shouldBeDelete() {
    Goal goal = new Goal(GoalType.DELETE);

    assertThat(goal.isCommand()).isTrue();
    assertThat(goal.isDelete()).isTrue();
  }

  @Test
  public void shouldBeFetchOne() {
    Goal goal = new Goal(GoalType.FETCH_ONE);

    assertThat(goal.isCommand()).isFalse();
    assertThat(goal.isFetchOne()).isTrue();
  }

  @Test
  public void shouldBeFetchCollection() {
    Goal goal = new Goal(GoalType.FETCH_COLLECTION);

    assertThat(goal.isCommand()).isFalse();
    assertThat(goal.isFetchCollection()).isTrue();
  }

  @Test
  public void shouldBeFetchPagedCollection() {
    Goal goal = new Goal(GoalType.FETCH_PAGED_COLLECTION);

    assertThat(goal.isCommand()).isFalse();
    assertThat(goal.isFetchPagedCollection()).isTrue();
  }

  @Override
  public Goal createObject1() {
    return new Goal(GoalType.CREATE);
  }

  @Override
  public Goal createObject2() {
    return new Goal(GoalType.MODIFY);
  }
}
