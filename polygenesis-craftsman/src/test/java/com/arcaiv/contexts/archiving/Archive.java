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

import com.arcaiv.contexts.ArcaivShared;
import com.oregor.trinity4j.Trinity4jShared;
import com.oregor.trinity4j.Trinity4jTenantAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataEnumeration;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.EnumerationValue;
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
import java.util.Optional;
import java.util.Set;

public class Archive {

  public static Thing create(PackageName rootPackageName) {
    Thing archive =
        ThingBuilder.endToEnd("archive")
            .setSuperClass(Trinity4jTenantAggregateRoot.create(rootPackageName))
            .setMultiTenant(Trinity4jShared.tenantId())
            .createThing(rootPackageName);

//    todo.addFunctions(
//        PurposeFunctionBuilder.forThing(todo, rootPackageName)
//            .withCrudFunction(createData(rootPackageName), FunctionRole.userAsSet())
//            .withFunctionModify("issue", "", new LinkedHashSet<>(), FunctionRole.userAsSet())
//            .build());

    archive.addFunctions(
        PurposeFunctionBuilder.forThing(archive, rootPackageName)
            .withCrudFunction(createRequestData(rootPackageName), FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "scrape", "Origin", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .build());

    // Override function create
//    Function functionCreate = archive.getFunctionByName("create");
//    functionCreate
//        .getReturnValue()
//        .getAsDataObject()
//        .addData(DataBuilder.create()
//            .withTextPropertyToValueObject("webPageUrlContentStorageKey", ArcaivShared.storageKey(rootPackageName))
//            .build()
//            .build()
//        );

    return archive;
  }

  private static Set<Data> createRequestData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("webPageUrl", ArcaivShared.webPageUrl(rootPackageName))
        .build()
        .withTextPropertyToValueObject("webPageUrlContentStorageKey", ArcaivShared.storageKey(rootPackageName))
        .build()
        .withEnumeration(archiveStatus(rootPackageName))
        .build();
  }

  private static DataEnumeration archiveStatus(PackageName rootPackageName) {
    Set<EnumerationValue> enumerationValues = new LinkedHashSet<>();

    enumerationValues.add(EnumerationValue.ofInitial("REQUESTED"));
    enumerationValues.add(EnumerationValue.of("PROCESSING"));
    enumerationValues.add(EnumerationValue.of("COMPLETED"));
    enumerationValues.add(EnumerationValue.of("FAILED"));

    return DataEnumeration.ofPurpose(
        new ObjectName("archiveStatus"),
        rootPackageName.withSubPackage("archive"),
        new VariableName("archiveStatus"),
        enumerationValues,
        DataPurpose.internalState());
  }
}
