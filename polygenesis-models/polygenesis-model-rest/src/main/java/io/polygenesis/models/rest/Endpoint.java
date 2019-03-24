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

package io.polygenesis.models.rest;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Objects;
import java.util.Set;

/**
 * The type Endpoint.
 *
 * @author Christos Tsakostas
 */
public class Endpoint {

  private Service service;
  private ServiceMethod serviceMethod;
  private HttpMethod httpMethod;
  private Set<Mapping> mappings;
  private Set<RequestParameter> requestParameters;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Endpoint.
   *
   * @param service the service
   * @param serviceMethod the service method
   * @param httpMethod the http method
   * @param mappings the mappings
   * @param requestParameters the request parameters
   */
  public Endpoint(Service service, ServiceMethod serviceMethod,
      HttpMethod httpMethod, Set<Mapping> mappings,
      Set<RequestParameter> requestParameters) {
    setService(service);
    setServiceMethod(serviceMethod);
    setHttpMethod(httpMethod);
    setMappings(mappings);
    setRequestParameters(requestParameters);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets service.
   *
   * @return the service
   */
  public Service getService() {
    return service;
  }

  /**
   * Gets service method.
   *
   * @return the service method
   */
  public ServiceMethod getServiceMethod() {
    return serviceMethod;
  }

  /**
   * Gets http method.
   *
   * @return the http method
   */
  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  /**
   * Gets mappings.
   *
   * @return the mappings
   */
  public Set<Mapping> getMappings() {
    return mappings;
  }

  /**
   * Gets request parameters.
   *
   * @return the request parameters
   */
  public Set<RequestParameter> getRequestParameters() {
    return requestParameters;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets service.
   *
   * @param service the service
   */
  private void setService(Service service) {
    Assertion.isNotNull(service, "service is required");
    this.service = service;
  }

  /**
   * Sets service method.
   *
   * @param serviceMethod the service method
   */
  public void setServiceMethod(ServiceMethod serviceMethod) {
    Assertion.isNotNull(serviceMethod, "serviceMethod is required");
    this.serviceMethod = serviceMethod;
  }

  /**
   * Sets http method.
   *
   * @param httpMethod the http method
   */
  private void setHttpMethod(HttpMethod httpMethod) {
    Assertion.isNotNull(httpMethod, "httpMethod is required");
    this.httpMethod = httpMethod;
  }

  /**
   * Sets mappings.
   *
   * @param mappings the mappings
   */
  private void setMappings(Set<Mapping> mappings) {
    Assertion.isNotNull(mappings, "mappings is required");
    this.mappings = mappings;
  }

  /**
   * Sets request parameters.
   *
   * @param requestParameters the request parameters
   */
  private void setRequestParameters(Set<RequestParameter> requestParameters) {
    Assertion.isNotNull(requestParameters, "requestParameters is required");
    this.requestParameters = requestParameters;
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
    Endpoint endpoint = (Endpoint) o;
    return Objects.equals(service, endpoint.service) &&
        Objects.equals(serviceMethod, endpoint.serviceMethod) &&
        httpMethod == endpoint.httpMethod &&
        Objects.equals(mappings, endpoint.mappings) &&
        Objects.equals(requestParameters, endpoint.requestParameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(service, serviceMethod, httpMethod, mappings, requestParameters);
  }
}
