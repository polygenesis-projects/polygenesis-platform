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

import io.polygenesis.models.api.ServiceModelRepository;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class StoreDeducerTest extends AbstractReactiveStateTest {

  private FeatureNameDeducer featureNameDeducer;
  private ActionGroupDeducer actionGroupDeducer;
  private EffectGroupDeducer effectGroupDeducer;
  private ModelDeducer modelDeducer;
  private ReducerGroupDeducer reducerGroupDeducer;
  private ServiceModelRepository serviceModelRepository;
  private StoreDeducer storeDeducer;

  @Before
  public void setUp() {
    featureNameDeducer = new FeatureNameDeducer();
    actionGroupDeducer = new ActionGroupDeducer();
    effectGroupDeducer = new EffectGroupDeducer();
    modelDeducer = new ModelDeducer();
    reducerGroupDeducer = new ReducerGroupDeducer();
    serviceModelRepository = mock(ServiceModelRepository.class);

    storeDeducer =
        new StoreDeducer(
            featureNameDeducer,
            actionGroupDeducer,
            effectGroupDeducer,
            modelDeducer,
            reducerGroupDeducer);
  }

  @Test
  public void deduceStoreForThing() {
    Store store = storeDeducer.deduceStoreFromThing(createThing(), serviceModelRepository);

    assertThat(store).isNotNull();
  }
}
