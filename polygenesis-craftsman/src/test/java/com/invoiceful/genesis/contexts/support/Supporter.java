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

package com.invoiceful.genesis.contexts.support;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import java.util.Set;

/**
 * The type Supporter.
 *
 * @author Christos Tsakostas
 */
public class Supporter {

  /**
   * Create thing.
   *
   * @param rootPackageName the root package name
   * @return the thing
   */
  public static Thing create(String rootPackageName) {
    Thing supporter = ThingBuilder.endToEnd("supporter").createThing();

    supporter.addFunctions(
        PurposeFunctionBuilder.forThing(supporter, rootPackageName)
            .withFunctionCreate(createData(rootPackageName))
            .build());

    return supporter;
  }

  private static Set<Data> createData(String rootPackageName) {
    return DataBuilder.create().withTextProperty("name").build().build();
  }
}
