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

package io.polygenesis.models.api;

import io.polygenesis.commons.assertion.Assertion;
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
  private DataGroup dataGroup;
  private Boolean virtual;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto.
   *
   * @param dtoType the dto type
   * @param dataGroup the data group
   * @param virtual the virtual
   */
  public Dto(DtoType dtoType, DataGroup dataGroup, Boolean virtual) {
    setDtoType(dtoType);
    setDataGroup(dataGroup);
    setVirtual(virtual);
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
   * Gets data group.
   *
   * @return the data group
   */
  public DataGroup getDataGroup() {
    return dataGroup;
  }

  /**
   * Gets virtual.
   *
   * @return the virtual
   */
  public Boolean getVirtual() {
    return virtual;
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
    return getDataGroup()
        .getModels()
        .stream()
        .filter(model -> model.isDataArray())
        .map(DataArray.class::cast)
        .filter(dataArray -> dataArray.getArrayElement() != null)
        .map(dataArray -> dataArray.getArrayElement())
        .findFirst();
  }

  /**
   * Gets thing identity as optional.
   *
   * @return the optional thing identity
   */
  public Optional<Data> getThingIdentityAsOptional() {
    return getDataGroup().getModels().stream().filter(data -> data.isThingIdentity()).findFirst();
  }

  /**
   * Gets parent thing identity as optional.
   *
   * @return the parent thing identity as optional
   */
  public Optional<Data> getParentThingIdentityAsOptional() {
    return getDataGroup()
        .getModels()
        .stream()
        .filter(data -> data.isParentThingIdentity())
        .findFirst();
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
   * Sets data group.
   *
   * @param dataGroup the data group
   */
  public void setDataGroup(DataGroup dataGroup) {
    Assertion.isNotNull(dataGroup, "dataGroup is required");
    this.dataGroup = dataGroup;
  }

  /**
   * Sets virtual.
   *
   * @param virtual the virtual
   */
  private void setVirtual(Boolean virtual) {
    Assertion.isNotNull(virtual, "virtual is required");
    this.virtual = virtual;
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
        && Objects.equals(dataGroup, dto.dataGroup)
        && Objects.equals(virtual, dto.virtual);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dtoType, dataGroup, virtual);
  }
}
