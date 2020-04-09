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

import java.util.Objects;
import java.util.Set;

public class ConstructorRepresentation {

  private Set<String> annotations;
  private String description;
  private String modifiers;
  private Set<ParameterRepresentation> parameterRepresentations;
  private String implementation;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Constructor representation.
   *
   * @param annotations the annotations
   * @param description the description
   * @param modifiers the modifiers
   * @param parameterRepresentations the parameter representations
   * @param implementation the implementation
   */
  public ConstructorRepresentation(
      Set<String> annotations,
      String description,
      String modifiers,
      Set<ParameterRepresentation> parameterRepresentations,
      String implementation) {
    setAnnotations(annotations);
    setDescription(description);
    setModifiers(modifiers);
    setParameterRepresentations(parameterRepresentations);
    setImplementation(implementation);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets annotations.
   *
   * @return the annotations
   */
  public Set<String> getAnnotations() {
    return annotations;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets modifiers.
   *
   * @return the modifiers
   */
  public String getModifiers() {
    return modifiers;
  }

  /**
   * Gets parameter representations.
   *
   * @return the parameter representations
   */
  public Set<ParameterRepresentation> getParameterRepresentations() {
    return parameterRepresentations;
  }

  /**
   * Gets implementation.
   *
   * @return the implementation
   */
  public String getImplementation() {
    return implementation;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets annotations.
   *
   * @param annotations the annotations
   */
  public void setAnnotations(Set<String> annotations) {
    this.annotations = annotations;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets modifiers.
   *
   * @param modifiers the modifiers
   */
  public void setModifiers(String modifiers) {
    this.modifiers = modifiers;
  }

  /**
   * Sets parameter representations.
   *
   * @param parameterRepresentations the parameter representations
   */
  public void setParameterRepresentations(Set<ParameterRepresentation> parameterRepresentations) {
    this.parameterRepresentations = parameterRepresentations;
  }

  /**
   * Sets implementation.
   *
   * @param implementation the implementation
   */
  public void setImplementation(String implementation) {
    this.implementation = implementation;
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
    ConstructorRepresentation that = (ConstructorRepresentation) o;
    return Objects.equals(annotations, that.annotations)
        && Objects.equals(description, that.description)
        && Objects.equals(modifiers, that.modifiers)
        && Objects.equals(parameterRepresentations, that.parameterRepresentations)
        && Objects.equals(implementation, that.implementation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        annotations, description, modifiers, parameterRepresentations, implementation);
  }
}
