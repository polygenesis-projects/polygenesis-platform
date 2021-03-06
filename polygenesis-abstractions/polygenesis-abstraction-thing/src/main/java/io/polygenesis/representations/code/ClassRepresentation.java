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

package io.polygenesis.representations.code;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.commons.assertion.Assertion;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Class representation.
 *
 * @author Christos Tsakostas
 */
public class ClassRepresentation extends AbstractObjectRepresentation {

  private Set<FieldRepresentation> staticFieldRepresentations;
  private Set<FieldRepresentation> stateFieldRepresentations;
  private Set<FieldRepresentation> dependencyFieldRepresentations;
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
   * @param staticFieldRepresentations the static field representations
   * @param stateFieldRepresentations the state field representations
   * @param dependencyFieldRepresentations the dependency field representations
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
      Set<FieldRepresentation> staticFieldRepresentations,
      Set<FieldRepresentation> stateFieldRepresentations,
      Set<FieldRepresentation> dependencyFieldRepresentations,
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
    setStaticFieldRepresentations(staticFieldRepresentations);
    setStateFieldRepresentations(stateFieldRepresentations);
    setDependencyFieldRepresentations(dependencyFieldRepresentations);
    setConstructorRepresentations(constructorRepresentations);
    setMethodRepresentations(methodRepresentations);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets static field representations.
   *
   * @return the static field representations
   */
  public Set<FieldRepresentation> getStaticFieldRepresentations() {
    return staticFieldRepresentations;
  }

  /**
   * Gets field representations.
   *
   * @return the field representations
   */
  public Set<FieldRepresentation> getStateFieldRepresentations() {
    return stateFieldRepresentations;
  }

  /**
   * Gets dependency field representations.
   *
   * @return the dependency field representations
   */
  public Set<FieldRepresentation> getDependencyFieldRepresentations() {
    return dependencyFieldRepresentations;
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
   * Gets method representations by.
   *
   * @param methodRepresentationType the method representation type
   * @return the method representations by
   */
  public Set<MethodRepresentation> getMethodRepresentationsBy(String methodRepresentationType) {
    return methodRepresentations
        .stream()
        .filter(
            methodRepresentation ->
                methodRepresentation
                    .getMethodRepresentationType()
                    .equals(MethodRepresentationType.valueOf(methodRepresentationType)))
        .collect(toCollection(LinkedHashSet::new));
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets static field representations.
   *
   * @param staticFieldRepresentations the static field representations
   */
  private void setStaticFieldRepresentations(Set<FieldRepresentation> staticFieldRepresentations) {
    Assertion.isNotNull(staticFieldRepresentations, "staticFieldRepresentations is required");
    this.staticFieldRepresentations = staticFieldRepresentations;
  }

  /**
   * Sets field representations.
   *
   * @param stateFieldRepresentations the field representations
   */
  private void setStateFieldRepresentations(Set<FieldRepresentation> stateFieldRepresentations) {
    Assertion.isNotNull(stateFieldRepresentations, "stateFieldRepresentations is required");
    this.stateFieldRepresentations = stateFieldRepresentations;
  }

  /**
   * Sets dependency field representations.
   *
   * @param dependencyFieldRepresentations the dependency field representations
   */
  public void setDependencyFieldRepresentations(
      Set<FieldRepresentation> dependencyFieldRepresentations) {
    Assertion.isNotNull(
        dependencyFieldRepresentations, "dependencyFieldRepresentations is required");
    this.dependencyFieldRepresentations = dependencyFieldRepresentations;
  }

  /**
   * Sets constructor representations.
   *
   * @param constructorRepresentations the constructor representations
   */
  private void setConstructorRepresentations(
      Set<ConstructorRepresentation> constructorRepresentations) {
    Assertion.isNotNull(constructorRepresentations, "constructorRepresentations is required");
    this.constructorRepresentations = constructorRepresentations;
  }

  /**
   * Sets method representations.
   *
   * @param methodRepresentations the method representations
   */
  private void setMethodRepresentations(Set<MethodRepresentation> methodRepresentations) {
    Assertion.isNotNull(methodRepresentations, "methodRepresentations is required");
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
    return Objects.equals(staticFieldRepresentations, that.staticFieldRepresentations)
        && Objects.equals(stateFieldRepresentations, that.stateFieldRepresentations)
        && Objects.equals(dependencyFieldRepresentations, that.dependencyFieldRepresentations)
        && Objects.equals(constructorRepresentations, that.constructorRepresentations)
        && Objects.equals(methodRepresentations, that.methodRepresentations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(),
        staticFieldRepresentations,
        stateFieldRepresentations,
        dependencyFieldRepresentations,
        constructorRepresentations,
        methodRepresentations);
  }
}
