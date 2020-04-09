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

package io.polygenesis.generators.angular.legacy.skeletons.model;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.representations.code.FieldRepresentation;
import java.util.Objects;
import java.util.Set;

/**
 * The type Model representation.
 *
 * @author Christos Tsakostas
 */
public class ModelRepresentation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private String name;
  private Set<FieldRepresentation> fieldRepresentations;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Model representation.
   *
   * @param name the name
   * @param fieldRepresentations the field representations
   */
  public ModelRepresentation(String name, Set<FieldRepresentation> fieldRepresentations) {
    setName(name);
    setFieldRepresentations(fieldRepresentations);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets field representations.
   *
   * @return the field representations
   */
  public Set<FieldRepresentation> getFieldRepresentations() {
    return fieldRepresentations;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(String name) {
    this.name = name;
  }

  /**
   * Sets field representations.
   *
   * @param fieldRepresentations the field representations
   */
  private void setFieldRepresentations(Set<FieldRepresentation> fieldRepresentations) {
    Assertion.isNotNull(fieldRepresentations, "fieldRepresentations is required");
    this.fieldRepresentations = fieldRepresentations;
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
    ModelRepresentation that = (ModelRepresentation) o;
    return Objects.equals(fieldRepresentations, that.fieldRepresentations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fieldRepresentations);
  }
}
