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

import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.iomodel.VariableName;
import java.util.Objects;
import java.util.Set;

/**
 * The type Aggregate root converter.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootConverter {

  private ClassDataType dataType;
  private VariableName variableName;
  private Set<ValueObjectFromDto> valueObjectFromDtos;
  private Set<FetchOneDtoFromAggregateRoot> fetchOneDtoFromAggregateRoots;
  private Set<FetchCollectionDtoFromAggregateRoot> fetchCollectionDtoFromAggregateRoots;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param valueObjectFromDtos the value object from dtos
   * @param fetchOneDtoFromAggregateRoots the fetch one dto from aggregate roots
   * @param fetchCollectionDtoFromAggregateRoots the fetch collection dto from aggregate roots
   */
  public AggregateRootConverter(
      ClassDataType dataType,
      VariableName variableName,
      Set<ValueObjectFromDto> valueObjectFromDtos,
      Set<FetchOneDtoFromAggregateRoot> fetchOneDtoFromAggregateRoots,
      Set<FetchCollectionDtoFromAggregateRoot> fetchCollectionDtoFromAggregateRoots) {
    setDataType(dataType);
    setVariableName(variableName);
    setValueObjectFromDtos(valueObjectFromDtos);
    setFetchOneDtoFromAggregateRoots(fetchOneDtoFromAggregateRoots);
    setFetchCollectionDtoFromAggregateRoots(fetchCollectionDtoFromAggregateRoots);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public ClassDataType getDataType() {
    return dataType;
  }

  /**
   * Gets variable name.
   *
   * @return the variable name
   */
  public VariableName getVariableName() {
    return variableName;
  }

  /**
   * Gets value object from dtos.
   *
   * @return the value object from dtos
   */
  public Set<ValueObjectFromDto> getValueObjectFromDtos() {
    return valueObjectFromDtos;
  }

  /**
   * Gets fetch one dto from aggregate roots.
   *
   * @return the fetch one dto from aggregate roots
   */
  public Set<FetchOneDtoFromAggregateRoot> getFetchOneDtoFromAggregateRoots() {
    return fetchOneDtoFromAggregateRoots;
  }

  /**
   * Gets fetch collection dto from aggregate roots.
   *
   * @return the fetch collection dto from aggregate roots
   */
  public Set<FetchCollectionDtoFromAggregateRoot> getFetchCollectionDtoFromAggregateRoots() {
    return fetchCollectionDtoFromAggregateRoots;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets data type.
   *
   * @param dataType the data type
   */
  private void setDataType(ClassDataType dataType) {
    this.dataType = dataType;
  }

  /**
   * Sets variable name.
   *
   * @param variableName the variable name
   */
  private void setVariableName(VariableName variableName) {
    this.variableName = variableName;
  }

  /**
   * Sets value object from dtos.
   *
   * @param valueObjectFromDtos the value object from dtos
   */
  private void setValueObjectFromDtos(Set<ValueObjectFromDto> valueObjectFromDtos) {
    this.valueObjectFromDtos = valueObjectFromDtos;
  }

  /**
   * Sets fetch one dto from aggregate roots.
   *
   * @param fetchOneDtoFromAggregateRoots the fetch one dto from aggregate roots
   */
  private void setFetchOneDtoFromAggregateRoots(
      Set<FetchOneDtoFromAggregateRoot> fetchOneDtoFromAggregateRoots) {
    this.fetchOneDtoFromAggregateRoots = fetchOneDtoFromAggregateRoots;
  }

  /**
   * Sets fetch collection dto from aggregate roots.
   *
   * @param fetchCollectionDtoFromAggregateRoots the fetch collection dto from aggregate roots
   */
  private void setFetchCollectionDtoFromAggregateRoots(
      Set<FetchCollectionDtoFromAggregateRoot> fetchCollectionDtoFromAggregateRoots) {
    this.fetchCollectionDtoFromAggregateRoots = fetchCollectionDtoFromAggregateRoots;
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
    AggregateRootConverter that = (AggregateRootConverter) o;
    return Objects.equals(dataType, that.dataType)
        && Objects.equals(variableName, that.variableName)
        && Objects.equals(valueObjectFromDtos, that.valueObjectFromDtos)
        && Objects.equals(fetchOneDtoFromAggregateRoots, that.fetchOneDtoFromAggregateRoots)
        && Objects.equals(
            fetchCollectionDtoFromAggregateRoots, that.fetchCollectionDtoFromAggregateRoots);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        dataType,
        variableName,
        valueObjectFromDtos,
        fetchOneDtoFromAggregateRoots,
        fetchCollectionDtoFromAggregateRoots);
  }
}
