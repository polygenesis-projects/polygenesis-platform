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

package io.polygenesis.models.api;

import io.polygenesis.core.iomodel.IoModelGroup;

/**
 * The type Dto.
 *
 * @author Christos Tsakostas
 */
public class Dto {

  private IoModelGroup originatingIoModelGroup;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto.
   *
   * @param originatingIoModelGroup the originating io model group
   */
  public Dto(IoModelGroup originatingIoModelGroup) {
    setOriginatingIoModelGroup(originatingIoModelGroup);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets originating io model group.
   *
   * @return the originating io model group
   */
  public IoModelGroup getOriginatingIoModelGroup() {
    return originatingIoModelGroup;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets originating io model group.
   *
   * @param originatingIoModelGroup the originating io model group
   */
  private void setOriginatingIoModelGroup(IoModelGroup originatingIoModelGroup) {
    this.originatingIoModelGroup = originatingIoModelGroup;
  }
}
