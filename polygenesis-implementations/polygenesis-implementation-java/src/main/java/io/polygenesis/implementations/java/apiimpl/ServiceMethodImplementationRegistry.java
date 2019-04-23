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

package io.polygenesis.implementations.java.apiimpl;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.GoalType;
import io.polygenesis.core.ThingScopeType;
import io.polygenesis.implementations.java.ScopeGoalTuple;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Service method implementation registry.
 *
 * @author Christos Tsakostas
 */
public class ServiceMethodImplementationRegistry {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  private static Map<ScopeGoalTuple, ServiceMethodImplementor> scopeAndGoalMap = new HashMap<>();

  static {
    // AGGREGATE ROOT
    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingScopeType.DOMAIN_AGGREGATE_ROOT, GoalType.CREATE.name()),
        new CreateAggregateRoot());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingScopeType.DOMAIN_AGGREGATE_ROOT, GoalType.MODIFY.name()),
        new UpdateAggregateRoot());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingScopeType.DOMAIN_AGGREGATE_ROOT, GoalType.FETCH_ONE.name()),
        new FetchOneAggregateRoot());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(
            ThingScopeType.DOMAIN_AGGREGATE_ROOT, GoalType.FETCH_PAGED_COLLECTION.name()),
        new FetchPagedCollectionAggregateRoot());

    // ENTITY
    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingScopeType.DOMAIN_AGGREGATE_ENTITY, GoalType.CREATE.name()),
        new CreateAggregateEntity());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingScopeType.DOMAIN_AGGREGATE_ENTITY, GoalType.MODIFY.name()),
        new UpdateAggregateEntity());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingScopeType.DOMAIN_AGGREGATE_ENTITY, GoalType.FETCH_ONE.name()),
        new FetchOneAggregateEntity());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(
            ThingScopeType.DOMAIN_AGGREGATE_ENTITY, GoalType.FETCH_PAGED_COLLECTION.name()),
        new FetchPagedCollectionAggregateEntity());
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Implementation optional.
   *
   * @param freemarkerService the freemarker service
   * @param serviceImplementation the service implementation
   * @param serviceMethod the service method
   * @param methodRepresentation the method representation
   * @return the optional
   */
  @SuppressWarnings("CPD-END")
  public Optional<String> implementation(
      FreemarkerService freemarkerService,
      ServiceImplementation serviceImplementation,
      ServiceMethod serviceMethod,
      MethodRepresentation methodRepresentation) {
    if (isServiceMethodSupported(serviceMethod)) {
      return Optional.of(
          serviceMethodImplementorFor(serviceMethod)
              .implementationFor(
                  freemarkerService, serviceImplementation, serviceMethod, methodRepresentation));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Supports boolean.
   *
   * @param serviceMethod the service method
   * @return the boolean
   */
  public boolean isServiceMethodSupported(ServiceMethod serviceMethod) {
    return scopeAndGoalMap.containsKey(
        new ScopeGoalTuple(
            serviceMethod.getFunction().getThing().getThingScopeType(),
            TextConverter.toUpperUnderscore(serviceMethod.getFunction().getGoal().getText())));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Service method implementor for service method implementor.
   *
   * @param serviceMethod the service method
   * @return the service method implementor
   */
  private ServiceMethodImplementor serviceMethodImplementorFor(ServiceMethod serviceMethod) {
    return scopeAndGoalMap.get(
        new ScopeGoalTuple(
            serviceMethod.getFunction().getThing().getThingScopeType(),
            TextConverter.toUpperUnderscore(serviceMethod.getFunction().getGoal().getText())));
  }
}
