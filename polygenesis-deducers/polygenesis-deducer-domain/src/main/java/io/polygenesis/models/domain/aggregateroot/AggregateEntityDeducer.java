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

import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.StateMutationMethod;
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
  private final AggregateEntityPropertyDeducer aggregateEntityPropertyDeducer;
  private final StateMutationMethodDeducer stateMutationMethodDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity deducer.
   *
   * @param aggregateEntityPropertyDeducer the aggregate entity property deducer
   * @param stateMutationMethodDeducer the state mutation method deducer
   */
  public AggregateEntityDeducer(
      AggregateEntityPropertyDeducer aggregateEntityPropertyDeducer,
      StateMutationMethodDeducer stateMutationMethodDeducer) {
    this.aggregateEntityPropertyDeducer = aggregateEntityPropertyDeducer;
    this.stateMutationMethodDeducer = stateMutationMethodDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce aggregate entity collections set.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<AggregateEntityCollection> deduceAggregateEntityCollections(
      Thing thing, PackageName rootPackageName) {
    Set<AggregateEntityCollection> aggregateEntityCollections = new LinkedHashSet<>();

    Set<AggregateEntity> aggregateEntities = deduceAggregateEntities(thing, rootPackageName);

    aggregateEntities.forEach(
        aggregateEntity ->
            aggregateEntityCollections.add(
                new AggregateEntityCollection(
                    convertAggregateEntityToDataArray(aggregateEntity), aggregateEntity)));

    return aggregateEntityCollections;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Deduce aggregate entities set.
   *
   * @param thing the thing
   * @return the set
   */
  private Set<AggregateEntity> deduceAggregateEntities(Thing thing, PackageName rootPackageName) {
    Set<AggregateEntity> aggregateEntities = new LinkedHashSet<>();

    thing
        .getChildren()
        .forEach(
            thingChild ->
                aggregateEntities.add(deduceAggregateEntity(thing, thingChild, rootPackageName)));

    return aggregateEntities;
  }

  /**
   * Deduce aggregate entity aggregate entity.
   *
   * @param thingChild the thing child
   * @return the aggregate entity
   */
  private AggregateEntity deduceAggregateEntity(
      Thing thingParent, Thing thingChild, PackageName rootPackageName) {
    PackageName thingPackageName = thingParent.makePackageName(rootPackageName, thingParent);

    AggregateEntity aggregateEntity =
        new AggregateEntity(
            InstantiationType.CONCRETE,
            new ObjectName(thingChild.getThingName().getText()),
            thingPackageName,
            aggregateEntityPropertyDeducer.deduceFrom(thingParent, thingChild, rootPackageName),
            aggregateEntityPropertyDeducer.deduceConstructors(null, thingChild, rootPackageName),
            thingParent.getMultiTenant(),
            makeSuperclass(thingParent.getMultiTenant()));

    Set<StateMutationMethod> stateMutationMethods =
        stateMutationMethodDeducer.deduceForStateMutation(thingPackageName, thingChild);

    aggregateEntity.addStateMutationMethods(stateMutationMethods);

    return aggregateEntity;
  }

  /**
   * Convert aggregate entity to data array data array.
   *
   * @param aggregateEntity the aggregate entity
   * @return the data array
   */
  private DataArray convertAggregateEntityToDataArray(AggregateEntity aggregateEntity) {
    DataObject dataObject = aggregateEntity.getData();

    return new DataArray(
        dataObject.getVariableName(),
        dataObject.getDataPurpose(),
        dataObject.getDataValidator(),
        dataObject);
  }

  /**
   * Make superclass optional.
   *
   * @return the optional
   */
  private AggregateEntity makeSuperclass(Boolean multiTenant) {
    return new AggregateEntity(
        InstantiationType.ABSTRACT,
        new ObjectName("AggregateEntity"),
        new PackageName("com.oregor.trinity4j.domain"),
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        multiTenant);
  }
}
