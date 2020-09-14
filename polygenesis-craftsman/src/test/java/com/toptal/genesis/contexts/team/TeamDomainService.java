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

public class TeamDomainService {

  /**
   * Create thing.
   *
   * @param rootPackageName the root package name
   * @return the thing
   */
  public static Thing create(Thing team, Thing country, PackageName rootPackageName) {
    Thing teamDomainService =
        ThingBuilder.domainService("teamDomainService")
            .setPreferredPackage("com.somg.team.team")
            .createThing();

    teamDomainService.addFunction(
        FunctionBuilder.of(
                teamDomainService,
                "generate",
                "RandomTeamName",
                Purpose.generate(),
                FunctionRole.userAsSet())
            .setReturnValue(Team.name(rootPackageName))
            .build());

    teamDomainService.addFunction(
        FunctionBuilder.of(
                teamDomainService,
                "generate",
                "RandomCountryId",
                Purpose.generate(),
                FunctionRole.userAsSet())
            .setReturnValue(
                country.getThingIdentityAsDataObject(
                    rootPackageName.withSubPackage("supportive"), new VariableName("countryId")))
            .build());

    teamDomainService.addFunction(
        FunctionBuilder.of(
                teamDomainService,
                "acquire",
                "InitialBudget",
                Purpose.genericQuery(),
                FunctionRole.userAsSet())
            .setReturnValue(TeamShared.monetaryValue("budget", rootPackageName))
            .build());

    teamDomainService.addFunction(
        FunctionBuilder.of(
                teamDomainService,
                "calculate",
                "TeamValue",
                Purpose.generate(),
                FunctionRole.userAsSet())
            .addArgument(
                team.getThingIdentityAsDataObject(rootPackageName, new VariableName("teamId")))
            .setReturnValue(TeamShared.monetaryValue("teamValue", rootPackageName))
            .build());

    teamDomainService.addFunction(
        FunctionBuilder.of(
                teamDomainService,
                "generate",
                "Players",
                Purpose.generate(),
                FunctionRole.userAsSet())
            .addArgument(
                team.getThingIdentityAsDataObject(rootPackageName, new VariableName("teamId")))
            .build());

    return teamDomainService;
  }
}
