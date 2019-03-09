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
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.core.data.IoModel;
import io.polygenesis.core.data.IoModelGroup;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Primitive collection.
 *
 * @author Christos Tsakostas
 */
public class PrimitiveCollection extends AbstractProperty {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private IoModel originatingIoModel;
  private PrimitiveType primitiveType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Primitive collection.
   *
   * @param originatingIoModel the originating io model
   * @param variableName the variable name
   * @param primitiveType the primitive type
   */
  public PrimitiveCollection(
      IoModel originatingIoModel, VariableName variableName, PrimitiveType primitiveType) {
    super(PropertyType.PRIMITIVE_COLLECTION, variableName);
    setOriginatingIoModel(originatingIoModel);
    setPrimitiveType(primitiveType);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets originating io model.
   *
   * @return the originating io model
   */
  public IoModel getOriginatingIoModel() {
    return originatingIoModel;
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
   * Sets originating io model.
   *
   * @param originatingIoModel the originating io model
   */
  public void setOriginatingIoModel(IoModel originatingIoModel) {
    Assertion.isNotNull(originatingIoModel, "originatingIoModel is required");
    this.originatingIoModel = originatingIoModel;
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
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Optional<IoModelGroup> getIoModelGroupAsOptional() {
    return Optional.empty();
  }

  @Override
  public IoModel getIoModel() {
    return getOriginatingIoModel();
  }

  @Override
  public KeyValue getAsKeyValue() {
    // TODO
    return new KeyValue("List<String>", getVariableName().getText());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrimitiveCollection that = (PrimitiveCollection) o;
    return primitiveType == that.primitiveType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(primitiveType);
  }
}
