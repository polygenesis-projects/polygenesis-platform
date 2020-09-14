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

import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;

public class PlayerDomainService {

  public static Thing create(Thing player, Thing team, PackageName rootPackageName) {
    Thing playerDomainService =
        ThingBuilder.domainService("playerDomainService")
            .setPreferredPackage(rootPackageName.withSubPackage("player").getText())
            .createThing(rootPackageName);

    playerDomainService.addFunction(
        FunctionBuilder.of(
                playerDomainService,
                "generate",
                "RandomPlayerName",
                Purpose.checkBoolean(),
                FunctionRole.userAsSet())
            .setReturnValue(Player.personalName(rootPackageName))
            .build());

    playerDomainService.addFunction(
        FunctionBuilder.of(
                playerDomainService,
                "acquire",
                "InitialValue",
                Purpose.checkBoolean(),
                FunctionRole.userAsSet())
            .setReturnValue(TeamShared.monetaryValue("value", rootPackageName))
            .build());

    playerDomainService.addFunction(
        FunctionBuilder.of(
                playerDomainService,
                "calculate",
                "ValueAfterTransfer",
                Purpose.genericCommand(),
                FunctionRole.userAsSet())
            .addArgument(TeamShared.monetaryValue("value", rootPackageName))
            .setReturnValue(TeamShared.monetaryValue("value", rootPackageName))
            .build());

    playerDomainService.addFunction(
        FunctionBuilder.of(
                playerDomainService,
                "transfer",
                "ToAnotherTeam",
                Purpose.genericCommand(),
                FunctionRole.userAsSet())
            .addArgument(player.getAsDataObject(rootPackageName))
            .addArgument(
                team.getThingIdentityAsDataObject(rootPackageName, new VariableName("newTeamId")))
            .addArgument(TeamShared.monetaryValue("newValue", rootPackageName))
            .build());

    return playerDomainService;
  }
}
