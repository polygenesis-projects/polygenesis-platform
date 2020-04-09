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

package io.polygenesis.commons.assertion;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;

public class AssertionTest {

  /**
   * Should fail to instantiate.
   *
   * @throws NoSuchMethodException the no such method exception
   */
  @Test
  public void shouldFailToInstantiate() throws NoSuchMethodException {
    Constructor<Assertion> constructor = Assertion.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
  }

  // ===============================================================================================

  /** Should succeed is null. */
  @Test
  public void shouldSucceedIsNull() {
    assertThatCode(() -> Assertion.isNull(null, "is null")).doesNotThrowAnyException();
  }

  /** Should fail is null. */
  @Test
  public void shouldFailIsNull() {
    assertThatThrownBy(() -> Assertion.isNull(new Object(), "is null"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================

  /** Should succeed is not null. */
  @Test
  public void shouldSucceedIsNotNull() {
    assertThatCode(() -> Assertion.isNotNull(new Object(), "is not null"))
        .doesNotThrowAnyException();
  }

  /** Should fail is not null. */
  @Test
  public void shouldFailIsNotNull() {
    assertThatThrownBy(() -> Assertion.isNotNull(null, "is not null"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================

  /** Should succeed is not empty. */
  @Test
  public void shouldSucceedIsNotEmpty() {
    assertThatCode(() -> Assertion.isNotEmpty("xxx", "not empty")).doesNotThrowAnyException();
  }

  /** Should fail is not empty for null input. */
  @Test
  public void shouldFailIsNotEmptyForNullInput() {
    assertThatThrownBy(() -> Assertion.isNotEmpty(null, "not empty"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /** Should fail is not empty for empty input. */
  @Test
  public void shouldFailIsNotEmptyForEmptyInput() {
    assertThatThrownBy(() -> Assertion.isNotEmpty("", "not empty"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================

  /** Should succeed is true. */
  @Test
  public void shouldSucceedIsTrue() {
    assertThatCode(() -> Assertion.isTrue(1 == 1, "wrong condition")).doesNotThrowAnyException();
  }

  /** Should fail is true. */
  @Test
  public void shouldFailIsTrue() {
    assertThatThrownBy(() -> Assertion.isTrue(1 == 2, "wrong condition"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================

  /** Should succeed is false. */
  @Test
  public void shouldSucceedIsFalse() {
    assertThatCode(() -> Assertion.isFalse(1 == 2, "wrong condition")).doesNotThrowAnyException();
  }

  /** Should fail is false. */
  @Test
  public void shouldFailIsFalse() {
    assertThatThrownBy(() -> Assertion.isFalse(1 == 1, "wrong condition"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
