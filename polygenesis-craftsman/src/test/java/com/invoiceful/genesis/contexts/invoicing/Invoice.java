/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.LinkedHashSet;
import java.util.Set;

/** @author Christos Tsakostas */
public class Invoice {

  public static Thing create(String rootPackageName) {
    Thing invoice = ThingBuilder.endToEnd("invoice").setMultiTenant(true).createThing();

    invoice.addFunctions(
        PurposeFunctionBuilder.forThing(invoice, rootPackageName)
            .withCrudFunction(createData(rootPackageName))
            .withFunctionModify("issue", new LinkedHashSet<>())
            .build());

    invoice.addChild(InvoiceItem.create(invoice, new PackageName(rootPackageName)));

    return invoice;
  }

  private static Set<Data> createData(String rootPackageName) {
    return DataBuilder.create().build();
  }
}
