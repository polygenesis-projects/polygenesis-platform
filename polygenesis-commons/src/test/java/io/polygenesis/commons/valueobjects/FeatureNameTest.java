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

package io.polygenesis.commons.valueobjects;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import org.junit.Test;

/** @author Christos Tsakostas */
public class FeatureNameTest extends AbstractEqualityTest<FeatureName> {

  @Test
  public void shouldSucceedToInstantiate() {
    FeatureName feature = new FeatureName("abc");

    assertThat(feature).isNotNull();
    assertThat(feature.getText()).isEqualTo("abc");
  }

  @Override
  public FeatureName createObject1() {
    return new FeatureName("xxx");
  }

  @Override
  public FeatureName createObject2() {
    return new FeatureName("yyy");
  }
}
