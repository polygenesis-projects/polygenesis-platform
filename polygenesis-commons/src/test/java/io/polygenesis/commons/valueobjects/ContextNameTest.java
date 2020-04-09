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

package io.polygenesis.commons.valueobjects;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import org.junit.Test;

public class ContextNameTest extends AbstractEqualityTest<ContextName> {

  @Test
  public void shouldSucceedToInstantiate() {
    ContextName contextName = new ContextName("abc");

    assertThat(contextName).isNotNull();
    assertThat(contextName.getText()).isEqualTo("abc");
  }

  @Test
  public void shouldInstantiateDefaultContext() {
    ContextName contextName = ContextName.defaultContext();

    assertThat(contextName).isNotNull();
    assertThat(contextName.getText()).isEqualTo("defaultContext");
  }

  @Override
  public ContextName createObject1() {
    return new ContextName("xxx");
  }

  @Override
  public ContextName createObject2() {
    return new ContextName("yyy");
  }
}
