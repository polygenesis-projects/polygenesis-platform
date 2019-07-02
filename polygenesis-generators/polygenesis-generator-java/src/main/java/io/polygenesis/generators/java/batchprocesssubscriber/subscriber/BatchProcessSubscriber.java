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

package io.polygenesis.generators.java.batchprocesssubscriber.subscriber;

import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ReturnValue;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodel;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * The type Batch process subscriber.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessSubscriber extends BatchProcessMetamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Function getSupportedMessageTypes;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process subscriber.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param commandServiceMethod the command service method
   * @param queryServiceMethod the query service method
   * @param queryCollectionItem the query collection item
   */
  public BatchProcessSubscriber(
      ObjectName objectName,
      PackageName packageName,
      ServiceMethod commandServiceMethod,
      ServiceMethod queryServiceMethod,
      Dto queryCollectionItem) {
    super(objectName, packageName, commandServiceMethod, queryServiceMethod, queryCollectionItem);
    this.getSupportedMessageTypes = makeGetSupportedMessageTypesFunction();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets get supported message types.
   *
   * @return the get supported message types
   */
  public Function getGetSupportedMessageTypes() {
    return getSupportedMessageTypes;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make extract message type function function.
   *
   * @return the function
   */
  private Function makeGetSupportedMessageTypesFunction() {
    Thing thing =
        ThingBuilder.apiClientBatchProcess().setThingName("batchProcessSubscriber").createThing();

    return new Function(
        thing,
        Purpose.reset(),
        new FunctionName("getSupportedMessageTypes"),
        new ReturnValue(
            DataArray.of(DataPrimitive.of(PrimitiveType.STRING, VariableName.response()))),
        new LinkedHashSet<>(Arrays.asList()),
        Activity.keyValues(
            new LinkedHashSet<>(
                Arrays.asList(
                    new KeyValue(
                        "supportedMessageTypes",
                        new LinkedHashSet<>(Arrays.asList(getObjectName().getText())))))));
  }
}
