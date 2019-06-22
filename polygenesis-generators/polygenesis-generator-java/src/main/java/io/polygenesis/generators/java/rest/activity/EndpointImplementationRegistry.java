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

package io.polygenesis.generators.java.rest.activity;

import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.generators.java.implementations.ScopePurposeTuple;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.representations.code.MethodRepresentation;
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
  private static Map<ScopePurposeTuple, EndpointImplementor> scopeAndPurposeMap = new HashMap<>();

  static {
    // AGGREGATE ROOT
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.create()),
        new CreateAggregateRoot());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.modify()),
        new UpdateAggregateRoot());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.fetchOne()),
        new FetchOneAggregateRoot());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(
            AbstractionScope.domainAggregateRoot(), Purpose.fetchPagedCollection()),
        new FetchPagedCollectionAggregateRoot());

    // AGGREGATE ENTITY
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.create()),
        new CreateAggregateEntity());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.modify()),
        new UpdateAggregateEntity());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.fetchOne()),
        new FetchOneAggregateEntity());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(
            AbstractionScope.domainAggregateEntity(), Purpose.fetchPagedCollection()),
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
          endpointImplementorFor(
                  getAbstractionScopeAsOptional(endpoint)
                      .orElseThrow(IllegalArgumentException::new),
                  endpoint)
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
    return getAbstractionScopeAsOptional(endpoint).isPresent();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Optional<AbstractionScope> getAbstractionScopeAsOptional(Endpoint endpoint) {
    return endpoint
        .getServiceMethod()
        .getFunction()
        .getThing()
        .getAbstractionsScopes()
        .stream()
        .filter(
            abstractionScope ->
                scopeAndPurposeMap.containsKey(
                    new ScopePurposeTuple(
                        abstractionScope, endpoint.getServiceMethod().getFunction().getPurpose())))
        .findFirst();
  }

  private EndpointImplementor endpointImplementorFor(
      AbstractionScope abstractionScope, Endpoint endpoint) {
    return scopeAndPurposeMap.get(
        new ScopePurposeTuple(
            abstractionScope, endpoint.getServiceMethod().getFunction().getPurpose()));
  }
}
