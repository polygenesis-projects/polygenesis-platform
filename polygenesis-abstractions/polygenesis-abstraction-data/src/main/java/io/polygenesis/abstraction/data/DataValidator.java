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

package io.polygenesis.abstraction.data;

import io.polygenesis.abstraction.data.validation.MaximumLengthValidation;
import io.polygenesis.abstraction.data.validation.RequiredValidation;
import io.polygenesis.abstraction.data.validation.Validation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Data validator.
 *
 * @author Christos Tsakostas
 */
public class DataValidator {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Empty data validator.
   *
   * @return the data validator
   */
  public static DataValidator empty() {
    return new DataValidator(new LinkedHashSet<>());
  }

  /**
   * Default for string data validator.
   *
   * @return the data validator
   */
  public static DataValidator defaultForString() {
    return new DataValidator(
        new LinkedHashSet<>(
            Arrays.asList(new RequiredValidation(), new MaximumLengthValidation(100))));
  }

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<Validation> validations;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data validator.
   *
   * @param validations the validations
   */
  public DataValidator(Set<Validation> validations) {
    setValidations(validations);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets validations.
   *
   * @return the validations
   */
  public Set<Validation> getValidations() {
    return validations;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets validations.
   *
   * @param validations the validations
   */
  private void setValidations(Set<Validation> validations) {
    this.validations = validations;
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
    DataValidator that = (DataValidator) o;
    return Objects.equals(validations, that.validations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(validations);
  }
}
