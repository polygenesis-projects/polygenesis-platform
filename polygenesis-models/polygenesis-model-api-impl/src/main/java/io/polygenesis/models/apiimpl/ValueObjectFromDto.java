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
import io.polygenesis.models.domain.ValueObject;
import java.util.Objects;

/**
 * The type Value object from dto.
 *
 * @author Christos Tsakostas
 */
public class ValueObjectFromDto {

  private ValueObject valueObject;
  private Dto dto;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object from dto.
   *
   * @param valueObject the value object
   * @param dto the dto
   */
  public ValueObjectFromDto(ValueObject valueObject, Dto dto) {
    setValueObject(valueObject);
    setDto(dto);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets value object.
   *
   * @return the value object
   */
  public ValueObject getValueObject() {
    return valueObject;
  }

  /**
   * Gets dto.
   *
   * @return the dto
   */
  public Dto getDto() {
    return dto;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets value object.
   *
   * @param valueObject the value object
   */
  private void setValueObject(ValueObject valueObject) {
    this.valueObject = valueObject;
  }

  /**
   * Sets dto.
   *
   * @param dto the dto
   */
  private void setDto(Dto dto) {
    this.dto = dto;
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
    ValueObjectFromDto that = (ValueObjectFromDto) o;
    return Objects.equals(valueObject, that.valueObject) && Objects.equals(dto, that.dto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valueObject, dto);
  }
}
