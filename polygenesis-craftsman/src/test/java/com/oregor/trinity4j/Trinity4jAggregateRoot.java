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

package com.oregor.trinity4j;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionScope;
import java.util.Set;

/**
 * The type Trinity 4 j aggregate root.
 *
 * @author Christos Tsakostas
 */
public class Trinity4jAggregateRoot {

  /**
   * Create thing.
   *
   * @param rootPackageName the root package name
   * @return the thing
   */
  public static Thing create(PackageName rootPackageName) {
    Thing aggregateRoot =
        ThingBuilder.domainAbstractAggregateRoot("aggregateRoot")
            .addAbstractionScope(AbstractionScope.externallyProvided())
            .setPreferredPackage("com.oregor.trinity4j.domain")
            .createThing(rootPackageName);

    aggregateRoot.addFunctions(
        PurposeFunctionBuilder.forThing(aggregateRoot, rootPackageName)
            .withFunctionCreate(createData())
            .build());

    DataRepository dataRepository = new DataRepository();
    dataRepository.addSetOfData(createData());
    aggregateRoot.assignThingProperties(dataRepository);

    return aggregateRoot;
  }

  private static Set<Data> createData() {
    return DataBuilder.create()
        .withDataPrimitive(Trinity4jShared.aggregateRootId().getAsDataPrimitive())
        .build();
  }
}
