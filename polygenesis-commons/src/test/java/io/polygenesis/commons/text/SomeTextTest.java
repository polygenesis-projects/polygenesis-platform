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

package io.polygenesis.commons.text;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

/** @author Christos Tsakostas */
public class SomeTextTest {

  @Test
  public void shouldSucceedToInstantiate() {
    SomeText someText = new SomeText("abc");

    assertThat(someText).isNotNull();
    assertThat(someText.getText()).isEqualTo("abc");
  }

  @Test
  public void shouldFailToInstantiateForNullInput() {
    assertThatThrownBy(() -> new SomeText(null)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldFailToInstantiateForEmptyInput() {
    assertThatThrownBy(() -> new SomeText("")).isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Test
  public void equalityShouldSucceedForTwoEqualObjects() {
    SomeText someText1 = createSomeText1();
    SomeText someText2 = createSomeText1();

    assertThat(someText1.equals(someText2)).isTrue();
  }

  @Test
  public void equalityShouldSucceedForSameObject() {
    SomeText someText1 = createSomeText1();

    assertThat(someText1.equals(someText1)).isTrue();
  }

  @Test
  public void equalityShouldFailForNotEqualObjects() {
    SomeText someText1 = createSomeText1();
    SomeText someText2 = createSomeText2();

    assertThat(someText1.equals(someText2)).isFalse();
  }

  @Test
  public void equalityShouldFailForNullInput() {
    SomeText someText1 = createSomeText1();

    assertThat(someText1.equals(null)).isFalse();
  }

  @Test
  public void equalityShouldFailForDifferentObject() {
    SomeText someText1 = createSomeText1();
    Object object = new Object();

    assertThat(someText1.equals(object)).isFalse();
  }

  @Test
  public void hashCodeShouldNotBeNull() {
    SomeText someText1 = createSomeText1();

    assertThat(someText1.hashCode()).isNotNull();
  }

  private SomeText createSomeText1() {
    return new SomeText("xxx");
  }

  private SomeText createSomeText2() {
    return new SomeText("yyy");
  }
}
