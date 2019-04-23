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

package io.polygenesis.models.reactivestate;

/**
 * The type Reactive state factory.
 *
 * @author Christos Tsakostas
 */
public class ReactiveStateFactory {

  private ReactiveStateFactory() {
    throw new IllegalStateException("Utility class");
  }

  private static final StoreDeducer storeDeducer;

  static {
    FeatureNameDeducer featureNameDeducer = new FeatureNameDeducer();
    ActionGroupDeducer actionGroupDeducer = new ActionGroupDeducer();
    EffectGroupDeducer effectGroupDeducer = new EffectGroupDeducer();
    ModelDeducer modelDeducer = new ModelDeducer();
    ReducerGroupDeducer reducerGroupDeducer = new ReducerGroupDeducer();

    storeDeducer =
        new StoreDeducer(
            featureNameDeducer,
            actionGroupDeducer,
            effectGroupDeducer,
            modelDeducer,
            reducerGroupDeducer);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * New instance reactive state deducer.
   *
   * @return the reactive state deducer
   */
  public static ReactiveStateDeducer newInstance() {
    return new ReactiveStateDeducer(storeDeducer);
  }
}
