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
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.domain.BaseDomainObject;
import java.util.Objects;

/**
 * The type Abstract fetch dto from domain object.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractFetchDtoFromDomainObject {

  private Dto dto;
  private BaseDomainObject<?> domainObject;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract fetch dto from domain object.
   *
   * @param dto the dto
   * @param domainObject the domain object
   */
  public AbstractFetchDtoFromDomainObject(Dto dto, BaseDomainObject<?> domainObject) {
    setDto(dto);
    setDomainObject(domainObject);
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
   * Gets domain object.
   *
   * @return the domain object
   */
  public BaseDomainObject<?> getDomainObject() {
    return domainObject;
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
    Assertion.isNotNull(dto, "dto is required");
    this.dto = dto;
  }

  /**
   * Sets domain object.
   *
   * @param domainObject the domain object
   */
  private void setDomainObject(BaseDomainObject<?> domainObject) {
    Assertion.isNotNull(domainObject, "domainObject is required");
    this.domainObject = domainObject;
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
    AbstractFetchDtoFromDomainObject that = (AbstractFetchDtoFromDomainObject) o;
    return Objects.equals(dto, that.dto) && Objects.equals(domainObject, that.domainObject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dto, domainObject);
  }
}
