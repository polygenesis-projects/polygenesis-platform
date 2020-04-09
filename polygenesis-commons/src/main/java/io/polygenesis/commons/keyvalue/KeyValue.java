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

package io.polygenesis.commons.keyvalue;

import io.polygenesis.commons.assertion.Assertion;

/**
 * Container for Key-Value Pairs.
 *
 * @author Christos Tsakostas
 */
public class KeyValue {

  private Object key;
  private Object value;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new KeyValue.
   *
   * @param key the key
   * @param value the value
   */
  public KeyValue(Object key, Object value) {
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
  public Object getKey() {
    return key;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public Object getValue() {
    return value;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets key.
   *
   * @param key the key
   */
  private void setKey(Object key) {
    Assertion.isNotNull(key, "key is required");
    this.key = key;
  }

  /**
   * Sets value.
   *
   * @param value the value
   */
  private void setValue(Object value) {
    Assertion.isNotNull(value, "value is required");
    this.value = value;
  }
}
