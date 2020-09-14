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

package com.toptal.genesis.contexts.market;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Set;

public class PlayerProjection {

  public static Thing create(PackageName rootPackageName) {
    Thing playerProjection =
        ThingBuilder.projection("playerProjection").createThing(rootPackageName);

    playerProjection.addFunctions(
        PurposeFunctionBuilder.forThing(playerProjection, rootPackageName)
            .withCrudFunction(createData(rootPackageName), FunctionRole.userAsSet())
            .withFunctionEnsureExistence()
            .build());

    return playerProjection;
  }

  private static Set<Data> createData(PackageName rootPackageName) {
    return DataBuilder.create()
        // .withReferenceToThingById(rootPackageName, team, "teamId")
        .withTextPropertyToValueObject("teamId", teamId(rootPackageName))
        .build()
        .withTextProperty("teamName")
        .build()
        .withTextProperty("playerName")
        .build()
        .withTextProperty("country")
        .build()
        .withDecimalProperty("value")
        .build()
        .build();
  }

  private static DataObject teamId(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("typeId").build().build();

    return new DataObject(
        new VariableName("teamId"),
        DataPurpose.thingIdentity(),
        DataValidator.empty(),
        new ObjectName("teamId"),
        rootPackageName.withSubPackage("playerProjection"),
        data,
        DataSourceType.DEFAULT);
  }
}
