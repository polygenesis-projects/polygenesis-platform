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

package io.polygenesis.models.api;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

/**
 * The type Service method.
 *
 * @author Christos Tsakostas
 */
public class ServiceMethod implements FunctionProvider {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Service service;
  private Function function;
  private Dto requestDto;
  private Dto responseDto;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service method.
   *
   * @param service the service
   * @param function the function
   * @param requestDto the request dto
   * @param responseDto the response dto
   */
  public ServiceMethod(Service service, Function function, Dto requestDto, Dto responseDto) {
    setService(service);
    setFunction(function);
    setRequestDto(requestDto);
    setResponseDto(responseDto);
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
  @Override
  public Function getFunction() {
    return function;
  }

  /**
   * Gets request dto.
   *
   * @return the request dto
   */
  public Dto getRequestDto() {
    return requestDto;
  }

  /**
   * Gets response dto.
   *
   * @return the response dto
   */
  public Dto getResponseDto() {
    return responseDto;
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
   * Sets function.
   *
   * @param function the function
   */
  private void setFunction(Function function) {
    Assertion.isNotNull(function, "function is required");
    this.function = function;
  }

  /**
   * Sets request dto.
   *
   * @param requestDto the request dto
   */
  private void setRequestDto(Dto requestDto) {
    Assertion.isNotNull(requestDto, "requestDto is required");
    this.requestDto = requestDto;
  }

  /**
   * Sets response dto.
   *
   * @param responseDto the response dto
   */
  private void setResponseDto(Dto responseDto) {
    Assertion.isNotNull(responseDto, "responseDto is required");
    this.responseDto = responseDto;
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
    ServiceMethod that = (ServiceMethod) o;
    return Objects.equals(function, that.function)
        && Objects.equals(requestDto, that.requestDto)
        && Objects.equals(responseDto, that.responseDto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(function, requestDto, responseDto);
  }
}
