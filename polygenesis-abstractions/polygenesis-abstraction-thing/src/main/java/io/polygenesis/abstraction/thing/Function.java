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

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.Goal;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * In the context of automatic programming, Function is defined as an activity expressing what has
 * to be done, with a {@link Goal}, written by a programmer in a specific programming language to or
 * the purpose of a {@link Thing}, which is characterized by a Name and Properties provided as
 * activity's optional arguments and return value.
 *
 * @author Christos Tsakostas
 */
public class Function {

  /** The {@link Thing} the Function belongs to. */
  private Thing thing;

  /** The {@link Goal} of the Function. */
  private Goal goal;

  /**
   * The name of the Function.
   *
   * <p>It will be used in code generation.
   */
  private FunctionName name;

  /** Optional return value of the Function. */
  private ReturnValue returnValue;

  /** Optional arguments of the Function. */
  private Set<Argument> arguments;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private Function(Thing thing, Goal goal, FunctionName name) {
    setThing(thing);
    setGoal(goal);
    setName(name);
  }

  /**
   * Instantiates a new Function with ReturnValue.
   *
   * @param thing the thing
   * @param goal the goal
   * @param name the name
   * @param returnValue the return value
   */
  public Function(Thing thing, Goal goal, FunctionName name, ReturnValue returnValue) {
    this(thing, goal, name);
    setReturnValue(returnValue);
    setArguments(new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Function with Arguments.
   *
   * @param thing the thing
   * @param goal the goal
   * @param name the name
   * @param arguments the arguments
   */
  public Function(Thing thing, Goal goal, FunctionName name, Set<Argument> arguments) {
    this(thing, goal, name);
    setArguments(arguments);
  }

  /**
   * Instantiates a new Function with ReturnValue and Arguments.
   *
   * @param thing the thing
   * @param goal the goal
   * @param name the name
   * @param arguments the arguments
   * @param returnValue the return value
   */
  public Function(
      Thing thing, Goal goal, FunctionName name, Set<Argument> arguments, ReturnValue returnValue) {
    this(thing, goal, name, arguments);
    setReturnValue(returnValue);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets thing.
   *
   * @return the thing
   */
  public Thing getThing() {
    return thing;
  }

  /**
   * Gets goal.
   *
   * @return the goal
   */
  public Goal getGoal() {
    return goal;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public FunctionName getName() {
    return name;
  }

  /**
   * Gets return value.
   *
   * @return the return value
   */
  public ReturnValue getReturnValue() {
    return returnValue;
  }

  /**
   * Gets arguments.
   *
   * @return the arguments
   */
  public Set<Argument> getArguments() {
    return arguments;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets thing.
   *
   * @param thing the thing
   */
  private void setThing(Thing thing) {
    Assertion.isNotNull(thing, "thing is required");
    this.thing = thing;
  }

  /**
   * Sets goal.
   *
   * @param goal the goal
   */
  private void setGoal(Goal goal) {
    Assertion.isNotNull(goal, "goal is required");
    this.goal = goal;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(FunctionName name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets return value.
   *
   * @param returnValue the return value
   */
  private void setReturnValue(ReturnValue returnValue) {
    Assertion.isNotNull(returnValue, "returnValue is required");
    this.returnValue = returnValue;
  }

  /**
   * Sets arguments.
   *
   * @param arguments the arguments
   */
  private void setArguments(Set<Argument> arguments) {
    Assertion.isNotNull(arguments, "arguments is required");
    this.arguments = arguments;
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

    Function function = (Function) o;

    return new EqualsBuilder()
        .append(thing, function.thing)
        .append(goal, function.goal)
        .append(name, function.name)
        .append(returnValue, function.returnValue)
        .append(arguments, function.arguments)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(thing)
        .append(goal)
        .append(name)
        .append(returnValue)
        .append(arguments)
        .toHashCode();
  }
}
