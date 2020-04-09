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

package io.polygenesis.deducers.stateredux;

public class StateReduxDeducerFactory {

  private static final StoreDeducer storeDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION
  // ===============================================================================================

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
  // CONSTRUCTOR
  // ===============================================================================================

  private StateReduxDeducerFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance state redux deducer.
   *
   * @return the state redux deducer
   */
  public static StateReduxDeducer newInstance() {
    return new StateReduxDeducer(storeDeducer);
  }
}
