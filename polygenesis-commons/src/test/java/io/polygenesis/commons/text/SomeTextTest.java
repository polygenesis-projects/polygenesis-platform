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

import io.polygenesis.commons.test.AbstractEqualityTest;
import org.junit.Test;

/** @author Christos Tsakostas */
public class SomeTextTest extends AbstractEqualityTest<SomeText> {

  @Test
  public void shouldSucceedToInstantiate() {
    SomeText someText = new SomeText("abc");

    assertThat(someText).isNotNull();
    assertThat(someText.getText()).isEqualTo("abc");
  }

  @Test
  public void shouldSucceedForAllUpperCase() {
    SomeText someText = new SomeText("CUSTOMER");

    assertThat(someText.getText()).isEqualTo("customer");
  }

  @Test
  public void shouldConvertUnderscoreToLowerCamel() {
    SomeText someText = new SomeText("hello_world");

    assertThat(someText.getText()).isEqualTo("helloWorld");
  }

  @Test
  public void shouldSucceedForPackageName() {
    SomeText someText = new SomeText("com.oregor.CreateUserRequest");

    assertThat(someText.getOriginal()).isEqualTo("com.oregor.CreateUserRequest");
    assertThat(someText.getText()).isEqualTo("createUserRequest");
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
  @Override
  public SomeText createObject1() {
    return new SomeText("xxx");
  }

  @Override
  public SomeText createObject2() {
    return new SomeText("yyy");
  }
}
