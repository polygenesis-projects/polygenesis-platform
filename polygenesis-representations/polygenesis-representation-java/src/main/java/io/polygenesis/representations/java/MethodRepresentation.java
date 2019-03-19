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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.representations.commons.ParameterRepresentation;
import java.util.Objects;
import java.util.Set;

/**
 * The type Method representation.
 *
 * @author Christos Tsakostas
 */
public class MethodRepresentation {

  private MethodRepresentationType methodRepresentationType;
  private Set<String> imports;
  private Set<String> annotations;
  private String description;
  private String modifiers;
  private String methodName;
  private Set<ParameterRepresentation> parameterRepresentations;
  private String returnValue;
  private String implementation;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Method representation.
   *
   * @param methodRepresentationType the method type
   * @param imports the imports
   * @param annotations the annotations
   * @param description the description
   * @param modifiers the modifiers
   * @param methodName the method name
   * @param parameterRepresentations the parameter representations
   * @param returnValue the return value
   * @param implementation the implementation
   */
  public MethodRepresentation(
      MethodRepresentationType methodRepresentationType,
      Set<String> imports,
      Set<String> annotations,
      String description,
      String modifiers,
      String methodName,
      Set<ParameterRepresentation> parameterRepresentations,
      String returnValue,
      String implementation) {
    setMethodRepresentationType(methodRepresentationType);
    setImports(imports);
    setAnnotations(annotations);
    setDescription(description);
    setModifiers(modifiers);
    setMethodName(methodName);
    setParameterRepresentations(parameterRepresentations);
    setReturnValue(returnValue);
    setImplementation(implementation);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * Change implementation to.
   *
   * @param implementation the implementation
   */
  public void changeImplementationTo(String implementation) {
    setImplementation(implementation);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets method type.
   *
   * @return the method type
   */
  public MethodRepresentationType getMethodRepresentationType() {
    return methodRepresentationType;
  }

  /**
   * Gets imports.
   *
   * @return the imports
   */
  public Set<String> getImports() {
    return imports;
  }

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
   * Gets method name.
   *
   * @return the method name
   */
  public String getMethodName() {
    return methodName;
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
   * Gets return value.
   *
   * @return the return value
   */
  public String getReturnValue() {
    return returnValue;
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
   * Sets method type.
   *
   * @param methodRepresentationType the method type
   */
  private void setMethodRepresentationType(MethodRepresentationType methodRepresentationType) {
    Assertion.isNotNull(methodRepresentationType, "methodRepresentationType is required");
    this.methodRepresentationType = methodRepresentationType;
  }

  /**
   * Sets imports.
   *
   * @param imports the imports
   */
  private void setImports(Set<String> imports) {
    Assertion.isNotNull(imports, "imports is required");
    this.imports = imports;
  }

  /**
   * Sets annotations.
   *
   * @param annotations the annotations
   */
  private void setAnnotations(Set<String> annotations) {
    Assertion.isNotNull(annotations, "annotations is required");
    this.annotations = annotations;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  private void setDescription(String description) {
    Assertion.isNotNull(description, "description is required");
    this.description = description;
  }

  /**
   * Sets modifiers.
   *
   * @param modifiers the modifiers
   */
  private void setModifiers(String modifiers) {
    Assertion.isNotNull(modifiers, "modifiers is required");
    this.modifiers = modifiers;
  }

  /**
   * Sets method name.
   *
   * @param methodName the method name
   */
  private void setMethodName(String methodName) {
    Assertion.isNotNull(methodName, "methodName is required");
    this.methodName = methodName;
  }

  /**
   * Sets parameter representations.
   *
   * @param parameterRepresentations the parameter representations
   */
  private void setParameterRepresentations(Set<ParameterRepresentation> parameterRepresentations) {
    Assertion.isNotNull(parameterRepresentations, "parameterRepresentations is required");
    this.parameterRepresentations = parameterRepresentations;
  }

  /**
   * Sets return value.
   *
   * @param returnValue the return value
   */
  private void setReturnValue(String returnValue) {
    Assertion.isNotNull(returnValue, "returnValue is required");
    this.returnValue = returnValue;
  }

  /**
   * Sets implementation.
   *
   * @param implementation the implementation
   */
  private void setImplementation(String implementation) {
    Assertion.isNotNull(implementation, "implementation is required");
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
    MethodRepresentation that = (MethodRepresentation) o;
    return methodRepresentationType == that.methodRepresentationType
        && Objects.equals(imports, that.imports)
        && Objects.equals(annotations, that.annotations)
        && Objects.equals(description, that.description)
        && Objects.equals(modifiers, that.modifiers)
        && Objects.equals(methodName, that.methodName)
        && Objects.equals(parameterRepresentations, that.parameterRepresentations)
        && Objects.equals(returnValue, that.returnValue)
        && Objects.equals(implementation, that.implementation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        methodRepresentationType,
        imports,
        annotations,
        description,
        modifiers,
        methodName,
        parameterRepresentations,
        returnValue,
        implementation);
  }
}
