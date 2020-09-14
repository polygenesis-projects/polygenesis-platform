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

package com.invoiceful.genesis.contexts.access;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;

public class SignUpDomainService {

  /**
   * Create thing.
   *
   * @param rootPackageName the root package name
   * @return the thing
   */
  public static Thing create(Thing signUp, PackageName rootPackageName) {
    Thing signUpDomainService =
        ThingBuilder.domainService("signUpDomainService")
            .setPreferredPackage("com.invoiceful.access.identity")
            .createThing();

    signUpDomainService.addFunction(
        FunctionBuilder.of(
                signUpDomainService, "create", "", Purpose.create(), FunctionRole.userAsSet())
            .addArgument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("email")))
            .addArgument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("password")))
            .setReturnValue(signUp.getAsDataObject(rootPackageName))
            .build());

    return signUpDomainService;
  }
}
