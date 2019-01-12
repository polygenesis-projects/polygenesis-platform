/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis System
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

package io.polygenesis.codegen;

import java.util.Set;

/**
 * The type Annotations request.
 *
 * @author Christos Tsakostas
 */
public class AnnotationsRequest {

  private Set<String> packagesToScan;
  private Set<String> interfacesIncluded;
  private Set<String> interfacesExcluded;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets packages to scan.
   *
   * @return the packages to scan
   */
  public Set<String> getPackagesToScan() {
    return packagesToScan;
  }

  /**
   * Gets interfaces included.
   *
   * @return the interfaces included
   */
  public Set<String> getInterfacesIncluded() {
    return interfacesIncluded;
  }

  /**
   * Gets interfaces excluded.
   *
   * @return the interfaces excluded
   */
  public Set<String> getInterfacesExcluded() {
    return interfacesExcluded;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets packages to scan.
   *
   * @param packagesToScan the packages to scan
   */
  public void setPackagesToScan(Set<String> packagesToScan) {
    this.packagesToScan = packagesToScan;
  }

  /**
   * Sets interfaces included.
   *
   * @param interfacesIncluded the interfaces included
   */
  public void setInterfacesIncluded(Set<String> interfacesIncluded) {
    this.interfacesIncluded = interfacesIncluded;
  }

  /**
   * Sets interfaces excluded.
   *
   * @param interfacesExcluded the interfaces excluded
   */
  public void setInterfacesExcluded(Set<String> interfacesExcluded) {
    this.interfacesExcluded = interfacesExcluded;
  }
}
