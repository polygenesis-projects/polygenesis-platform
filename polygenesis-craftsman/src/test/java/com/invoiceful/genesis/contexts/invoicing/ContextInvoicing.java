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

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingContext;
import io.polygenesis.abstraction.thing.ThingContextBuilder;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Deducer;
import java.util.Set;

/** @author Christos Tsakostas */
public class ContextInvoicing {

  public static ThingContext get(
      String rootPackageName, ContextGenerator contextGenerator, Set<Deducer<?>> deducers) {
    Thing invoice = Invoice.create(rootPackageName);
    Thing document = Document.create(rootPackageName);
    Thing language = Language.create(rootPackageName);

    return ThingContextBuilder.of("invoicing", contextGenerator)
        .withDeducers(deducers)
        //        .addThing(Language.create(rootPackageName))
        //        .addThing(DocumentRole.create(rootPackageName))
        //        .addThing(TaxRole.create(rootPackageName))

        //        .addThing(DocumentType.create(rootPackageName))
        //        .addThing(Tax.create(rootPackageName))

        //        .addThing(document)
        //        .addThing(OutgoingDocument.create(document, rootPackageName))

        .addThing(invoice)

        // Supportive
        .addThing(language)

        // Projections
        // .addThing(InvoicesPerYear.create(rootPackageName))
        .build();
  }
}
