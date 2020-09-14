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

import com.oregor.trinity4j.Trinity4jAggregateRoot;
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

public class Transfer {

  public static Thing create(PackageName rootPackageName) {
    Thing transfer =
        ThingBuilder.endToEnd("transfer")
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

    transfer.addFunctions(
        PurposeFunctionBuilder.forThing(transfer, rootPackageName)
            .withFunctionCreate(createRequestData(rootPackageName), FunctionRole.userAsSet())
            .build());

    return transfer;
  }

  private static Set<Data> createRequestData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("sellerTeamId", sellerTeamId(rootPackageName))
        .build()
        .withTextPropertyToValueObject("buyerTeamId", buyerTeamId(rootPackageName))
        .build()
        .withTextPropertyToValueObject("playerId", playerId(rootPackageName))
        .build()
        .build();
  }

  private static DataObject sellerTeamId(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("typeId").build().build();

    return new DataObject(
        new VariableName("sellerTeamId"),
        DataPurpose.thingIdentity(),
        DataValidator.empty(),
        new ObjectName("sellerTeamId"),
        rootPackageName.withSubPackage("transfer"),
        data,
        DataSourceType.DEFAULT);
  }

  private static DataObject buyerTeamId(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("typeId").build().build();

    return new DataObject(
        new VariableName("buyerTeamId"),
        DataPurpose.thingIdentity(),
        DataValidator.empty(),
        new ObjectName("buyerTeamId"),
        rootPackageName.withSubPackage("transfer"),
        data,
        DataSourceType.DEFAULT);
  }

  private static DataObject playerId(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("typeId").build().build();

    return new DataObject(
        new VariableName("playerId"),
        DataPurpose.thingIdentity(),
        DataValidator.empty(),
        new ObjectName("playerId"),
        rootPackageName.withSubPackage("transfer"),
        data,
        DataSourceType.DEFAULT);
  }
}
