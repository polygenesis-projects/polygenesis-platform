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

package io.polygenesis.abstraction.data.validation;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

public class MinimumLengthValidation extends Validation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Integer minimumLength;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Minimum length validation.
   *
   * @param minimumLength the minimum length
   */
  public MinimumLengthValidation(Integer minimumLength) {
    super(ValidationType.MINIMUM_LENGTH);
    setMinimumLength(minimumLength);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets minimum length.
   *
   * @return the minimum length
   */
  public Integer getMinimumLength() {
    return minimumLength;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets minimum length.
   *
   * @param minimumLength the minimum length
   */
  public void setMinimumLength(Integer minimumLength) {
    Assertion.isNotNull(minimumLength, "minimumLength is required");
    this.minimumLength = minimumLength;
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
    MinimumLengthValidation that = (MinimumLengthValidation) o;
    return Objects.equals(minimumLength, that.minimumLength);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), minimumLength);
  }
}
