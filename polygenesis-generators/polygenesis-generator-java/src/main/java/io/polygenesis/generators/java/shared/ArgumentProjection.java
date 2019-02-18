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

package io.polygenesis.generators.java.shared;

import com.oregor.ddd4j.check.assertion.Assertion;
import java.util.Objects;

/**
 * The type Argument projection.
 *
 * @author Christos Tsakostas
 */
public class ArgumentProjection {

  private Object key;
  private Object value;
  private Object annotations;
  private Boolean isThingIdentity;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Argument projection.
   *
   * @param key the key
   * @param value the value
   */
  public ArgumentProjection(Object key, Object value) {
    setKey(key);
    setValue(value);
    setThingIdentity(false);
  }

  /**
   * Instantiates a new Argument projection.
   *
   * @param key the key
   * @param value the value
   * @param isThingIdentity the is thing identity
   */
  public ArgumentProjection(Object key, Object value, Boolean isThingIdentity) {
    setKey(key);
    setValue(value);
    setThingIdentity(isThingIdentity);
  }

  /**
   * Instantiates a new Argument projection.
   *
   * @param key the key
   * @param value the value
   * @param annotations the annotations
   */
  public ArgumentProjection(Object key, Object value, Object annotations) {
    setKey(key);
    setValue(value);
    setAnnotations(annotations);
    setThingIdentity(false);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets key.
   *
   * @return the key
   */
  public Object getKey() {
    return key;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Gets annotations.
   *
   * @return the annotations
   */
  public Object getAnnotations() {
    return annotations;
  }

  /**
   * Gets thing identity.
   *
   * @return the thing identity
   */
  public Boolean getThingIdentity() {
    return isThingIdentity;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets key.
   *
   * @param key the key
   */
  private void setKey(Object key) {
    Assertion.isNotNull(key, "key is required");
    this.key = key;
  }

  /**
   * Sets value.
   *
   * @param value the value
   */
  private void setValue(Object value) {
    Assertion.isNotNull(value, "value is required");
    this.value = value;
  }

  /**
   * Sets annotations.
   *
   * @param annotations the annotations
   */
  private void setAnnotations(Object annotations) {
    Assertion.isNotNull(annotations, "annotations is required");
    this.annotations = annotations;
  }

  /**
   * Sets thing identity.
   *
   * @param thingIdentity the thing identity
   */
  private void setThingIdentity(Boolean thingIdentity) {
    isThingIdentity = thingIdentity;
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
    ArgumentProjection that = (ArgumentProjection) o;
    return Objects.equals(key, that.key)
        && Objects.equals(value, that.value)
        && Objects.equals(annotations, that.annotations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value, annotations);
  }
}
