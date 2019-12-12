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

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingMetadataKey;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainEvent;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.models.domain.StateMutationMethod;
import io.polygenesis.models.domain.domainmessage.DomainEventConstructorDeducer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain object deducer.
 *
 * @author Christos Tsakostas
 */
public abstract class DomainObjectDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final DomainObjectPropertiesDeducer domainObjectPropertiesDeducer;
  private final IdentityDomainObjectPropertiesDeducer identityDomainObjectPropertiesDeducer;
  private final ConstructorsDeducer constructorsDeducer;
  private final StateMutationMethodDeducer stateMutationMethodDeducer;
  private final DomainEventConstructorDeducer domainEventConstructorDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object deducer.
   *
   * @param domainObjectPropertiesDeducer the domain object properties deducer
   * @param identityDomainObjectPropertiesDeducer the identity domain object properties deducer
   * @param constructorsDeducer the constructors deducer
   * @param stateMutationMethodDeducer the state mutation method deducer
   * @param domainEventConstructorDeducer the domain event constructor deducer
   */
  public DomainObjectDeducer(
      DomainObjectPropertiesDeducer domainObjectPropertiesDeducer,
      IdentityDomainObjectPropertiesDeducer identityDomainObjectPropertiesDeducer,
      ConstructorsDeducer constructorsDeducer,
      StateMutationMethodDeducer stateMutationMethodDeducer,
      DomainEventConstructorDeducer domainEventConstructorDeducer) {
    this.domainObjectPropertiesDeducer = domainObjectPropertiesDeducer;
    this.identityDomainObjectPropertiesDeducer = identityDomainObjectPropertiesDeducer;
    this.constructorsDeducer = constructorsDeducer;
    this.stateMutationMethodDeducer = stateMutationMethodDeducer;
    this.domainEventConstructorDeducer = domainEventConstructorDeducer;
  }

  // ===============================================================================================
  // PROTECTED - ABSTRACT
  // ===============================================================================================

  /**
   * Gets abstract abstraction scope.
   *
   * @return the abstract abstraction scope
   */
  protected abstract AbstractionScope getAbstractAbstractionScope();

  // ===============================================================================================
  // PUBLIC
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thingRepository the thing repository
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<DomainObject> deduceFrom(
      ThingRepository thingRepository, PackageName rootPackageName) {

    // Get Top-Level abstract superclasses first
    Set<DomainObject> topLevelDomainObjects = new LinkedHashSet<>();
    thingRepository
        .getAbstractionItemsByScope(getAbstractAbstractionScope())
        .stream()
        .filter(Thing::doesNotExtendThing)
        .forEach(
            thing ->
                makeDomainObject(
                    topLevelDomainObjects, thing, rootPackageName, new LinkedHashSet<>()));

    // Get Abstract Subclasses of Top-Level abstract superclasses
    Set<DomainObject> abstractDomainObjects = new LinkedHashSet<>();
    thingRepository
        .getAbstractionItemsByScope(getAbstractAbstractionScope())
        .stream()
        .filter(Thing::extendsThing)
        .forEach(
            thing ->
                makeDomainObject(
                    abstractDomainObjects, thing, rootPackageName, topLevelDomainObjects));

    // Continue with subclasses
    Set<DomainObject> concreteAndAbstractDomainObjects = new LinkedHashSet<>();
    concreteAndAbstractDomainObjects.addAll(topLevelDomainObjects);
    concreteAndAbstractDomainObjects.addAll(abstractDomainObjects);

    thingRepository
        .getAbstractionItemsByScope(AbstractionScope.domainAggregateRoot())
        .stream()
        .forEach(
            thing ->
                makeDomainObject(
                    concreteAndAbstractDomainObjects,
                    thing,
                    rootPackageName,
                    concreteAndAbstractDomainObjects));

    return concreteAndAbstractDomainObjects;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void makeDomainObject(
      Set<DomainObject> domainObjectsToFill,
      Thing thing,
      PackageName rootPackageName,
      Set<DomainObject> abstractDomainObjects) {
    if (thing.getOptionalParent() != null) {
      throw new IllegalArgumentException(
          String.format(
              "The aggregate root=%s should not have a parent", thing.getThingName().getText()));
    }

    PackageName thingPackageName = thing.makePackageName(rootPackageName, thing);
    ObjectName domainObjectObjectName = new ObjectName(thing.getThingName().getText());

    // Start by getting the superclass
    DomainObject superClass = makeSuperclass(abstractDomainObjects, thing);

    // Create DomainObject
    DomainObject domainObject =
        new AggregateRoot(
            isAbstract(thing) ? InstantiationType.ABSTRACT : InstantiationType.CONCRETE,
            domainObjectObjectName,
            thingPackageName,
            thing.getMultiTenant());

    // Continue with the properties and exclude the ones already in the superclass.
    Set<DomainObjectProperty<?>> properties =
        domainObjectPropertiesDeducer.deduceRootDomainObjectPropertiesFromThing(
            domainObject,
            superClass,
            identityDomainObjectPropertiesDeducer.makeRootIdentityDomainObjectProperties(
                thing, rootPackageName),
            thing);

    domainObject.assignProperties(properties);

    // Get Constructors
    Set<Constructor> constructors =
        constructorsDeducer.deduceConstructors(
            rootPackageName,
            domainObject,
            superClass,
            identityDomainObjectPropertiesDeducer.makeRootIdentityDomainObjectProperties(
                thing, rootPackageName),
            thing);

    if (!isAbstract(thing)) {
      constructors.forEach(
          constructor -> {
            DomainEvent domainEvent =
                domainEventConstructorDeducer.deduceFrom(rootPackageName, thing, constructor);
            constructor.assignDomainEvent(domainEvent);
          });
    }

    // Add constructors
    domainObject.addConstructors(constructors);

    // Get StateMutationMethods
    Set<StateMutationMethod> stateMutationMethods =
        stateMutationMethodDeducer.deduce(rootPackageName, domainObject, thing, thingPackageName);

    // Set Superclass
    domainObject.assignSuperClass(superClass);

    // Add State Mutation Methods
    domainObject.addStateMutationMethods(stateMutationMethods);

    // Persistence
    if (!isAbstract(thing)) {
      Persistence persistence =
          new Persistence(
              thingPackageName,
              new ObjectName(String.format("%sRepository", thing.getThingName().getText())),
              domainObjectObjectName,
              new ObjectName(String.format("%sId", thing.getThingName().getText())),
              thing.getMultiTenant());

      // Add persistence
      domainObject.assignPersistence(persistence);
    }

    // Exportable
    if (thing.supportsAbstractionScope(AbstractionScope.externallyProvided())) {
      domainObject.changeExportableTo(false);
    }

    // Add
    domainObjectsToFill.add(domainObject);
  }

  private DomainObject makeSuperclass(Set<DomainObject> abstractDomainObjects, Thing thing) {
    if (thing.getMetadataValueIfExists(ThingMetadataKey.SUPER_CLASS) != null) {
      Thing thingSuperclass =
          Thing.class.cast(thing.getMetadataValue(ThingMetadataKey.SUPER_CLASS));

      return abstractDomainObjects
          .stream()
          .filter(
              abstractDomainObject ->
                  abstractDomainObject
                      .getObjectName()
                      .equals(new ObjectName(thingSuperclass.getThingName().getText())))
          .findFirst()
          .orElseThrow(
              () ->
                  new IllegalStateException(
                      String.format(
                          "Could not find superclass=%s for thing=%s",
                          thingSuperclass.getThingName().getText(),
                          thing.getThingName().getText())));
    } else {
      ObjectName objectNameSuperclass =
          thing.getMultiTenant()
              ? new ObjectName("TenantAggregateRoot")
              : new ObjectName("AggregateRoot");
      PackageName packageName = new PackageName("com.oregor.trinity4j.domain");

      return new AggregateRoot(
          InstantiationType.ABSTRACT, objectNameSuperclass, packageName, thing.getMultiTenant());
    }
  }

  private Boolean isAbstract(Thing thing) {
    return thing.getAbstractionsScopes().contains(getAbstractAbstractionScope());
  }
}
