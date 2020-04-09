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

package io.polygenesis.generators.java.batchprocess.process;

import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.batchprocess.process.activity.BatchProcessActivityRegistry;
import io.polygenesis.models.periodicprocess.BatchProcessMethod;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class BatchProcessMethodTransformer extends AbstractMethodTransformer<BatchProcessMethod> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final BatchProcessActivityRegistry batchProcessActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param batchProcessActivityRegistry the batch process activity registry
   */
  public BatchProcessMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      BatchProcessActivityRegistry batchProcessActivityRegistry) {
    super(dataTypeTransformer);
    this.batchProcessActivityRegistry = batchProcessActivityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<String> annotations(BatchProcessMethod source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Override"));
  }

  @Override
  public String description(BatchProcessMethod source, Object... args) {
    return "";
  }

  @Override
  public String implementation(BatchProcessMethod source, Object... args) {
    if (batchProcessActivityRegistry.isActivitySupportedFor(source)) {
      return batchProcessActivityRegistry.activityFor(source, args);
    } else {
      return super.implementation(source, args);
    }
  }
}
