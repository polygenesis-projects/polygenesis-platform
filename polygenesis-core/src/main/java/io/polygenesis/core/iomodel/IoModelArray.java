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

package io.polygenesis.core.iomodel;

import io.polygenesis.core.datatype.ClassDataType;

/**
 * The type Io model array.
 *
 * @author Christos Tsakostas
 */
public class IoModelArray extends IoModelGroup {

  private final IoModel arrayElement;
  private final GenericTypeName genericType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model array.
   *
   * @param parent the parent
   */
  public IoModelArray(IoModelGroup parent) {
    super(parent);
    this.arrayElement = null;
    this.genericType = null;
  }

  /**
   * Instantiates a new Io model array.
   *
   * @param arrayElement the array element
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModelArray(IoModel arrayElement, ClassDataType dataType, VariableName variableName) {
    super(dataType, variableName);
    this.arrayElement = arrayElement;
    this.genericType = null;
  }

  /**
   * Instantiates a new Io model array.
   *
   * @param genericType the generic type
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModelArray(
      GenericTypeName genericType, ClassDataType dataType, VariableName variableName) {
    super(dataType, variableName);
    this.arrayElement = null;
    this.genericType = genericType;
  }

  /**
   * Instantiates a new Io model array.
   *
   * @param parent the parent
   * @param arrayElement the array element
   * @param genericType the generic type
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModelArray(
      IoModelGroup parent,
      IoModel arrayElement,
      GenericTypeName genericType,
      ClassDataType dataType,
      VariableName variableName) {
    super(parent, dataType, variableName);
    this.arrayElement = arrayElement;
    this.genericType = genericType;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets array element.
   *
   * @return the array element
   */
  public IoModel getArrayElement() {
    return arrayElement;
  }

  /**
   * Gets generic type.
   *
   * @return the generic type
   */
  public GenericTypeName getGenericType() {
    return genericType;
  }
}
