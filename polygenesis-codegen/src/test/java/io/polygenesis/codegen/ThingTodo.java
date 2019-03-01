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

package io.polygenesis.codegen;

import io.polygenesis.core.Thing;
import io.polygenesis.core.dsl.DataBuilder;
import io.polygenesis.core.dsl.ThingBuilder;
import io.polygenesis.core.iomodel.IoModel;
import java.util.Set;

/** @author Christos Tsakostas */
public class ThingTodo {

  public static Thing create() {
    Thing business =
        ThingBuilder.create("todo", OregorDdd4jExampleGenesisTest.JAVA_ROOT_PACKAGE)
            .withFunctionCreate(data())
            .withFunctionModify(data())
            .withFunctionFetchOne(data())
            .withFunctionFetchPagedCollection(data())
            .get();

    return business;
  }

  // ===============================================================================================
  // DATA
  // ===============================================================================================

  private static Set<IoModel> data() {
    return DataBuilder.create()
        .withTextProperty("description")
        .build()
        .withBooleanProperty("done")
        .build()
        .build();
  }
}
