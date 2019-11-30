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

package io.polygenesis.generators.java.batchprocessscheduler.scheduler;

import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodel;
import java.util.LinkedHashSet;

/**
 * The type Batch process scheduler route.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessSchedulerRoute extends BatchProcessMetamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Function configure;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process scheduler route.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param commandServiceMethod the command service method
   * @param queryServiceMethod the query service method
   * @param queryCollectionItem the query collection item
   */
  public BatchProcessSchedulerRoute(
      ObjectName objectName,
      PackageName packageName,
      ServiceMethod commandServiceMethod,
      ServiceMethod queryServiceMethod,
      Dto queryCollectionItem) {
    super(objectName, packageName, commandServiceMethod, queryServiceMethod, queryCollectionItem);
    this.configure = makeConfigureFunction();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

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
    Thing thing = ThingBuilder.apiClientBatchProcess("batchProcessSchedulerRoute").createThing();

    return new Function(
        thing,
        Purpose.reset(),
        new FunctionName("configure"),
        null,
        new LinkedHashSet<>(),
        Activity.empty(),
        thing.getAbstractionsScopes());
  }
}
