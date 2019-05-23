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
import io.polygenesis.abstraction.data.DataGroup;

/**
 * The type Reference to aggregate root.
 *
 * @author Christos Tsakostas
 */
public class ReferenceToAggregateRoot extends BaseProperty<DataGroup> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Reference to aggregate root.
   *
   * @param data the data
   */
  public ReferenceToAggregateRoot(DataGroup data) {
    super(PropertyType.REFERENCE_TO_AGGREGATE_ROOT, data);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Data getTypeParameterData() {
    throw new UnsupportedOperationException();
  }
}
