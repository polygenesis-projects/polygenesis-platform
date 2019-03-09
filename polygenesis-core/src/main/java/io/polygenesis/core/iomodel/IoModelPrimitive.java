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

import io.polygenesis.core.datatype.PrimitiveDataType;
import java.lang.annotation.Annotation;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The type Io model primitive.
 *
 * @author Christos Tsakostas
 */
public class IoModelPrimitive extends IoModel {

  private final Set<Annotation> annotations;
  private final DataBusinessType dataBusinessType;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of.
   *
   * @param primitiveDataType the primitive data type
   * @param variableName the variable name
   * @return the io model primitive
   */
  public static IoModelPrimitive of(
      PrimitiveDataType primitiveDataType, VariableName variableName) {
    return new IoModelPrimitive(primitiveDataType, variableName, null, DataBusinessType.ANY);
  }

  /**
   * Of thing identity with parent io model primitive.
   *
   * @param dataBusinessType the data business type
   * @param primitiveDataType the primitive data type
   * @param variableName the variable name
   * @return the io model primitive
   */
  public static IoModelPrimitive ofDataBusinessType(
      DataBusinessType dataBusinessType,
      PrimitiveDataType primitiveDataType,
      VariableName variableName) {
    IoModelPrimitive ioModelPrimitive =
        new IoModelPrimitive(primitiveDataType, variableName, null, dataBusinessType);

    return ioModelPrimitive;
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model primitive.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   * @param dataBusinessType the data business type
   */
  public IoModelPrimitive(
      PrimitiveDataType dataType,
      VariableName variableName,
      Set<Annotation> annotations,
      DataBusinessType dataBusinessType) {
    super(DataKind.PRIMITIVE, dataType, variableName);
    this.annotations = annotations;
    this.dataBusinessType = dataBusinessType;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Get annotations.
   *
   * @return the annotations
   */
  public Set<Annotation> getAnnotations() {
    return annotations;
  }

  /**
   * Gets data business type.
   *
   * @return the data business type
   */
  public DataBusinessType getDataBusinessType() {
    return dataBusinessType;
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
    return dataBusinessType.equals(DataBusinessType.THING_IDENTITY);
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

    IoModelPrimitive that = (IoModelPrimitive) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(annotations, that.annotations)
        .append(dataBusinessType, that.dataBusinessType)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(annotations)
        .append(dataBusinessType)
        .toHashCode();
  }
}
