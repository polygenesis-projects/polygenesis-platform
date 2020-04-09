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
import java.util.Set;

public class EnumerationRepresentation extends ClassRepresentation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<EnumerationValueRepresentation> enumerationValueRepresentations;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Enumeration representation.
   *
   * @param packageName the package name
   * @param imports the imports
   * @param annotations the annotations
   * @param description the description
   * @param modifiers the modifiers
   * @param simpleObjectName the simple object name
   * @param fullObjectName the full object name
   * @param staticFieldRepresentations the static field representations
   * @param stateFieldRepresentations the state field representations
   * @param dependencyFieldRepresentations the dependency field representations
   * @param constructorRepresentations the constructor representations
   * @param methodRepresentations the method representations
   * @param enumerationValueRepresentations the enumeration value representations
   */
  public EnumerationRepresentation(
      String packageName,
      Set<String> imports,
      Set<String> annotations,
      String description,
      String modifiers,
      String simpleObjectName,
      String fullObjectName,
      Set<FieldRepresentation> staticFieldRepresentations,
      Set<FieldRepresentation> stateFieldRepresentations,
      Set<FieldRepresentation> dependencyFieldRepresentations,
      Set<ConstructorRepresentation> constructorRepresentations,
      Set<MethodRepresentation> methodRepresentations,
      Set<EnumerationValueRepresentation> enumerationValueRepresentations) {
    super(
        packageName,
        imports,
        annotations,
        description,
        modifiers,
        simpleObjectName,
        fullObjectName,
        staticFieldRepresentations,
        stateFieldRepresentations,
        dependencyFieldRepresentations,
        constructorRepresentations,
        methodRepresentations);
    setEnumerationValueRepresentations(enumerationValueRepresentations);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets enumeration value representations.
   *
   * @return the enumeration value representations
   */
  public Set<EnumerationValueRepresentation> getEnumerationValueRepresentations() {
    return enumerationValueRepresentations;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setEnumerationValueRepresentations(
      Set<EnumerationValueRepresentation> enumerationValueRepresentations) {
    Assertion.isNotNull(
        enumerationValueRepresentations, "enumerationValueRepresentations is required");
    this.enumerationValueRepresentations = enumerationValueRepresentations;
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
    EnumerationRepresentation that = (EnumerationRepresentation) o;
    return Objects.equals(enumerationValueRepresentations, that.enumerationValueRepresentations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), enumerationValueRepresentations);
  }
}
