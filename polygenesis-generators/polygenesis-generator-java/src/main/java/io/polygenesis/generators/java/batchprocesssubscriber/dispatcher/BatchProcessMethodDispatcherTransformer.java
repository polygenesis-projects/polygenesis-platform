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

package io.polygenesis.generators.java.batchprocesssubscriber.dispatcher;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.activity.BatchProcessExtractMessageTypeActivityGenerator;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class BatchProcessMethodDispatcherTransformer extends AbstractMethodTransformer<Function> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final BatchProcessExtractMessageTypeActivityGenerator
      batchProcessExtractMessageTypeActivityGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process method dispatcher transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param batchProcessExtractMessageTypeActivityGenerator the extract message type activity
   *     generator
   */
  public BatchProcessMethodDispatcherTransformer(
      DataTypeTransformer dataTypeTransformer,
      BatchProcessExtractMessageTypeActivityGenerator
          batchProcessExtractMessageTypeActivityGenerator) {
    super(dataTypeTransformer);
    this.batchProcessExtractMessageTypeActivityGenerator =
        batchProcessExtractMessageTypeActivityGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<String> annotations(Function source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Override"));
  }

  @Override
  public String implementation(Function source, Object... args) {
    return batchProcessExtractMessageTypeActivityGenerator.generate(source.getFunction());
  }
}
