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

package io.polygenesis.generators.flutter.context.ui.widget.activity;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.metamodels.ui.screen.Screen;
import java.util.Set;

/**
 * The type Build template data.
 *
 * @author Christos Tsakostas
 */
public class BuildTemplateData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<Screen> screens;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Build template data.
   *
   * @param screens the screens
   */
  public BuildTemplateData(Set<Screen> screens) {
    setScreens(screens);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets screens.
   *
   * @return the screens
   */
  public Set<Screen> getScreens() {
    return screens;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets screens.
   *
   * @param screens the screens
   */
  private void setScreens(Set<Screen> screens) {
    Assertion.isNotNull(screens, "screens is required");
    this.screens = screens;
  }
}
