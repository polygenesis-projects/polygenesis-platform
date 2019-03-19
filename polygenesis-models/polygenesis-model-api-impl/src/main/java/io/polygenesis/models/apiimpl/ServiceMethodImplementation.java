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
import io.polygenesis.models.api.ServiceMethod;
import java.util.Objects;

/**
 * The type Service method implementation.
 *
 * @author Christos Tsakostas
 */
public class ServiceMethodImplementation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ServiceMethod serviceMethod;
  private ServiceMethodImplementationType serviceMethodImplementationType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service method implementation.
   *
   * @param serviceMethod the service method
   * @param serviceMethodImplementationType the service method implementation type
   */
  public ServiceMethodImplementation(
      ServiceMethod serviceMethod,
      ServiceMethodImplementationType serviceMethodImplementationType) {
    setServiceMethod(serviceMethod);
    setServiceMethodImplementationType(serviceMethodImplementationType);
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

  /**
   * Gets service method implementation type.
   *
   * @return the service method implementation type
   */
  public ServiceMethodImplementationType getServiceMethodImplementationType() {
    return serviceMethodImplementationType;
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

  /**
   * Sets service method implementation type.
   *
   * @param serviceMethodImplementationType the service method implementation type
   */
  private void setServiceMethodImplementationType(
      ServiceMethodImplementationType serviceMethodImplementationType) {
    Assertion.isNotNull(
        serviceMethodImplementationType, "serviceMethodImplementationType is required");
    this.serviceMethodImplementationType = serviceMethodImplementationType;
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
    ServiceMethodImplementation that = (ServiceMethodImplementation) o;
    return Objects.equals(serviceMethod, that.serviceMethod)
        && serviceMethodImplementationType == that.serviceMethodImplementationType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceMethod, serviceMethodImplementationType);
  }
}
