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

package io.polygenesis.models.api;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelArray;
import io.polygenesis.core.iomodel.IoModelGroup;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Dto.
 *
 * @author Christos Tsakostas
 */
public class Dto {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private DtoType dtoType;
  private IoModelGroup originatingIoModelGroup;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto.
   *
   * @param dtoType the dto type
   * @param originatingIoModelGroup the originating io model group
   */
  public Dto(DtoType dtoType, IoModelGroup originatingIoModelGroup) {
    setDtoType(dtoType);
    setOriginatingIoModelGroup(originatingIoModelGroup);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets dto type.
   *
   * @return the dto type
   */
  public DtoType getDtoType() {
    return dtoType;
  }

  /**
   * Gets originating io model group.
   *
   * @return the originating io model group
   */
  public IoModelGroup getOriginatingIoModelGroup() {
    return originatingIoModelGroup;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets array element as optional.
   *
   * @return the array element as optional
   */
  public Optional<IoModel> getArrayElementAsOptional() {
    if (getOriginatingIoModelGroup().isIoModelArray()) {
      return Optional.of(((IoModelArray) getOriginatingIoModelGroup()).getArrayElement());
    } else {
      return Optional.empty();
    }
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets dto type.
   *
   * @param dtoType the dto type
   */
  private void setDtoType(DtoType dtoType) {
    Assertion.isNotNull(dtoType, "dtoType is required");
    this.dtoType = dtoType;
  }

  /**
   * Sets originating io model group.
   *
   * @param originatingIoModelGroup the originating io model group
   */
  private void setOriginatingIoModelGroup(IoModelGroup originatingIoModelGroup) {
    Assertion.isNotNull(originatingIoModelGroup, "originatingIoModelGroup is required");
    this.originatingIoModelGroup = originatingIoModelGroup;
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
    Dto dto = (Dto) o;
    return dtoType == dto.dtoType
        && Objects.equals(originatingIoModelGroup, dto.originatingIoModelGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dtoType, originatingIoModelGroup);
  }
}
