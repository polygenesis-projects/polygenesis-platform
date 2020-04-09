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

package io.polygenesis.deducers.stateprovider;

import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.deducers.stateprovider.internal.ProviderCollectionDeducer;
import io.polygenesis.deducers.stateprovider.internal.ProviderDetailDeducer;
import io.polygenesis.metamodels.stateprovider.Provider;
import io.polygenesis.metamodels.stateprovider.ProviderMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProviderDeducer implements Deducer<ProviderMetamodelRepository> {

  private final ProviderCollectionDeducer providerCollectionDeducer;
  private final ProviderDetailDeducer providerDetailDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public ProviderDeducer(
      ProviderCollectionDeducer providerCollectionDeducer,
      ProviderDetailDeducer providerDetailDeducer) {
    this.providerCollectionDeducer = providerCollectionDeducer;
    this.providerDetailDeducer = providerDetailDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ProviderMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<Provider> providers = new LinkedHashSet<>();

    CoreRegistry.getAbstractionRepositoryResolver()
        .resolve(abstractionRepositories, ThingRepository.class)
        .getAbstractionItemsByScope(AbstractionScope.api())
        .stream()
        .flatMap(thing -> thing.getFunctions().stream())
        .forEach(
            function -> {
              if (function.getPurpose().isFetchPagedCollection()
                  || function.getPurpose().isFetchPagedCollection()) {
                providers.add(providerCollectionDeducer.deduce(function));
              } else if (function.getPurpose().isFetchOne()) {
                providers.add(providerDetailDeducer.deduce(function));
              }
            });

    return new ProviderMetamodelRepository(providers);
  }
}
