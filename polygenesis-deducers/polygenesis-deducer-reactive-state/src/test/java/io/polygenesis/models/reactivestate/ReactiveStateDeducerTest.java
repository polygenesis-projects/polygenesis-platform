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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.ThingRepositoryImpl;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceModelRepository;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ReactiveStateDeducerTest {

  @Test
  public void deduce() {
    FeatureNameDeducer featureNameDeducer = new FeatureNameDeducer();
    ActionGroupDeducer actionGroupDeducer = new ActionGroupDeducer();
    EffectGroupDeducer effectGroupDeducer = new EffectGroupDeducer();
    ModelDeducer modelDeducer = new ModelDeducer();
    ReducerGroupDeducer reducerGroupDeducer = new ReducerGroupDeducer();

    StoreDeducer storeDeducer =
        new StoreDeducer(
            featureNameDeducer,
            actionGroupDeducer,
            effectGroupDeducer,
            modelDeducer,
            reducerGroupDeducer);

    ReactiveStateDeducer reactiveStateDeducer = new ReactiveStateDeducer(storeDeducer);
    ThingRepository thingRepository = new ThingRepositoryImpl(new LinkedHashSet<>());

    ReactiveStateModelRepository reactiveStateModelRepository =
        reactiveStateDeducer.deduce(thingRepository, modelRepositories());

    assertThat(reactiveStateModelRepository).isNotNull();
    assertThat(reactiveStateModelRepository.getStores()).isNotNull();
    assertThat(reactiveStateModelRepository.getStores().size()).isEqualTo(0);
  }

  private Set<ModelRepository> modelRepositories() {
    Set<ModelRepository> modelRepositories = new LinkedHashSet<>();

    Set<Service> services = new LinkedHashSet<>();
    services.add(mock(Service.class));
    ServiceModelRepository serviceModelRepository = new ServiceModelRepository(services);
    modelRepositories.add(serviceModelRepository);

    return modelRepositories;
  }
}
