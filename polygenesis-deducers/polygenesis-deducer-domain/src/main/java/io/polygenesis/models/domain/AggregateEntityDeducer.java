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

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Thing;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.DataGroup;
import java.util.LinkedHashSet;
import java.util.Optional;
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
  private final DomainObjectConstructorDeducer domainObjectConstructorDeducer;
  private final AggregateEntityPropertyDeducer aggregateEntityPropertyDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public AggregateEntityDeducer(
      DomainObjectConstructorDeducer domainObjectConstructorDeducer,
      AggregateEntityPropertyDeducer aggregateEntityPropertyDeducer) {
    this.domainObjectConstructorDeducer = domainObjectConstructorDeducer;
    this.aggregateEntityPropertyDeducer = aggregateEntityPropertyDeducer;
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
            thingChild -> {
              aggregateEntities.add(deduceAggregateEntity(thing, thingChild, rootPackageName));
            });

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
    return new AggregateEntity(
        InstantiationType.CONCRETE,
        makeSuperclass(thingParent.getMultiTenant()),
        new ObjectName(thingChild.getThingName().getText()),
        thingParent.makePackageName(rootPackageName, thingParent),
        aggregateEntityPropertyDeducer.deduceFrom(thingParent, thingChild, rootPackageName),
        domainObjectConstructorDeducer.deduceFrom(thingChild, rootPackageName),
        thingParent.getMultiTenant());
  }

  /**
   * Convert aggregate entity to data array data array.
   *
   * @param aggregateEntity the aggregate entity
   * @return the data array
   */
  private DataArray convertAggregateEntityToDataArray(AggregateEntity aggregateEntity) {
    DataGroup dataGroup = aggregateEntity.getData();

    return new DataArray(
        dataGroup.getDataSource(),
        dataGroup.getVariableName(),
        dataGroup.getDataBusinessType(),
        dataGroup.getDataValidator(),
        dataGroup);
  }

  /**
   * Make superclass optional.
   *
   * @return the optional
   */
  private Optional<AggregateEntity> makeSuperclass(Boolean multiTenant) {
    return Optional.of(
        new AggregateEntity(
            InstantiationType.ABSTRACT,
            Optional.empty(),
            new ObjectName("AggregateEntity"),
            new PackageName("com.oregor.ddd4j.core"),
            new LinkedHashSet<>(),
            new LinkedHashSet<>(),
            multiTenant));
  }
}
