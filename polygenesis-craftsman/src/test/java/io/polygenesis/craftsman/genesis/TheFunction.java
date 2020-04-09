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

package io.polygenesis.craftsman.genesis;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import java.util.Set;

/**
 * The type Debtor.
 *
 * @author Christos Tsakostas
 */
public class TheFunction {

  /**
   * Create thing.
   *
   * @return the thing
   */
  public static Thing create(String rootPackageName, Thing parentThing) {
    Thing thing = ThingBuilder.endToEndChildWithIdentity("function", parentThing).createThing();

    thing.addFunctions(
        PurposeFunctionBuilder.forThing(thing, rootPackageName)
            .withFunctionCreate(createData(rootPackageName))
            .build());

    return thing;
  }

  private static Set<Data> createData(String rootPackageName) {
    return DataBuilder.create()
        .withGroupData(Shared.name(rootPackageName).withVariableName("name"))
        .build();
  }
}
