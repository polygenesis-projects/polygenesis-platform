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

package io.polygenesis.models.apiimpl;

import io.polygenesis.models.api.Dto;
import io.polygenesis.models.domain.AggregateRoot;
import java.util.Objects;

/**
 * The type Abstract fetch dto from aggregate root.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractFetchDtoFromAggregateRoot {

  private Dto dto;
  private AggregateRoot aggregateRoot;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract fetch dto from aggregate root.
   *
   * @param dto the dto
   * @param aggregateRoot the aggregate root
   */
  public AbstractFetchDtoFromAggregateRoot(Dto dto, AggregateRoot aggregateRoot) {
    setDto(dto);
    setAggregateRoot(aggregateRoot);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets dto.
   *
   * @return the dto
   */
  public Dto getDto() {
    return dto;
  }

  /**
   * Gets aggregate root.
   *
   * @return the aggregate root
   */
  public AggregateRoot getAggregateRoot() {
    return aggregateRoot;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets dto.
   *
   * @param dto the dto
   */
  private void setDto(Dto dto) {
    this.dto = dto;
  }

  /**
   * Sets aggregate root.
   *
   * @param aggregateRoot the aggregate root
   */
  private void setAggregateRoot(AggregateRoot aggregateRoot) {
    this.aggregateRoot = aggregateRoot;
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
    AbstractFetchDtoFromAggregateRoot that = (AbstractFetchDtoFromAggregateRoot) o;
    return Objects.equals(dto, that.dto) && Objects.equals(aggregateRoot, that.aggregateRoot);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dto, aggregateRoot);
  }
}
