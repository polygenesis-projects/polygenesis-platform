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

package io.polygenesis.core.data;

import io.polygenesis.core.Function;
import java.util.Objects;

/**
 * The type Data source.
 *
 * @author Christos Tsakostas
 */
public class DataSource {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Data input is provided by user.
   *
   * @return the data source
   */
  public static DataSource user() {
    return new DataSource(null);
  }

  /**
   * Data input is provided by the return value of a function.
   *
   * @param function the function
   * @return the data source
   */
  public static DataSource function(Function function) {
    return new DataSource(function);
  }

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final Function function;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data source.
   *
   * @param function the function
   */
  public DataSource(Function function) {
    this.function = function;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Is user boolean.
   *
   * @return the boolean
   */
  public boolean isUser() {
    return getFunction() == null;
  }

  /**
   * Is function output boolean.
   *
   * @return the boolean
   */
  public boolean isFunctionOutput() {
    return getFunction() != null;
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
    DataSource that = (DataSource) o;
    return Objects.equals(function, that.function);
  }

  @Override
  public int hashCode() {
    return Objects.hash(function);
  }
}
