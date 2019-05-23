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

package io.polygenesis.abstraction.data.validation;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

/**
 * The type Maximum length validation.
 *
 * @author Christos Tsakostas
 */
public class MaximumLengthValidation extends Validation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Integer maximumLength;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Maximum length validation.
   *
   * @param maximumLength the maximum length
   */
  public MaximumLengthValidation(Integer maximumLength) {
    super(ValidationType.MAXIMUM_LENGTH);
    setMaximumLength(maximumLength);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets maximum length.
   *
   * @return the maximum length
   */
  public Integer getMaximumLength() {
    return maximumLength;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets maximum length.
   *
   * @param maximumLength the maximum length
   */
  private void setMaximumLength(Integer maximumLength) {
    Assertion.isNotNull(maximumLength, "maximumLength is required");
    this.maximumLength = maximumLength;
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
    if (!super.equals(o)) {
      return false;
    }
    MaximumLengthValidation that = (MaximumLengthValidation) o;
    return Objects.equals(maximumLength, that.maximumLength);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), maximumLength);
  }
}
