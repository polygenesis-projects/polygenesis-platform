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

import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.VariableName;

/**
 * The type Aggregate entity.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntity extends ValueObject {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity.
   *
   * @param originatingIoModelGroup the originating io model group
   * @param ioModelGroup the io model group
   * @param variableName the variable name
   */
  public AggregateEntity(
      IoModelGroup originatingIoModelGroup, IoModelGroup ioModelGroup, VariableName variableName) {
    super(PropertyType.AGGREGATE_ENTITY, originatingIoModelGroup, ioModelGroup, variableName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

}
