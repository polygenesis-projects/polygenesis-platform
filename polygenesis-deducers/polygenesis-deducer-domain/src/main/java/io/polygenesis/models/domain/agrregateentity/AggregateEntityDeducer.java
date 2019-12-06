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

package io.polygenesis.models.domain.agrregateentity;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.StateMutationMethod;
import io.polygenesis.models.domain.common.ConstructorsDeducer;
import io.polygenesis.models.domain.common.DomainObjectPropertiesDeducer;
import io.polygenesis.models.domain.common.IdentityDomainObjectPropertiesDeducer;
import io.polygenesis.models.domain.common.StateMutationMethodDeducer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate entity deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final DomainObjectPropertiesDeducer domainObjectPropertiesDeducer;
  private final IdentityDomainObjectPropertiesDeducer identityDomainObjectPropertiesDeducer;
  private final ConstructorsDeducer constructorsDeducer;
  private final StateMutationMethodDeducer stateMutationMethodDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity deducer.
   *
   * @param domainObjectPropertiesDeducer the domain object properties deducer
   * @param identityDomainObjectPropertiesDeducer the identity domain object properties deducer
   * @param constructorsDeducer the constructors deducer
   * @param stateMutationMethodDeducer the state mutation method deducer
   */
  public AggregateEntityDeducer(
      DomainObjectPropertiesDeducer domainObjectPropertiesDeducer,
      IdentityDomainObjectPropertiesDeducer identityDomainObjectPropertiesDeducer,
      ConstructorsDeducer constructorsDeducer,
      StateMutationMethodDeducer stateMutationMethodDeducer) {
    this.domainObjectPropertiesDeducer = domainObjectPropertiesDeducer;
    this.identityDomainObjectPropertiesDeducer = identityDomainObjectPropertiesDeducer;
    this.constructorsDeducer = constructorsDeducer;
    this.stateMutationMethodDeducer = stateMutationMethodDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce aggregate entities set.
   *
   * @param thingParent the thingParent
   * @return the set
   */
  public Set<DomainObject> deduceAggregateEntities(
      Thing thingParent, DomainObject domainObjectParent, PackageName rootPackageName) {
    Set<DomainObject> aggregateEntities = new LinkedHashSet<>();

    fillAggregateEntities(aggregateEntities, thingParent, domainObjectParent, rootPackageName);

    return aggregateEntities;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void fillAggregateEntities(
      Set<DomainObject> aggregateEntitiesToFill,
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
                      domainObjectParent, thingChild, rootPackageName, thingPackageName);

              aggregateEntitiesToFill.add(aggregateEntity);

              // Recursive iteration into children of entities
              fillAggregateEntities(
                  aggregateEntitiesToFill, thingChild, aggregateEntity, rootPackageName);
            });
  }

  private AggregateEntity deduceAggregateEntity(
      DomainObject domainObjectParent,
      Thing thingChild,
      PackageName rootPackageName,
      PackageName thingPackageName) {

    DomainObject aggregateEntitySuperClass = makeSuperclass();

    Set<DomainObjectProperty<?>> properties =
        domainObjectPropertiesDeducer.deduceEntityDomainObjectPropertiesFromThing(
            thingChild,
            identityDomainObjectPropertiesDeducer.makeEntityIdentityDomainObjectProperties(
                thingChild, rootPackageName));

    AggregateEntity aggregateEntity =
        new AggregateEntity(
            InstantiationType.CONCRETE,
            new ObjectName(thingChild.getThingName().getText()),
            thingPackageName,
            properties,
            domainObjectParent);

    aggregateEntity.assignSuperClass(aggregateEntitySuperClass);

    // Get Constructors
    Set<Constructor> constructors =
        constructorsDeducer.deduceConstructors(
            rootPackageName,
            aggregateEntitySuperClass,
            identityDomainObjectPropertiesDeducer.makeEntityIdentityDomainObjectProperties(
                thingChild, rootPackageName),
            thingChild);

    // Add Constructors
    aggregateEntity.addConstructors(constructors);

    // stateMutationMethods
    Set<StateMutationMethod> stateMutationMethods =
        stateMutationMethodDeducer.deduceForStateMutation(
            rootPackageName, aggregateEntity, thingChild, thingPackageName);

    aggregateEntity.addStateMutationMethods(stateMutationMethods);

    return aggregateEntity;
  }

  private DomainObject makeSuperclass() {
    return new AggregateEntity(
        InstantiationType.ABSTRACT,
        new ObjectName("AggregateEntity"),
        new PackageName("com.oregor.trinity4j.domain"),
        new LinkedHashSet<>(),
        null);
  }
}
