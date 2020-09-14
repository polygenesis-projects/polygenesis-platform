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

import com.oregor.trinity4j.Trinity4jAggregateRoot;
import com.oregor.trinity4j.Trinity4jTenantAggregateRoot;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingContext;
import io.polygenesis.abstraction.thing.ThingContextBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Deducer;
import java.util.Set;

public class ContextTeam {

  public static ThingContext get(
      PackageName rootPackageName, ContextGenerator contextGenerator, Set<Deducer<?>> deducers) {
    Thing country = Country.create(rootPackageName);
    Thing team = Team.create(country, rootPackageName);
    Thing player = Player.create(team, country, rootPackageName);

    return ThingContextBuilder.of("team", contextGenerator)
        .withDeducers(deducers)

        // Trinity4J Abstract Aggregate Root
        .addThing(Trinity4jAggregateRoot.create(rootPackageName))
        .addThing(Trinity4jTenantAggregateRoot.create(rootPackageName))
        // .addThing(Trinity4jAggregateEntity.create(rootPackageName))

        // Aggregate Roots
        .addThing(team)
        //        .addThing(player)
        //
        //        // Supportive Entities
        //        .addThing(country)
        //
        //        // Domain Services
        //        .addThing(PlayerDomainService.create(player, team, rootPackageName))
        //        .addThing(TeamDomainService.create(team, country, rootPackageName))
        //
        //        // Subscribers
        //        .addThing(OnUserCreated.create(team, rootPackageName))
        //        .addThing(OnTeamCreated.create(team, rootPackageName))
        //        .addThing(OnPlayerCreated.create(team, rootPackageName))

        .build();
  }
}
