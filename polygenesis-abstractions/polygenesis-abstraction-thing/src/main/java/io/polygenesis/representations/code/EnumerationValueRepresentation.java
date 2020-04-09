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

package io.polygenesis.representations.code;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

public class EnumerationValueRepresentation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String value;
  private Boolean initial;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Enumeration value representation.
   *
   * @param value the value
   * @param initial the initial
   */
  public EnumerationValueRepresentation(String value, Boolean initial) {
    setValue(value);
    setInitial(initial);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets value.
   *
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * Gets initial.
   *
   * @return the initial
   */
  public Boolean getInitial() {
    return initial;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setValue(String value) {
    Assertion.isNotNull(value, "value is required");
    this.value = value;
  }

  private void setInitial(Boolean initial) {
    Assertion.isNotNull(initial, "initial is required");
    this.initial = initial;
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
    EnumerationValueRepresentation that = (EnumerationValueRepresentation) o;
    return Objects.equals(value, that.value) && Objects.equals(initial, that.initial);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, initial);
  }
}
