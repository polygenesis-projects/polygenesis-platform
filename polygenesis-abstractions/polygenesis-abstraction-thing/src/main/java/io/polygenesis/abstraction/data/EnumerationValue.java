/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.abstraction.data;

import java.util.Objects;

/**
 * The type Enumeration value.
 *
 * @author Christos Tsakostas
 */
public class EnumerationValue {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final String value;
  private final Boolean initial;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of enumeration value.
   *
   * @param value the value
   * @return the enumeration value
   */
  public static EnumerationValue of(String value) {
    return new EnumerationValue(value, false);
  }

  /**
   * Of initial enumeration value.
   *
   * @param value the value
   * @return the enumeration value
   */
  public static EnumerationValue ofInitial(String value) {
    return new EnumerationValue(value, true);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Enumeration value.
   *
   * @param value the value
   * @param initial the initial
   */
  private EnumerationValue(String value, Boolean initial) {
    this.value = value;
    this.initial = initial;
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
    EnumerationValue that = (EnumerationValue) o;
    return Objects.equals(value, that.value) && Objects.equals(initial, that.initial);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, initial);
  }
}
