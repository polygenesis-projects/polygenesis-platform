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

package com.surprisor.genesis.contexts.payment;

import com.oregor.trinity4j.Trinity4jAggregateRoot;
import com.toptal.genesis.contexts.team.TeamShared;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataEnumeration;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.EnumerationValue;
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

public class Checkout {

  public static Thing create(Thing team, Thing country, PackageName rootPackageName) {
    Thing checkout =
        ThingBuilder.endToEnd("checkout")
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

    checkout.addFunctions(
        PurposeFunctionBuilder.forThing(checkout, rootPackageName)
            .withCrudFunction(
                createRequestData(team, country, rootPackageName), FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "leave", "CurrentTeam", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "join",
                "NewTeam",
                transferCheckoutToAnotherTeam(team, rootPackageName),
                FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "increase",
                "Value",
                new LinkedHashSet<>(
                    Arrays.asList(TeamShared.monetaryValue("value", rootPackageName))),
                FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "place",
                "OnTheTransferMarket",
                DataBuilder.create()
                    .withGroupData(TeamShared.monetaryValue("priceOnTheMarket", rootPackageName))
                    .build(),
                FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "remove", "FromTheTransferMarket", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .build());

    return checkout;
  }

  private static Set<Data> createRequestData(
      Thing team, Thing country, PackageName rootPackageName) {
    return DataBuilder.create()
        .withReferenceToThingById(rootPackageName, team, "teamId")
        .withGroupData(personalName(rootPackageName))
        .withEnumeration(positionInTheField(rootPackageName))
        .withGroupData(TeamShared.monetaryValue("value", rootPackageName))
        .withTextPropertyToValueObject("yearOfBirth", yearOfBirth(rootPackageName))
        .build()
        // TODO .withReferenceToThing(country, "country")
        .withReferenceToThingById(rootPackageName, country, "countryId")
        // .withGroupData(Shared.language(rootPackageName))
        .build();
  }

  private static Set<Data> transferCheckoutToAnotherTeam(Thing team, PackageName rootPackageName) {
    return DataBuilder.create()
        .withReferenceToThingById(rootPackageName, team, "teamId")
        .withGroupData(TeamShared.monetaryValue("value", rootPackageName))
        .build();
  }

  public static DataObject personalName(PackageName rootPackageName) {
    Set<Data> data =
        DataBuilder.create()
            .withTextProperty("firstName")
            .build()
            .withTextProperty("lastName")
            .build()
            .build();

    return new DataObject(
        new VariableName("personalName"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("personalName"),
        rootPackageName.withSubPackage("checkout"),
        data,
        DataSourceType.DEFAULT);
  }

  private static DataObject yearOfBirth(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withIntegerProperty("value").build().build();

    return new DataObject(
        new VariableName("yearOfBirth"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("yearOfBirth"),
        rootPackageName.withSubPackage("checkout"),
        data,
        DataSourceType.DEFAULT);
  }

  private static DataEnumeration positionInTheField(PackageName rootPackageName) {
    Set<EnumerationValue> enumerationValues = new LinkedHashSet<>();

    enumerationValues.add(EnumerationValue.ofInitial("GOALKEEPER"));
    enumerationValues.add(EnumerationValue.of("DEFENDER"));
    enumerationValues.add(EnumerationValue.of("MIDFIELDER"));
    enumerationValues.add(EnumerationValue.of("ATTACKER"));

    return DataEnumeration.ofPurpose(
        new ObjectName("positionInTheField"),
        rootPackageName.withSubPackage("checkout"),
        new VariableName("positionInTheField"),
        enumerationValues,
        DataPurpose.internalState());
  }

  public static DataObject placementOnTheTransferMarket(PackageName rootPackageName) {
    Set<Data> data =
        DataBuilder.create()
            .withBooleanProperty("availableOnTheMarket")
            .build()
            .withGroupData(TeamShared.monetaryValue("priceOnTheMarket", rootPackageName))
            .build();

    return new DataObject(
        new VariableName("placementOnTheTransferMarket"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("placementOnTheTransferMarket"),
        rootPackageName.withSubPackage("checkout"),
        data,
        DataSourceType.DEFAULT);
  }
}
