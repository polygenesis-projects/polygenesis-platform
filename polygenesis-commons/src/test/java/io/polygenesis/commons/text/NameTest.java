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
import io.polygenesis.commons.valueobjects.ObjectName;
import org.junit.Test;

/** @author Christos Tsakostas */
public class NameTest extends AbstractEqualityTest<ObjectName> {

  @Test
  public void shouldSucceedToInstantiate() {
    ObjectName bame = new ObjectName("abc");

    assertThat(bame).isNotNull();
    assertThat(bame.getText()).isEqualTo("abc");
  }

  @Test
  public void shouldSucceedForAllUpperCase() {
    ObjectName bame = new ObjectName("CUSTOMER");

    assertThat(bame.getText()).isEqualTo("customer");
  }

  @Test
  public void shouldConvertUnderscoreToLowerCamel() {
    ObjectName bame = new ObjectName("hello_world");

    assertThat(bame.getText()).isEqualTo("helloWorld");
  }

  @Test
  public void shouldSucceedForPackageName() {
    ObjectName bame = new ObjectName("com.oregor.CreateUserRequest");

    assertThat(bame.getOriginal()).isEqualTo("com.oregor.CreateUserRequest");
    assertThat(bame.getText()).isEqualTo("createUserRequest");
  }

  @Test
  public void shouldFailToInstantiateForNullInput() {
    assertThatThrownBy(() -> new ObjectName(null)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldFailToInstantiateForEmptyInput() {
    assertThatThrownBy(() -> new ObjectName("")).isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Override
  public ObjectName createObject1() {
    return new ObjectName("xxx");
  }

  @Override
  public ObjectName createObject2() {
    return new ObjectName("yyy");
  }
}
