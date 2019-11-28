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

package io.polygenesis.models.domain.aggregateroot;

import static io.polygenesis.models.domain.PropertyType.AGGREGATE_ENTITY_COLLECTION;
import static java.util.stream.Collectors.toCollection;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.StateMutationMethod;
import io.polygenesis.models.domain.domainmessage.DomainEventMutationDeducer;
import io.polygenesis.models.domain.shared.AbstractPropertyDeducer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type State mutation method deducer.
 *
 * @author Christos Tsakostas
 */
public class StateMutationMethodDeducer extends AbstractPropertyDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final DomainEventMutationDeducer domainEventMutationDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new State mutation method deducer.
   *
   * @param domainEventMutationDeducer the domain event mutation deducer
   */
  public StateMutationMethodDeducer(DomainEventMutationDeducer domainEventMutationDeducer) {
    this.domainEventMutationDeducer = domainEventMutationDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  @Override
  protected Set<DomainObjectProperty<?>> makeIdentityDomainObjectProperties(
      Thing thing, PackageName rootPackageName) {
    throw new UnsupportedOperationException();
  }

  /**
   * Deduce set.
   *
   * @param thingPackageName the thing package name
   * @param thing the thing
   * @param properties the properties
   * @return the set
   */
  public Set<StateMutationMethod> deduce(
      PackageName thingPackageName, Thing thing, Set<DomainObjectProperty<?>> properties) {
    Set<StateMutationMethod> stateMutationMethods = new LinkedHashSet<>();

    stateMutationMethods.addAll(deduceForAggregateEntities(thing, properties));
    stateMutationMethods.addAll(deduceForStateMutation(thingPackageName, thing));

    return stateMutationMethods;
  }

  // ===============================================================================================
  // PRIVATE - STATE MUTATION
  // ===============================================================================================

  /**
   * Deduce for state mutation set.
   *
   * @param thing the thing
   * @return the set
   */
  private Set<StateMutationMethod> deduceForStateMutation(
      PackageName thingPackageName, Thing thing) {
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
              StateMutationMethod stateMutationMethod =
                  new StateMutationMethod(
                      convertToDomainFunction(function),
                      deduceDomainObjectPropertiesFromFunction(function));

              if (!function.supportsAbstractionScope(
                  AbstractionScope.domainAbstractAggregateRoot())) {
                stateMutationMethod.assignDomainEvent(
                    domainEventMutationDeducer.deduceFrom(
                        thingPackageName, thing, stateMutationMethod));
              }

              stateMutationMethods.add(stateMutationMethod);
            });

    return stateMutationMethods;
  }

  /**
   * Convert to domain function.
   *
   * @param function the function
   * @return the function
   */
  private Function convertToDomainFunction(Function function) {
    Set<DomainObjectProperty<?>> domainObjectProperties =
        deduceDomainObjectPropertiesFromFunction(function);

    return FunctionBuilder.of(
            function.getThing(), function.getName().getText(), function.getPurpose())
        .addArguments(
            domainObjectProperties
                .stream()
                .map(DomainObjectProperty::getData)
                .collect(toCollection(LinkedHashSet::new)))
        .build();
  }

  // ===============================================================================================
  // PRIVATE - AGGREGATE ENTITIES
  // ===============================================================================================

  /**
   * Deduce for aggregate entities set.
   *
   * @param thing the thing
   * @param properties the properties
   * @return the set
   */
  private Set<StateMutationMethod> deduceForAggregateEntities(
      Thing thing, Set<DomainObjectProperty<?>> properties) {
    Set<StateMutationMethod> stateMutationMethods = new LinkedHashSet<>();

    properties.forEach(
        property -> {
          if (property.getPropertyType().equals(AGGREGATE_ENTITY_COLLECTION)) {
            stateMutationMethods.addAll(
                forAggregateEntityCollection(
                    thing, AggregateEntityCollection.class.cast(property)));
          }
        });

    return stateMutationMethods;
  }

  /**
   * For aggregate entity collection set.
   *
   * @param thing the thing
   * @param aggregateEntityCollection the aggregate entity collection
   * @return the set
   */
  private Set<StateMutationMethod> forAggregateEntityCollection(
      Thing thing, AggregateEntityCollection aggregateEntityCollection) {
    Set<StateMutationMethod> stateMutationMethods = new LinkedHashSet<>();

    stateMutationMethods.add(
        aggregateRootCreateEntity(thing, aggregateEntityCollection.getAggregateEntity()));

    stateMutationMethods.add(
        aggregateRootUpdateEntity(thing, aggregateEntityCollection.getAggregateEntity()));

    stateMutationMethods.add(
        aggregateRootDeleteEntity(thing, aggregateEntityCollection.getAggregateEntity()));

    return stateMutationMethods;
  }

  /**
   * Aggregate root create entity state mutation method.
   *
   * @param thing the thing
   * @param aggregateEntity the aggregate entity
   * @return the state mutation method
   */
  private StateMutationMethod aggregateRootCreateEntity(
      Thing thing, AggregateEntity aggregateEntity) {

    Function function =
        FunctionBuilder.of(
                thing,
                String.format(
                    "define%s",
                    TextConverter.toUpperCamel(aggregateEntity.getObjectName().getText())),
                Purpose.aggregateRootCreateEntity())
            .setReturnValue(aggregateEntity.getData())
            .build();

    return new StateMutationMethod(function, deduceDomainObjectPropertiesFromFunction(function));
  }

  /**
   * Aggregate root update entity state mutation method.
   *
   * @param thing the thing
   * @param aggregateEntity the aggregate entity
   * @return the state mutation method
   */
  private StateMutationMethod aggregateRootUpdateEntity(
      Thing thing, AggregateEntity aggregateEntity) {
    Function function =
        FunctionBuilder.of(
                thing,
                String.format(
                    "modify%s",
                    TextConverter.toUpperCamel(aggregateEntity.getObjectName().getText())),
                Purpose.aggregateRootUpdateEntity())
            .setReturnValue(aggregateEntity.getData())
            .build();

    return new StateMutationMethod(function, deduceDomainObjectPropertiesFromFunction(function));
  }

  /**
   * Aggregate root delete entity state mutation method.
   *
   * @param thing the thing
   * @param aggregateEntity the aggregate entity
   * @return the state mutation method
   */
  private StateMutationMethod aggregateRootDeleteEntity(
      Thing thing, AggregateEntity aggregateEntity) {
    Function function =
        FunctionBuilder.of(
                thing,
                String.format(
                    "remove%s",
                    TextConverter.toUpperCamel(aggregateEntity.getObjectName().getText())),
                Purpose.aggregateRootDeleteEntity())
            .setReturnValue(aggregateEntity.getData())
            .build();

    return new StateMutationMethod(function, deduceDomainObjectPropertiesFromFunction(function));
  }
}
