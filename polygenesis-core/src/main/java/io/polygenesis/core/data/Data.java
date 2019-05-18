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

package io.polygenesis.core.data;

import java.util.Objects;

/**
 * This is the base class for any Data.
 *
 * <p>References:
 *
 * <ul>
 * <li>https://en.wikibooks.org/wiki/Java_Programming/Primitive_Types
 * <li>https://en.wikipedia.org/wiki/Primitive_data_type
 * <li>https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
 * </ul>
 *
 * @author Christos Tsakostas
 */
public abstract class Data {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final DataPrimaryType dataPrimaryType;
  private final DataSource dataSource;
  private final VariableName variableName;
  private final DataBusinessType dataBusinessType;
  private final DataValidator dataValidator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data.
   *
   * @param dataPrimaryType the data primary type
   * @param dataSource the data source
   * @param variableName the variable name
   * @param dataBusinessType the data business type
   * @param dataValidator the data validator
   */
  public Data(
      DataPrimaryType dataPrimaryType,
      DataSource dataSource,
      VariableName variableName,
      DataBusinessType dataBusinessType,
      DataValidator dataValidator) {
    this.dataPrimaryType = dataPrimaryType;
    this.dataSource = dataSource;
    this.variableName = variableName;
    this.dataBusinessType = dataBusinessType;
    this.dataValidator = dataValidator;
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
   * Gets data primary type.
   *
   * @return the data primary type
   */
  public DataPrimaryType getDataPrimaryType() {
    return dataPrimaryType;
  }

  /**
   * Gets data source.
   *
   * @return the data source
   */
  public DataSource getDataSource() {
    return dataSource;
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
   * Gets data business type.
   *
   * @return the data business type
   */
  public DataBusinessType getDataBusinessType() {
    return dataBusinessType;
  }

  /**
   * Gets data validator.
   *
   * @return the data validator
   */
  public DataValidator getDataValidator() {
    return dataValidator;
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
   * Gets as data group.
   *
   * @return the as data group
   */
  public DataGroup getAsDataGroup() {
    if (isDataGroup()) {
      return (DataGroup) this;
    } else {
      throw new IllegalStateException(
          String.format("Data of type=%s is not a DataGroup", getDataPrimaryType().name()));
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
    return getDataBusinessType().equals(DataBusinessType.THING_IDENTITY);
  }

  /**
   * Is parent thing identity boolean.
   *
   * @return the boolean
   */
  public boolean isParentThingIdentity() {
    return getDataBusinessType().equals(DataBusinessType.PARENT_THING_IDENTITY);
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
    Data data = (Data) o;
    return dataPrimaryType == data.dataPrimaryType
        && Objects.equals(dataSource, data.dataSource)
        && Objects.equals(variableName, data.variableName)
        && dataBusinessType == data.dataBusinessType
        && Objects.equals(dataValidator, data.dataValidator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataPrimaryType, dataSource, variableName, dataBusinessType, dataValidator);
  }
}
