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
 * The type Primitive data type.
 *
 * @author Christos Tsakostas
 */
public class PrimitiveDataType extends AbstractDataType {

  private PrimitiveType primitiveType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Primitive data type.
   *
   * @param primitiveType the primary type
   */
  public PrimitiveDataType(PrimitiveType primitiveType) {
    super(DataKind.PRIMITIVE, new DataTypeName(primitiveType.name()));
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

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Optional<PackageName> getOptionalPackageName() {
    return Optional.empty();
  }
}
