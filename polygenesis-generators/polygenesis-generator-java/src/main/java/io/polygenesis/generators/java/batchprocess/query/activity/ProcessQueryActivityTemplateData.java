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

package io.polygenesis.generators.java.batchprocess.query.activity;

import io.polygenesis.commons.assertion.Assertion;

/**
 * The type Process query activity template data.
 *
 * @author Christos Tsakostas
 */
public class ProcessQueryActivityTemplateData {

  private String queryService;
  private String queryMethod;
  private String requestDto;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Process query activity template data.
   *
   * @param queryService the query service
   * @param queryMethod the query method
   * @param requestDto the request dto
   */
  public ProcessQueryActivityTemplateData(
      String queryService, String queryMethod, String requestDto) {
    setQueryService(queryService);
    setQueryMethod(queryMethod);
    setRequestDto(requestDto);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets query service.
   *
   * @return the query service
   */
  public String getQueryService() {
    return queryService;
  }

  /**
   * Gets query method.
   *
   * @return the query method
   */
  public String getQueryMethod() {
    return queryMethod;
  }

  /**
   * Gets request dto.
   *
   * @return the request dto
   */
  public String getRequestDto() {
    return requestDto;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets query service.
   *
   * @param queryService the query service
   */
  private void setQueryService(String queryService) {
    Assertion.isNotNull(queryService, "queryService is required");
    this.queryService = queryService;
  }

  /**
   * Sets query method.
   *
   * @param queryMethod the query method
   */
  private void setQueryMethod(String queryMethod) {
    Assertion.isNotNull(queryMethod, "queryMethod is required");
    this.queryMethod = queryMethod;
  }

  /**
   * Sets request dto.
   *
   * @param requestDto the request dto
   */
  private void setRequestDto(String requestDto) {
    Assertion.isNotNull(requestDto, "requestDto is required");
    this.requestDto = requestDto;
  }
}
