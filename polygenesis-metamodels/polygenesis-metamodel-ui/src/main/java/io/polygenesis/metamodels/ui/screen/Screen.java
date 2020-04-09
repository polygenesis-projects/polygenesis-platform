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

package io.polygenesis.metamodels.ui.screen;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Nameable;
import io.polygenesis.metamodels.ui.feature.Feature;
import java.util.Objects;

public class Screen implements Generatable, Nameable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Feature feature;
  private Name name;
  private ScreenType screenType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Screen.
   *
   * @param feature the feature
   * @param name the name
   * @param screenType the screen type
   */
  public Screen(Feature feature, Name name, ScreenType screenType) {
    setFeature(feature);
    setName(name);
    setScreenType(screenType);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets feature.
   *
   * @return the feature
   */
  public Feature getFeature() {
    return feature;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public Name getName() {
    return name;
  }

  /**
   * Gets screen type.
   *
   * @return the screen type
   */
  public ScreenType getScreenType() {
    return screenType;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets feature.
   *
   * @param feature the feature
   */
  private void setFeature(Feature feature) {
    Assertion.isNotNull(feature, "feature is required");
    this.feature = feature;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(Name name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets screen type.
   *
   * @param screenType the screen type
   */
  private void setScreenType(ScreenType screenType) {
    Assertion.isNotNull(screenType, "screenType is required");
    this.screenType = screenType;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(getName().getText());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Screen screen = (Screen) o;
    return Objects.equals(feature, screen.feature)
        && Objects.equals(name, screen.name)
        && screenType == screen.screenType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(feature, name, screenType);
  }
}
