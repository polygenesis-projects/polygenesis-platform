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

package io.polygenesis.core.data;

import io.polygenesis.core.datatype.PrimitiveType;
import io.polygenesis.core.iomodel.DataBusinessType;
import io.polygenesis.core.iomodel.DataKind;
import io.polygenesis.core.iomodel.VariableName;

/**
 * The type Primitive data.
 *
 * @author Christos Tsakostas
 */
public abstract class PrimitiveData extends Data {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final PrimitiveType primitiveType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Primitive data.
   *
   * @param variableName the variable name
   * @param primitiveType the primitive type
   */
  public PrimitiveData(VariableName variableName, PrimitiveType primitiveType) {
    this(variableName, DataBusinessType.ANY, primitiveType);
  }

  /**
   * Instantiates a new Primitive data.
   *
   * @param variableName the variable name
   * @param dataBusinessType the data business type
   * @param primitiveType the primitive type
   */
  public PrimitiveData(
      VariableName variableName, DataBusinessType dataBusinessType, PrimitiveType primitiveType) {
    super(DataKind.PRIMITIVE, variableName, dataBusinessType);
    this.primitiveType = primitiveType;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets primitive type.
   *
   * @return the primitive type
   */
  public PrimitiveType getPrimitiveType() {
    return primitiveType;
  }
}
