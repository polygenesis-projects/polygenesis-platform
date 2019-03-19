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
        DataSource.user(),
        variableName,
        DataBusinessType.ANY,
        DataValidator.empty(),
        primitiveType,
        null);
  }

  /**
   * Of thing identity with parent data primitive.
   *
   * @param dataBusinessType the data business type
   * @param primitiveType the primitive type
   * @param variableName the variable name
   * @return the data primitive
   */
  public static DataPrimitive ofDataBusinessType(
      DataBusinessType dataBusinessType, PrimitiveType primitiveType, VariableName variableName) {
    return new DataPrimitive(
        DataSource.user(),
        variableName,
        dataBusinessType,
        DataValidator.empty(),
        primitiveType,
        null);
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
   * @param dataBusinessType the data business type
   */
  public DataPrimitive(
      PrimitiveType primitiveType,
      VariableName variableName,
      Set<Annotation> annotations,
      DataBusinessType dataBusinessType) {
    this(
        DataSource.user(),
        variableName,
        dataBusinessType,
        DataValidator.empty(),
        primitiveType,
        annotations);
  }

  /**
   * Instantiates a new Data primitive.
   *
   * @param dataSource the data source
   * @param variableName the variable name
   * @param dataBusinessType the data business type
   * @param primitiveType the primitive type
   * @param annotations the annotations
   */
  public DataPrimitive(
      DataSource dataSource,
      VariableName variableName,
      DataBusinessType dataBusinessType,
      DataValidator dataValidator,
      PrimitiveType primitiveType,
      Set<Annotation> annotations) {
    super(DataPrimaryType.PRIMITIVE, dataSource, variableName, dataBusinessType, dataValidator);
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
  // QUERIES
  // ===============================================================================================

  /**
   * Checks if primitive model is thing identity.
   *
   * @return the thing identity flag
   */
  public Boolean getThingIdentity() {
    return getDataBusinessType().equals(DataBusinessType.THING_IDENTITY);
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
