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

import io.polygenesis.core.Function;
import io.polygenesis.models.api.Service;
import java.util.Set;

/**
 * The type Endpoint.
 *
 * @author Christos Tsakostas
 */
public class Endpoint {

  private Service service;
  private Function function;
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
   * @param function the function
   * @param httpMethod the http method
   * @param mappings the mappings
   * @param requestParameters the request parameters
   */
  public Endpoint(
      Service service,
      Function function,
      HttpMethod httpMethod,
      Set<Mapping> mappings,
      Set<RequestParameter> requestParameters) {
    setService(service);
    setFunction(function);
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
   * Gets function.
   *
   * @return the function
   */
  public Function getFunction() {
    return function;
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
    this.service = service;
  }

  /**
   * Sets function.
   *
   * @param function the function
   */
  private void setFunction(Function function) {
    this.function = function;
  }

  /**
   * Sets http method.
   *
   * @param httpMethod the http method
   */
  private void setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }

  /**
   * Sets mappings.
   *
   * @param mappings the mappings
   */
  private void setMappings(Set<Mapping> mappings) {
    this.mappings = mappings;
  }

  /**
   * Sets request parameters.
   *
   * @param requestParameters the request parameters
   */
  private void setRequestParameters(Set<RequestParameter> requestParameters) {
    this.requestParameters = requestParameters;
  }
}
