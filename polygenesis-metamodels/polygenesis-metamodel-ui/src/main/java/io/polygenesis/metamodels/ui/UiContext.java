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

package io.polygenesis.metamodels.ui;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Metamodel;
import io.polygenesis.metamodels.ui.feature.Feature;
import java.util.LinkedHashSet;
import java.util.Set;

public class UiContext implements Metamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<Feature> features;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Ui context. */
  public UiContext() {
    setFeatures(new LinkedHashSet<>());
  }

  // ===============================================================================================
  // BEHAVIOR
  // ===============================================================================================

  /**
   * Add feature.
   *
   * @param feature the feature
   */
  public void addFeature(Feature feature) {
    Assertion.isNotNull(feature, "feature is required");
    getFeatures().add(feature);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets features.
   *
   * @return the features
   */
  public Set<Feature> getFeatures() {
    return features;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets features.
   *
   * @param features the features
   */
  private void setFeatures(Set<Feature> features) {
    Assertion.isNotNull(features, "features is required");
    this.features = features;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName("app");
  }
}
