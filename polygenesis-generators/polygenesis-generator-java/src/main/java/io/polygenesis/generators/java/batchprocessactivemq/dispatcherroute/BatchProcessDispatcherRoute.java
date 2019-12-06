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

package io.polygenesis.generators.java.batchprocessactivemq.dispatcherroute;

import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractNameablePackageable;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.BatchProcessDispatcher;

/**
 * The type Batch process dispatcher route.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessDispatcherRoute extends AbstractNameablePackageable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private BatchProcessDispatcher batchProcessDispatcher;
  private Function configure;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process dispatcher route.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param batchProcessDispatcher the batch process dispatcher
   */
  public BatchProcessDispatcherRoute(
      ObjectName objectName,
      PackageName packageName,
      BatchProcessDispatcher batchProcessDispatcher) {
    super(objectName, packageName);
    this.batchProcessDispatcher = batchProcessDispatcher;
    this.configure = makeConfigureFunction();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets batch process dispatcher.
   *
   * @return the batch process dispatcher
   */
  public BatchProcessDispatcher getBatchProcessDispatcher() {
    return batchProcessDispatcher;
  }

  /**
   * Gets configure.
   *
   * @return the configure
   */
  public Function getConfigure() {
    return configure;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function makeConfigureFunction() {
    Thing thing = ThingBuilder.apiClientBatchProcess("abstractSubscriber").createThing();

    return new Function(
        thing,
        Purpose.reset(),
        new FunctionName("configure"),
        null,
        new DataRepository(),
        Activity.empty(),
        thing.getAbstractionsScopes());
  }
}
