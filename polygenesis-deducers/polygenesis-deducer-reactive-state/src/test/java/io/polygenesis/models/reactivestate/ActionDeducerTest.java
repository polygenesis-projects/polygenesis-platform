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

package io.polygenesis.models.reactivestate;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.text.Text;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ActionDeducerTest extends AbstractReactiveStateTest {

  private ActionDeducer actionDeducer;

  @Before
  public void setUp() {
    actionDeducer = new ActionDeducer();
  }

  @Test
  public void deduce() {
    Set<Action> actions = actionDeducer.deduce(createThing());

    assertThat(actions.size()).isEqualTo(3);

    assertThat(actions)
        .contains(new Action(ActionType.SUBMIT, new Text("CREATE_SOME_THING_SUBMIT")));
    assertThat(actions)
        .contains(new Action(ActionType.ON_SUCCESS, new Text("CREATE_SOME_THING_ON_SUCCESS")));
    assertThat(actions)
        .contains(new Action(ActionType.ON_FAILURE, new Text("CREATE_SOME_THING_ON_FAILURE")));
  }
}