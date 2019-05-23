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

package io.polygenesis.abstraction.thing;

import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.data.DataMap;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;

/**
 * The type Thing data container.
 *
 * @author Christos Tsakostas
 */
public class ThingDataContainer {

  private DataPurpose dataPurpose;
  private DataArray dataArray;
  private DataGroup dataGroup;
  private DataMap dataMap;
  private DataPrimitive dataPrimitive;

  /**
   * Array thing data container.
   *
   * @param dataArray the data array
   * @return the thing data container
   */
  public static ThingDataContainer array(DataArray dataArray) {
    return new ThingDataContainer(DataPurpose.any(), dataArray, null, null, null);
  }

  /**
   * Group thing data container.
   *
   * @param dataGroup the data group
   * @return the thing data container
   */
  public static ThingDataContainer group(DataGroup dataGroup) {
    return new ThingDataContainer(DataPurpose.any(), null, dataGroup, null, null);
  }

  /**
   * Map thing data container.
   *
   * @param dataMap the data map
   * @return the thing data container
   */
  public static ThingDataContainer map(DataMap dataMap) {
    return new ThingDataContainer(DataPurpose.any(), null, null, dataMap, null);
  }

  /**
   * Primitive thing data container.
   *
   * @param dataPrimitive the data primitive
   * @return the thing data container
   */
  public static ThingDataContainer primitive(DataPrimitive dataPrimitive) {
    return new ThingDataContainer(DataPurpose.any(), null, null, null, dataPrimitive);
  }

  /** Instantiates a new Thing data container. */
  protected ThingDataContainer() {
    super();
  }

  private ThingDataContainer(
      DataPurpose dataPurpose,
      DataArray dataArray,
      DataGroup dataGroup,
      DataMap dataMap,
      DataPrimitive dataPrimitive) {
    this.dataPurpose = dataPurpose;
    this.dataArray = dataArray;
    this.dataGroup = dataGroup;
    this.dataMap = dataMap;
    this.dataPrimitive = dataPrimitive;
  }

  /**
   * Gets thing data type.
   *
   * @return the thing data type
   */
  public DataPurpose getDataPurpose() {
    return dataPurpose;
  }

  /**
   * Gets data array.
   *
   * @return the data array
   */
  public DataArray getDataArray() {
    return dataArray;
  }

  /**
   * Gets data group.
   *
   * @return the data group
   */
  public DataGroup getDataGroup() {
    return dataGroup;
  }

  /**
   * Gets data map.
   *
   * @return the data map
   */
  public DataMap getDataMap() {
    return dataMap;
  }

  /**
   * Gets data primitive.
   *
   * @return the data primitive
   */
  public DataPrimitive getDataPrimitive() {
    return dataPrimitive;
  }
}
