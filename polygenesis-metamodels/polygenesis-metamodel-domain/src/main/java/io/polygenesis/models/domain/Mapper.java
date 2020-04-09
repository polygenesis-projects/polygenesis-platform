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

package io.polygenesis.models.domain;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataMap;

/**
 * The type Mapper.
 *
 * @author Christos Tsakostas
 */
public class Mapper extends BaseProperty<DataMap> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Mapper.
   *
   * @param data the data
   */
  public Mapper(DataMap data) {
    super(PropertyType.MAP, data);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Data getTypeParameterData() {
    throw new UnsupportedOperationException();
  }

  /**
   * Gets type parameter data key.
   *
   * @return the type parameter data key
   */
  public Data getTypeParameterDataKey() {
    return getData().getKey();
  }

  /**
   * Gets type parameter data value.
   *
   * @return the type parameter data value
   */
  public Data getTypeParameterDataValue() {
    return getData().getValue();
  }
}
