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

package io.polygenesis.core;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Set;

/**
 * The type Abstract deducer.
 *
 * @param <R> the type parameter
 * @author Christos Tsakostas
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractDeducer<R extends MetamodelRepository<? extends Metamodel>>
    implements Deducer<R> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private DeducerStrategy<R> deducerStrategy;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract deducer.
   *
   * @param deducerStrategy the deducer strategy
   */
  public AbstractDeducer(DeducerStrategy<R> deducerStrategy) {
    setDeducerStrategy(deducerStrategy);
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets deducer strategy.
   *
   * @param deducerStrategy the deducer strategy
   */
  private void setDeducerStrategy(DeducerStrategy<R> deducerStrategy) {
    Assertion.isNotNull(deducerStrategy, "deducerStrategy is required");
    this.deducerStrategy = deducerStrategy;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public R deduce(
      Set<AbstractionRepository> abstractionRepositories,
      Set<MetamodelRepository> metamodelRepositories) {
    return deducerStrategy.deduce(abstractionRepositories, metamodelRepositories);
  }
}
