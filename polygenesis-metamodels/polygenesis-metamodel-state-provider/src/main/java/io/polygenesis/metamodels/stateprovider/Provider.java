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

package io.polygenesis.metamodels.stateprovider;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.FeatureName;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Metamodel;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Provider.
 *
 * @author Christos Tsakostas
 */
public class Provider implements Metamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Name name;
  private FeatureName featureName;
  private ProviderType providerType;
  private Set<ProviderMethod> providerMethods;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Provider.
   *
   * @param name the name
   * @param featureName the feature name
   * @param providerType the provider type
   */
  public Provider(Name name, FeatureName featureName, ProviderType providerType) {
    setName(name);
    setFeatureName(featureName);
    setProviderType(providerType);
    setProviderMethods(new LinkedHashSet<>());
  }

  // ===============================================================================================
  // BEHAVIOR
  // ===============================================================================================

  /**
   * Add provider method.
   *
   * @param providerMethod the provider method
   */
  public void addProviderMethod(ProviderMethod providerMethod) {
    Assertion.isNotNull(providerMethod, "providerMethod is required");
    providerMethods.add(providerMethod);
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
   * Gets name.
   *
   * @return the name
   */
  public Name getName() {
    return name;
  }

  /**
   * Gets featureName.
   *
   * @return the featureName
   */
  public FeatureName getFeatureName() {
    return featureName;
  }

  /**
   * Gets provider type.
   *
   * @return the provider type
   */
  public ProviderType getProviderType() {
    return providerType;
  }

  /**
   * Gets provider methods.
   *
   * @return the provider methods
   */
  public Set<ProviderMethod> getProviderMethods() {
    return providerMethods;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(Name name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

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
   * Sets provider type.
   *
   * @param providerType the provider type
   */
  private void setProviderType(ProviderType providerType) {
    Assertion.isNotNull(providerType, "providerType is required");
    this.providerType = providerType;
  }

  /**
   * Sets provider methods.
   *
   * @param providerMethods the provider methods
   */
  private void setProviderMethods(Set<ProviderMethod> providerMethods) {
    Assertion.isNotNull(providerMethods, "providerMethods is required");
    this.providerMethods = providerMethods;
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
    Provider provider = (Provider) o;
    return Objects.equals(featureName, provider.featureName)
        && Objects.equals(providerMethods, provider.providerMethods);
  }

  @Override
  public int hashCode() {
    return Objects.hash(featureName, providerMethods);
  }
}
