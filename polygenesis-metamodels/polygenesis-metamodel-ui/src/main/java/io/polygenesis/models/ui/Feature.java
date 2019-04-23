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

package io.polygenesis.models.ui;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.FeatureName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Metamodel;
import io.polygenesis.models.ui.container.AbstractContainer;
import java.util.Set;

/**
 * Encapsulates all of the UI models related to a Feature. A Feature is equivalent to a {@link
 * io.polygenesis.core.Thing} from the core model.
 *
 * @author Christos Tsakostas
 * @see io.polygenesis.core.Thing
 */
public class Feature implements Metamodel {

  private ContextName contextName;
  private FeatureName featureName;
  private Set<AbstractContainer> containers;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Feature.
   *
   * @param contextName the context name
   * @param featureName the feature name
   * @param containers the containers
   */
  public Feature(
      ContextName contextName, FeatureName featureName, Set<AbstractContainer> containers) {
    setContextName(contextName);
    setFeatureName(featureName);
    setContainers(containers);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(featureName.getText());
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ContextName getContextName() {
    return contextName;
  }

  /**
   * Gets feature name.
   *
   * @return the feature name
   */
  public FeatureName getFeatureName() {
    return featureName;
  }

  /**
   * Gets containers.
   *
   * @return the containers
   */
  public Set<AbstractContainer> getContainers() {
    return containers;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets context name.
   *
   * @param contextName the context name
   */
  public void setContextName(ContextName contextName) {
    Assertion.isNotNull(contextName, "contextName is required");
    this.contextName = contextName;
  }

  /**
   * Sets feature name.
   *
   * @param featureName the feature name
   */
  private void setFeatureName(FeatureName featureName) {
    Assertion.isNotNull(featureName, "Feature Name is required");
    this.featureName = featureName;
  }

  /**
   * Sets containers.
   *
   * @param containers the containers
   */
  private void setContainers(Set<AbstractContainer> containers) {
    Assertion.isNotNull(containers, "containers is required");
    this.containers = containers;
  }
}
