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

package io.polygenesis.representations.java;

import java.util.Objects;
import java.util.Set;

/**
 * The type Interface representation.
 *
 * @author Christos Tsakostas
 */
public class InterfaceRepresentation extends AbstractObjectRepresentation {

  private Set<MethodRepresentation> methodRepresentations;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Interface representation.
   *
   * @param packageName the package name
   * @param imports the imports
   * @param annotations the annotations
   * @param description the description
   * @param modifiers the modifiers
   * @param simpleObjectName the simple object name
   * @param fullObjectName the full object name
   * @param methodRepresentations the method representations
   */
  public InterfaceRepresentation(
      String packageName,
      Set<String> imports,
      Set<String> annotations,
      String description,
      String modifiers,
      String simpleObjectName,
      String fullObjectName,
      Set<MethodRepresentation> methodRepresentations) {
    super(
        packageName,
        imports,
        annotations,
        description,
        modifiers,
        simpleObjectName,
        fullObjectName);
    setMethodRepresentations(methodRepresentations);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets method representations.
   *
   * @return the method representations
   */
  public Set<MethodRepresentation> getMethodRepresentations() {
    return methodRepresentations;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets method representations.
   *
   * @param methodRepresentations the method representations
   */
  public void setMethodRepresentations(Set<MethodRepresentation> methodRepresentations) {
    this.methodRepresentations = methodRepresentations;
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
    InterfaceRepresentation that = (InterfaceRepresentation) o;
    return Objects.equals(methodRepresentations, that.methodRepresentations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), methodRepresentations);
  }
}
