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

package io.polygenesis.core.data.validation;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

/**
 * The type Validation.
 *
 * @author Christos Tsakostas
 */
public abstract class Validation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ValidationType validationType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Validation.
   *
   * @param validationType the validation type
   */
  protected Validation(ValidationType validationType) {
    setValidationType(validationType);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets validation type.
   *
   * @return the validation type
   */
  public ValidationType getValidationType() {
    return validationType;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets validation type.
   *
   * @param validationType the validation type
   */
  public void setValidationType(ValidationType validationType) {
    Assertion.isNotNull(validationType, "validationType is required");
    this.validationType = validationType;
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
    Validation that = (Validation) o;
    return validationType == that.validationType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(validationType);
  }
}
