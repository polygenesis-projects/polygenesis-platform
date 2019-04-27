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

package io.polygenesis.models.rest;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.GoalType;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMethod;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Endpoint deducer.
 *
 * @author Christos Tsakostas
 */
public class EndpointDeducer {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================
  private static Map<String, HttpMethod> goalToHttpMethod = new HashMap<>();

  static {
    goalToHttpMethod.put(GoalType.CREATE.name(), HttpMethod.POST);
    goalToHttpMethod.put(GoalType.MODIFY.name(), HttpMethod.PUT);
    goalToHttpMethod.put(GoalType.DELETE.name(), HttpMethod.DELETE);
    goalToHttpMethod.put(GoalType.FETCH_ONE.name(), HttpMethod.GET);
    goalToHttpMethod.put(GoalType.FETCH_COLLECTION.name(), HttpMethod.GET);
    goalToHttpMethod.put(GoalType.FETCH_PAGED_COLLECTION.name(), HttpMethod.GET);
  }

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final MappingDeducer mappingDeducer;
  private final RequestParameterDeducer requestParameterDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Endpoint deducer.
   *
   * @param mappingDeducer the mapping deducer
   * @param requestParameterDeducer the request parameter deducer
   */
  public EndpointDeducer(
      MappingDeducer mappingDeducer, RequestParameterDeducer requestParameterDeducer) {
    this.mappingDeducer = mappingDeducer;
    this.requestParameterDeducer = requestParameterDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from function optional endpoint.
   *
   * @param serviceMethod the service method
   * @param service the service
   * @return the optional endpoint
   */
  public Optional<Endpoint> deduceFromServiceMethod(ServiceMethod serviceMethod, Service service) {
    String goalType =
        TextConverter.toUpperUnderscore(serviceMethod.getFunction().getGoal().getText());

    if (goalToHttpMethod.containsKey(goalType)) {
      HttpMethod httpMethod = goalToHttpMethod.get(goalType);

      return Optional.of(
          new Endpoint(
              service,
              serviceMethod,
              httpMethod,
              mappingDeducer.deduceFrom(serviceMethod.getFunction(), httpMethod),
              requestParameterDeducer.deduceFrom()));
    } else {
      return Optional.empty();
    }
  }
}
