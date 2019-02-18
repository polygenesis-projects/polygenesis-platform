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

import io.polygenesis.core.Function;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Function projection transforms data from {@link Function} to a friendly format for the Java
 * Generators.
 *
 * @author Christos Tsakostas
 */
public class FunctionProjection {

  private Function function;
  private String name;
  private String description;
  private String returnValue;
  private Set<ArgumentProjection> arguments;
  private String argumentsCommaSeparated;
  private Set<String> annotations;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Function projection.
   *
   * @param function the function
   * @param name the name
   * @param description the description
   * @param returnValue the return value
   * @param arguments the arguments
   * @param annotations the annotations
   */
  public FunctionProjection(
      Function function,
      String name,
      String description,
      String returnValue,
      Set<ArgumentProjection> arguments,
      Set<String> annotations) {
    setFunction(function);
    setName(name);
    setDescription(description);
    setReturnValue(returnValue);
    setArguments(arguments);
    setArgumentsCommaSeparated(makeArgumentsCommaSeparated(arguments));
    setAnnotations(annotations);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets function.
   *
   * @return the function
   */
  public Function getFunction() {
    return function;
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
  public Set<ArgumentProjection> getArguments() {
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

  /**
   * Gets annotations.
   *
   * @return the annotations
   */
  public Set<String> getAnnotations() {
    return annotations;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets function.
   *
   * @param function the function
   */
  public void setFunction(Function function) {
    this.function = function;
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
  private void setArguments(Set<ArgumentProjection> arguments) {
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

  /**
   * Sets annotations.
   *
   * @param annotations the annotations
   */
  private void setAnnotations(Set<String> annotations) {
    this.annotations = annotations;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private String makeArgumentsCommaSeparated(Set<ArgumentProjection> arguments) {
    return arguments
        .stream()
        .map(
            argumentProjection ->
                String.format(
                    "%s%s%s %s",
                    argumentProjection.getAnnotations() != null
                        ? argumentProjection.getAnnotations()
                        : "",
                    argumentProjection.getAnnotations() != null ? " " : "",
                    argumentProjection.getKey(),
                    argumentProjection.getValue()))
        .collect(Collectors.joining(", "));
  }
}
