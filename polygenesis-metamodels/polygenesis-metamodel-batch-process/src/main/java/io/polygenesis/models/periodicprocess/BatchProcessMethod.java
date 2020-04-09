/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.models.periodicprocess;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.commons.assertion.Assertion;

/**
 * The type Batch process method.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessMethod implements FunctionProvider {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private BatchProcessMetamodel batchProcessMetamodel;
  private Function function;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process method.
   *
   * @param batchProcessMetamodel the batch process metamodel
   * @param function the function
   */
  public BatchProcessMethod(BatchProcessMetamodel batchProcessMetamodel, Function function) {
    Assertion.isNotNull(batchProcessMetamodel, "batchProcessMetamodel is required");
    Assertion.isNotNull(function, "function is required");
    this.batchProcessMetamodel = batchProcessMetamodel;
    this.function = function;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets batch process metamodel.
   *
   * @return the batch process metamodel
   */
  public BatchProcessMetamodel getBatchProcessMetamodel() {
    return batchProcessMetamodel;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Function getFunction() {
    return function;
  }
}
