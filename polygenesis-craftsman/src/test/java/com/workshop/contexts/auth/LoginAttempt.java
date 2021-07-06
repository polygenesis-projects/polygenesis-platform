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

package com.workshop.contexts.auth;

import com.oregor.trinity4j.Trinity4jAggregateRoot;
import com.workshop.contexts.WorkshopShared;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;

public class LoginAttempt {

  public static Thing create(PackageName rootPackageName) {
    Thing login =
        ThingBuilder.endToEnd("loginAttempt")
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

    login.addFunctions(
        PurposeFunctionBuilder.forThing(login, rootPackageName)
            .withFunctionCreate(
                createData(rootPackageName), outputsData(rootPackageName), FunctionRole.userAsSet())
            .build());

    return login;
  }

  private static Set<Data> createData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("emailAddress", WorkshopShared.emailAddress(rootPackageName))
        .build()
        .withTextPropertyToValueObject("password", WorkshopShared.password(rootPackageName))
        .build()
        .build();
  }

  private static Set<Data> outputsData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextProperty("userId").build()
        .withTextProperty("emailAddress").build()
        .withTextProperty("token").build()
        .build();
  }
}
