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

package com.workshop.contexts.purchasing;

import com.oregor.trinity4j.Trinity4jShared;
import com.oregor.trinity4j.Trinity4jTenantAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;

public class Order {

  public static Thing create(PackageName rootPackageName, Thing customer) {
    Thing order =
        ThingBuilder.endToEnd("order")
            .setSuperClass(Trinity4jTenantAggregateRoot.create(rootPackageName))
            .setMultiTenant(Trinity4jShared.tenantId())
            .createThing(rootPackageName);

    order.addFunctions(
        PurposeFunctionBuilder.forThing(order, rootPackageName)
            .withFunctionCreate(createData(rootPackageName, customer), FunctionRole.userAsSet())
            .withFunctionFetchOne(noData(), FunctionRole.userAsSet())
            .withFunctionFetchPagedCollection(noData(), FunctionRole.userAsSet())
            .withFunctionModify("cancel", "", noData(), FunctionRole.userAsSet())
            .build());

    order.addChild(LineItem.create(order, rootPackageName));

    return order;
  }

  private static Set<Data> createData(PackageName rootPackageName, Thing customer) {
    return DataBuilder.create()
        .withReferenceToThingById(rootPackageName, customer, "customerId")
        .build();
  }

  private static Set<Data> noData() {
    return DataBuilder.create()
        .build();
  }
}