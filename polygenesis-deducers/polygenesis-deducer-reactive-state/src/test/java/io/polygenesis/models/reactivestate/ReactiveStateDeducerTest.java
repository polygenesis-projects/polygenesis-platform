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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.abstraction.thing.ThingRepositoryImpl;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ReactiveStateDeducerTest {

  @Test
  @SuppressWarnings("rawtypes")
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
    Set<AbstractionRepository> abstractionRepositories =
        new LinkedHashSet<>(Arrays.asList(thingRepository));

    ReactiveStateMetamodelRepository reactiveStateModelRepository =
        reactiveStateDeducer.deduce(abstractionRepositories, modelRepositories());

    assertThat(reactiveStateModelRepository).isNotNull();
    assertThat(reactiveStateModelRepository.getItems()).isNotNull();
    assertThat(reactiveStateModelRepository.getItems().size()).isEqualTo(0);
  }

  @SuppressWarnings("rawtypes")
  private Set<MetamodelRepository> modelRepositories() {
    Set<MetamodelRepository> modelRepositories = new LinkedHashSet<>();

    Set<Service> services = new LinkedHashSet<>();
    services.add(mock(Service.class));
    ServiceMetamodelRepository serviceModelRepository = new ServiceMetamodelRepository(services);
    modelRepositories.add(serviceModelRepository);

    return modelRepositories;
  }
}
