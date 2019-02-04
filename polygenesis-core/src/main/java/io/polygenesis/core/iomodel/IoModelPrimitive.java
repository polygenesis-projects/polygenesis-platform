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
  private final Boolean isThingIdentity;

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
    return new IoModelPrimitive(primitiveDataType, variableName, null, false);
  }

  /**
   * Of parent io model primitive.
   *
   * @param parent the parent
   * @param primitiveDataType the primitive data type
   * @param variableName the variable name
   * @return the io model primitive
   */
  public static IoModelPrimitive ofParent(
      IoModelGroup parent, PrimitiveDataType primitiveDataType, VariableName variableName) {
    return new IoModelPrimitive(primitiveDataType, variableName, parent, null, false);
  }

  /**
   * Of thing identity.
   *
   * @param primitiveDataType the primitive data type
   * @param variableName the variable name
   * @return the io model primitive
   */
  public static IoModelPrimitive ofThingIdentity(
      PrimitiveDataType primitiveDataType, VariableName variableName) {
    IoModelPrimitive ioModelPrimitive =
        new IoModelPrimitive(primitiveDataType, variableName, null, true);

    return ioModelPrimitive;
  }

  /**
   * Of thing identity with parent io model primitive.
   *
   * @param parent the parent
   * @param primitiveDataType the primitive data type
   * @param variableName the variable name
   * @return the io model primitive
   */
  public static IoModelPrimitive ofThingIdentityWithParent(
      IoModelGroup parent, PrimitiveDataType primitiveDataType, VariableName variableName) {
    IoModelPrimitive ioModelPrimitive =
        new IoModelPrimitive(primitiveDataType, variableName, parent, null, true);

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
   * @param isThingIdentity the is thing identity
   */
  public IoModelPrimitive(
      PrimitiveDataType dataType,
      VariableName variableName,
      Set<Annotation> annotations,
      Boolean isThingIdentity) {
    super(dataType, variableName);
    this.annotations = annotations;
    this.isThingIdentity = isThingIdentity;
  }

  /**
   * Instantiates a new Io model primitive.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param parent the parent
   * @param annotations the annotations
   * @param isThingIdentity the is thing identity
   */
  public IoModelPrimitive(
      PrimitiveDataType dataType,
      VariableName variableName,
      IoModelGroup parent,
      Set<Annotation> annotations,
      Boolean isThingIdentity) {
    super(dataType, variableName, parent);
    this.annotations = annotations;
    this.isThingIdentity = isThingIdentity;
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
   * Checks id primitive model is thing identity.
   *
   * @return the thing identity flag
   */
  public Boolean getThingIdentity() {
    return isThingIdentity;
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
        .append(isThingIdentity, that.isThingIdentity)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(annotations)
        .append(isThingIdentity)
        .toHashCode();
  }
}
