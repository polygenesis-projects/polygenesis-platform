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

package io.polygenesis.abstraction.thing.dsl;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.abstraction.thing.Thing;
import org.junit.Test;

/** @author Christos Tsakostas */
public class MessageSubscriberThingBuilderTest {

  @Test
  public void shouldCreateMessageSubscriber() {
    Thing user = ThingBuilder.endToEnd().setThingName("user").createThing();

    Thing onUserCreated =
        MessageSubscriberThingBuilder.of("onUserCreated").setRelatedThing(user).createThing();

    assertThat(onUserCreated).isNotNull();
    assertThat(onUserCreated.getMetadata().size()).isEqualTo(1);
  }
}
