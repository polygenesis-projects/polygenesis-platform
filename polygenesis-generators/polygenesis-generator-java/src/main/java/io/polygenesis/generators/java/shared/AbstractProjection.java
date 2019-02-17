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

package io.polygenesis.generators.java.shared;

import java.util.Set;

/**
 * The type Abstract projection.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractProjection {

  private String packageName;
  private Set<String> imports;
  private String description;
  private String objectName;
  private String objectNameWithOptionalExtendsImplements;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract projection.
   *
   * @param packageName the package name
   * @param imports the imports
   * @param description the description
   * @param objectName the object name
   * @param objectNameWithOptionalExtendsImplements the object name with optional extends implements
   */
  public AbstractProjection(
      String packageName,
      Set<String> imports,
      String description,
      String objectName,
      String objectNameWithOptionalExtendsImplements) {
    setPackageName(packageName);
    setImports(imports);
    setDescription(description);
    setObjectName(objectName);
    setObjectNameWithOptionalExtendsImplements(objectNameWithOptionalExtendsImplements);
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
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets object name.
   *
   * @return the object name
   */
  public String getObjectName() {
    return objectName;
  }

  /**
   * Gets object name with optional extends implements.
   *
   * @return the object name with optional extends implements
   */
  public String getObjectNameWithOptionalExtendsImplements() {
    return objectNameWithOptionalExtendsImplements;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  protected void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  /**
   * Sets imports.
   *
   * @param imports the imports
   */
  protected void setImports(Set<String> imports) {
    this.imports = imports;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  protected void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets object name.
   *
   * @param objectName the object name
   */
  protected void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  /**
   * Sets object name with optional extends implements.
   *
   * @param objectNameWithOptionalExtendsImplements the object name with optional extends implements
   */
  protected void setObjectNameWithOptionalExtendsImplements(
      String objectNameWithOptionalExtendsImplements) {
    this.objectNameWithOptionalExtendsImplements = objectNameWithOptionalExtendsImplements;
  }
}
