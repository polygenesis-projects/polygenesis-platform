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

package io.polygenesis.generators.java.apiimpl;

import io.polygenesis.core.Function;
import io.polygenesis.generators.java.shared.ArgumentProjection;
import io.polygenesis.generators.java.shared.FunctionProjection;
import java.util.Set;

/**
 * The type Api impl method projection.
 *
 * @author Christos Tsakostas
 */
public class ApiImplMethodProjection extends FunctionProjection {

  private String implementation;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl method projection.
   *
   * @param function the function
   * @param name the name
   * @param description the description
   * @param returnValue the return value
   * @param arguments the arguments
   * @param annotations the annotations
   * @param implementation the implementation
   */
  public ApiImplMethodProjection(
      Function function,
      String name,
      String description,
      String returnValue,
      Set<ArgumentProjection> arguments,
      Set<String> annotations,
      String implementation) {
    super(function, name, description, returnValue, arguments, annotations);
    setImplementation(implementation);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets implementation.
   *
   * @return the implementation
   */
  public String getImplementation() {
    return implementation;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets implementation.
   *
   * @param implementation the implementation
   */
  private void setImplementation(String implementation) {
    this.implementation = implementation;
  }
}
