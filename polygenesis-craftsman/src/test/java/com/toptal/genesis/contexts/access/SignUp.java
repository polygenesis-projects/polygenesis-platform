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

package com.toptal.genesis.contexts.access;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;

public class SignUp {

  public static Thing create(Thing confirmationSuperClass, PackageName rootPackageName) {
    Thing signUp =
        ThingBuilder.endToEnd("signUp")
            .setPreferredPackage("com.staticfy.access.identity")
            .setSuperClass(confirmationSuperClass)
            .createThing(rootPackageName);

    signUp.addFunctions(
        PurposeFunctionBuilder.forThing(signUp, rootPackageName)
            .withFunctionCreate(createData(rootPackageName), outputsData(rootPackageName))
            .withFunctionModify("confirm", "", confirmData(rootPackageName))
            .build());

    return signUp;
  }

  private static Set<Data> createData(PackageName rootPackageName) {
    return DataBuilder.create()
        //        .withTextProperty("email").build()
        //        .withTextProperty("password").build()

        //        .withGroupData(Shared.emailAddress(rootPackageName))
        //        .withGroupData(Shared.password(rootPackageName))

        .withTextPropertyToValueObject(
            "email", new ObjectName("email"), new PackageName("com.staticfy.access.identity"))
        .build()
        .withTextPropertyToValueObject(
            "password", new ObjectName("password"), new PackageName("com.staticfy.access.identity"))
        .build()
        .build();
  }

  private static Set<Data> outputsData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject(
            "confirmationCode",
            Shared.confirmationCode(rootPackageName).getObjectName(),
            Shared.confirmationCode(rootPackageName).getPackageName())
        .build()
        .build();
  }

  private static Set<Data> confirmData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject(
            "confirmationCode",
            Shared.confirmationCode(rootPackageName).getObjectName(),
            Shared.confirmationCode(rootPackageName).getPackageName())
        .build()
        .build();
  }
}
