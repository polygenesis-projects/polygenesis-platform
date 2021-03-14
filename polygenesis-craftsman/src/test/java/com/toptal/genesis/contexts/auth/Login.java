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

package com.toptal.genesis.contexts.auth;

import com.oregor.trinity4j.Trinity4jAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Set;

public class Login {

  public static Thing create(PackageName rootPackageName) {
    Thing login =
        ThingBuilder.endToEnd("login")
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

    String functionName = "login";
    login.addFunctions(
        PurposeFunctionBuilder.forThing(login, rootPackageName)
            .withFunctionCreate(
                functionName, createRequestData(rootPackageName), FunctionRole.userAsSet())
            .build());

    enhanceLoginResponseWithToken(login, functionName);

    return login;
  }

  private static Set<Data> createRequestData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("email", AuthShared.email(rootPackageName))
        .build()
        .withTextPropertyToValueObject("password", AuthShared.password(rootPackageName))
        .build()
        .build();
  }

  private static void enhanceLoginResponseWithToken(Thing login, String functionName) {
    Function functionCreate = login.getFunctionByName(functionName);
    functionCreate
        .getReturnValue()
        .getAsDataObject()
        .addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("token")));
  }
}