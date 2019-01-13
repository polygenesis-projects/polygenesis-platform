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

package io.polygenesis.models.state;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Denotes a State Management Store for a Feature.
 *
 * @author Christos Tsakostas
 */
public class Store {

  private Feature feature;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Store.
   *
   * @param feature the feature
   */
  public Store(Feature feature) {
    setFeature(feature);
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

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setFeature(Feature feature) {
    this.feature = feature;
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

    Store store = (Store) o;

    return new EqualsBuilder().append(feature, store.feature).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(feature).toHashCode();
  }
}
