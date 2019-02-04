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

package io.polygenesis.core.datatype;

import java.util.Optional;

/**
 * The type Map data type.
 *
 * @author Christos Tsakostas
 */
public class MapDataType extends AbstractDataType {

  private MapType mapType;
  private AbstractDataType key;
  private AbstractDataType value;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Map data type.
   *
   * @param dataTypeName the data type name
   * @param mapType the kind of map data type
   * @param key the key
   * @param value the value
   */
  public MapDataType(
      DataTypeName dataTypeName, MapType mapType, AbstractDataType key, AbstractDataType value) {
    super(DataKind.MAP, dataTypeName);
    this.mapType = mapType;
    this.key = key;
    this.value = value;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets kind of map data type.
   *
   * @return the kind of map data type
   */
  public MapType getMapType() {
    return mapType;
  }

  /**
   * Gets key.
   *
   * @return the key
   */
  public AbstractDataType getKey() {
    return key;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public AbstractDataType getValue() {
    return value;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Optional<PackageName> getOptionalPackageName() {
    return Optional.empty();
  }
}
