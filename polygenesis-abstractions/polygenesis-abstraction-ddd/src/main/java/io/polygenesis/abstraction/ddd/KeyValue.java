/*-
 * ==========================LICENSE_START=================================
 * STATICFY
 * ========================================================================
 * Copyright (C) 2020 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.abstraction.ddd;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

/**
 * The type Key value.
 *
 * @author Christos Tsakostas
 */
public class KeyValue {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String key;
  private String value;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Key value.
   *
   * @param key the key
   * @param value the value
   */
  public KeyValue(String key, String value) {
    setKey(key);
    setValue(value);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets key.
   *
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public String getValue() {
    return value;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets key.
   *
   * @param key the key
   */
  public void setKey(String key) {
    Assertion.isNotNull(key, "key is required");
    this.key = key.replace("--", "");
  }

  /**
   * Sets value.
   *
   * @param value the value
   */
  public void setValue(String value) {
    this.value = value;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeyValue keyValue = (KeyValue) o;
    return Objects.equals(key, keyValue.key) && Objects.equals(value, keyValue.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value);
  }
}
