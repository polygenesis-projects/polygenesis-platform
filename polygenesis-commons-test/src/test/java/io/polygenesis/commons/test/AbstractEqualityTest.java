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

package io.polygenesis.commons.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Abstract class for Equality check.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractEqualityTest<T> {

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Test
  public void equalityShouldSucceedForTwoEqualObjects() {
    T object1 = createObject1();
    T object2 = createObject1();

    assertThat(object1.equals(object2)).isTrue();
  }

  @Test
  public void equalityShouldSucceedForSameObject() {
    T object1 = createObject1();

    assertThat(object1.equals(object1)).isTrue();
  }

  @Test
  public void equalityShouldFailForNotEqualObjects() {
    T object1 = createObject1();
    T object2 = createObject2();

    assertThat(object1.equals(object2)).isFalse();
  }

  @Test
  public void equalityShouldFailForNullInput() {
    T object1 = createObject1();

    assertThat(object1.equals(null)).isFalse();
  }

  @Test
  public void equalityShouldFailForDifferentObject() {
    T object1 = createObject1();
    Object object = new Object();

    assertThat(object1.equals(object)).isFalse();
  }

  @Test
  public void hashCodeShouldNotBeNull() {
    T object1 = createObject1();

    assertThat(object1.hashCode()).isNotNull();
  }

  public abstract T createObject1();

  public abstract T createObject2();
}
