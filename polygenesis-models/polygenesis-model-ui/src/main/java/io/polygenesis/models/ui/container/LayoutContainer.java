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

package io.polygenesis.models.ui.container;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Model;

/**
 * The type Layout container.
 *
 * @author Christos Tsakostas
 */
public class LayoutContainer extends InlineContainer implements Model {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Layout container.
   *
   * @param containerName the container name
   * @param top the top
   * @param right the right
   * @param bottom the bottom
   * @param left the left
   * @param center the center
   */
  public LayoutContainer(
      ContainerName containerName, Top top, Right right, Bottom bottom, Left left, Center center) {
    super(containerName, top, right, bottom, left, center);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return null;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

}
