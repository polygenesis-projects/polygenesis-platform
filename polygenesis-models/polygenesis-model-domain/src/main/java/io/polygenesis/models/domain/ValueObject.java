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

import io.polygenesis.core.data.IoModel;
import io.polygenesis.core.data.IoModelGroup;
import io.polygenesis.core.data.VariableName;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Value object.
 *
 * @author Christos Tsakostas
 */
public class ValueObject extends AbstractProperty {

  private IoModelGroup originatingIoModelGroup;
  private IoModelGroup ioModelGroup;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object.
   *
   * @param propertyType the property type
   * @param originatingIoModelGroup the originating io model group
   * @param ioModelGroup the io model group
   * @param variableName the variable name
   */
  public ValueObject(
      PropertyType propertyType,
      IoModelGroup originatingIoModelGroup,
      IoModelGroup ioModelGroup,
      VariableName variableName) {
    super(propertyType, variableName);
    setOriginatingIoModelGroup(originatingIoModelGroup);
    setIoModelGroup(ioModelGroup);
  }

  /**
   * Instantiates a new Value object.
   *
   * @param originatingIoModelGroup the originating io model group
   * @param ioModelGroup the io model group
   * @param variableName the variable name
   */
  public ValueObject(
      IoModelGroup originatingIoModelGroup, IoModelGroup ioModelGroup, VariableName variableName) {
    super(PropertyType.VALUE_OBJECT, variableName);
    setOriginatingIoModelGroup(originatingIoModelGroup);
    setIoModelGroup(ioModelGroup);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets originating io model group.
   *
   * @return the originating io model group
   */
  public IoModelGroup getOriginatingIoModelGroup() {
    return originatingIoModelGroup;
  }

  /**
   * Gets io model group.
   *
   * @return the io model group
   */
  public IoModelGroup getIoModelGroup() {
    return ioModelGroup;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets originating io model group.
   *
   * @param originatingIoModelGroup the originating io model group
   */
  private void setOriginatingIoModelGroup(IoModelGroup originatingIoModelGroup) {
    this.originatingIoModelGroup = originatingIoModelGroup;
  }

  /**
   * Sets io model group.
   *
   * @param ioModelGroup the io model group
   */
  private void setIoModelGroup(IoModelGroup ioModelGroup) {
    this.ioModelGroup = ioModelGroup;
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Optional<IoModelGroup> getIoModelGroupAsOptional() {
    return Optional.of(getIoModelGroup());
  }

  @Override
  public IoModel getIoModel() {
    return ioModelGroup;
  }

  @Override
  public IoModel getTypeParameterDataModel() {
    throw new UnsupportedOperationException();
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
    ValueObject that = (ValueObject) o;
    return Objects.equals(originatingIoModelGroup, that.originatingIoModelGroup)
        && Objects.equals(ioModelGroup, that.ioModelGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(originatingIoModelGroup, ioModelGroup);
  }
}
