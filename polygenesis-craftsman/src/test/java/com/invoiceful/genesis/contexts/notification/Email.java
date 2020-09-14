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

package com.invoiceful.genesis.contexts.notification;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import java.util.LinkedHashSet;
import java.util.Set;

public class Email {

  public static Thing create(String rootPackageName) {
    Thing email = ThingBuilder.endToEnd("email").createThing();

    email.addFunctions(
        PurposeFunctionBuilder.forThing(email, rootPackageName)
            .withFunctionCreate(createData(rootPackageName), FunctionRole.userAsSet())
            .withFunctionFetchOne(createData(rootPackageName), FunctionRole.userAsSet())
            .build());

    return email;
  }

  private static Set<Data> createData(String rootPackageName) {
    return new LinkedHashSet<>();
    //    return DataBuilder.create()
    //        .withTextProperty(
    //            "email", new ObjectName("email"), new
    // PackageName("com.invoiceful.access.identity"))
    //        .build()
    //        .withTextProperty(
    //            "password",
    //            new ObjectName("password"),
    //            new PackageName("com.invoiceful.access.identity"))
    //        .build()
    //        .build();
  }
}
