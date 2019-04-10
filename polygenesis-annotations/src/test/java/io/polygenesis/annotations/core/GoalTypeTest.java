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

package io.polygenesis.annotations.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/** @author Christos Tsakostas */
public class GoalTypeTest {

  @Test
  public void shouldSucceed() {
    assertThat(GoalType.CREATE.name()).isEqualTo("CREATE");
    assertThat(GoalType.CREATE.getCqsType()).isEqualTo(CqsType.COMMAND);
    assertThat(GoalType.MODIFY.name()).isEqualTo("MODIFY");
    assertThat(GoalType.MODIFY.getCqsType()).isEqualTo(CqsType.COMMAND);
    assertThat(GoalType.DELETE.name()).isEqualTo("DELETE");
    assertThat(GoalType.DELETE.getCqsType()).isEqualTo(CqsType.COMMAND);

    assertThat(GoalType.FETCH_ONE.name()).isEqualTo("FETCH_ONE");
    assertThat(GoalType.FETCH_ONE.getCqsType()).isEqualTo(CqsType.QUERY);
    assertThat(GoalType.FETCH_COLLECTION.name()).isEqualTo("FETCH_COLLECTION");
    assertThat(GoalType.FETCH_COLLECTION.getCqsType()).isEqualTo(CqsType.QUERY);
    assertThat(GoalType.FETCH_PAGED_COLLECTION.name()).isEqualTo("FETCH_PAGED_COLLECTION");
    assertThat(GoalType.FETCH_PAGED_COLLECTION.getCqsType()).isEqualTo(CqsType.QUERY);

    assertThat(GoalType.CALCULATE.name()).isEqualTo("CALCULATE");
    assertThat(GoalType.CALCULATE.getCqsType()).isEqualTo(CqsType.SUPPORTIVE);
    assertThat(GoalType.SYNCHRONIZE.name()).isEqualTo("SYNCHRONIZE");
    assertThat(GoalType.SYNCHRONIZE.getCqsType()).isEqualTo(CqsType.SUPPORTIVE);
    assertThat(GoalType.VALIDATE.name()).isEqualTo("VALIDATE");
    assertThat(GoalType.VALIDATE.getCqsType()).isEqualTo(CqsType.SUPPORTIVE);
  }
}
