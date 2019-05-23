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
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * In the context of automatic programming, Function is defined as an activity expressing what has
 * to be done, with a {@link Purpose}, written by a programmer in a specific programming language to
 * or the purpose of a {@link Thing}, which is characterized by a Name and Properties provided as
 * activity's optional arguments and return value.
 *
 * @author Christos Tsakostas
 */
public class Function {

  /** The {@link Thing} the Function belongs to. */
  private Thing thing;

  /** The {@link Purpose} of the Function. */
  private Purpose purpose;

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

  private Function(Thing thing, Purpose purpose, FunctionName name) {
    setThing(thing);
    setPurpose(purpose);
    setName(name);
  }

  /**
   * Instantiates a new Function with ReturnValue.
   *
   * @param thing the thing
   * @param purpose the purpose
   * @param name the name
   * @param returnValue the return value
   */
  public Function(Thing thing, Purpose purpose, FunctionName name, ReturnValue returnValue) {
    this(thing, purpose, name);
    setReturnValue(returnValue);
    setArguments(new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Function with Arguments.
   *
   * @param thing the thing
   * @param purpose the purpose
   * @param name the name
   * @param arguments the arguments
   */
  public Function(Thing thing, Purpose purpose, FunctionName name, Set<Argument> arguments) {
    this(thing, purpose, name);
    setArguments(arguments);
  }

  /**
   * Instantiates a new Function with ReturnValue and Arguments.
   *
   * @param thing the thing
   * @param purpose the purpose
   * @param name the name
   * @param arguments the arguments
   * @param returnValue the return value
   */
  public Function(
      Thing thing,
      Purpose purpose,
      FunctionName name,
      Set<Argument> arguments,
      ReturnValue returnValue) {
    this(thing, purpose, name, arguments);
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
   * Gets purpose.
   *
   * @return the purpose
   */
  public Purpose getPurpose() {
    return purpose;
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
   * Sets purpose.
   *
   * @param purpose the purpose
   */
  private void setPurpose(Purpose purpose) {
    Assertion.isNotNull(purpose, "purpose is required");
    this.purpose = purpose;
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
    return Objects.equals(purpose, function.purpose)
        && Objects.equals(name, function.name)
        && Objects.equals(returnValue, function.returnValue)
        && Objects.equals(arguments, function.arguments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(purpose, name, returnValue, arguments);
  }
}
