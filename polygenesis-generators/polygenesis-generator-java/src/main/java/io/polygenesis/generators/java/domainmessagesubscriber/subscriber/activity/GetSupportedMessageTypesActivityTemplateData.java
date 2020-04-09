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

package io.polygenesis.generators.java.domainmessagesubscriber.subscriber.activity;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Set;

/**
 * The type Get supported message types activity template data.
 *
 * @author Christos Tsakostas
 */
public class GetSupportedMessageTypesActivityTemplateData {

  private Set<String> supportedMessageTypes;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Get supported message types activity template data.
   *
   * @param supportedMessageTypes the supported message types
   */
  public GetSupportedMessageTypesActivityTemplateData(Set<String> supportedMessageTypes) {
    setSupportedMessageTypes(supportedMessageTypes);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets supported message types.
   *
   * @return the supported message types
   */
  public Set<String> getSupportedMessageTypes() {
    return supportedMessageTypes;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets supported message types.
   *
   * @param supportedMessageTypes the supported message types
   */
  private void setSupportedMessageTypes(Set<String> supportedMessageTypes) {
    Assertion.isNotNull(supportedMessageTypes, "supportedMessageTypes is required");
    this.supportedMessageTypes = supportedMessageTypes;
  }
}
