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

package io.polygenesis.generators.java.domain.shared;

import java.util.Set;

/**
 * The type Abstract java object projection.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractJavaObjectProjection {

  private String packageName;
  private Set<String> imports;

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

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  /**
   * Sets imports.
   *
   * @param imports the imports
   */
  public void setImports(Set<String> imports) {
    this.imports = imports;
  }
}
