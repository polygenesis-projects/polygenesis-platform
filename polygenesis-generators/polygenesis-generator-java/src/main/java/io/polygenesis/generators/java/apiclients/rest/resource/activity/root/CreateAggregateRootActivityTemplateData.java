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

package io.polygenesis.generators.java.apiclients.rest.resource.activity.root;

import io.polygenesis.generators.java.apiclients.rest.resource.activity.common.ResourceActivityTemplateData;
import io.polygenesis.models.api.Dto;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Set;

/**
 * The type Create aggregate root activity template data.
 *
 * @author Christos Tsakostas
 */
public class CreateAggregateRootActivityTemplateData extends ResourceActivityTemplateData {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Create aggregate root activity template data.
   *
   * @param parameterRepresentations the parameter representations
   * @param serviceName the service name
   * @param serviceMethodName the service method name
   * @param requestDto the request dto
   * @param responseDto the response dto
   */
  @SuppressWarnings("CPD-START")
  public CreateAggregateRootActivityTemplateData(
      Set<ParameterRepresentation> parameterRepresentations,
      String serviceName,
      String serviceMethodName,
      Dto requestDto,
      Dto responseDto) {
    super(parameterRepresentations, serviceName, serviceMethodName, requestDto, responseDto);
  }
}
