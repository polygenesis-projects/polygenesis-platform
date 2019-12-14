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

package io.polygenesis.generators.java.apiclients.rest.resource.activity.common;

import io.polygenesis.models.api.Dto;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Set;

/**
 * The type Create aggregate root activity template data.
 *
 * @author Christos Tsakostas
 */
public abstract class ResourceActivityTemplateData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<ParameterRepresentation> parameterRepresentations;
  private String serviceName;
  private String serviceMethodName;
  private Dto requestDto;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource activity template data.
   *
   * @param parameterRepresentations the parameter representations
   * @param serviceName the service name
   * @param serviceMethodName the service method name
   * @param requestDto the request dto
   */
  public ResourceActivityTemplateData(
      Set<ParameterRepresentation> parameterRepresentations,
      String serviceName,
      String serviceMethodName,
      Dto requestDto) {
    this.parameterRepresentations = parameterRepresentations;
    this.serviceName = serviceName;
    this.serviceMethodName = serviceMethodName;
    this.requestDto = requestDto;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets parameter representations.
   *
   * @return the parameter representations
   */
  public Set<ParameterRepresentation> getParameterRepresentations() {
    return parameterRepresentations;
  }

  /**
   * Gets service name.
   *
   * @return the service name
   */
  public String getServiceName() {
    return serviceName;
  }

  /**
   * Gets service method name.
   *
   * @return the service method name
   */
  public String getServiceMethodName() {
    return serviceMethodName;
  }

  /**
   * Gets request dto.
   *
   * @return the request dto
   */
  public Dto getRequestDto() {
    return requestDto;
  }
}
