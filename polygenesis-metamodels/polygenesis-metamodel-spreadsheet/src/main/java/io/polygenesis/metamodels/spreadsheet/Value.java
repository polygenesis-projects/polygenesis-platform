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

package io.polygenesis.metamodels.spreadsheet;

import io.polygenesis.commons.assertion.Assertion;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Value.
 *
 * @author Christos Tsakostas
 */
public class Value implements Serializable {

  private static final long serialVersionUID = 1L;

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Object value;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value.
   *
   * @param value the value
   */
  public Value(Object value) {
    setValue(value);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  public Object getValue() {
    return value;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets value.
   *
   * @param value the value
   */
  private void setValue(Object value) {
    Assertion.isNotNull(value, "value is required");
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
    Value value1 = (Value) o;
    return Objects.equals(value, value1.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
