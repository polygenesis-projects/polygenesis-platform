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

package io.polygenesis.models.state;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/** @author Christos Tsakostas */
public class StoreTest {

  @Test
  public void shouldSucceedToInstantiate() {
    Store store = new Store(new Feature("abc"));

    assertThat(store).isNotNull();
    assertThat(store.getFeature()).isEqualTo(new Feature("abc"));
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Test
  public void equalityShouldSucceedForTwoEqualObjects() {
    Store store1 = createStore1();
    Store store2 = createStore1();

    assertThat(store1.equals(store2)).isTrue();
  }

  @Test
  public void equalityShouldSucceedForSameObject() {
    Store store1 = createStore1();

    assertThat(store1.equals(store1)).isTrue();
  }

  @Test
  public void equalityShouldFailForNotEqualObjects() {
    Store store1 = createStore1();
    Store store2 = createStore2();

    assertThat(store1.equals(store2)).isFalse();
  }

  @Test
  public void equalityShouldFailForNullInput() {
    Store store1 = createStore1();

    assertThat(store1.equals(null)).isFalse();
  }

  @Test
  public void equalityShouldFailForDifferentObject() {
    Store store1 = createStore1();
    Object object = new Object();

    assertThat(store1.equals(object)).isFalse();
  }

  @Test
  public void hashCodeShouldNotBeNull() {
    Store store1 = createStore1();

    assertThat(store1.hashCode()).isNotNull();
  }

  private Store createStore1() {
    return new Store(new Feature("xxx"));
  }

  private Store createStore2() {
    return new Store(new Feature("yyy"));
  }
}
