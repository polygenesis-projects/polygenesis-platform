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

package io.polygenesis.generators.java.api;

import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.models.api.Method;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Method projection transforms data from {@link io.polygenesis.models.api.Method} to a friendly
 * format for the Java Generators.
 *
 * @author Christos Tsakostas
 */
public class MethodProjection {

  private Method method;
  private String name;
  private String description;
  private String returnValue;
  private Set<KeyValue> arguments;
  private String argumentsCommaSeparated;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Method projection.
   *
   * @param method the method
   * @param name the name
   * @param description the description
   * @param returnValue the return value
   * @param arguments the arguments
   */
  public MethodProjection(
      Method method, String name, String description, String returnValue, Set<KeyValue> arguments) {
    setMethod(method);
    setName(name);
    setDescription(description);
    setReturnValue(returnValue);
    setArguments(arguments);
    setArgumentsCommaSeparated(makeArgumentsCommaSeparated(arguments));
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets method.
   *
   * @return the method
   */
  public Method getMethod() {
    return method;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets return value.
   *
   * @return the return value
   */
  public String getReturnValue() {
    return returnValue;
  }

  /**
   * Gets arguments.
   *
   * @return the arguments
   */
  public Set<KeyValue> getArguments() {
    return arguments;
  }

  /**
   * Gets arguments comma separated.
   *
   * @return the arguments comma separated
   */
  public String getArgumentsCommaSeparated() {
    return argumentsCommaSeparated;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets method.
   *
   * @param method the method
   */
  private void setMethod(Method method) {
    this.method = method;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(String name) {
    this.name = name;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  private void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets return value.
   *
   * @param returnValue the return value
   */
  private void setReturnValue(String returnValue) {
    this.returnValue = returnValue;
  }

  /**
   * Sets arguments.
   *
   * @param arguments the arguments
   */
  private void setArguments(Set<KeyValue> arguments) {
    this.arguments = arguments;
  }

  /**
   * Sets arguments comma separated.
   *
   * @param argumentsCommaSeparated the arguments comma separated
   */
  private void setArgumentsCommaSeparated(String argumentsCommaSeparated) {
    this.argumentsCommaSeparated = argumentsCommaSeparated;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private String makeArgumentsCommaSeparated(Set<KeyValue> arguments) {
    return arguments
        .stream()
        .map(keyValue -> keyValue.getKey() + " " + keyValue.getValue())
        .collect(Collectors.joining(", "));
  }
}
