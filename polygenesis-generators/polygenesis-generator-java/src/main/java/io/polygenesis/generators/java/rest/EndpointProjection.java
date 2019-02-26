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

package io.polygenesis.generators.java.rest;

import io.polygenesis.core.Function;
import io.polygenesis.generators.java.shared.ArgumentProjection;
import io.polygenesis.generators.java.shared.FunctionProjection;
import java.util.Set;

/**
 * The type Endpoint projection.
 *
 * @author Christos Tsakostas
 */
public class EndpointProjection extends FunctionProjection {

  private String serviceName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Endpoint projection.
   *
   * @param function the function
   * @param name the name
   * @param description the description
   * @param returnValue the return value
   * @param arguments the arguments
   * @param annotations the annotations
   * @param serviceName the service name
   */
  public EndpointProjection(
      Function function,
      String name,
      String description,
      String returnValue,
      Set<ArgumentProjection> arguments,
      Set<String> annotations,
      String serviceName) {
    super(function, name, description, returnValue, arguments, annotations);
    setServiceName(serviceName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets service name.
   *
   * @return the service name
   */
  public String getServiceName() {
    return serviceName;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets service name.
   *
   * @param serviceName the service name
   */
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }
}
