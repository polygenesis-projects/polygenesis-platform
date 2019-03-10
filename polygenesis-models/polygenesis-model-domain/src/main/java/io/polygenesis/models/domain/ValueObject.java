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

/**
 * The type Value object.
 *
 * @author Christos Tsakostas
 */
public class ValueObject extends AbstractProperty {

  private DataGroup originatingDataGroup;
  private DataGroup dataGroup;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object.
   *
   * @param propertyType the property type
   * @param originatingDataGroup the originating data group
   * @param dataGroup the data group
   * @param variableName the variable name
   */
  public ValueObject(
      PropertyType propertyType,
      DataGroup originatingDataGroup,
      DataGroup dataGroup,
      VariableName variableName) {
    super(propertyType, variableName);
    setOriginatingDataGroup(originatingDataGroup);
    setDataGroup(dataGroup);
  }

  /**
   * Instantiates a new Value object.
   *
   * @param originatingDataGroup the originating data group
   * @param dataGroup the data group
   * @param variableName the variable name
   */
  public ValueObject(
      DataGroup originatingDataGroup, DataGroup dataGroup, VariableName variableName) {
    super(PropertyType.VALUE_OBJECT, variableName);
    setOriginatingDataGroup(originatingDataGroup);
    setDataGroup(dataGroup);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets originating data group.
   *
   * @return the originating data group
   */
  public DataGroup getOriginatingDataGroup() {
    return originatingDataGroup;
  }

  /**
   * Gets data group.
   *
   * @return the data group
   */
  public DataGroup getDataGroup() {
    return dataGroup;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets originating data group.
   *
   * @param originatingDataGroup the originating data group
   */
  private void setOriginatingDataGroup(DataGroup originatingDataGroup) {
    this.originatingDataGroup = originatingDataGroup;
  }

  /**
   * Sets data group.
   *
   * @param dataGroup the data group
   */
  private void setDataGroup(DataGroup dataGroup) {
    this.dataGroup = dataGroup;
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Data getData() {
    return dataGroup;
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
    return Objects.equals(originatingDataGroup, that.originatingDataGroup)
        && Objects.equals(dataGroup, that.dataGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(originatingDataGroup, dataGroup);
  }
}
