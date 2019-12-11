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

package io.polygenesis.models.domain.common;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.StateMutationMethod;
import io.polygenesis.models.domain.domainmessage.DomainEventMutationDeducer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type State mutation method deducer.
 *
 * @author Christos Tsakostas
 */
public class StateMutationMethodDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final DataToDomainObjectPropertyConverter dataToDomainObjectPropertyConverter;
  private final DomainEventMutationDeducer domainEventMutationDeducer;
  private final IdentityDomainObjectPropertiesDeducer identityDomainObjectPropertiesDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new State mutation method deducer.
   *
   * @param dataToDomainObjectPropertyConverter the data to domain object property converter
   * @param domainEventMutationDeducer the domain event mutation deducer
   * @param identityDomainObjectPropertiesDeducer the identity domain object properties deducer
   */
  public StateMutationMethodDeducer(
      DataToDomainObjectPropertyConverter dataToDomainObjectPropertyConverter,
      DomainEventMutationDeducer domainEventMutationDeducer,
      IdentityDomainObjectPropertiesDeducer identityDomainObjectPropertiesDeducer) {
    this.dataToDomainObjectPropertyConverter = dataToDomainObjectPropertyConverter;
    this.domainEventMutationDeducer = domainEventMutationDeducer;
    this.identityDomainObjectPropertiesDeducer = identityDomainObjectPropertiesDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce set.
   *
   * @param rootPackageName the root package name
   * @param domainObject the domain object
   * @param thing the thing
   * @param thingPackageName the thing package name
   * @return the set
   */
  public Set<StateMutationMethod> deduce(
      PackageName rootPackageName,
      DomainObject domainObject,
      Thing thing,
      PackageName thingPackageName) {
    Set<StateMutationMethod> stateMutationMethods = new LinkedHashSet<>();

    stateMutationMethods.addAll(
        deduceForStateMutation(rootPackageName, domainObject, thing, thingPackageName));

    return stateMutationMethods;
  }

  // ===============================================================================================
  // PRIVATE - STATE MUTATION
  // ===============================================================================================

  /**
   * Deduce for state mutation set.
   *
   * @param rootPackageName the root package name
   * @param domainObject the domain object
   * @param thing the thing
   * @param thingPackageName the thing package name
   * @return the set
   */
  public Set<StateMutationMethod> deduceForStateMutation(
      PackageName rootPackageName,
      DomainObject domainObject,
      Thing thing,
      PackageName thingPackageName) {
    Set<StateMutationMethod> stateMutationMethods = new LinkedHashSet<>();

    thing
        .getFunctions()
        .stream()
        .filter(
            function ->
                function.supportsAbstractionScope(AbstractionScope.domainAggregateRoot())
                    || function.supportsAbstractionScope(
                        AbstractionScope.domainAbstractAggregateRoot())
                    || function.supportsAbstractionScope(AbstractionScope.domainAggregateEntity()))
        .filter(function -> function.getPurpose().isModify())
        .forEach(
            function -> {
              Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

              properties.addAll(getIdentityProperties(function, rootPackageName));
              properties.addAll(
                  dataToDomainObjectPropertyConverter.convertMany(
                      domainObject,
                      function.getArguments().findDataExcludingIdentitiesAndPaging()));

              StateMutationMethod stateMutationMethod =
                  new StateMutationMethod(domainObject, function, properties, rootPackageName);

              if (!function.supportsAbstractionScope(
                  AbstractionScope.domainAbstractAggregateRoot())) {
                stateMutationMethod.assignDomainEvent(
                    domainEventMutationDeducer.deduceFrom(
                        rootPackageName, thingPackageName, thing, stateMutationMethod));
              }

              stateMutationMethods.add(stateMutationMethod);
            });

    return stateMutationMethods;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<DomainObjectProperty<?>> getIdentityProperties(
      Function function, PackageName rootPackageName) {
    if (function.getDelegatesToFunction() != null) {
      Thing thing = function.getDelegatesToFunction().getThing();

      if (thing.getOptionalParent() == null) {
        return identityDomainObjectPropertiesDeducer.makeRootIdentityDomainObjectProperties(
            thing, rootPackageName);
      } else {
        return identityDomainObjectPropertiesDeducer.makeEntityIdentityDomainObjectProperties(
            thing, rootPackageName);
      }
    } else {
      return new LinkedHashSet<>();
    }
  }
}
