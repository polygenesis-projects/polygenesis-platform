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

package io.polygenesis.generators.java.batchprocess.command.activity;

import io.polygenesis.commons.assertion.Assertion;

/**
 * The type Process command activity template data.
 *
 * @author Christos Tsakostas
 */
public class ProcessCommandActivityTemplateData {

  private String commandService;
  private String commandMethod;
  private String requestDto;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Process command activity template data.
   *
   * @param commandService the command service
   * @param commandMethod the command method
   * @param requestDto the request dto
   */
  public ProcessCommandActivityTemplateData(
      String commandService, String commandMethod, String requestDto) {
    setCommandService(commandService);
    setCommandMethod(commandMethod);
    setRequestDto(requestDto);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets command service.
   *
   * @return the command service
   */
  public String getCommandService() {
    return commandService;
  }

  /**
   * Gets command method.
   *
   * @return the command method
   */
  public String getCommandMethod() {
    return commandMethod;
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
   * Sets command service.
   *
   * @param commandService the command service
   */
  private void setCommandService(String commandService) {
    Assertion.isNotNull(commandService, "commandService is required");
    this.commandService = commandService;
  }

  /**
   * Sets command method.
   *
   * @param commandMethod the command method
   */
  private void setCommandMethod(String commandMethod) {
    Assertion.isNotNull(commandMethod, "commandMethod is required");
    this.commandMethod = commandMethod;
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
