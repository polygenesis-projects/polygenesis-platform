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
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;

public class PublicArchive {

  public static Thing create(PackageName rootPackageName) {
    Thing publicArchive =
        ThingBuilder.endToEnd("publicArchive")
            .setSuperClass(Trinity4jTenantAggregateRoot.create(rootPackageName))
            .setMultiTenant(Trinity4jShared.tenantId())
            .createThing(rootPackageName);

    publicArchive.addFunctions(
        PurposeFunctionBuilder.forThing(publicArchive, rootPackageName)
            .withCrudFunction(createRequestData(rootPackageName), FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "scrape", "Origin", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .build());

    return publicArchive;
  }

  private static Set<Data> createRequestData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("webPageUrl", ArcaivShared.webPageUrl(rootPackageName))
        .build()
        .withTextPropertyToValueObject("webPageUrlContentStorageKey", ArcaivShared.storageKey(rootPackageName))
        .build()
        .withEnumeration(publicArchiveStatus(rootPackageName))
        .build();
  }

  private static DataEnumeration publicArchiveStatus(PackageName rootPackageName) {
    Set<EnumerationValue> enumerationValues = new LinkedHashSet<>();

    enumerationValues.add(EnumerationValue.ofInitial("REQUESTED"));
    enumerationValues.add(EnumerationValue.of("PROCESSING"));
    enumerationValues.add(EnumerationValue.of("COMPLETED"));
    enumerationValues.add(EnumerationValue.of("FAILED"));

    return DataEnumeration.ofPurpose(
        new ObjectName("publicArchiveStatus"),
        rootPackageName.withSubPackage("publicArchive"),
        new VariableName("publicArchiveStatus"),
        enumerationValues,
        DataPurpose.internalState());
  }
}
