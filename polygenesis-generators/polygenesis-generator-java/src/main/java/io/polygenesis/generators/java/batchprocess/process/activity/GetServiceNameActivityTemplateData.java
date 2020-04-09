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

package io.polygenesis.generators.java.batchprocess.process.activity;

import io.polygenesis.commons.assertion.Assertion;

/**
 * The type Get service name activity template data.
 *
 * @author Christos Tsakostas
 */
public class GetServiceNameActivityTemplateData {

  private String batchProcessServiceName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Get service name activity template data.
   *
   * @param batchProcessServiceName the service name
   */
  public GetServiceNameActivityTemplateData(String batchProcessServiceName) {
    setBatchProcessServiceName(batchProcessServiceName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets service name.
   *
   * @return the service name
   */
  public String getBatchProcessServiceName() {
    return batchProcessServiceName;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setBatchProcessServiceName(String batchProcessServiceName) {
    Assertion.isNotNull(batchProcessServiceName, "serviceName is required");
    this.batchProcessServiceName = batchProcessServiceName;
  }
}
