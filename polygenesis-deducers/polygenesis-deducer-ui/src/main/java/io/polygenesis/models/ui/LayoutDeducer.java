/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.models.ui;

import io.polygenesis.models.ui.container.Bottom;
import io.polygenesis.models.ui.container.Center;
import io.polygenesis.models.ui.container.ContainerName;
import io.polygenesis.models.ui.container.LayoutContainer;
import io.polygenesis.models.ui.container.Left;
import io.polygenesis.models.ui.container.Right;
import io.polygenesis.models.ui.container.Top;
import io.polygenesis.models.ui.element.ElementGroup;
import io.polygenesis.models.ui.element.ElementName;
import java.util.LinkedHashSet;
import java.util.Set;

public class LayoutDeducer {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce layouts from features set.
   *
   * @return the set
   */
  public Set<LayoutContainer> deduceLayoutsFromFeatures() {
    Set<LayoutContainer> layoutContainers = new LinkedHashSet<>();

    layoutContainers.add(createDefaultLayoutContainer());

    return layoutContainers;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private LayoutContainer createDefaultLayoutContainer() {
    Top top =
        new Top(
            new ContainerName("default-layout-top"),
            new ElementGroup(
                new ElementName("default-layout-top-element-group"), new LinkedHashSet<>()));
    Right right = null;
    Bottom bottom = null;
    Left left = null;
    Center center = null;

    return new LayoutContainer(
        new ContainerName("default-layout"), top, right, bottom, left, center);
  }
}
