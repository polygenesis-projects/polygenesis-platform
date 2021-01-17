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

package com.arheio.contexts.archiving;

import com.oregor.trinity4j.Trinity4jAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataEnumeration;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.EnumerationValue;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;

public class Demo {

  public static Thing create(PackageName rootPackageName) {
    Thing demo =
        ThingBuilder.endToEnd("demo")
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

    demo.addFunctions(
        PurposeFunctionBuilder.forThing(demo, rootPackageName)
            .withFunctionCreate(createRequestData(rootPackageName), FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "scrape", "Origin", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .build());

    Function functionCreate = demo.getFunctionByName("create");
    functionCreate
        .getReturnValue()
        .getAsDataObject()
        .addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("path")));

    return demo;
  }

  private static Set<Data> createRequestData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("email", Shared.email(rootPackageName))
        .build()
        .withTextPropertyToValueObject("originDomain", Shared.domain(rootPackageName))
        .build()
        .withEnumeration(demoStatus(rootPackageName))
        .build();
  }

  private static DataEnumeration demoStatus(PackageName rootPackageName) {
    Set<EnumerationValue> enumerationValues = new LinkedHashSet<>();

    enumerationValues.add(EnumerationValue.ofInitial("SUBMITTED"));
    enumerationValues.add(EnumerationValue.of("PREPARED"));
    enumerationValues.add(EnumerationValue.of("SCRAPED"));

    return DataEnumeration.ofPurpose(
        new ObjectName("demoStatus"),
        rootPackageName.withSubPackage("demo"),
        new VariableName("demoStatus"),
        enumerationValues,
        DataPurpose.internalState());
  }
}
