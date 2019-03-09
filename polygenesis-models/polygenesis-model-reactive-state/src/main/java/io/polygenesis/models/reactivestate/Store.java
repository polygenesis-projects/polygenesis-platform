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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.commons.valueobjects.FeatureName;
import java.util.Objects;
import java.util.Set;

/**
 * Denotes a Reactive State Management Store for a Feature.
 *
 * @author Christos Tsakostas
 */
public class Store {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private FeatureName featureName;
  private Set<ActionGroup> actionGroups;
  private Set<EffectGroup> effectGroups;
  private Set<Model> models;
  private Set<ReducerGroup> reducerGroups;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Store.
   *
   * @param featureName the feature name
   * @param actionGroups the action groups
   * @param effectGroups the effect groups
   * @param models the models
   * @param reducerGroups the reducer groups
   */
  public Store(
      FeatureName featureName,
      Set<ActionGroup> actionGroups,
      Set<EffectGroup> effectGroups,
      Set<Model> models,
      Set<ReducerGroup> reducerGroups) {
    setFeatureName(featureName);
    setActionGroups(actionGroups);
    setEffectGroups(effectGroups);
    setModels(models);
    setReducerGroups(reducerGroups);
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
   * Gets action groups.
   *
   * @return the action groups
   */
  public Set<ActionGroup> getActionGroups() {
    return actionGroups;
  }

  /**
   * Gets effect groups.
   *
   * @return the effect groups
   */
  public Set<EffectGroup> getEffectGroups() {
    return effectGroups;
  }

  /**
   * Gets models.
   *
   * @return the models
   */
  public Set<Model> getModels() {
    return models;
  }

  /**
   * Gets reducer groups.
   *
   * @return the reducer groups
   */
  public Set<ReducerGroup> getReducerGroups() {
    return reducerGroups;
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
    Assertion.isNotNull(featureName, "featureName name is required");
    this.featureName = featureName;
  }

  /**
   * Sets action groups.
   *
   * @param actionGroups the action groups
   */
  private void setActionGroups(Set<ActionGroup> actionGroups) {
    Assertion.isNotNull(actionGroups, "actionGroups name is required");
    this.actionGroups = actionGroups;
  }

  /**
   * Sets effect groups.
   *
   * @param effectGroups the effect groups
   */
  private void setEffectGroups(Set<EffectGroup> effectGroups) {
    Assertion.isNotNull(effectGroups, "effectGroups name is required");
    this.effectGroups = effectGroups;
  }

  /**
   * Sets models.
   *
   * @param models the models
   */
  private void setModels(Set<Model> models) {
    Assertion.isNotNull(models, "models name is required");
    this.models = models;
  }

  /**
   * Sets reducer groups.
   *
   * @param reducerGroups the reducer groups
   */
  private void setReducerGroups(Set<ReducerGroup> reducerGroups) {
    Assertion.isNotNull(reducerGroups, "reducerGroups name is required");
    this.reducerGroups = reducerGroups;
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
    return Objects.equals(featureName, store.featureName)
        && Objects.equals(actionGroups, store.actionGroups)
        && Objects.equals(effectGroups, store.effectGroups)
        && Objects.equals(models, store.models)
        && Objects.equals(reducerGroups, store.reducerGroups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(featureName, actionGroups, effectGroups, models, reducerGroups);
  }
}
