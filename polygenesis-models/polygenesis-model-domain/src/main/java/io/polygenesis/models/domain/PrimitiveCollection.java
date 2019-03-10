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

package io.polygenesis.models.domain;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import java.util.Objects;

/**
 * The type Primitive collection.
 *
 * @author Christos Tsakostas
 */
public class PrimitiveCollection extends AbstractProperty {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private DataArray originatingDataArray;
  private PrimitiveType primitiveType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Primitive collection.
   *
   * @param originatingDataArray the originating data array
   * @param variableName the variable name
   * @param primitiveType the primitive type
   */
  public PrimitiveCollection(
      DataArray originatingDataArray, VariableName variableName, PrimitiveType primitiveType) {
    super(PropertyType.PRIMITIVE_COLLECTION, variableName);
    setOriginatingDataArray(originatingDataArray);
    setPrimitiveType(primitiveType);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets originating data array.
   *
   * @return the originating data array
   */
  public DataArray getOriginatingDataArray() {
    return originatingDataArray;
  }

  /**
   * Gets primitive type.
   *
   * @return the primitive type
   */
  public PrimitiveType getPrimitiveType() {
    return primitiveType;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets originating data array.
   *
   * @param originatingDataArray the originating data array
   */
  private void setOriginatingDataArray(DataArray originatingDataArray) {
    Assertion.isNotNull(originatingDataArray, "originatingDataArray is required");
    this.originatingDataArray = originatingDataArray;
  }

  /**
   * Sets primitive type.
   *
   * @param primitiveType the primitive type
   */
  private void setPrimitiveType(PrimitiveType primitiveType) {
    Assertion.isNotNull(primitiveType, "primitiveType is required");
    this.primitiveType = primitiveType;
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Data getData() {
    return getOriginatingDataArray();
  }

  @Override
  public Data getTypeParameterData() {
    return getOriginatingDataArray().getArrayElement();
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
    PrimitiveCollection that = (PrimitiveCollection) o;
    return Objects.equals(originatingDataArray, that.originatingDataArray)
        && primitiveType == that.primitiveType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(originatingDataArray, primitiveType);
  }
}
