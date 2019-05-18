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

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.GoalType;
import io.polygenesis.core.ThingType;
import io.polygenesis.generators.java.implementations.ScopeGoalTuple;
import io.polygenesis.generators.java.skeletons.MethodRepresentation;
import io.polygenesis.models.rest.Endpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Endpoint implementation registry.
 *
 * @author Christos Tsakostas
 */
public class EndpointImplementationRegistry {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  private static Map<ScopeGoalTuple, EndpointImplementor> scopeAndGoalMap = new HashMap<>();

  static {
    // AGGREGATE ROOT
    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingType.DOMAIN_AGGREGATE_ROOT, GoalType.CREATE.name()),
        new CreateAggregateRoot());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingType.DOMAIN_AGGREGATE_ROOT, GoalType.MODIFY.name()),
        new UpdateAggregateRoot());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingType.DOMAIN_AGGREGATE_ROOT, GoalType.FETCH_ONE.name()),
        new FetchOneAggregateRoot());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(
            ThingType.DOMAIN_AGGREGATE_ROOT, GoalType.FETCH_PAGED_COLLECTION.name()),
        new FetchPagedCollectionAggregateRoot());

    // AGGREGATE ENTITY
    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingType.DOMAIN_AGGREGATE_ENTITY, GoalType.CREATE.name()),
        new CreateAggregateEntity());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingType.DOMAIN_AGGREGATE_ENTITY, GoalType.MODIFY.name()),
        new UpdateAggregateEntity());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingType.DOMAIN_AGGREGATE_ENTITY, GoalType.FETCH_ONE.name()),
        new FetchOneAggregateEntity());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(
            ThingType.DOMAIN_AGGREGATE_ENTITY, GoalType.FETCH_PAGED_COLLECTION.name()),
        new FetchPagedCollectionAggregateEntity());
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Implementation optional.
   *
   * @param freemarkerService the freemarker service
   * @param endpoint the endpoint
   * @param methodRepresentation the method representation
   * @return the optional
   */
  @SuppressWarnings("CPD-END")
  public Optional<String> implementation(
      FreemarkerService freemarkerService,
      Endpoint endpoint,
      MethodRepresentation methodRepresentation) {
    if (isEndpointSupported(endpoint)) {
      return Optional.of(
          endpointImplementorFor(endpoint)
              .implementationFor(freemarkerService, endpoint, methodRepresentation));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Is endpoint supported boolean.
   *
   * @param endpoint the endpoint
   * @return the boolean
   */
  public boolean isEndpointSupported(Endpoint endpoint) {
    return scopeAndGoalMap.containsKey(
        new ScopeGoalTuple(
            endpoint.getServiceMethod().getFunction().getThing().getThingType(),
            TextConverter.toUpperUnderscore(
                endpoint.getServiceMethod().getFunction().getGoal().getText())));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private EndpointImplementor endpointImplementorFor(Endpoint endpoint) {
    return scopeAndGoalMap.get(
        new ScopeGoalTuple(
            endpoint.getServiceMethod().getFunction().getThing().getThingType(),
            TextConverter.toUpperUnderscore(
                endpoint.getServiceMethod().getFunction().getGoal().getText())));
  }
}
