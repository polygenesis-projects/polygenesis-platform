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

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.FeatureName;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class StoreTest extends AbstractEqualityTest<Store> {

  @Test
  public void shouldSucceedToInstantiate() {
    Set<ActionGroup> actionGroups = new LinkedHashSet<>();
    Set<EffectGroup> effectGroups = new LinkedHashSet<>();
    Set<Model> models = new LinkedHashSet<>();
    Set<ReducerGroup> reducerGroups = new LinkedHashSet<>();

    Store store =
        new Store(new FeatureName("abc"), actionGroups, effectGroups, models, reducerGroups);

    assertThat(store).isNotNull();
    assertThat(store.getFeatureName()).isEqualTo(new FeatureName("abc"));
    assertThat(store.getActionGroups().size()).isEqualTo(0);
  }

  @Override
  public Store createObject1() {
    FeatureName featureName = new FeatureName("xxx");
    Set<ActionGroup> actionGroups = new LinkedHashSet<>();
    Set<EffectGroup> effectGroups = new LinkedHashSet<>();
    Set<Model> models = new LinkedHashSet<>();
    Set<ReducerGroup> reducerGroups = new LinkedHashSet<>();

    return new Store(featureName, actionGroups, effectGroups, models, reducerGroups);
  }

  @Override
  public Store createObject2() {
    FeatureName featureName = new FeatureName("yyy");
    Set<ActionGroup> actionGroups = new LinkedHashSet<>();
    Set<EffectGroup> effectGroups = new LinkedHashSet<>();
    Set<Model> models = new LinkedHashSet<>();
    Set<ReducerGroup> reducerGroups = new LinkedHashSet<>();

    return new Store(featureName, actionGroups, effectGroups, models, reducerGroups);
  }
}
