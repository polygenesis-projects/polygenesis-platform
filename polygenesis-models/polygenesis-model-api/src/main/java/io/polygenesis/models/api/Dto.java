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
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.DataGroup;
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
  private DataGroup originatingDataGroup;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto.
   *
   * @param dtoType the dto type
   * @param originatingDataGroup the originating data group
   */
  public Dto(DtoType dtoType, DataGroup originatingDataGroup) {
    setDtoType(dtoType);
    setOriginatingDataGroup(originatingDataGroup);
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
   * Gets originating data group.
   *
   * @return the originating data group
   */
  public DataGroup getOriginatingDataGroup() {
    return originatingDataGroup;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets array element as optional.
   *
   * @return the array element as optional
   */
  public Optional<Data> getArrayElementAsOptional() {
    return getOriginatingDataGroup()
        .getModels()
        .stream()
        .filter(model -> model.isDataArray())
        .map(DataArray.class::cast)
        .map(dataArray -> dataArray.getArrayElement())
        .findFirst();

    // TODO: fix as getOriginatingDataGroup() cannot be DataArray
    //    if (getOriginatingDataGroup().isDataArray()) {
    //      return Optional.of(((DataArray) getOriginatingDataGroup()).getArrayElement());
    //    } else {
    //      return Optional.empty();
    //    }
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
   * Sets originating data group.
   *
   * @param originatingDataGroup the originating data group
   */
  private void setOriginatingDataGroup(DataGroup originatingDataGroup) {
    Assertion.isNotNull(originatingDataGroup, "originatingDataGroup is required");
    this.originatingDataGroup = originatingDataGroup;
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
    return dtoType == dto.dtoType && Objects.equals(originatingDataGroup, dto.originatingDataGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dtoType, originatingDataGroup);
  }
}
