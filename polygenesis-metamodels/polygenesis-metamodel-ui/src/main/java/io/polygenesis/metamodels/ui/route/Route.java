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

package io.polygenesis.metamodels.ui.route;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Metamodel;
import io.polygenesis.metamodels.ui.screen.Screen;
import java.util.Objects;

public class Route implements Metamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Screen screen;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Route.
   *
   * @param screen the screen
   */
  public Route(Screen screen) {
    setScreen(screen);
  }

  // ===============================================================================================
  // BEHAVIOR
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets screen.
   *
   * @return the screen
   */
  public Screen getScreen() {
    return screen;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets screen.
   *
   * @param screen the screen
   */
  public void setScreen(Screen screen) {
    Assertion.isNotNull(screen, "screen is required");
    this.screen = screen;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName("app");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Route route = (Route) o;
    return Objects.equals(screen, route.screen);
  }

  @Override
  public int hashCode() {
    return Objects.hash(screen);
  }
}
