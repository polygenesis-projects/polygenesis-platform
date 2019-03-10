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
 * The type Aggregate root id.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootId extends AbstractProperty {

  private DataGroup dataGroup;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root id.
   *
   * @param dataGroup the data group
   * @param variableName the variable name
   */
  public AggregateRootId(DataGroup dataGroup, VariableName variableName) {
    super(PropertyType.AGGREGATE_ROOT_ID, variableName);
    setDataGroup(dataGroup);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

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
    AggregateRootId that = (AggregateRootId) o;
    return Objects.equals(dataGroup, that.dataGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataGroup);
  }
}
