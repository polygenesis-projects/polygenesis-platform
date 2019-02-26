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

import java.util.Objects;
import java.util.Optional;

/**
 * The Data type.
 *
 * <p>References:
 *
 * <ul>
 *   <li>https://en.wikibooks.org/wiki/Java_Programming/Primitive_Types
 *   <li>https://en.wikipedia.org/wiki/Primitive_data_type
 *   <li>https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
 * </ul>
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractDataType {

  private final DataKind dataKind;

  /** Name such as: int, BigDecimal, CreateCustomerRequest etc. */
  private final DataTypeName dataTypeName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract data type.
   *
   * @param dataKind the data type
   * @param dataTypeName the data type name
   */
  public AbstractDataType(DataKind dataKind, DataTypeName dataTypeName) {
    this.dataKind = dataKind;
    this.dataTypeName = dataTypeName;
  }

  /**
   * Gets optional package name.
   *
   * @return the optional package name
   */
  // ===============================================================================================
  // ABSTRACT
  // ===============================================================================================
  public abstract Optional<PackageName> getOptionalPackageName();

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data type name.
   *
   * @return the data type name
   */
  public DataTypeName getDataTypeName() {
    return dataTypeName;
  }

  /**
   * Gets data kind.
   *
   * @return the data kind
   */
  public DataKind getDataKind() {
    return dataKind;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Is primitive boolean.
   *
   * @return the boolean
   */
  public boolean isPrimitive() {
    return dataKind.equals(DataKind.PRIMITIVE);
  }

  /**
   * Is class boolean.
   *
   * @return the boolean
   */
  public boolean isClass() {
    return dataKind.equals(DataKind.CLASS);
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
    AbstractDataType that = (AbstractDataType) o;
    return Objects.equals(dataTypeName, that.dataTypeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataTypeName);
  }
}
