/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.models.domain;

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.ThingScopeType;
import io.polygenesis.core.data.PackageName;
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
  private final AggregateConstructorDeducer aggregateConstructorDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root deducer.
   *
   * @param aggregateRootPropertyDeducer the aggregate root property deducer
   * @param aggregateConstructorDeducer the aggregate constructor deducer
   */
  public AggregateRootDeducer(
      AggregateRootPropertyDeducer aggregateRootPropertyDeducer,
      AggregateConstructorDeducer aggregateConstructorDeducer) {
    this.aggregateRootPropertyDeducer = aggregateRootPropertyDeducer;
    this.aggregateConstructorDeducer = aggregateConstructorDeducer;
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
        .getAbstractDomainAggregateRootThings()
        .forEach(
            thing -> {
              aggregateRoots.add(makeAggregateRoot(thing, rootPackageName));
            });

    thingRepository
        .getApiThings()
        .forEach(
            thing -> {
              aggregateRoots.add(makeAggregateRoot(thing, rootPackageName));
            });

    return aggregateRoots;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make aggregate root aggregate root.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the aggregate root
   */
  protected AggregateRoot makeAggregateRoot(Thing thing, PackageName rootPackageName) {
    PackageName packageName = makeAggregateRootPackageName(rootPackageName, thing);

    Name aggregateRootName = makeAggregateRootName(thing);

    Set<AbstractProperty> properties =
        aggregateRootPropertyDeducer.deduceFrom(thing, rootPackageName);

    Set<Constructor> constructors = aggregateConstructorDeducer.deduceFrom(thing, rootPackageName);

    Set<StateMutationMethod> stateMutationMethods = new LinkedHashSet<>();
    Set<StateQueryMethod> stateQueryMethods = new LinkedHashSet<>();
    Set<FactoryMethod> factoryMethods = new LinkedHashSet<>();

    Persistence persistence =
        new Persistence(
            packageName,
            makePersistenceName(thing),
            aggregateRootName,
            makeAggregateRootIdName(thing),
            thing.getMultiTenant());

    return new AggregateRoot(
        makeSuperclass(),
        thing.getThingScopeType().equals(ThingScopeType.ABSTRACT_DOMAIN_AGGREGATE_ROOT)
            ? InstantiationType.ABSTRACT
            : InstantiationType.NORMAL,
        packageName,
        aggregateRootName,
        properties,
        constructors,
        stateMutationMethods,
        stateQueryMethods,
        factoryMethods,
        persistence,
        thing.getMultiTenant());
  }

  /**
   * Make aggregate root package name package name.
   *
   * @param rootPackageName the root package name
   * @param thing the thing
   * @return the package name
   */
  private PackageName makeAggregateRootPackageName(PackageName rootPackageName, Thing thing) {
    return new PackageName(
        rootPackageName.getText() + "." + thing.getThingName().getText().toLowerCase());
  }

  /**
   * Make aggregate root name name.
   *
   * @param thing the thing
   * @return the name
   */
  private Name makeAggregateRootName(Thing thing) {
    return new Name(thing.getThingName().getText());
  }

  /**
   * Make aggregate root id name name.
   *
   * @param thing the thing
   * @return the name
   */
  private Name makeAggregateRootIdName(Thing thing) {
    return new Name(thing.getThingName().getText() + "Id");
  }

  /**
   * Make persistence name name.
   *
   * @param thing the thing
   * @return the name
   */
  private Name makePersistenceName(Thing thing) {
    return new Name(thing.getThingName().getText() + "Persistence");
  }

  /**
   * Make superclass aggregate root.
   *
   * @return the aggregate root
   */
  private AggregateRoot makeSuperclass() {
    return new AggregateRoot(
        AggregateRoot.noSuperClass(),
        InstantiationType.ABSTRACT,
        new PackageName("com.oregor.ddd4j.core"),
        new Name("AggregateRoot"),
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        false);
  }
}
