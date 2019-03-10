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

package io.polygenesis.core.data;

import java.util.Objects;

/**
 * This is the base class for {@link DataPrimitive}, {@link DataGroup}, and {@link DataArray}*.
 *
 * <p>References:
 *
 * <ul>
 *   <li>https://en.wikibooks.org/wiki/Java_Programming/Primitive_Types
 *   <li>https://en.wikipedia.org/wiki/Primitive_data_type
 *   <li>https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
 * </ul>
 *
 * @author Christos Tsakostas
 */
public abstract class Data {

  private final DataKind dataKind;
  private final VariableName variableName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new data.
   *
   * @param dataKind the data kind
   */
  public Data(DataKind dataKind) {
    this(dataKind, null);
  }

  /**
   * Instantiates a new data.
   *
   * @param dataKind the data kind
   * @param variableName the variable name
   */
  public Data(DataKind dataKind, VariableName variableName) {
    this.dataKind = dataKind;
    this.variableName = variableName;
  }

  // ===============================================================================================
  // ABSTRACT
  // ===============================================================================================

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public abstract String getDataType();

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data kind.
   *
   * @return the data kind
   */
  public DataKind getDataKind() {
    return dataKind;
  }

  /**
   * Gets variable name.
   *
   * @return the variable name
   */
  public VariableName getVariableName() {
    return variableName;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets as data group.
   *
   * @return the as data group
   */
  public DataGroup getAsDataGroup() {
    if (isDataGroup()) {
      return (DataGroup) this;
    } else {
      throw new IllegalStateException(
          String.format("Model of type=%s is not a DataGroup", getDataKind().name()));
    }
  }

  /**
   * Gets as data array.
   *
   * @return the as data array
   */
  public DataArray getAsDataArray() {
    if (isDataArray()) {
      return DataArray.class.cast(this);
    } else {
      throw new IllegalStateException(
          String.format("Model of type=%s is not a DataArray", getDataKind().name()));
    }
  }

  /**
   * Is primitive.
   *
   * @return the boolean
   */
  public boolean isDataPrimitive() {
    return getDataKind().equals(DataKind.PRIMITIVE);
  }

  /**
   * Is data group boolean.
   *
   * @return the boolean
   */
  public boolean isDataGroup() {
    return getDataKind().equals(DataKind.OBJECT);
  }

  /**
   * Is data array boolean.
   *
   * @return the boolean
   */
  public boolean isDataArray() {
    return getDataKind().equals(DataKind.ARRAY);
  }

  /**
   * Is thing identity boolean.
   *
   * @return the boolean
   */
  public boolean isThingIdentity() {
    if (this instanceof DataPrimitive) {
      return ((DataPrimitive) this).getThingIdentity();
    } else {
      return false;
    }
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
    Data data = (Data) o;
    return dataKind == data.dataKind && Objects.equals(variableName, data.variableName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataKind, variableName);
  }
}
