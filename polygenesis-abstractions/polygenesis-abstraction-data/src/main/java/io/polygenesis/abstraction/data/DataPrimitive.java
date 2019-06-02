/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.abstraction.data;

import io.polygenesis.commons.valueobjects.VariableName;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

/**
 * The type data primitive.
 *
 * @author Christos Tsakostas
 */
public class DataPrimitive extends Data {

  private final PrimitiveType primitiveType;
  private final Set<Annotation> annotations;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of.
   *
   * @param primitiveType the primitive type
   * @param variableName the variable name
   * @return the data primitive
   */
  public static DataPrimitive of(PrimitiveType primitiveType, VariableName variableName) {
    return new DataPrimitive(
        variableName, DataPurpose.any(), DataValidator.empty(), primitiveType, null);
  }

  /**
   * Of thing identity with parent data primitive.
   *
   * @param dataPurpose the data business type
   * @param primitiveType the primitive type
   * @param variableName the variable name
   * @return the data primitive
   */
  public static DataPrimitive ofDataBusinessType(
      DataPurpose dataPurpose, PrimitiveType primitiveType, VariableName variableName) {
    return new DataPrimitive(variableName, dataPurpose, DataValidator.empty(), primitiveType, null);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data primitive.
   *
   * @param primitiveType the primitive type
   * @param variableName the variable name
   * @param annotations the annotations
   * @param dataPurpose the data business type
   */
  public DataPrimitive(
      PrimitiveType primitiveType,
      VariableName variableName,
      Set<Annotation> annotations,
      DataPurpose dataPurpose) {
    this(variableName, dataPurpose, DataValidator.empty(), primitiveType, annotations);
  }

  /**
   * Instantiates a new Data primitive.
   *
   * @param variableName the variable name
   * @param dataPurpose the data business type
   * @param primitiveType the primitive type
   * @param annotations the annotations
   */
  public DataPrimitive(
      VariableName variableName,
      DataPurpose dataPurpose,
      DataValidator dataValidator,
      PrimitiveType primitiveType,
      Set<Annotation> annotations) {
    super(DataPrimaryType.PRIMITIVE, variableName, dataPurpose, dataValidator);
    this.primitiveType = primitiveType;
    this.annotations = annotations;
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

  /**
   * Get annotations.
   *
   * @return the annotations
   */
  public Set<Annotation> getAnnotations() {
    return annotations;
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATION
  // ===============================================================================================

  @Override
  public String getDataType() {
    return getPrimitiveType().name();
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
    if (!super.equals(o)) {
      return false;
    }
    DataPrimitive that = (DataPrimitive) o;
    return primitiveType == that.primitiveType && Objects.equals(annotations, that.annotations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), primitiveType, annotations);
  }
}
