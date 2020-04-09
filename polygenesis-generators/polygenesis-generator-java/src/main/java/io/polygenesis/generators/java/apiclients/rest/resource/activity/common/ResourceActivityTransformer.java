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

package io.polygenesis.generators.java.apiclients.rest.resource.activity.common;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.ActivityTemplateTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceMethodParameterRepresentationService;
import io.polygenesis.models.rest.Endpoint;

public abstract class ResourceActivityTransformer implements ActivityTemplateTransformer<Endpoint> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  /** The Resource method parameter representation service. */
  protected final ResourceMethodParameterRepresentationService
      resourceMethodParameterRepresentationService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource activity transformer.
   *
   * @param resourceMethodParameterRepresentationService the resource method parameter
   *     representation service
   */
  public ResourceActivityTransformer(
      ResourceMethodParameterRepresentationService resourceMethodParameterRepresentationService) {
    this.resourceMethodParameterRepresentationService =
        resourceMethodParameterRepresentationService;
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Thing identity string.
   *
   * @param endpoint the endpoint
   * @return the string
   */
  protected String thingIdentity(Endpoint endpoint) {

    Data thingIdentity =
        endpoint
            .getServiceMethod()
            .getRequestDto()
            .getThingIdentityAsOptional()
            .orElseThrow(IllegalStateException::new);

    return thingIdentity.getVariableName().getText();
  }

  /**
   * Parent thing identity variable name string.
   *
   * @param endpoint the endpoint
   * @return the string
   */
  protected String parentThingIdentityVariableName(Endpoint endpoint) {
    Data parentThingIdentity =
        endpoint
            .getServiceMethod()
            .getRequestDto()
            .getParentThingIdentityAsOptional()
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        String.format(
                            "No parentThingIdentity defined for thing=%s and endpoint=%s",
                            endpoint
                                .getServiceMethod()
                                .getFunction()
                                .getThing()
                                .getThingName()
                                .getText(),
                            endpoint.getServiceMethod().getFunction().getName().getFullName())));

    return parentThingIdentity.getVariableName().getText();
  }
}
