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

package io.polygenesis.abstraction.thing;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.keyvalue.KeyValue;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Activity.
 *
 * @author Christos Tsakostas
 */
public class Activity {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<KeyValue> keyValues;

  private Set<Data> externalData;
  private Set<Function> externalFunctions;
  private Set<String> strings;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Empty activity.
   *
   * @return the activity
   */
  public static Activity empty() {
    return new Activity(new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(),
        new LinkedHashSet<>());
  }

  /**
   * Key value activity.
   *
   * @param keyValue the key value
   * @return the activity
   */
  public static Activity keyValue(KeyValue keyValue) {
    return new Activity(new LinkedHashSet<>(Arrays.asList(keyValue)),
        new LinkedHashSet<>(), new LinkedHashSet<>(),
        new LinkedHashSet<>());
  }

  /**
   * Key values activity.
   *
   * @param keyValues the key values
   * @return the activity
   */
  public static Activity keyValues(Set<KeyValue> keyValues) {
    return new Activity(keyValues, new LinkedHashSet<>(), new LinkedHashSet<>(),
        new LinkedHashSet<>());
  }

  /**
   * Message handler activity.
   *
   * @param jsonData the json data
   * @param handlerFunctions the handler functions
   * @return the activity
   */
  public static Activity messageHandler(Set<Data> jsonData, Set<Function> handlerFunctions) {
    return new Activity(new LinkedHashSet<>(), jsonData, handlerFunctions, new LinkedHashSet<>());
  }

  /**
   * Message handler supported messages activity.
   *
   * @param supportedMessages the supported messages
   * @return the activity
   */
  public static Activity messageHandlerSupportedMessages(Set<String> supportedMessages) {
    return new Activity(new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(),
        supportedMessages);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Activity.
   *
   * @param keyValues the key values
   * @param externalData the external data
   * @param externalFunctions the external functions
   * @param strings the strings
   */
  public Activity(Set<KeyValue> keyValues,
      Set<Data> externalData,
      Set<Function> externalFunctions, Set<String> strings) {
    setKeyValues(keyValues);
    setExternalData(externalData);
    setExternalFunctions(externalFunctions);
    setStrings(strings);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets key values.
   *
   * @return the key values
   */
  public Set<KeyValue> getKeyValues() {
    return keyValues;
  }

  /**
   * Gets external data.
   *
   * @return the external data
   */
  public Set<Data> getExternalData() {
    return externalData;
  }

  /**
   * Gets external functions.
   *
   * @return the external functions
   */
  public Set<Function> getExternalFunctions() {
    return externalFunctions;
  }

  /**
   * Gets strings.
   *
   * @return the strings
   */
  public Set<String> getStrings() {
    return strings;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets value.
   *
   * @param key the key
   * @return the value
   */
  public Object getValue(Object key) {
    return getKeyValues()
        .stream()
        .filter(keyValue -> keyValue.getKey().equals(key))
        .map(keyValue -> keyValue.getValue())
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets key values.
   *
   * @param keyValues the key values
   */
  private void setKeyValues(Set<KeyValue> keyValues) {
    Assertion.isNotNull(keyValues, "keyValues is required");
    this.keyValues = keyValues;
  }

  /**
   * Sets external data.
   *
   * @param externalData the external data
   */
  public void setExternalData(Set<Data> externalData) {
    Assertion.isNotNull(externalData, "externalData is required");
    this.externalData = externalData;
  }

  /**
   * Sets related functions.
   *
   * @param externalFunctions the related functions
   */
  private void setExternalFunctions(Set<Function> externalFunctions) {
    Assertion.isNotNull(externalFunctions, "externalFunctions is required");
    this.externalFunctions = externalFunctions;
  }

  /**
   * Sets strings.
   *
   * @param strings the strings
   */
  private void setStrings(Set<String> strings) {
    Assertion.isNotNull(strings, "strings is required");
    this.strings = strings;
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
    Activity activity = (Activity) o;
    return Objects.equals(externalData, activity.externalData) &&
        Objects.equals(externalFunctions, activity.externalFunctions) &&
        Objects.equals(strings, activity.strings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(externalData, externalFunctions, strings);
  }
}
