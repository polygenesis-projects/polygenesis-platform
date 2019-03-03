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
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.models.api.ServiceModelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Redux deducer.
 *
 * @author Christos Tsakostas
 */
public class ReactiveStateDeducer implements Deducer<ReactiveStateModelRepository> {

  private final StoreDeducer storeDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Redux deducer.
   *
   * @param storeDeducer the store deducer
   */
  public ReactiveStateDeducer(StoreDeducer storeDeducer) {
    Assertion.isNotNull(storeDeducer, "storeDeducer is required");
    this.storeDeducer = storeDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ReactiveStateModelRepository deduce(
      ThingRepository thingRepository, Set<ModelRepository> modelRepositories) {
    Set<Store> stores = new LinkedHashSet<>();

    ServiceModelRepository serviceModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, ServiceModelRepository.class);

    if (serviceModelRepository.getServices().isEmpty()) {
      throw new IllegalStateException(String.format("serviceModelRepository cannot be empty."));
    }

    thingRepository
        .getThings()
        .forEach(
            thing -> stores.add(storeDeducer.deduceStoreFromThing(thing, serviceModelRepository)));

    return new ReactiveStateModelRepository(stores);
  }
}
