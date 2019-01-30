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

import io.polygenesis.commons.feature.FeatureName;
import io.polygenesis.commons.test.AbstractEqualityTest;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class StoreTest extends AbstractEqualityTest<Store> {

  @Test
  public void shouldSucceedToInstantiate() {
    Set<Action> actions = new LinkedHashSet<>();
    Store store = new Store(new FeatureName("abc"), actions);

    assertThat(store).isNotNull();
    assertThat(store.getFeatureName()).isEqualTo(new FeatureName("abc"));
    assertThat(store.getActions().size()).isEqualTo(0);
  }

  @Override
  public Store createObject1() {
    FeatureName featureName = new FeatureName("xxx");
    Set<Action> actions = new LinkedHashSet<>();
    return new Store(featureName, actions);
  }

  @Override
  public Store createObject2() {
    FeatureName featureName = new FeatureName("yyy");
    Set<Action> actions = new LinkedHashSet<>();
    return new Store(featureName, actions);
  }
}
