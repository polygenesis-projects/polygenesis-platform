/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Objects;

public class ServiceMethodImplementation implements FunctionProvider {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ServiceMethod serviceMethod;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service method implementation.
   *
   * @param serviceMethod the service method
   */
  public ServiceMethodImplementation(ServiceMethod serviceMethod) {
    setServiceMethod(serviceMethod);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets service method.
   *
   * @return the service method
   */
  public ServiceMethod getServiceMethod() {
    return serviceMethod;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets service method.
   *
   * @param serviceMethod the service method
   */
  private void setServiceMethod(ServiceMethod serviceMethod) {
    Assertion.isNotNull(serviceMethod, "serviceMethod is required");
    this.serviceMethod = serviceMethod;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Function getFunction() {
    return serviceMethod.getFunction();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceMethodImplementation that = (ServiceMethodImplementation) o;
    return Objects.equals(serviceMethod, that.serviceMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceMethod);
  }
}
