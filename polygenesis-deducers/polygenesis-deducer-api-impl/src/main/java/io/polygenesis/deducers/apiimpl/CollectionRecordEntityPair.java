/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.deducers.apiimpl;

import io.polygenesis.models.api.Dto;
import io.polygenesis.models.domain.DomainObject;
import java.util.Objects;

/**
 * The type Collection record entity pair.
 *
 * @author Christos Tsakostas
 */
public class CollectionRecordEntityPair {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Dto dto;
  private DomainObject entity;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Collection record entity pair.
   *
   * @param dto the dto
   * @param entity the entity
   */
  public CollectionRecordEntityPair(Dto dto, DomainObject entity) {
    this.dto = dto;
    this.entity = entity;
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
   * Gets entity.
   *
   * @return the entity
   */
  public DomainObject getEntity() {
    return entity;
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
    CollectionRecordEntityPair that = (CollectionRecordEntityPair) o;
    return Objects.equals(dto.getDataObject(), that.dto.getDataObject())
        && Objects.equals(entity, that.entity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dto.getDataObject(), entity);
  }
}
