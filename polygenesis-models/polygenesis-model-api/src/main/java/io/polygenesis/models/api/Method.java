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

package io.polygenesis.models.api;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.Function;
import java.util.Objects;

/**
 * The type Method.
 *
 * @author Christos Tsakostas
 */
public class Method {

  private Function function;
  private Dto requestDto;
  private Dto responseDto;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Method.
   *
   * @param function the function
   * @param requestDto the request dto
   * @param responseDto the response dto
   */
  public Method(Function function, Dto requestDto, Dto responseDto) {
    setFunction(function);
    setRequestDto(requestDto);
    setResponseDto(responseDto);
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
    Method method = (Method) o;
    return Objects.equals(function, method.function)
        && Objects.equals(requestDto, method.requestDto)
        && Objects.equals(responseDto, method.responseDto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(function, requestDto, responseDto);
  }
}
