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

package com.toptal.genesis.contexts.team;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class OnTeamCreated {

  /** @return the thing */
  public static Thing create(Thing team, PackageName rootPackageName) {
    Thing onTeamCreated = ThingBuilder.domainMessageSubscriber("onTeamCreated").createThing();

    onTeamCreated.addMetadata(new KeyValue("relatedThing", team));

    onTeamCreated.addMetadata(
        new KeyValue(
            "supportedMessageTypes",
            new LinkedHashSet<>(Collections.singletonList("TeamCreated"))));

    onTeamCreated.addMetadata(new KeyValue("messageData", messageData(rootPackageName)));

    onTeamCreated.addMetadata(new KeyValue("process", team.getFunctionByName("generatePlayers")));

    return onTeamCreated;
  }

  private static Set<Data> messageData(PackageName rootPackageName) {
    return DataBuilder.create().withTextProperty("rootId").build().build();
  }
}