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
 * The type Aggregate root id.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootId extends AbstractProperty {

  private IoModelGroup ioModelGroup;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root id.
   *
   * @param ioModelGroup the io model group
   * @param variableName the variable name
   */
  public AggregateRootId(IoModelGroup ioModelGroup, VariableName variableName) {
    super(PropertyType.AGGREGATE_ROOT_ID, variableName);
    setIoModelGroup(ioModelGroup);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

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
   * Sets io model group.
   *
   * @param ioModelGroup the io model group
   */
  public void setIoModelGroup(IoModelGroup ioModelGroup) {
    this.ioModelGroup = ioModelGroup;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Optional<IoModelGroup> getIoModelGroupAsOptional() {
    return Optional.of(ioModelGroup);
  }

  @Override
  public IoModel getIoModel() {
    return ioModelGroup;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggregateRootId that = (AggregateRootId) o;
    return Objects.equals(ioModelGroup, that.ioModelGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ioModelGroup);
  }
}
