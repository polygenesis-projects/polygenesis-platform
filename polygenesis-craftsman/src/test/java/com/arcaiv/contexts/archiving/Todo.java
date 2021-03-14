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

package com.arcaiv.contexts.archiving;

import com.invoiceful.genesis.contexts.invoicing.Shared;
import com.oregor.trinity4j.Trinity4jShared;
import com.oregor.trinity4j.Trinity4jTenantAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.LinkedHashSet;
import java.util.Set;

public class Todo {

  public static Thing create(PackageName rootPackageName) {
    Thing todo =
        ThingBuilder.endToEnd("todo")
            .setSuperClass(Trinity4jTenantAggregateRoot.create(rootPackageName))
            .setMultiTenant(Trinity4jShared.tenantId())
            .createThing(rootPackageName);

    todo.addFunctions(
        PurposeFunctionBuilder.forThing(todo, rootPackageName)
            .withCrudFunction(createData(rootPackageName), FunctionRole.userAsSet())
            .withFunctionModify("issue", "", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .build());

    // todo.addChild(TodoChild.create(todo, rootPackageName));

    return todo;
  }

  private static Set<Data> createData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("title", com.invoiceful.genesis.contexts.invoicing.Shared.title(rootPackageName))
        .build()
        .withTextPropertyToValueObject("description", com.invoiceful.genesis.contexts.invoicing.Shared
            .description(rootPackageName))
        .build()
        .withDecimalPropertyToValueObject("quantity", com.invoiceful.genesis.contexts.invoicing.Shared
            .quantity(rootPackageName))
        .build()
        .withDecimalPropertyToValueObject("unitCost", com.invoiceful.genesis.contexts.invoicing.Shared
            .unitCost(rootPackageName))
        .build()
        .withDecimalPropertyToValueObject("lineTotal", com.invoiceful.genesis.contexts.invoicing.Shared
            .lineTotal(rootPackageName))
        .build()
        .withGroupData(Shared.discount(rootPackageName))
        .build();
  }
}
