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

import io.polygenesis.core.Function;
import java.util.Objects;

/**
 * The type Method.
 *
 * @author Christos Tsakostas
 */
public class Method {

  private Function function;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Method.
   *
   * @param function the function
   */
  public Method(Function function) {
    setFunction(function);
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

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets function.
   *
   * @param function the function
   */
  private void setFunction(Function function) {
    this.function = function;
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
    return Objects.equals(function, method.function);
  }

  @Override
  public int hashCode() {
    return Objects.hash(function);
  }
}
