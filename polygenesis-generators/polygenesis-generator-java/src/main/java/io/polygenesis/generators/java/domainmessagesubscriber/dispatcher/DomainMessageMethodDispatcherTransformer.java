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

package io.polygenesis.generators.java.domainmessagesubscriber.dispatcher;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.activity.ExtractMessageTypeActivityGenerator;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain message method dispatcher transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageMethodDispatcherTransformer extends AbstractMethodTransformer<Function> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final ExtractMessageTypeActivityGenerator extractMessageTypeActivityGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message method dispatcher transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param extractMessageTypeActivityGenerator the extract message type activity generator
   */
  public DomainMessageMethodDispatcherTransformer(
      DataTypeTransformer dataTypeTransformer,
      ExtractMessageTypeActivityGenerator extractMessageTypeActivityGenerator) {
    super(dataTypeTransformer);
    this.extractMessageTypeActivityGenerator = extractMessageTypeActivityGenerator;
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
    return extractMessageTypeActivityGenerator.generate(source.getFunction());
  }
}
