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

package io.polygenesis.generators.java.apiclients.rest.resource.activity.entity;

import io.polygenesis.generators.java.apiclients.rest.resource.activity.common.ResourceActivityTemplateData;
import io.polygenesis.models.api.Dto;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Set;

public class CreateAggregateEntityActivityTemplateData extends ResourceActivityTemplateData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String parentThingIdentityVariableName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Create aggregate entity activity template data.
   *
   * @param parameterRepresentations the parameter representations
   * @param serviceName the service name
   * @param serviceMethodName the service method name
   * @param requestDto the request dto
   * @param responseDto the response dto
   * @param parentThingIdentityVariableName the parent thing identity variable name
   */
  @SuppressWarnings("CPD-START")
  public CreateAggregateEntityActivityTemplateData(
      Set<ParameterRepresentation> parameterRepresentations,
      String serviceName,
      String serviceMethodName,
      Dto requestDto,
      Dto responseDto,
      String parentThingIdentityVariableName) {
    super(parameterRepresentations, serviceName, serviceMethodName, requestDto, responseDto);
    this.parentThingIdentityVariableName = parentThingIdentityVariableName;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets parent thing identity variable name.
   *
   * @return the parent thing identity variable name
   */
  public String getParentThingIdentityVariableName() {
    return parentThingIdentityVariableName;
  }
}
