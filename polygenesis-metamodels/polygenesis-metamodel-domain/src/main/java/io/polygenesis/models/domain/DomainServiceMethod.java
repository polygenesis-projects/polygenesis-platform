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

package io.polygenesis.models.domain;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

/**
 * The type Domain service method.
 *
 * @author Christos Tsakostas
 */
public class DomainServiceMethod implements FunctionProvider {

  private Function function;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service method.
   *
   * @param function the function
   */
  public DomainServiceMethod(Function function) {
    setFunction(function);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets function.
   *
   * @param function the function
   */
  private void setFunction(Function function) {
    Assertion.isNotNull(function, "function is required");
    this.function = function;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Function getFunction() {
    return function;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DomainServiceMethod that = (DomainServiceMethod) o;
    return Objects.equals(function, that.function);
  }

  @Override
  public int hashCode() {
    return Objects.hash(function);
  }
}
