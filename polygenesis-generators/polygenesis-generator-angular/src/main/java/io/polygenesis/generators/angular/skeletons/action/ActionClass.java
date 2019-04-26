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

package io.polygenesis.generators.angular.skeletons.action;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

/**
 * The type Action class.
 *
 * @author Christos Tsakostas
 */
public class ActionClass {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String name;
  private String enumerationKey;
  private String payload;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action class.
   *
   * @param name the name
   * @param enumerationKey the enumeration key
   * @param payload the payload
   */
  public ActionClass(String name, String enumerationKey, String payload) {
    setName(name);
    setEnumerationKey(enumerationKey);
    setPayload(payload);
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
   * Gets enumeration key.
   *
   * @return the enumeration key
   */
  public String getEnumerationKey() {
    return enumerationKey;
  }

  /**
   * Gets payload.
   *
   * @return the payload
   */
  public String getPayload() {
    return payload;
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
   * Sets enumeration key.
   *
   * @param enumerationKey the enumeration key
   */
  private void setEnumerationKey(String enumerationKey) {
    Assertion.isNotNull(enumerationKey, "enumerationKey is required");
    this.enumerationKey = enumerationKey;
  }

  /**
   * Sets payload.
   *
   * @param payload the payload
   */
  private void setPayload(String payload) {
    Assertion.isNotNull(payload, "payload is required");
    this.payload = payload;
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
    ActionClass that = (ActionClass) o;
    return Objects.equals(name, that.name)
        && Objects.equals(enumerationKey, that.enumerationKey)
        && Objects.equals(payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, enumerationKey, payload);
  }
}
