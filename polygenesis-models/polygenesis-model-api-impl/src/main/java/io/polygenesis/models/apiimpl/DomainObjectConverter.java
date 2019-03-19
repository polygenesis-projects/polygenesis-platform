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
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.data.VariableName;
import io.polygenesis.models.domain.BaseDomainObject;
import java.util.Objects;
import java.util.Set;

/**
 * The type Domain object converter.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverter extends ServiceDependency {

  private BaseDomainObject<?> domainObject;
  private Set<ValueObjectFromDto> valueObjectFromDtos;
  private Set<FetchOneDtoFromDomainObject> fetchOneDtoFromAggregateRoots;
  private Set<FetchCollectionDtoFromDomainObject> fetchCollectionDtoFromAggregateRoots;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object converter.
   *
   * @param domainObject the domain object
   * @param valueObjectFromDtos the value object from dtos
   * @param fetchOneDtoFromAggregateRoots the fetch one dto from aggregate roots
   * @param fetchCollectionDtoFromAggregateRoots the fetch collection dto from aggregate roots
   */
  public DomainObjectConverter(
      BaseDomainObject<?> domainObject,
      Set<ValueObjectFromDto> valueObjectFromDtos,
      Set<FetchOneDtoFromDomainObject> fetchOneDtoFromAggregateRoots,
      Set<FetchCollectionDtoFromDomainObject> fetchCollectionDtoFromAggregateRoots) {
    super(
        new ObjectName(
            String.format(
                "%sConverter", TextConverter.toUpperCamel(domainObject.getObjectName().getText()))),
        domainObject.getPackageName(),
        new VariableName(
            String.format(
                "%sConverter",
                TextConverter.toLowerCamel(domainObject.getObjectName().getText()))));
    setDomainObject(domainObject);
    setValueObjectFromDtos(valueObjectFromDtos);
    setFetchOneDtoFromAggregateRoots(fetchOneDtoFromAggregateRoots);
    setFetchCollectionDtoFromAggregateRoots(fetchCollectionDtoFromAggregateRoots);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets domain object.
   *
   * @return the domain object
   */
  public BaseDomainObject<?> getDomainObject() {
    return domainObject;
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
  public Set<FetchOneDtoFromDomainObject> getFetchOneDtoFromAggregateRoots() {
    return fetchOneDtoFromAggregateRoots;
  }

  /**
   * Gets fetch collection dto from aggregate roots.
   *
   * @return the fetch collection dto from aggregate roots
   */
  public Set<FetchCollectionDtoFromDomainObject> getFetchCollectionDtoFromAggregateRoots() {
    return fetchCollectionDtoFromAggregateRoots;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets domain object.
   *
   * @param domainObject the domain object
   */
  private void setDomainObject(BaseDomainObject<?> domainObject) {
    Assertion.isNotNull(domainObject, "domainObject is required");
    this.domainObject = domainObject;
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
      Set<FetchOneDtoFromDomainObject> fetchOneDtoFromAggregateRoots) {
    Assertion.isNotNull(fetchOneDtoFromAggregateRoots, "fetchOneDtoFromAggregateRoots is required");
    this.fetchOneDtoFromAggregateRoots = fetchOneDtoFromAggregateRoots;
  }

  /**
   * Sets fetch collection dto from aggregate roots.
   *
   * @param fetchCollectionDtoFromAggregateRoots the fetch collection dto from aggregate roots
   */
  private void setFetchCollectionDtoFromAggregateRoots(
      Set<FetchCollectionDtoFromDomainObject> fetchCollectionDtoFromAggregateRoots) {
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
    if (!super.equals(o)) {
      return false;
    }
    DomainObjectConverter that = (DomainObjectConverter) o;
    return Objects.equals(domainObject, that.domainObject)
        && Objects.equals(valueObjectFromDtos, that.valueObjectFromDtos)
        && Objects.equals(fetchOneDtoFromAggregateRoots, that.fetchOneDtoFromAggregateRoots)
        && Objects.equals(
            fetchCollectionDtoFromAggregateRoots, that.fetchCollectionDtoFromAggregateRoots);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(),
        domainObject,
        valueObjectFromDtos,
        fetchOneDtoFromAggregateRoots,
        fetchCollectionDtoFromAggregateRoots);
  }
}
