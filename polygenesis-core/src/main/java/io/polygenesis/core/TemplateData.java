/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.core;

import java.util.Map;

public class TemplateData {

  private Map<String, Object> dataModel;
  private String template;

  /**
   * Instantiates a new Template data.
   *
   * @param dataModel the data model
   * @param template the template
   */
  public TemplateData(Map<String, Object> dataModel, String template) {
    this.dataModel = dataModel;
    this.template = template;
  }

  /**
   * Gets data model.
   *
   * @return the data model
   */
  public Map<String, Object> getDataModel() {
    return dataModel;
  }

  /**
   * Gets template.
   *
   * @return the template
   */
  public String getTemplate() {
    return template;
  }
}
