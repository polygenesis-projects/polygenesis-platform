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

package io.polygenesis.metamodels.ui.feature;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.FeatureName;
import io.polygenesis.metamodels.ui.screen.Screen;
import io.polygenesis.metamodels.ui.widget.Widget;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Feature {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private FeatureName featureName;
  private ContextName contextName;
  private Set<Screen> screens;
  private Set<Widget> widgets;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Feature.
   *
   * @param featureName the feature name
   * @param contextName the context name
   */
  public Feature(FeatureName featureName, ContextName contextName) {
    setFeatureName(featureName);
    setContextName(contextName);
    setScreens(new LinkedHashSet<>());
    setWidgets(new LinkedHashSet<>());
  }

  // ===============================================================================================
  // BEHAVIOR
  // ===============================================================================================

  /**
   * Add screens.
   *
   * @param screens the screens
   */
  public void addScreens(Set<Screen> screens) {
    Assertion.isNotNull(screens, "screens is required");
    getScreens().addAll(screens);
  }

  /**
   * Add widgets.
   *
   * @param widgets the widgets
   */
  public void addWidgets(Set<Widget> widgets) {
    Assertion.isNotNull(widgets, "widgets is required");
    getWidgets().addAll(widgets);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets feature name.
   *
   * @return the feature name
   */
  public FeatureName getFeatureName() {
    return featureName;
  }

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ContextName getContextName() {
    return contextName;
  }

  /**
   * Gets screens.
   *
   * @return the screens
   */
  public Set<Screen> getScreens() {
    return screens;
  }

  /**
   * Gets widgets.
   *
   * @return the widgets
   */
  public Set<Widget> getWidgets() {
    return widgets;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets feature name.
   *
   * @param featureName the feature name
   */
  private void setFeatureName(FeatureName featureName) {
    Assertion.isNotNull(featureName, "featureName is required");
    this.featureName = featureName;
  }

  /**
   * Sets context name.
   *
   * @param contextName the context name
   */
  private void setContextName(ContextName contextName) {
    Assertion.isNotNull(contextName, "contextName is required");
    this.contextName = contextName;
  }

  /**
   * Sets screens.
   *
   * @param screens the screens
   */
  private void setScreens(Set<Screen> screens) {
    Assertion.isNotNull(screens, "screens is required");
    this.screens = screens;
  }

  /**
   * Sets widgets.
   *
   * @param widgets the widgets
   */
  private void setWidgets(Set<Widget> widgets) {
    Assertion.isNotNull(widgets, "widgets is required");
    this.widgets = widgets;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Feature feature = (Feature) o;
    return Objects.equals(featureName, feature.featureName)
        && Objects.equals(contextName, feature.contextName)
        && Objects.equals(screens, feature.screens)
        && Objects.equals(widgets, feature.widgets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(featureName, contextName);
  }
}
