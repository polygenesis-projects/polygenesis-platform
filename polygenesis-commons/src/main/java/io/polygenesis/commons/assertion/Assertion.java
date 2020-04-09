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

public final class Assertion {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private Assertion() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Is null.
   *
   * @param object the object
   * @param message the message
   */
  public static void isNull(Object object, String message) {
    if (object != null) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Is not null.
   *
   * @param object the object
   * @param message the message
   */
  public static void isNotNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Is not empty.
   *
   * @param stringToCheck the string to check
   * @param message the message
   */
  public static void isNotEmpty(String stringToCheck, String message) {
    if (stringToCheck == null || stringToCheck.trim().equals("")) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Is true.
   *
   * @param expression the expression
   * @param message the message
   */
  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Is false.
   *
   * @param condition the condition
   * @param message the message
   */
  public static void isFalse(boolean condition, String message) {
    Assertion.isTrue(!condition, message);
  }
}
