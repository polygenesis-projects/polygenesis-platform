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
public interface Data {

  /**
   * Gets data type.
   *
   * @return the data type
   */
  String getDataType();

  /**
   * Gets data primary type.
   *
   * @return the data primary type
   */
  DataPrimaryType getDataPrimaryType();

  /**
   * Gets variable name.
   *
   * @return the variable name
   */
  VariableName getVariableName();

  /**
   * Gets data business type.
   *
   * @return the data business type
   */
  DataPurpose getDataPurpose();

  /**
   * Gets data validator.
   *
   * @return the data validator
   */
  DataValidator getDataValidator();

  /**
   * Gets data source type.
   *
   * @return the data source type
   */
  DataSourceType getDataSourceType();

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets as data primitive.
   *
   * @return the as data primitive
   */
  DataPrimitive getAsDataPrimitive();

  /**
   * Gets as data object.
   *
   * @return the as data object
   */
  DataObject getAsDataObject();

  /**
   * Gets as data array.
   *
   * @return the as data array
   */
  DataArray getAsDataArray();

  /**
   * Gets as data map.
   *
   * @return the as data map
   */
  DataMap getAsDataMap();

  /**
   * Is primitive.
   *
   * @return the boolean
   */
  boolean isDataPrimitive();

  /**
   * Is data group boolean.
   *
   * @return the boolean
   */
  boolean isDataGroup();

  /**
   * Is data array boolean.
   *
   * @return the boolean
   */
  boolean isDataArray();

  /**
   * Is data map boolean.
   *
   * @return the boolean
   */
  boolean isDataMap();

  /**
   * Is thing identity boolean.
   *
   * @return the boolean
   */
  boolean isThingIdentity();

  /**
   * Is parent thing identity boolean.
   *
   * @return the boolean
   */
  boolean isParentThingIdentity();
}
