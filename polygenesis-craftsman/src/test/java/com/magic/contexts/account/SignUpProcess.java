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

package com.magic.contexts.account;

import com.oregor.trinity4j.Trinity4jAggregateRoot;
import com.magic.contexts.MagicShared;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataEnumeration;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.EnumerationValue;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;

public class SignUpProcess {

  public static Thing create(PackageName rootPackageName) {
    Thing signUp =
        ThingBuilder.endToEnd("signUpProcess")
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

    signUp.addFunctions(
        PurposeFunctionBuilder.forThing(signUp, rootPackageName)
            .withFunctionCreate(createData(rootPackageName), FunctionRole.userAsSet())
            .withFunctionFetchOne(fetchData(rootPackageName), FunctionRole.adminAsSet())
            .withFunctionFetchPagedCollection(fetchData(rootPackageName),
                FunctionRole.adminAsSet())
            .withFunctionModify("submit", "VerificationCode",  verifyData(rootPackageName), FunctionRole.userAsSet())
            .build());

    return signUp;
  }

  private static Set<Data> createData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("emailAddress", MagicShared.emailAddress(rootPackageName))
        .build()
        .withEnumeration(state(rootPackageName))
        .build();
  }

  private static Set<Data> verifyData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextProperty("verificationCode")
        .build()
        .withEnumeration(state(rootPackageName))
        .build();
  }

  private static Set<Data> fetchData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("emailAddress", MagicShared.emailAddress(rootPackageName))
        .build()
        .build();
  }

  private static DataEnumeration state(PackageName rootPackageName) {
    Set<EnumerationValue> enumerationValues = new LinkedHashSet<>();

    enumerationValues.add(EnumerationValue.ofInitial("INITIATED"));
    enumerationValues.add(EnumerationValue.of("TERMINATED_BECAUSE_EMAIL_ADDRESS_IS_ALREADY_REGISTERED"));
    enumerationValues.add(EnumerationValue.of("AWAITING_FOR_VERIFICATION"));
    enumerationValues.add(EnumerationValue.of("COMPLETED"));

    return DataEnumeration.ofPurpose(
        new ObjectName("signUpProcessState"),
        rootPackageName.withSubPackage("signUpProcess"),
        new VariableName("signUpProcessState"),
        enumerationValues,
        DataPurpose.internalState());
  }
}
