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
public class Function implements FunctionProvider {

  private Thing thing;
  private Purpose purpose;
  private FunctionName name;
  private ReturnValue returnValue;
  private Set<Argument> arguments;
  private Activity activity;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Function.
   *
   * @param thing the thing
   * @param purpose the purpose
   * @param name the name
   * @param returnValue the return value
   * @param arguments the arguments
   * @param activity the activity
   */
  public Function(
      Thing thing,
      Purpose purpose,
      FunctionName name,
      ReturnValue returnValue,
      Set<Argument> arguments,
      Activity activity) {
    setThing(thing);
    setPurpose(purpose);
    setName(name);

    if (returnValue != null) {
      setReturnValue(returnValue);
    }

    if (arguments != null) {
      setArguments(arguments);
    }

    if (activity != null) {
      setActivity(activity);
    }
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

  public Activity getActivity() {
    return activity;
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

  /**
   * Sets activity.
   *
   * @param activity the activity
   */
  public void setActivity(Activity activity) {
    Assertion.isNotNull(activity, "activity is required");
    this.activity = activity;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Function getFunction() {
    return this;
  }

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
        && Objects.equals(arguments, function.arguments)
        && Objects.equals(activity, function.activity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(purpose, name, returnValue, arguments, activity);
  }
}
