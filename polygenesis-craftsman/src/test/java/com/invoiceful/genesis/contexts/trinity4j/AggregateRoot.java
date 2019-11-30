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

package com.invoiceful.genesis.contexts.trinity4j;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Set;

/** @author Christos Tsakostas */
public class AggregateRoot {

  public static Thing create(String rootPackageName) {
    Thing aggregateRoot =
        ThingBuilder.domainAbstractAggregateRoot("aggregateRoot")
            .setPreferredPackage("com.oregor.trinity4j.domain")
            .createThing();

    aggregateRoot.addFunctions(
        PurposeFunctionBuilder.forThing(aggregateRoot, rootPackageName)
            .withFunctionCreate(createData(rootPackageName))
            .build());

    return aggregateRoot;
  }

  private static Set<Data> createData(String rootPackageName) {
    return DataBuilder.create().withGroupData(aggregateRootId(rootPackageName)).build();
  }

  private static DataObject aggregateRootId(String rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("typeId").build().build();

    return new DataObject(
        new VariableName("aggregateRootId"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("aggregateRootId"),
        new PackageName("com.oregor.trinity4j.domain"),
        data);
  }
}
