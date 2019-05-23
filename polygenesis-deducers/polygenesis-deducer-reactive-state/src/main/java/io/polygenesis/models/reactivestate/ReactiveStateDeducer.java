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

import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Redux deducer.
 *
 * @author Christos Tsakostas
 */
public class ReactiveStateDeducer implements Deducer<ReactiveStateMetamodelRepository> {

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

  @SuppressWarnings("rawtypes")
  @Override
  public ReactiveStateMetamodelRepository deduce(
      Set<AbstractionRepository> abstractionRepositories,
      Set<MetamodelRepository> modelRepositories) {
    Set<Store> stores = new LinkedHashSet<>();

    ServiceMetamodelRepository serviceModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, ServiceMetamodelRepository.class);

    if (serviceModelRepository.getItems().isEmpty()) {
      throw new IllegalStateException("ServiceModelRepository cannot be empty.");
    }

    CoreRegistry.getAbstractionRepositoryResolver()
        .resolve(abstractionRepositories, ThingRepository.class)
        .getAbstractionItemsByScope(AbstractionScope.api())
        .forEach(
            thing -> stores.add(storeDeducer.deduceStoreFromThing(thing, serviceModelRepository)));

    return new ReactiveStateMetamodelRepository(stores);
  }
}
