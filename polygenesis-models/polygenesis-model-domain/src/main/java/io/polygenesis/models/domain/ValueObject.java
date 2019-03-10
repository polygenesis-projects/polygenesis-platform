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

import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.VariableName;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Value object.
 *
 * @author Christos Tsakostas
 */
public class ValueObject extends AbstractProperty {

  private DataGroup originatingIoModelGroup;
  private DataGroup ioModelGroup;

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
      DataGroup originatingIoModelGroup,
      DataGroup ioModelGroup,
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
      DataGroup originatingIoModelGroup, DataGroup ioModelGroup, VariableName variableName) {
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
  public DataGroup getOriginatingIoModelGroup() {
    return originatingIoModelGroup;
  }

  /**
   * Gets io model group.
   *
   * @return the io model group
   */
  public DataGroup getIoModelGroup() {
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
  private void setOriginatingIoModelGroup(DataGroup originatingIoModelGroup) {
    this.originatingIoModelGroup = originatingIoModelGroup;
  }

  /**
   * Sets io model group.
   *
   * @param ioModelGroup the io model group
   */
  private void setIoModelGroup(DataGroup ioModelGroup) {
    this.ioModelGroup = ioModelGroup;
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Optional<DataGroup> getDataGroupAsOptional() {
    return Optional.of(getIoModelGroup());
  }

  @Override
  public Data getData() {
    return ioModelGroup;
  }

  @Override
  public Data getTypeParameterData() {
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
