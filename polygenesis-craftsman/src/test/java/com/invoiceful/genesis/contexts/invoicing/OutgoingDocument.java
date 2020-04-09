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

package com.invoiceful.genesis.contexts.invoicing;

import com.invoiceful.genesis.contexts.access.Shared;
import com.oregor.trinity4j.Trinity4jShared;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;

public class OutgoingDocument {

  public static Thing create(Thing document, String rootPackageName) {
    Thing outgoingDocument =
        ThingBuilder.domainAbstractAggregateRoot("outgoingDocument")
            .setSuperClass(document)
            .setMultiTenant(Trinity4jShared.tenantId())
            .createThing();

    outgoingDocument.addFunctions(
        PurposeFunctionBuilder.forThing(outgoingDocument, rootPackageName)
            .withFunctionCreate(createData(rootPackageName))
            .build());

    //    outgoingDocument.addFunctions(
    //        PurposeFunctionBuilder.forThing(outgoingDocument, rootPackageName)
    //            .withFunctionModify("confirm", confirmData(rootPackageName))
    //            .build());

    return outgoingDocument;
  }

  private static Set<Data> createData(String rootPackageName) {
    return DataBuilder.create().withTextProperty("outName").build().build();
  }

  private static Set<Data> confirmData(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject(
            "confirmationCode",
            com.invoiceful.genesis.contexts.access.Shared.confirmationCode(rootPackageName)
                .getObjectName(),
            Shared.confirmationCode(rootPackageName).getPackageName())
        .build()
        .build();
  }
}
