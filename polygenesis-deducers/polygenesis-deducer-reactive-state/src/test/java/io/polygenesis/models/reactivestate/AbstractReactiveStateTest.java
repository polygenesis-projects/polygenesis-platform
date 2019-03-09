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

package io.polygenesis.models.reactivestate;

import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.datatype.PrimitiveType;
import io.polygenesis.core.iomodel.DataBusinessType;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import java.util.LinkedHashSet;

/** @author Christos Tsakostas */
public abstract class AbstractReactiveStateTest {

  protected Thing createThing() {
    Thing thing = new Thing(new ThingName("someThing"));

    // =============================================================================================
    // CREATE
    // =============================================================================================
    Function createFunction =
        new Function(
            thing,
            new Goal(GoalType.CREATE),
            new FunctionName("createSomeThing"),
            new ReturnValue(
                new IoModelPrimitive(
                    PrimitiveType.STRING,
                    new VariableName("response"),
                    new LinkedHashSet<>(),
                    DataBusinessType.ANY)));
    thing.appendFunction(createFunction);

    // =============================================================================================
    // CUSTOM GOAL
    // =============================================================================================
    Function customGoalFunction =
        new Function(
            thing,
            new Goal(GoalType.VALIDATE),
            new FunctionName("createSomeThing"),
            new ReturnValue(
                new IoModelPrimitive(
                    PrimitiveType.STRING,
                    new VariableName("response"),
                    new LinkedHashSet<>(),
                    DataBusinessType.ANY)));
    thing.appendFunction(customGoalFunction);

    return thing;
  }
}
