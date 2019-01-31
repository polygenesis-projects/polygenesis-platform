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

package io.polygenesis.models.api;

import io.polygenesis.core.Argument;
import io.polygenesis.core.ReturnValue;
import java.util.Objects;
import java.util.Set;

/**
 * The type Method.
 *
 * @author Christos Tsakostas
 */
public class Method {

  private MethodName methodName;
  private ReturnValue returnValue;
  private Set<Argument> arguments;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Method.
   *
   * @param methodName the method name
   * @param returnValue the return value
   * @param arguments the arguments
   */
  public Method(MethodName methodName, ReturnValue returnValue, Set<Argument> arguments) {
    setMethodName(methodName);
    setReturnValue(returnValue);
    setArguments(arguments);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets method name.
   *
   * @return the method name
   */
  public MethodName getMethodName() {
    return methodName;
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
   * Sets method name.
   *
   * @param methodName the method name
   */
  private void setMethodName(MethodName methodName) {
    this.methodName = methodName;
  }

  /**
   * Sets return value.
   *
   * @param returnValue the return value
   */
  private void setReturnValue(ReturnValue returnValue) {
    this.returnValue = returnValue;
  }

  /**
   * Sets arguments.
   *
   * @param arguments the arguments
   */
  private void setArguments(Set<Argument> arguments) {
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
    Method method = (Method) o;
    return Objects.equals(methodName, method.methodName)
        && Objects.equals(returnValue, method.returnValue)
        && Objects.equals(arguments, method.arguments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(methodName, returnValue, arguments);
  }
}
