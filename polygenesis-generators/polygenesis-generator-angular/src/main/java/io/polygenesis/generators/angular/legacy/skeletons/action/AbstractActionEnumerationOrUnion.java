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

package io.polygenesis.generators.angular.legacy.skeletons.action;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.keyvalue.KeyValue;
import java.util.Objects;
import java.util.Set;

/**
 * The type Abstract action enumeration or union.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractActionEnumerationOrUnion {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String name;
  private Set<KeyValue> keyValues;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract action enumeration or union.
   *
   * @param name the name
   * @param keyValues the key values
   */
  public AbstractActionEnumerationOrUnion(String name, Set<KeyValue> keyValues) {
    setName(name);
    setKeyValues(keyValues);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets key values.
   *
   * @return the key values
   */
  public Set<KeyValue> getKeyValues() {
    return keyValues;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(String name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets key values.
   *
   * @param keyValues the key values
   */
  private void setKeyValues(Set<KeyValue> keyValues) {
    Assertion.isNotNull(keyValues, "keyValues is required");
    this.keyValues = keyValues;
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
    AbstractActionEnumerationOrUnion that = (AbstractActionEnumerationOrUnion) o;
    return Objects.equals(name, that.name) && Objects.equals(keyValues, that.keyValues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, keyValues);
  }
}
