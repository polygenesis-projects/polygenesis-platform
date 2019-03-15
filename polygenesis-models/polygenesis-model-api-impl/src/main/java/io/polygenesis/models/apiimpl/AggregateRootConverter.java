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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.data.VariableName;
import java.util.Objects;
import java.util.Set;

/**
 * The type Aggregate root converter.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootConverter {

  private ObjectName objectName;
  private PackageName packageName;
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
   * @param objectName the object name
   * @param packageName the package name
   * @param variableName the variable name
   * @param valueObjectFromDtos the value object from dtos
   * @param fetchOneDtoFromAggregateRoots the fetch one dto from aggregate roots
   * @param fetchCollectionDtoFromAggregateRoots the fetch collection dto from aggregate roots
   */
  public AggregateRootConverter(
      ObjectName objectName,
      PackageName packageName,
      VariableName variableName,
      Set<ValueObjectFromDto> valueObjectFromDtos,
      Set<FetchOneDtoFromAggregateRoot> fetchOneDtoFromAggregateRoots,
      Set<FetchCollectionDtoFromAggregateRoot> fetchCollectionDtoFromAggregateRoots) {
    setObjectName(objectName);
    setPackageName(packageName);
    setVariableName(variableName);
    setValueObjectFromDtos(valueObjectFromDtos);
    setFetchOneDtoFromAggregateRoots(fetchOneDtoFromAggregateRoots);
    setFetchCollectionDtoFromAggregateRoots(fetchCollectionDtoFromAggregateRoots);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets object name.
   *
   * @return the object name
   */
  public ObjectName getObjectName() {
    return objectName;
  }

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
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
   * Sets object name.
   *
   * @param objectName the object name
   */
  private void setObjectName(ObjectName objectName) {
    Assertion.isNotNull(objectName, "objectName is required");
    this.objectName = objectName;
  }

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(PackageName packageName) {
    Assertion.isNotNull(packageName, "packageName is required");
    this.packageName = packageName;
  }

  /**
   * Sets variable name.
   *
   * @param variableName the variable name
   */
  private void setVariableName(VariableName variableName) {
    Assertion.isNotNull(variableName, "variableName is required");
    this.variableName = variableName;
  }

  /**
   * Sets value object from dtos.
   *
   * @param valueObjectFromDtos the value object from dtos
   */
  private void setValueObjectFromDtos(Set<ValueObjectFromDto> valueObjectFromDtos) {
    Assertion.isNotNull(valueObjectFromDtos, "valueObjectFromDtos is required");
    this.valueObjectFromDtos = valueObjectFromDtos;
  }

  /**
   * Sets fetch one dto from aggregate roots.
   *
   * @param fetchOneDtoFromAggregateRoots the fetch one dto from aggregate roots
   */
  private void setFetchOneDtoFromAggregateRoots(
      Set<FetchOneDtoFromAggregateRoot> fetchOneDtoFromAggregateRoots) {
    Assertion.isNotNull(fetchOneDtoFromAggregateRoots, "fetchOneDtoFromAggregateRoots is required");
    this.fetchOneDtoFromAggregateRoots = fetchOneDtoFromAggregateRoots;
  }

  /**
   * Sets fetch collection dto from aggregate roots.
   *
   * @param fetchCollectionDtoFromAggregateRoots the fetch collection dto from aggregate roots
   */
  private void setFetchCollectionDtoFromAggregateRoots(
      Set<FetchCollectionDtoFromAggregateRoot> fetchCollectionDtoFromAggregateRoots) {
    Assertion.isNotNull(
        fetchCollectionDtoFromAggregateRoots, "fetchCollectionDtoFromAggregateRoots is required");
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
    return Objects.equals(objectName, that.objectName)
        && Objects.equals(packageName, that.packageName)
        && Objects.equals(variableName, that.variableName)
        && Objects.equals(valueObjectFromDtos, that.valueObjectFromDtos)
        && Objects.equals(fetchOneDtoFromAggregateRoots, that.fetchOneDtoFromAggregateRoots)
        && Objects.equals(
            fetchCollectionDtoFromAggregateRoots, that.fetchCollectionDtoFromAggregateRoots);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        objectName,
        packageName,
        variableName,
        valueObjectFromDtos,
        fetchOneDtoFromAggregateRoots,
        fetchCollectionDtoFromAggregateRoots);
  }
}
