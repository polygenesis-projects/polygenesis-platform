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
import com.oregor.trinity4j.Trinity4jShared;
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
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Team {

  public static Thing create(Thing country, PackageName rootPackageName) {
    Thing team =
        ThingBuilder.endToEnd("team")
            .setMultiTenant(Trinity4jShared.tenantId())
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

    team.addFunctions(
        PurposeFunctionBuilder.forThing(team, rootPackageName)
            //            .withFunctionCreate(createRequestData(country, rootPackageName))
            .withCrudFunction(createRequestData(country, rootPackageName), FunctionRole.userAsSet())
            .withFunctionModify(
                "modify", "", modifyRequestData(country, rootPackageName), FunctionRole.userAsSet())
            .withFunctionModify(
                "generate", "Players", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .withFunctionModify(
                "calculate", "Value", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .withFunctionModify(
                "assign", "Value", assignValue(rootPackageName), FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "increase",
                "Budget",
                new LinkedHashSet<>(
                    Arrays.asList(TeamShared.monetaryValue("budget", rootPackageName))),
                FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "decrease",
                "Budget",
                new LinkedHashSet<>(
                    Arrays.asList(TeamShared.monetaryValue("budget", rootPackageName))),
                FunctionRole.userAsSet())
            .build());

    //    Function functionCreate = team.getFunctionByName("create");
    //    functionCreate
    //        .getReturnValue()
    //        .getAsDataObject()
    //        .addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("path")));

    // team.addChild(Player.create(team, rootPackageName));

    return team;
  }

  private static Set<Data> createRequestData(Thing country, PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("name", name(rootPackageName))
        .build()
        .withReferenceToThingById(rootPackageName, country, "countryId")
        .withGroupData(TeamShared.monetaryValue("budget", rootPackageName))
        .build();
  }

  private static Set<Data> modifyRequestData(Thing country, PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("name", name(rootPackageName))
        .build()
        .withReferenceToThingById(rootPackageName, country, "countryId")
        .withGroupData(TeamShared.monetaryValue("budget", rootPackageName))
        .build();
  }

  private static Set<Data> assignValue(PackageName rootPackageName) {
    return DataBuilder.create()
        .withGroupData(TeamShared.monetaryValue("value", rootPackageName))
        .build();
  }

  public static DataObject name(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("text").build().build();

    return new DataObject(
        new VariableName("name"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("name"),
        rootPackageName.withSubPackage("team"),
        data,
        DataSourceType.DEFAULT);
  }
}
