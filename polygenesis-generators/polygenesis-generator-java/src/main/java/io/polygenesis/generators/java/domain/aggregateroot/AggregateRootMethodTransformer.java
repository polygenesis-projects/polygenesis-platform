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

package io.polygenesis.generators.java.domain.aggregateroot;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.transformers.java.AbstractMethodTransformer;

public class AggregateRootMethodTransformer extends AbstractMethodTransformer<Function> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateRootActivityRegistry aggregateRootActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param aggregateRootActivityRegistry the aggregate root activity registry
   */
  public AggregateRootMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      AggregateRootActivityRegistry aggregateRootActivityRegistry) {
    super(dataTypeTransformer);
    this.aggregateRootActivityRegistry = aggregateRootActivityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String implementation(Function source, Object... args) {
    if (aggregateRootActivityRegistry.isActivitySupportedFor(source)) {
      return aggregateRootActivityRegistry.activityFor(source, args);
    } else {
      return super.implementation(source, args);
    }
  }
}
