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

package io.polygenesis.models.ui.container;

import io.polygenesis.models.ui.element.ElementGroup;

/**
 * The type Abstract container.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractContainerWithElements extends AbstractContainer {

  private ElementGroup elementGroup;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public AbstractContainerWithElements(
      ContainerName containerName,
      Top top,
      Right right,
      Bottom bottom,
      Left left,
      Center center,
      ElementGroup elementGroup) {
    super(containerName, top, right, bottom, left, center);
    this.elementGroup = elementGroup;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets element group.
   *
   * @return the element group
   */
  public ElementGroup getElementGroup() {
    return elementGroup;
  }
}
