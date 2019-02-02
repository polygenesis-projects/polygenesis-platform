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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.ThingRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Redux deducer.
 *
 * @author Christos Tsakostas
 */
public class ReactiveStateDeducerImpl implements ReactiveStateDeducer {

  private final StoreDeducer storeDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Redux deducer.
   *
   * @param storeDeducer the store deducer
   */
  public ReactiveStateDeducerImpl(StoreDeducer storeDeducer) {
    Assertion.isNotNull(storeDeducer, "storeDeducer is required");
    this.storeDeducer = storeDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ReactiveStateModelRepository deduce(ThingRepository thingRepository) {
    Set<Store> stores = new LinkedHashSet<>();

    thingRepository
        .getThings()
        .forEach(thing -> stores.add(storeDeducer.deduceStoreFromThing(thing)));

    return new ReactiveStateModelRepository(stores);
  }
}
