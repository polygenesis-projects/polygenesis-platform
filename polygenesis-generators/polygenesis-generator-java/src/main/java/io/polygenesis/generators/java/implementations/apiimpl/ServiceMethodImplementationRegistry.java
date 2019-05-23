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

package io.polygenesis.generators.java.implementations.apiimpl;

import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.generators.java.implementations.ScopePurposeTuple;
import io.polygenesis.generators.java.skeletons.MethodRepresentation;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.apiimpl.ServiceImplementation;
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
  private static Map<ScopePurposeTuple, ServiceMethodImplementor> scopeAndPurposeMap =
      new HashMap<>();

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

    // ENTITY
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
          serviceMethodImplementorFor(
                  getAbstractionScopeAsOptional(serviceMethod)
                      .orElseThrow(IllegalArgumentException::new),
                  serviceMethod)
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
    return getAbstractionScopeAsOptional(serviceMethod).isPresent();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Optional<AbstractionScope> getAbstractionScopeAsOptional(ServiceMethod serviceMethod) {
    return serviceMethod
        .getFunction()
        .getThing()
        .getAbstractionsScopes()
        .stream()
        .filter(
            abstractionScope ->
                scopeAndPurposeMap.containsKey(
                    new ScopePurposeTuple(
                        abstractionScope, serviceMethod.getFunction().getPurpose())))
        .findFirst();
  }

  /**
   * Service method implementor for service method implementor.
   *
   * @param serviceMethod the service method
   * @return the service method implementor
   */
  private ServiceMethodImplementor serviceMethodImplementorFor(
      AbstractionScope abstractionScope, ServiceMethod serviceMethod) {
    return scopeAndPurposeMap.get(
        new ScopePurposeTuple(abstractionScope, serviceMethod.getFunction().getPurpose()));
  }
}
