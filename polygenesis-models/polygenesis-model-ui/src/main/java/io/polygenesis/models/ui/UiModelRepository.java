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

package io.polygenesis.models.ui;

import io.polygenesis.commons.assertions.Assertion;
import io.polygenesis.core.ModelRepository;
import java.util.Set;

/**
 * The type Ui model repository.
 *
 * @author Christos Tsakostas
 */
public class UiModelRepository implements ModelRepository {

  private Set<Feature> features;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Ui model repository.
   *
   * @param features the features
   */
  public UiModelRepository(Set<Feature> features) {
    setFeatures(features);
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
}
