/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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
import java.util.stream.Collectors;

/**
 * The type Class representation.
 *
 * @author Christos Tsakostas
 */
public class ClassRepresentation extends AbstractObjectRepresentation {

  private Set<FieldRepresentation> fieldRepresentations;
  private Set<ConstructorRepresentation> constructorRepresentations;
  private Set<MethodRepresentation> methodRepresentations;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Class representation.
   *
   * @param packageName the package name
   * @param imports the imports
   * @param annotations the annotations
   * @param description the description
   * @param modifiers the modifiers
   * @param simpleObjectName the simple object name
   * @param fullObjectName the full object name
   * @param fieldRepresentations the field representations
   * @param constructorRepresentations the constructor representations
   * @param methodRepresentations the method representations
   */
  public ClassRepresentation(
      String packageName,
      Set<String> imports,
      Set<String> annotations,
      String description,
      String modifiers,
      String simpleObjectName,
      String fullObjectName,
      Set<FieldRepresentation> fieldRepresentations,
      Set<ConstructorRepresentation> constructorRepresentations,
      Set<MethodRepresentation> methodRepresentations) {
    super(
        packageName,
        imports,
        annotations,
        description,
        modifiers,
        simpleObjectName,
        fullObjectName);
    setFieldRepresentations(fieldRepresentations);
    setConstructorRepresentations(constructorRepresentations);
    setMethodRepresentations(methodRepresentations);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets field representations.
   *
   * @return the field representations
   */
  public Set<FieldRepresentation> getFieldRepresentations() {
    return fieldRepresentations;
  }

  /**
   * Gets constructor representations.
   *
   * @return the constructor representations
   */
  public Set<ConstructorRepresentation> getConstructorRepresentations() {
    return constructorRepresentations;
  }

  /**
   * Gets method representations.
   *
   * @return the method representations
   */
  public Set<MethodRepresentation> getMethodRepresentations() {
    return methodRepresentations;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets getters.
   *
   * @return the getters
   */
  public Set<MethodRepresentation> getGetters() {
    return methodRepresentations
        .stream()
        .filter(
            methodRepresentation -> methodRepresentation.getMethodType().equals(MethodType.GETTER))
        .collect(Collectors.toSet());
  }

  /**
   * Gets setters.
   *
   * @return the setters
   */
  public Set<MethodRepresentation> getSetters() {
    return methodRepresentations
        .stream()
        .filter(
            methodRepresentation -> methodRepresentation.getMethodType().equals(MethodType.SETTER))
        .collect(Collectors.toSet());
  }

  /**
   * Gets guards.
   *
   * @return the guards
   */
  public Set<MethodRepresentation> getGuards() {
    return methodRepresentations
        .stream()
        .filter(
            methodRepresentation -> methodRepresentation.getMethodType().equals(MethodType.GUARD))
        .collect(Collectors.toSet());
  }

  /**
   * Gets anys.
   *
   * @return the anys
   */
  public Set<MethodRepresentation> getAnys() {
    return methodRepresentations
        .stream()
        .filter(methodRepresentation -> methodRepresentation.getMethodType().equals(MethodType.ANY))
        .collect(Collectors.toSet());
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets field representations.
   *
   * @param fieldRepresentations the field representations
   */
  public void setFieldRepresentations(Set<FieldRepresentation> fieldRepresentations) {
    this.fieldRepresentations = fieldRepresentations;
  }

  /**
   * Sets constructor representations.
   *
   * @param constructorRepresentations the constructor representations
   */
  public void setConstructorRepresentations(
      Set<ConstructorRepresentation> constructorRepresentations) {
    this.constructorRepresentations = constructorRepresentations;
  }

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
    ClassRepresentation that = (ClassRepresentation) o;
    return Objects.equals(fieldRepresentations, that.fieldRepresentations)
        && Objects.equals(constructorRepresentations, that.constructorRepresentations)
        && Objects.equals(methodRepresentations, that.methodRepresentations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(), fieldRepresentations, constructorRepresentations, methodRepresentations);
  }
}
