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

package io.polygenesis.generators.java.implementations.rest;

import io.polygenesis.core.data.Data;
import io.polygenesis.generators.java.skeletons.MethodRepresentation;
import io.polygenesis.models.rest.Endpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Abstract endpoint implementor.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractEndpointImplementor {

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Aggregate root data model map.
   *
   * @param endpoint the endpoint
   * @param methodRepresentation the method representation
   * @return the map
   */
  protected Map<String, Object> aggregateRootDataModel(
      Endpoint endpoint, MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = defaultDataModel(endpoint, methodRepresentation);

    return dataModel;
  }

  /**
   * Aggregate root data model with thing identity map.
   *
   * @param endpoint the endpoint
   * @param methodRepresentation the method representation
   * @return the map
   */
  protected Map<String, Object> aggregateRootDataModelWithThingIdentity(
      Endpoint endpoint, MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = aggregateRootDataModel(endpoint, methodRepresentation);

    Data thingIdentity =
        endpoint
            .getServiceMethod()
            .getRequestDto()
            .getThingIdentityAsOptional()
            .orElseThrow(IllegalStateException::new);

    dataModel.put("thingIdentityVariableName", thingIdentity.getVariableName().getText());

    return dataModel;
  }

  /**
   * Aggregate entity data model map.
   *
   * @param endpoint the endpoint
   * @param methodRepresentation the method representation
   * @return the map
   */
  protected Map<String, Object> aggregateEntityDataModel(
      Endpoint endpoint, MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = defaultDataModel(endpoint, methodRepresentation);

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
                            endpoint.getServiceMethod().getFunction().getName().getText())));

    dataModel.put(
        "parentThingIdentityVariableName", parentThingIdentity.getVariableName().getText());

    return dataModel;
  }

  /**
   * Aggregate entity data model with thing identity map.
   *
   * @param endpoint the endpoint
   * @param methodRepresentation the method representation
   * @return the map
   */
  protected Map<String, Object> aggregateEntityDataModelWithThingIdentity(
      Endpoint endpoint, MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = aggregateEntityDataModel(endpoint, methodRepresentation);

    Data thingIdentity =
        endpoint
            .getServiceMethod()
            .getRequestDto()
            .getThingIdentityAsOptional()
            .orElseThrow(IllegalStateException::new);

    dataModel.put("thingIdentityVariableName", thingIdentity.getVariableName().getText());

    return dataModel;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Default data model map.
   *
   * @param endpoint the endpoint
   * @param methodRepresentation the method representation
   * @return the map
   */
  private Map<String, Object> defaultDataModel(
      Endpoint endpoint, MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = new HashMap<>();

    dataModel.put("representation", methodRepresentation);
    dataModel.put("requestDto", endpoint.getServiceMethod().getRequestDto());
    dataModel.put("serviceName", endpoint.getService().getServiceName().getText());
    dataModel.put(
        "serviceMethodName", endpoint.getServiceMethod().getFunction().getName().getText());

    return dataModel;
  }
}
