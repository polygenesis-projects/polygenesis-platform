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

package io.polygenesis.core.datatype;

import java.util.Optional;

/**
 * Base class for classes and interfaces.
 *
 * @author Christos Tsakostas
 */
public class AbstractObjectDataType extends AbstractDataType {

  private PackageName packageName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract object data type.
   *
   * @param dataTypeName the data type name
   * @param packageName the package name
   */
  public AbstractObjectDataType(DataTypeName dataTypeName, PackageName packageName) {
    super(dataTypeName);
    setPackageName(packageName);
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Optional<PackageName> getOptionalPackageName() {
    return Optional.of(packageName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(PackageName packageName) {
    this.packageName = packageName;
  }
}
