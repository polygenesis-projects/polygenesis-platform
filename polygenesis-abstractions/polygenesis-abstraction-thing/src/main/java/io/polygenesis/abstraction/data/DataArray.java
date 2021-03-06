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

package io.polygenesis.abstraction.data;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Objects;

/**
 * The type data array.
 *
 * @author Christos Tsakostas
 */
public class DataArray extends AbstractData {

  private final Data arrayElement;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of data array.
   *
   * @param arrayElement the array element
   * @return the data array
   */
  public static DataArray of(Data arrayElement) {
    return new DataArray(VariableName.response(), arrayElement);
  }

  /**
   * Of data array.
   *
   * @param arrayElement the array element
   * @param variableName the variable name
   * @return the data array
   */
  public static DataArray of(Data arrayElement, String variableName) {
    return new DataArray(new VariableName(variableName), arrayElement);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data array.
   *
   * @param variableName the variable name
   * @param arrayElement the array element
   */
  public DataArray(VariableName variableName, Data arrayElement) {
    this(variableName, DataPurpose.any(), DataValidator.empty(), arrayElement);
  }

  /**
   * Instantiates a new Data array.
   *
   * @param variableName the variable name
   * @param dataPurpose the data business type
   * @param dataValidator the data validator
   * @param arrayElement the array element
   */
  public DataArray(
      VariableName variableName,
      DataPurpose dataPurpose,
      DataValidator dataValidator,
      Data arrayElement) {
    super(
        DataPrimaryType.ARRAY,
        variableName != null
            ? new VariableName(TextConverter.toPlural(variableName.getText()))
            : null,
        dataPurpose,
        dataValidator,
        DataSourceType.DEFAULT);
    this.arrayElement = arrayElement;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets array element.
   *
   * @return the array element
   */
  public Data getArrayElement() {
    return arrayElement;
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATION
  // ===============================================================================================

  @Override
  public String getDataType() {
    return String.format("%s;%s", DataPrimaryType.ARRAY.name(), getArrayElement().getDataType());
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
    DataArray that = (DataArray) o;
    return Objects.equals(arrayElement, that.arrayElement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), arrayElement);
  }
}
