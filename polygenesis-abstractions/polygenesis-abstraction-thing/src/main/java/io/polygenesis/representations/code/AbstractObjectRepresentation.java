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

import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;
import java.util.Set;

/**
 * Base class for Java Objects representations.
 *
 * <p>Every Java object consists of:
 *
 * <ul>
 *   <li>package
 *   <li>imports
 * </ul>
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractObjectRepresentation {

  private String packageName;
  private Set<String> imports;
  private Set<String> annotations;
  private String description;
  private String modifiers;
  private String simpleObjectName;
  /** Full object Name With Extends and Implements, if any. */
  private String fullObjectName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract object representation.
   *
   * @param packageName the package name
   * @param imports the imports
   * @param annotations the annotations
   * @param description the description
   * @param modifiers the modifiers
   * @param simpleObjectName the simple object name
   * @param fullObjectName the full object name
   */
  public AbstractObjectRepresentation(
      String packageName,
      Set<String> imports,
      Set<String> annotations,
      String description,
      String modifiers,
      String simpleObjectName,
      String fullObjectName) {
    setPackageName(packageName);
    setImports(imports);
    setAnnotations(annotations);
    setDescription(description);
    setModifiers(modifiers);
    setSimpleObjectName(simpleObjectName);
    setFullObjectName(fullObjectName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public String getPackageName() {
    return packageName;
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
   * Gets simple object name.
   *
   * @return the simple object name
   */
  public String getSimpleObjectName() {
    return simpleObjectName;
  }

  /**
   * Gets full object name.
   *
   * @return the full object name
   */
  public String getFullObjectName() {
    return fullObjectName;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(String packageName) {
    this.packageName = packageName;
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
    this.annotations = annotations;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  private void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets modifiers.
   *
   * @param modifiers the modifiers
   */
  private void setModifiers(String modifiers) {
    this.modifiers = modifiers;
  }

  /**
   * Sets simple object name.
   *
   * @param simpleObjectName the simple object name
   */
  private void setSimpleObjectName(String simpleObjectName) {
    this.simpleObjectName = simpleObjectName;
  }

  /**
   * Sets full object name.
   *
   * @param fullObjectName the full object name
   */
  private void setFullObjectName(String fullObjectName) {
    this.fullObjectName = fullObjectName;
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
    AbstractObjectRepresentation that = (AbstractObjectRepresentation) o;
    return Objects.equals(packageName, that.packageName)
        && Objects.equals(imports, that.imports)
        && Objects.equals(annotations, that.annotations)
        && Objects.equals(description, that.description)
        && Objects.equals(modifiers, that.modifiers)
        && Objects.equals(simpleObjectName, that.simpleObjectName)
        && Objects.equals(fullObjectName, that.fullObjectName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        packageName,
        imports,
        annotations,
        description,
        modifiers,
        simpleObjectName,
        fullObjectName);
  }
}
