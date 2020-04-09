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

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.models.ui.container.AbstractContainer;
import io.polygenesis.models.ui.container.Bottom;
import io.polygenesis.models.ui.container.Center;
import io.polygenesis.models.ui.container.ContainerName;
import io.polygenesis.models.ui.container.InlineContainer;
import io.polygenesis.models.ui.container.Left;
import io.polygenesis.models.ui.container.Right;
import io.polygenesis.models.ui.container.Top;

public class ContainerDeducer {

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
   * Deduce container from thing function.
   *
   * @param feature the feature
   * @param function the function
   * @return the container
   */
  public AbstractContainer deduceContainerFromThingFunction(Feature feature, Function function) {
    return new InlineContainer(
        new ContainerName(function.getName().getFullName()),
        new Top(null, null, null, null, null, null, null),
        new Right(null, null, null, null, null, null, null),
        new Bottom(null, null, null, null, null, null, null),
        new Left(null, null, null, null, null, null, null),
        new Center(null, null, null, null, null, null, null));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

}
