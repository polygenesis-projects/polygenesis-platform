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
import java.util.Set;

/**
 * The type Class data type.
 *
 * @author Christos Tsakostas
 */
public class ClassDataType extends AbstractDataType {

  private PackageName packageName;

  // Optional
  private Set<AbstractDataType> genericsDefinitions;

  // Optional
  private Set<ClassDataType> extendsObjectDataTypes;
  private Set<InterfaceDataType> implementsInterfaceDataTypes;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Class data type.
   *
   * @param dataTypeName the data type name
   * @param packageName the package name
   */
  public ClassDataType(DataTypeName dataTypeName, PackageName packageName) {
    super(DataKind.CLASS, dataTypeName);
    this.packageName = packageName;
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  /**
   * Change data type name to.
   *
   * @param dataTypeName the data type name
   */
  public ClassDataType changeDataTypeNameTo(DataTypeName dataTypeName) {
    return new ClassDataType(dataTypeName, getOptionalPackageName().get());
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
   * Gets extends object data types.
   *
   * @return the extends object data types
   */
  public Set<ClassDataType> getExtendsObjectDataTypes() {
    return extendsObjectDataTypes;
  }

  /**
   * Gets implements interface data types.
   *
   * @return the implements interface data types
   */
  public Set<InterfaceDataType> getImplementsInterfaceDataTypes() {
    return implementsInterfaceDataTypes;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Optional<PackageName> getOptionalPackageName() {
    return Optional.of(packageName);
  }
}
