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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.activity.BatchProcessPublisherSendActivityGenerator;
import io.polygenesis.generators.java.shared.transformer.AbstractMethodTransformer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Christos Tsakostas
 */
public class ScheduledDomainMessagePublisherMethodTransformer extends AbstractMethodTransformer<Function> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final BatchProcessPublisherSendActivityGenerator
      batchProcessPublisherSendActivityGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public ScheduledDomainMessagePublisherMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      BatchProcessPublisherSendActivityGenerator batchProcessPublisherSendActivityGenerator) {
    super(dataTypeTransformer);
    this.batchProcessPublisherSendActivityGenerator = batchProcessPublisherSendActivityGenerator;
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
    return batchProcessPublisherSendActivityGenerator.generate(source.getFunction(), args);
  }
}
