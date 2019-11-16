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

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.FactoryMethod;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.models.domain.StateMutationMethod;
import io.polygenesis.models.domain.StateQueryMethod;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate root deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final AggregateRootPropertyDeducer aggregateRootPropertyDeducer;
  private final StateMutationMethodDeducer stateMutationMethodDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root deducer.
   *
   * @param aggregateRootPropertyDeducer the aggregate root property deducer
   * @param stateMutationMethodDeducer the state mutation method deducer
   */
  public AggregateRootDeducer(
      AggregateRootPropertyDeducer aggregateRootPropertyDeducer,
      StateMutationMethodDeducer stateMutationMethodDeducer) {
    this.aggregateRootPropertyDeducer = aggregateRootPropertyDeducer;
    this.stateMutationMethodDeducer = stateMutationMethodDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thingRepository the thing repository
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<AggregateRoot> deduceFrom(
      ThingRepository thingRepository, PackageName rootPackageName) {
    Set<AggregateRoot> aggregateRoots = new LinkedHashSet<>();

    thingRepository
        .getAbstractionItemsByScopes(
            new LinkedHashSet<>(
                Arrays.asList(
                    AbstractionScope.domainAggregateRoot(),
                    AbstractionScope.domainAbstractAggregateRoot())))
        .stream()
        .forEach(
            thing -> {
              if (thing.getOptionalParent() == null) {
                makeAggregateRoot(aggregateRoots, thing, rootPackageName);
              } else {
                throw new IllegalArgumentException(
                    String.format(
                        "The aggregate root=%s should not have a parent",
                        thing.getThingName().getText()));
              }
            });

    return aggregateRoots;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make aggregate root aggregate root.
   *
   * @param aggregateRoots the aggregate roots
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the aggregate root
   */
  private void makeAggregateRoot(
      Set<AggregateRoot> aggregateRoots, Thing thing, PackageName rootPackageName) {
    PackageName packageName = thing.makePackageName(rootPackageName, thing);

    ObjectName aggregateRootObjectName = makeAggregateRootName(thing);

    Set<DomainObjectProperty<?>> properties =
        aggregateRootPropertyDeducer.deduceFromThing(thing, rootPackageName);

    Set<Constructor> constructors =
        aggregateRootPropertyDeducer.deduceConstructors(thing, rootPackageName);

    Set<StateMutationMethod> stateMutationMethods =
        stateMutationMethodDeducer.deduce(thing, properties);
    Set<StateQueryMethod> stateQueryMethods = new LinkedHashSet<>();
    Set<FactoryMethod> factoryMethods = new LinkedHashSet<>();

    if (isAbstract(thing)) {
      aggregateRoots.add(
          new AggregateRoot(
              isAbstract(thing) ? InstantiationType.ABSTRACT : InstantiationType.CONCRETE,
              aggregateRootObjectName,
              packageName,
              properties,
              constructors,
              thing.getMultiTenant(),
              stateMutationMethods,
              stateQueryMethods,
              factoryMethods,
              makeSuperclass(thing.getMultiTenant())));
    } else {
      Persistence persistence =
          new Persistence(
              packageName,
              makePersistenceName(thing),
              aggregateRootObjectName,
              makeAggregateRootIdName(thing),
              thing.getMultiTenant());

      aggregateRoots.add(
          new AggregateRootPersistable(
              isAbstract(thing) ? InstantiationType.ABSTRACT : InstantiationType.CONCRETE,
              aggregateRootObjectName,
              packageName,
              properties,
              constructors,
              thing.getMultiTenant(),
              stateMutationMethods,
              stateQueryMethods,
              factoryMethods,
              makeSuperclass(thing.getMultiTenant()),
              persistence));
    }

  }

  /**
   * Make aggregate root name name.
   *
   * @param thing the thing
   * @return the name
   */
  private ObjectName makeAggregateRootName(Thing thing) {
    return new ObjectName(thing.getThingName().getText());
  }

  /**
   * Make aggregate root id name name.
   *
   * @param thing the thing
   * @return the name
   */
  private ObjectName makeAggregateRootIdName(Thing thing) {
    return new ObjectName(thing.getThingName().getText() + "Id");
  }

  /**
   * Make persistence name name.
   *
   * @param thing the thing
   * @return the name
   */
  private ObjectName makePersistenceName(Thing thing) {
    return new ObjectName(thing.getThingName().getText() + "Persistence");
  }

  /**
   * Make superclass aggregate root.
   *
   * @return the aggregate root
   */
  private AggregateRoot makeSuperclass(Boolean multiTenant) {
    return new AggregateRoot(
        InstantiationType.ABSTRACT,
        multiTenant ? new ObjectName("TenantAggregateRoot") : new ObjectName("AggregateRoot"),
        new PackageName("com.oregor.trinity4j.domain"),
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        multiTenant,
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        new LinkedHashSet<>());
  }

  /**
   * Is abstract boolean.
   *
   * @param thing the thing
   * @return the boolean
   */
  private Boolean isAbstract(Thing thing) {
    return thing.getAbstractionsScopes().contains(AbstractionScope.domainAbstractAggregateRoot());
  }
}
