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

import java.util.Set;

/**
 * The type Interface data type.
 *
 * @author Christos Tsakostas
 */
public class InterfaceDataType extends AbstractObjectDataType {

  // Optional
  private Set<AbstractDataType> genericsDefinitions;

  // Optional
  private Set<InterfaceDataType> extendsInterfaceDataTypes;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Interface data type.
   *
   * @param dataTypeName the data type name
   * @param packageName the package name
   */
  public InterfaceDataType(DataTypeName dataTypeName, PackageName packageName) {
    super(dataTypeName, packageName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets generics definitions.
   *
   * @return the generics definitions
   */
  public Set<AbstractDataType> getGenericsDefinitions() {
    return genericsDefinitions;
  }

  /**
   * Gets extends interface data types.
   *
   * @return the extends interface data types
   */
  public Set<InterfaceDataType> getExtendsInterfaceDataTypes() {
    return extendsInterfaceDataTypes;
  }
}
