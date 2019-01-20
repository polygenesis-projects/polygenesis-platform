/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

import io.polygenesis.core.Thing;

/**
 * The type Store deducer.
 *
 * @author Christos Tsakostas
 */
public class StoreDeducer {

  private FeatureNameDeducer featureNameDeducer;
  private ActionDeducer actionDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public StoreDeducer(FeatureNameDeducer featureNameDeducer, ActionDeducer actionDeducer) {
    this.featureNameDeducer = featureNameDeducer;
    this.actionDeducer = actionDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce {@link Store} from {@link Thing}.
   *
   * @param thing the thing
   * @return the store
   */
  public Store deduceStoreFromThing(Thing thing) {
    return new Store(featureNameDeducer.from(thing), actionDeducer.deduce(thing));
  }
}
