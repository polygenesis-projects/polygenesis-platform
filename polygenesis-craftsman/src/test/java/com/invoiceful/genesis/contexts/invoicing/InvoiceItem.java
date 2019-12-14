/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

import com.oregor.trinity4j.Trinity4jAggregateEntity;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;

/**
 * The type Invoice item.
 *
 * @author Christos Tsakostas
 */
public class InvoiceItem {

  /**
   * Create thing.
   *
   * @param invoice the invoice
   * @param rootPackageName the root package name
   * @return the thing
   */
  public static Thing create(Thing invoice, PackageName rootPackageName) {
    Thing invoiceItem =
        ThingBuilder.endToEndChildWithIdentity("invoiceItem", invoice)
            .setSuperClass(Trinity4jAggregateEntity.create(rootPackageName))
            .createThing(rootPackageName);

    invoiceItem.addFunctions(
        PurposeFunctionBuilder.forThing(invoiceItem, rootPackageName.getText())
            .withCrudFunction(data(rootPackageName))
            .build());

    // invoiceItem.addChild(InvoiceItemTax.create(invoiceItem, rootPackageName));

    return invoiceItem;
  }

  // ===============================================================================================
  // DATA
  // ===============================================================================================

  private static Set<Data> data(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("title", Shared.title(rootPackageName))
        .build()
        .withTextPropertyToValueObject("description", Shared.description(rootPackageName))
        .build()
        .withDecimalPropertyToValueObject("quantity", Shared.quantity(rootPackageName))
        .build()
        .withDecimalPropertyToValueObject("unitCost", Shared.unitCost(rootPackageName))
        .build()
        .withDecimalPropertyToValueObject("lineTotal", Shared.lineTotal(rootPackageName))
        .build()
        .withGroupData(Shared.discount(rootPackageName))
        .build();
  }
}
