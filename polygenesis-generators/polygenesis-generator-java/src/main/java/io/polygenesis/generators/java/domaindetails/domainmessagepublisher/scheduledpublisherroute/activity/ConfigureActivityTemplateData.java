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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute.activity;

/**
 * The type Configure activity template data.
 *
 * @author Christos Tsakostas
 */
public class ConfigureActivityTemplateData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String context;
  private String startupOrder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public ConfigureActivityTemplateData(String context, String startupOrder) {
    this.context = context;
    this.startupOrder = startupOrder;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  public String getContext() {
    return context;
  }

  public String getStartupOrder() {
    return startupOrder;
  }
}
