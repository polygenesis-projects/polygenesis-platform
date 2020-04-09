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
 * @param <T> the type parameter
 * @author Christos Tsakostas
 */
public abstract class DomainObjectDeducer<T extends DomainObject> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final Class<T> clazzDomainObject;
  /** The Domain object properties deducer. */
  protected final DomainObjectPropertiesDeducer domainObjectPropertiesDeducer;
  /** The Constructors deducer. */
  protected final ConstructorsDeducer constructorsDeducer;
  /** The State mutation method deducer. */
  protected final StateMutationMethodDeducer stateMutationMethodDeducer;

  private final DomainEventConstructorDeducer domainEventConstructorDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object deducer.
   *
   * @param clazzDomainObject the clazz domain object
   * @param domainObjectPropertiesDeducer the domain object properties deducer
   * @param constructorsDeducer the constructors deducer
   * @param stateMutationMethodDeducer the state mutation method deducer
   * @param domainEventConstructorDeducer the domain event constructor deducer
   */
  public DomainObjectDeducer(
      Class<T> clazzDomainObject,
      DomainObjectPropertiesDeducer domainObjectPropertiesDeducer,
      ConstructorsDeducer constructorsDeducer,
      StateMutationMethodDeducer stateMutationMethodDeducer,
      DomainEventConstructorDeducer domainEventConstructorDeducer) {
    this.clazzDomainObject = clazzDomainObject;
    this.domainObjectPropertiesDeducer = domainObjectPropertiesDeducer;
    this.constructorsDeducer = constructorsDeducer;
    this.stateMutationMethodDeducer = stateMutationMethodDeducer;
    this.domainEventConstructorDeducer = domainEventConstructorDeducer;
  }

  // ===============================================================================================
  // PROTECTED - ABSTRACT
  // ===============================================================================================

  /**
   * Gets abstract domain object abstraction scope.
   *
   * @return the abstract domain object abstraction scope
   */
  protected abstract AbstractionScope getAbstractDomainObjectAbstractionScope();

  /**
   * Gets domain object abstraction scope.
   *
   * @return the domain object abstraction scope
   */
  protected abstract AbstractionScope getDomainObjectAbstractionScope();

  // ===============================================================================================
  // PUBLIC
  // ===============================================================================================

  /**
   * Deduce domain objects set.
   *
   * @param thingRepository the thing repository
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<DomainObject> deduceDomainObjects(
      ThingRepository thingRepository, PackageName rootPackageName) {

    // All Abstract DomainObjects
    Set<DomainObject> allAbstractDomainObjects =
        getTopLevelAndAbstractSubclassesOfTopLevel(thingRepository, rootPackageName);

    // Continue with subclasses
    Set<DomainObject> concreteAndAbstractDomainObjects = new LinkedHashSet<>();
    concreteAndAbstractDomainObjects.addAll(allAbstractDomainObjects);

    thingRepository
        .getAbstractionItemsByScope(getDomainObjectAbstractionScope())
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
  // PROTECTED
  // ===============================================================================================

  /**
   * Gets top level and abstract subclasses of top level.
   *
   * @param thingRepository the thing repository
   * @param rootPackageName the root package name
   * @return the top level and abstract subclasses of top level
   */
  protected Set<DomainObject> getTopLevelAndAbstractSubclassesOfTopLevel(
      ThingRepository thingRepository, PackageName rootPackageName) {

    // Get Top-Level abstract superclasses first
    Set<DomainObject> topLevelDomainObjects = new LinkedHashSet<>();
    thingRepository
        .getAbstractionItemsByScope(getAbstractDomainObjectAbstractionScope())
        .stream()
        .filter(Thing::doesNotExtendThing)
        .forEach(
            thing ->
                makeDomainObject(
                    topLevelDomainObjects, thing, rootPackageName, new LinkedHashSet<>()));

    // Get Abstract Subclasses of Top-Level abstract superclasses
    Set<DomainObject> abstractDomainObjects = new LinkedHashSet<>();
    thingRepository
        .getAbstractionItemsByScope(getAbstractDomainObjectAbstractionScope())
        .stream()
        .filter(Thing::extendsThing)
        .forEach(
            thing ->
                makeDomainObject(
                    abstractDomainObjects, thing, rootPackageName, topLevelDomainObjects));

    // Concat all
    Set<DomainObject> allAbstractDomainObjects = new LinkedHashSet<>();
    allAbstractDomainObjects.addAll(topLevelDomainObjects);
    allAbstractDomainObjects.addAll(abstractDomainObjects);

    return allAbstractDomainObjects;
  }

  /**
   * Make superclass domain object.
   *
   * @param abstractDomainObjects the abstract domain objects
   * @param thing the thing
   * @return the domain object
   */
  protected DomainObject makeSuperclass(Set<DomainObject> abstractDomainObjects, Thing thing) {
    if (thing.supportsAbstractionScope(AbstractionScope.externallyProvided())) {
      return null;
    }

    if (thing.getMetadataValueIfExists(ThingMetadataKey.SUPER_CLASS) == null) {
      throw new IllegalStateException(
          String.format(
              "Super Class must be defined for thing=%s", thing.getThingName().getText()));
    }

    Thing thingSuperclass = Thing.class.cast(thing.getMetadataValue(ThingMetadataKey.SUPER_CLASS));

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
                        thingSuperclass.getThingName().getText(), thing.getThingName().getText())));
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
    DomainObject domainObject = createDomainObject(thing, domainObjectObjectName, thingPackageName);

    // Continue with the properties and exclude the ones already in the superclass.
    Set<DomainObjectProperty<?>> properties =
        domainObjectPropertiesDeducer.deduceDomainObjectPropertiesFromThingProperties(
            domainObject, superClass, thing);

    domainObject.assignProperties(properties);

    // Get Constructors
    Set<Constructor> constructors =
        constructorsDeducer.deduceConstructors(rootPackageName, domainObject, superClass, thing);

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
    if (superClass != null) {
      domainObject.assignSuperClass(superClass);
    }

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

  private Boolean isAbstract(Thing thing) {
    return thing.getAbstractionsScopes().contains(getAbstractDomainObjectAbstractionScope());
  }

  private DomainObject createDomainObject(
      Thing thing, ObjectName objectName, PackageName packageName) {
    DomainObject domainObject;

    if (clazzDomainObject.getSimpleName().equals("AggregateRoot")) {
      domainObject = createAggregateRoot(thing, objectName, packageName);
    } else if (clazzDomainObject.getSimpleName().equals("AggregateEntity")) {
      domainObject = createAggregateEntity(thing, objectName, packageName);
    } else {
      throw new IllegalStateException(clazzDomainObject.getSimpleName());
    }

    return domainObject;
  }

  private DomainObject createAggregateRoot(
      Thing thing, ObjectName objectName, PackageName packageName) {
    try {
      return clazzDomainObject
          .getDeclaredConstructor(
              InstantiationType.class, ObjectName.class, PackageName.class, Boolean.class)
          .newInstance(
              isAbstract(thing) ? InstantiationType.ABSTRACT : InstantiationType.CONCRETE,
              objectName,
              packageName,
              thing.getMultiTenant());
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }

  private DomainObject createAggregateEntity(
      Thing thing, ObjectName objectName, PackageName packageName) {
    try {
      return clazzDomainObject
          .getDeclaredConstructor(
              InstantiationType.class, ObjectName.class, PackageName.class, DomainObject.class)
          .newInstance(
              isAbstract(thing) ? InstantiationType.ABSTRACT : InstantiationType.CONCRETE,
              objectName,
              packageName,
              null);
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }
}
