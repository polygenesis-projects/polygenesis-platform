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

import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Objects;

/**
 * This is the base class for any Data.
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
public abstract class AbstractData implements Data {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final DataPrimaryType dataPrimaryType;
  private final VariableName variableName;
  private final DataPurpose dataPurpose;
  private final DataValidator dataValidator;
  private final DataSourceType dataSourceType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates new Data.
   *
   * @param dataPrimaryType the data primary type
   * @param variableName the variable name
   * @param dataPurpose the data business type
   * @param dataValidator the data validator
   */
  protected AbstractData(
      DataPrimaryType dataPrimaryType,
      VariableName variableName,
      DataPurpose dataPurpose,
      DataValidator dataValidator,
      DataSourceType dataSourceType) {
    this.dataPrimaryType = dataPrimaryType;
    this.variableName = variableName;
    this.dataPurpose = dataPurpose;
    this.dataValidator = dataValidator;
    this.dataSourceType = dataSourceType;
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
  // GETTERS - OVERRIDES
  // ===============================================================================================

  @Override
  public DataPrimaryType getDataPrimaryType() {
    return dataPrimaryType;
  }

  @Override
  public VariableName getVariableName() {
    return variableName;
  }

  @Override
  public DataPurpose getDataPurpose() {
    return dataPurpose;
  }

  @Override
  public DataValidator getDataValidator() {
    return dataValidator;
  }

  @Override
  public DataSourceType getDataSourceType() {
    return dataSourceType;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets as data primitive.
   *
   * @return the as data primitive
   */
  public DataPrimitive getAsDataPrimitive() {
    if (isDataPrimitive()) {
      return (DataPrimitive) this;
    } else {
      throw new IllegalStateException(
          String.format("Data of type=%s is not a DataPrimitive", getDataPrimaryType().name()));
    }
  }

  /**
   * Gets as data object.
   *
   * @return the as data object
   */
  public DataObject getAsDataObject() {
    if (isDataGroup()) {
      return (DataObject) this;
    } else {
      throw new IllegalStateException(
          String.format(
              "Data of type=%s with name=%s is not a DataObject",
              getDataPrimaryType().name(), getVariableName().getText()));
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
          String.format("Data of type=%s is not a DataArray", getDataPrimaryType().name()));
    }
  }

  /**
   * Gets as data map.
   *
   * @return the as data map
   */
  public DataMap getAsDataMap() {
    if (isDataMap()) {
      return (DataMap) this;
    } else {
      throw new IllegalStateException(
          String.format("Data of type=%s is not a DataMap", getDataPrimaryType().name()));
    }
  }

  /**
   * Is primitive.
   *
   * @return the boolean
   */
  public boolean isDataPrimitive() {
    return getDataPrimaryType().equals(DataPrimaryType.PRIMITIVE);
  }

  /**
   * Is data group boolean.
   *
   * @return the boolean
   */
  public boolean isDataGroup() {
    return getDataPrimaryType().equals(DataPrimaryType.OBJECT);
  }

  /**
   * Is data array boolean.
   *
   * @return the boolean
   */
  public boolean isDataArray() {
    return getDataPrimaryType().equals(DataPrimaryType.ARRAY);
  }

  /**
   * Is data map boolean.
   *
   * @return the boolean
   */
  public boolean isDataMap() {
    return getDataPrimaryType().equals(DataPrimaryType.MAP);
  }

  /**
   * Is thing identity boolean.
   *
   * @return the boolean
   */
  public boolean isThingIdentity() {
    return getDataPurpose().equals(DataPurpose.thingIdentity());
  }

  /**
   * Is parent thing identity boolean.
   *
   * @return the boolean
   */
  public boolean isParentThingIdentity() {
    return getDataPurpose().equals(DataPurpose.parentThingIdentity());
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

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
    AbstractData that = (AbstractData) o;
    return dataPrimaryType == that.dataPrimaryType
        && Objects.equals(variableName, that.variableName)
        && Objects.equals(dataPurpose, that.dataPurpose)
        && Objects.equals(dataValidator, that.dataValidator)
        && dataSourceType == that.dataSourceType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataPrimaryType, variableName, dataPurpose, dataValidator, dataSourceType);
  }
}
