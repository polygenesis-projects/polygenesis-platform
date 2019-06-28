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

package io.polygenesis.generators.java.batchprocesssubscriber.dispatcher;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Argument;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ReturnValue;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractNameablePackageable;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * The type Batch process dispatcher.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessDispatcher extends AbstractNameablePackageable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Function function;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dispatcher.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public BatchProcessDispatcher(ObjectName objectName, PackageName packageName) {
    super(objectName, packageName);
    this.function = makeExtractMessageTypeFunction();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets function.
   *
   * @return the function
   */
  public Function getFunction() {
    return function;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make extract message type function function.
   *
   * @return the function
   */
  private Function makeExtractMessageTypeFunction() {
    Thing thing =
        ThingBuilder.apiClientBatchProcess().setThingName("batchProcessDispatcher").createThing();

    return new Function(
        thing,
        Purpose.reset(),
        new FunctionName("extractMessageType"),
        new ReturnValue(DataPrimitive.of(PrimitiveType.STRING, VariableName.response())),
        new LinkedHashSet<>(
            Arrays.asList(
                new Argument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("message"))))),
        Activity.empty());
  }
}
