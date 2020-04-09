/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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
import io.polygenesis.models.domain.ValueObject;
import java.util.Objects;

public class ValueObjectDtoPair {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ValueObject valueObject;
  private Dto dto;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object dto pair.
   *
   * @param valueObject the value object
   * @param dto the dto
   */
  public ValueObjectDtoPair(ValueObject valueObject, Dto dto) {
    this.valueObject = valueObject;
    this.dto = dto;
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
    ValueObjectDtoPair that = (ValueObjectDtoPair) o;
    return Objects.equals(valueObject, that.valueObject)
        && Objects.equals(dto.getDataObject(), that.dto.getDataObject());
  }

  @Override
  public int hashCode() {
    return Objects.hash(valueObject, dto.getDataObject());
  }
}
