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

package io.polygenesis.models.domain;

import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataGroup;

/**
 * The type Value object.
 *
 * @author Christos Tsakostas
 */
public class ValueObject extends BaseProperty<DataGroup> {

  // TODO: should be removed
  private DataGroup originatingDataGroup;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object.
   *
   * @param data the data
   * @param originatingDataGroup the originating data group
   */
  public ValueObject(DataGroup data, DataGroup originatingDataGroup) {
    super(PropertyType.VALUE_OBJECT, data);
    this.originatingDataGroup = originatingDataGroup;
  }

  // ===============================================================================================
  // GETTER
  // ===============================================================================================

  /**
   * Gets originating data group.
   *
   * @return the originating data group
   */
  public DataGroup getOriginatingDataGroup() {
    return originatingDataGroup;
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Data getTypeParameterData() {
    throw new UnsupportedOperationException();
  }
}
