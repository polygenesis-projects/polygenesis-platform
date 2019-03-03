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
import static org.mockito.Mockito.mock;

import io.polygenesis.models.api.ServiceModelRepository;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ActionGroupDeducerTest extends AbstractReactiveStateTest {

  private ServiceModelRepository serviceModelRepository;
  private ActionGroupDeducer actionGroupDeducer;

  @Before
  public void setUp() {
    serviceModelRepository = mock(ServiceModelRepository.class);
    actionGroupDeducer = new ActionGroupDeducer();
  }

  @Test
  public void deduce() {
    Set<ActionGroup> actionGroups =
        actionGroupDeducer.deduce(
            // TODO: remove createThing
            createThing(), serviceModelRepository);

    // TODO
    assertThat(actionGroups.size()).isEqualTo(0);

    // TODO
    //    assertThat(actionGroups)
    //        .contains(new Action(ActionType.SUBMIT, new ActionName("CREATE_SOME_THING_SUBMIT")));
    //    assertThat(actionGroups)
    //        .contains(
    //            new Action(ActionType.ON_SUCCESS, new
    // ActionName("CREATE_SOME_THING_ON_SUCCESS")));
    //    assertThat(actionGroups)
    //        .contains(
    //            new Action(ActionType.ON_FAILURE, new
    // ActionName("CREATE_SOME_THING_ON_FAILURE")));
  }
}
