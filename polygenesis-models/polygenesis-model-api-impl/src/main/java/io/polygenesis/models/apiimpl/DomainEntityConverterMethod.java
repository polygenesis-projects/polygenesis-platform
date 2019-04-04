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

package io.polygenesis.models.apiimpl;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.Function;
import java.util.Objects;

/**
 * The type Domain object converter method.
 *
 * @author Christos Tsakostas
 */
public class DomainEntityConverterMethod {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Function function;
  private Object from;
  private Object to;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object converter method.
   *
   * @param function the function
   * @param from the from
   * @param to the to
   */
  public DomainEntityConverterMethod(Function function, Object from, Object to) {
    setFunction(function);
    setFrom(from);
    setTo(to);
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
   * Gets from.
   *
   * @return the from
   */
  public Object getFrom() {
    return from;
  }

  /**
   * Gets to.
   *
   * @return the to
   */
  public Object getTo() {
    return to;
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
    Assertion.isNotNull(function, "function is required");
    this.function = function;
  }

  /**
   * Sets from.
   *
   * @param from the from
   */
  private void setFrom(Object from) {
    Assertion.isNotNull(from, "from is required");
    this.from = from;
  }

  /**
   * Sets to.
   *
   * @param to the to
   */
  private void setTo(Object to) {
    Assertion.isNotNull(to, "to is required");
    this.to = to;
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
    DomainEntityConverterMethod that = (DomainEntityConverterMethod) o;
    return Objects.equals(function, that.function)
        && Objects.equals(from, that.from)
        && Objects.equals(to, that.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(function, from, to);
  }
}
