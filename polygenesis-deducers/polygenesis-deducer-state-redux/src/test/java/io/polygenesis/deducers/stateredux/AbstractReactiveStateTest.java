/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.deducers.stateredux;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.CqsType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;

public abstract class AbstractReactiveStateTest {

  protected Thing createThing() {
    Thing thing = ThingBuilder.endToEnd("someThing").createThing(PackageName.any());

    // =============================================================================================
    // CREATE
    // =============================================================================================
    Function createFunction =
        FunctionBuilder.of(thing, "createSomeThing", "", Purpose.create())
            .setReturnValue(DataPrimitive.of(PrimitiveType.STRING, new VariableName("response")))
            .build();

    thing.addFunction(createFunction);

    // =============================================================================================
    // Custom Purpose
    // =============================================================================================
    Function customPurposeFunction =
        FunctionBuilder.of(
                thing, "createSomeThing", "", Purpose.custom("validate", CqsType.COMMAND))
            .setReturnValue(DataPrimitive.of(PrimitiveType.STRING, new VariableName("response")))
            .build();

    thing.addFunction(customPurposeFunction);

    return thing;
  }
}
