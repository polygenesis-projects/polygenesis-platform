/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.models.domain.agrregateentity;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.StateMutationMethod;
import io.polygenesis.models.domain.common.ConstructorsDeducer;
import io.polygenesis.models.domain.common.DomainObjectDeducer;
import io.polygenesis.models.domain.common.DomainObjectPropertiesDeducer;
import io.polygenesis.models.domain.common.StateMutationMethodDeducer;
import io.polygenesis.models.domain.domainmessage.DomainEventConstructorDeducer;
import java.util.LinkedHashSet;
import java.util.Set;

public class AggregateEntityDeducer extends DomainObjectDeducer<AggregateEntity> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity deducer.
   *
   * @param domainObjectPropertiesDeducer the domain object properties deducer
   * @param constructorsDeducer the constructors deducer
   * @param stateMutationMethodDeducer the state mutation method deducer
   * @param domainEventConstructorDeducer the domain event constructor deducer
   */
  public AggregateEntityDeducer(
      DomainObjectPropertiesDeducer domainObjectPropertiesDeducer,
      ConstructorsDeducer constructorsDeducer,
      StateMutationMethodDeducer stateMutationMethodDeducer,
      DomainEventConstructorDeducer domainEventConstructorDeducer) {
    super(
        AggregateEntity.class,
        domainObjectPropertiesDeducer,
        constructorsDeducer,
        stateMutationMethodDeducer,
        domainEventConstructorDeducer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  protected AbstractionScope getAbstractDomainObjectAbstractionScope() {
    return AbstractionScope.domainAbstractAggregateEntity();
  }

  @Override
  protected AbstractionScope getDomainObjectAbstractionScope() {
    return AbstractionScope.domainAggregateEntity();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce aggregate entities set.
   *
   * @param thingParent the thingParent
   * @param domainObjectParent the domain object parent
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<DomainObject> deduceAggregateEntities(
      ThingRepository thingRepository,
      Thing thingParent,
      DomainObject domainObjectParent,
      PackageName rootPackageName) {
    Set<DomainObject> aggregateEntities = new LinkedHashSet<>();

    Set<DomainObject> allAbstractDomainObjects =
        getTopLevelAndAbstractSubclassesOfTopLevel(thingRepository, rootPackageName);

    fillAggregateEntities(
        aggregateEntities,
        allAbstractDomainObjects,
        thingParent,
        domainObjectParent,
        rootPackageName);

    return aggregateEntities;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void fillAggregateEntities(
      Set<DomainObject> aggregateEntitiesToFill,
      Set<DomainObject> allAbstractDomainObjects,
      Thing thingParent,
      DomainObject domainObjectParent,
      PackageName rootPackageName) {

    thingParent
        .getChildren()
        .forEach(
            thingChild -> {
              PackageName thingPackageName =
                  thingParent.makePackageName(rootPackageName, thingParent);

              DomainObject aggregateEntity =
                  deduceAggregateEntity(
                      allAbstractDomainObjects,
                      domainObjectParent,
                      thingChild,
                      rootPackageName,
                      thingPackageName);

              aggregateEntitiesToFill.add(aggregateEntity);

              // Recursive iteration into children of entities
              fillAggregateEntities(
                  aggregateEntitiesToFill,
                  allAbstractDomainObjects,
                  thingChild,
                  aggregateEntity,
                  rootPackageName);
            });
  }

  private AggregateEntity deduceAggregateEntity(
      Set<DomainObject> allAbstractDomainObjects,
      DomainObject domainObjectParent,
      Thing thingChild,
      PackageName rootPackageName,
      PackageName thingPackageName) {

    // Super Class
    DomainObject aggregateEntitySuperClass = makeSuperclass(allAbstractDomainObjects, thingChild);

    // Domain Object
    AggregateEntity aggregateEntity =
        new AggregateEntity(
            InstantiationType.CONCRETE,
            new ObjectName(thingChild.getThingName().getText()),
            thingPackageName,
            domainObjectParent);

    aggregateEntity.assignSuperClass(aggregateEntitySuperClass);

    // Properties
    Set<DomainObjectProperty<?>> properties =
        domainObjectPropertiesDeducer.deduceDomainObjectPropertiesFromThingProperties(
            aggregateEntity, aggregateEntitySuperClass, thingChild);

    aggregateEntity.assignProperties(properties);

    // Get Constructors
    Set<Constructor> constructors =
        constructorsDeducer.deduceConstructors(
            rootPackageName, aggregateEntity, aggregateEntitySuperClass, thingChild);

    // Add Constructors
    aggregateEntity.addConstructors(constructors);

    // stateMutationMethods
    Set<StateMutationMethod> stateMutationMethods =
        stateMutationMethodDeducer.deduceForStateMutation(
            rootPackageName, aggregateEntity, thingChild, thingPackageName);

    aggregateEntity.addStateMutationMethods(stateMutationMethods);

    return aggregateEntity;
  }
}
