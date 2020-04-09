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

import com.oregor.trinity4j.Trinity4jShared;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;

/** @author Christos Tsakostas */
public class Tax {

  public static Thing create(PackageName rootPackageName) {
    Thing tax =
        ThingBuilder.endToEnd("tax").setMultiTenant(Trinity4jShared.tenantId()).createThing();

    tax.addFunctions(
        PurposeFunctionBuilder.forThing(tax, rootPackageName)
            .withCrudFunction(data(rootPackageName))
            .build());

    tax.addChild(TaxTranslation.create(tax, Language.create(rootPackageName), rootPackageName));

    return tax;
  }

  // ===============================================================================================
  // DATA
  // ===============================================================================================

  private static Set<Data> data(PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextProperty("taxName")
        .build()
        //        .withGroupData(Shared.language(rootPackageName))
        .build();
  }
}
