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

package io.polygenesis.models.reactivestate;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.data.VariableName;
import io.polygenesis.abstraction.thing.CqsType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ReturnValue;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import java.util.LinkedHashSet;

/** @author Christos Tsakostas */
public abstract class AbstractReactiveStateTest {

  protected Thing createThing() {
    Thing thing = ThingBuilder.endToEnd().setThingName("someThing").createThing();

    // =============================================================================================
    // CREATE
    // =============================================================================================
    Function createFunction =
        new Function(
            thing,
            Purpose.create(),
            new FunctionName("createSomeThing"),
            new ReturnValue(
                new DataPrimitive(
                    PrimitiveType.STRING,
                    new VariableName("response"),
                    new LinkedHashSet<>(),
                    DataPurpose.any())));
    thing.addFunction(createFunction);

    // =============================================================================================
    // Custom Purpose
    // =============================================================================================
    Function customPurposeFunction =
        new Function(
            thing,
            Purpose.custom("validate", CqsType.COMMAND),
            new FunctionName("createSomeThing"),
            new ReturnValue(
                new DataPrimitive(
                    PrimitiveType.STRING,
                    new VariableName("response"),
                    new LinkedHashSet<>(),
                    DataPurpose.any())));
    thing.addFunction(customPurposeFunction);

    return thing;
  }
}
