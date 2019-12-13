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
public class TaxTranslation {

  public static Thing create(Thing tax, Thing language, PackageName rootPackageName) {
    Thing taxTranslation =
        ThingBuilder.domainAggregateEntity("taxTranslation", tax)
            .setMultiTenant(Trinity4jShared.tenantId())
            .createThing();

    taxTranslation.addFunctions(
        PurposeFunctionBuilder.forThing(taxTranslation, rootPackageName)
            .withCrudFunction(data(language, rootPackageName))
            .build());

    return taxTranslation;
  }

  // ===============================================================================================
  // DATA
  // ===============================================================================================

  private static Set<Data> data(Thing language, PackageName rootPackageName) {
    return DataBuilder.create()
        // TODO .withReferenceToThing(language, "language")
        .withGroupData(Shared.language(rootPackageName))
        .withTextProperty("translation")
        .build()
        .build();
  }
}
