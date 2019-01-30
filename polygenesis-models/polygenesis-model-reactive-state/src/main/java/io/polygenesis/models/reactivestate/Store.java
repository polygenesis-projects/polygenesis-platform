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

package io.polygenesis.models.reactivestate;

import io.polygenesis.commons.assertions.Assertion;
import io.polygenesis.commons.feature.FeatureName;
import java.util.Objects;
import java.util.Set;

/**
 * Denotes a Reactive State Management Store for a Feature.
 *
 * <p>A Store is a container for {@link Action Actions}, {@link Effect Effects}.
 *
 * @author Christos Tsakostas
 */
public class Store {

  private FeatureName featureName;
  private Set<Action> actions;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Store.
   *
   * @param featureName the feature name
   * @param actions the actions
   */
  public Store(FeatureName featureName, Set<Action> actions) {
    setFeatureName(featureName);
    setActions(actions);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets featureName.
   *
   * @return the featureName
   */
  public FeatureName getFeatureName() {
    return featureName;
  }

  /**
   * Gets actions.
   *
   * @return the actions
   */
  public Set<Action> getActions() {
    return actions;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setFeatureName(FeatureName featureName) {
    Assertion.isNotNull(featureName, "featureName name is required");
    this.featureName = featureName;
  }

  private void setActions(Set<Action> actions) {
    Assertion.isNotNull(actions, "actions is required");
    this.actions = actions;
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
    return Objects.equals(featureName, store.featureName) && Objects.equals(actions, store.actions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(featureName, actions);
  }
}
