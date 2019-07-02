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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Set;

/**
 * The type Process activity template data.
 *
 * @author Christos Tsakostas
 */
public class ProcessActivityTemplateData {

  private Set<Data> messageData;
  private ServiceMethod ensureExistenceServiceMethod;
  private ServiceMethod commandServiceMethod;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Process activity template data.
   *
   * @param messageData the message data
   * @param ensureExistenceServiceMethod the ensure existence service method
   * @param commandServiceMethod the command service method
   */
  public ProcessActivityTemplateData(
      Set<Data> messageData,
      ServiceMethod ensureExistenceServiceMethod,
      ServiceMethod commandServiceMethod) {
    this.messageData = messageData;
    this.ensureExistenceServiceMethod = ensureExistenceServiceMethod;
    this.commandServiceMethod = commandServiceMethod;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets message data.
   *
   * @return the message data
   */
  public Set<Data> getMessageData() {
    return messageData;
  }

  /**
   * Gets ensure existence service method.
   *
   * @return the ensure existence service method
   */
  public ServiceMethod getEnsureExistenceServiceMethod() {
    return ensureExistenceServiceMethod;
  }

  /**
   * Gets command service method.
   *
   * @return the command service method
   */
  public ServiceMethod getCommandServiceMethod() {
    return commandServiceMethod;
  }
}
