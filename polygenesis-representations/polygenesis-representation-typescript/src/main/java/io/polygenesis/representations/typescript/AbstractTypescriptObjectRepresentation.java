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

package io.polygenesis.representations.typescript;

import com.oregor.ddd4j.check.assertion.Assertion;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The type Abstract typescript object representation.
 *
 * @author Christos Tsakostas
 */
public class AbstractTypescriptObjectRepresentation {

  private Map<Set<String>, String> importObjects;
  private Map<String, String> importAliases;
  private String description;
  private String simpleObjectName;
  /** Full object Name With Extends and Implements, if any. */
  private String fullObjectName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract typescript object representation.
   *
   * @param importObjects the import objects
   * @param importAliases the import aliases
   * @param description the description
   * @param simpleObjectName the simple object name
   * @param fullObjectName the full object name
   */
  public AbstractTypescriptObjectRepresentation(
      Map<Set<String>, String> importObjects,
      Map<String, String> importAliases,
      String description,
      String simpleObjectName,
      String fullObjectName) {
    setImportObjects(importObjects);
    setImportAliases(importAliases);
    setDescription(description);
    setSimpleObjectName(simpleObjectName);
    setFullObjectName(fullObjectName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets import objects.
   *
   * @return the import objects
   */
  public Map<Set<String>, String> getImportObjects() {
    return importObjects;
  }

  /**
   * Gets import aliases.
   *
   * @return the import aliases
   */
  public Map<String, String> getImportAliases() {
    return importAliases;
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
   * Sets import objects.
   *
   * @param importObjects the import objects
   */
  private void setImportObjects(Map<Set<String>, String> importObjects) {
    Assertion.isNotNull(importObjects, "importObjects is required.");
    this.importObjects = importObjects;
  }

  /**
   * Sets import aliases.
   *
   * @param importAliases the import aliases
   */
  private void setImportAliases(Map<String, String> importAliases) {
    Assertion.isNotNull(importAliases, "importAliases is required.");
    this.importAliases = importAliases;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  private void setDescription(String description) {
    Assertion.isNotNull(description, "description is required.");
    this.description = description;
  }

  /**
   * Sets simple object name.
   *
   * @param simpleObjectName the simple object name
   */
  private void setSimpleObjectName(String simpleObjectName) {
    Assertion.isNotNull(simpleObjectName, "simpleObjectName is required.");
    this.simpleObjectName = simpleObjectName;
  }

  /**
   * Sets full object name.
   *
   * @param fullObjectName the full object name
   */
  private void setFullObjectName(String fullObjectName) {
    Assertion.isNotNull(fullObjectName, "fullObjectName is required.");
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
    AbstractTypescriptObjectRepresentation that = (AbstractTypescriptObjectRepresentation) o;
    return Objects.equals(importObjects, that.importObjects)
        && Objects.equals(importAliases, that.importAliases)
        && Objects.equals(description, that.description)
        && Objects.equals(simpleObjectName, that.simpleObjectName)
        && Objects.equals(fullObjectName, that.fullObjectName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        importObjects, importAliases, description, simpleObjectName, fullObjectName);
  }
}
