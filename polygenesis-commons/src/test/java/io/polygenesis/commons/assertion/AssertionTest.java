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

package io.polygenesis.commons.assertion;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;

/** @author Christos Tsakostas */
public class AssertionTest {

  @Test
  public void shouldFailToInstantiate() throws NoSuchMethodException {
    Constructor<Assertion> constructor = Assertion.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
  }

  // ===============================================================================================

  @Test
  public void shouldSucceedIsNull() {
    Assertion.isNull(null, "is null");
  }

  @Test
  public void shouldFailIsNull() {
    assertThatThrownBy(() -> Assertion.isNull(new Object(), "is null"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================

  @Test
  public void shouldSucceedIsNotNull() {
    Assertion.isNotNull(new Object(), "is not null");
  }

  @Test
  public void shouldFailIsNotNull() {
    assertThatThrownBy(() -> Assertion.isNotNull(null, "is not null"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================

  @Test
  public void shouldSucceedIsNotEmpty() {
    Assertion.isNotEmpty("xxx", "not empty");
  }

  @Test
  public void shouldFailIsNotEmptyForNullInput() {
    assertThatThrownBy(() -> Assertion.isNotEmpty(null, "not empty"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldFailIsNotEmptyForEmptyInput() {
    assertThatThrownBy(() -> Assertion.isNotEmpty("", "not empty"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================

  @Test
  public void shouldSucceedIsTrue() {
    Assertion.isTrue(1 == 1, "wrong condition");
  }

  @Test
  public void shouldFailIsTrue() {
    assertThatThrownBy(() -> Assertion.isTrue(1 == 2, "wrong condition"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================

  @Test
  public void shouldSucceedIsFalse() {
    Assertion.isFalse(1 == 2, "wrong condition");
  }

  @Test
  public void shouldFailIsFalse() {
    assertThatThrownBy(() -> Assertion.isFalse(1 == 1, "wrong condition"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
