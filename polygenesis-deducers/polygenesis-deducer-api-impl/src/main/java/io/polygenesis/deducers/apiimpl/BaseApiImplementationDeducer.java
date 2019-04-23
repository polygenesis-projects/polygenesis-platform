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

package io.polygenesis.deducers.apiimpl;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Thing;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.AggregateRootPersistable;
import io.polygenesis.models.domain.BaseDomainEntity;
import io.polygenesis.models.domain.DomainModelRepository;
import io.polygenesis.models.domain.PropertyType;
import java.util.Optional;
import java.util.Set;

/**
 * The type Base api implementation deducer.
 *
 * @author Christos Tsakostas
 */
public abstract class BaseApiImplementationDeducer {

  /**
   * Gets optional domain entity.
   *
   * @param thing the thing
   * @param domainModelRepository the domain model repository
   * @return the optional domain object
   */
  protected Optional<BaseDomainEntity<?>> getOptionalDomainEntity(
      Thing thing, DomainModelRepository domainModelRepository) {
    ObjectName objectName = new ObjectName(thing.getThingName().getText());

    Optional<AggregateRoot> optionalAggregateRoot =
        domainModelRepository
            .getItems()
            .stream()
            .filter(aggregateRoot -> aggregateRoot.getObjectName().equals(objectName))
            .findFirst();

    if (optionalAggregateRoot.isPresent()) {
      AggregateRoot aggregateRoot = optionalAggregateRoot.get();
      return Optional.of(aggregateRoot);
    }

    Optional<AggregateEntityCollection> optionalAggregateEntityCollection =
        domainModelRepository
            .getItems()
            .stream()
            .flatMap(aggregateRoot -> aggregateRoot.getProperties().stream())
            .filter(
                property ->
                    property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_COLLECTION))
            .map(AggregateEntityCollection.class::cast)
            .filter(
                aggregateEntityCollection ->
                    aggregateEntityCollection
                        .getAggregateEntity()
                        .getObjectName()
                        .equals(objectName))
            .findFirst();

    if (optionalAggregateEntityCollection.isPresent()) {
      return Optional.of(optionalAggregateEntityCollection.get().getAggregateEntity());
    }

    return Optional.empty();
  }

  /**
   * Gets optional domain object converter.
   *
   * @param domainEntityConverters the domain object converters
   * @param domainObjectName the domain object name
   * @return the optional domain object converter
   */
  protected Optional<DomainEntityConverter> getOptionalDomainObjectConverter(
      Set<DomainEntityConverter> domainEntityConverters, ObjectName domainObjectName) {
    return domainEntityConverters
        .stream()
        .filter(
            domainObjectConverter ->
                domainObjectConverter.getDomainEntity().getObjectName().equals(domainObjectName))
        .findFirst();
  }

  /**
   * Aggregate root parent optional.
   *
   * @param domainModelRepository the domain model repository
   * @param domainEntity the domain entity
   * @return the optional
   */
  protected Optional<AggregateRootPersistable> aggregateRootParent(
      DomainModelRepository domainModelRepository, BaseDomainEntity<?> domainEntity) {

    return domainModelRepository
        .getItems()
        .stream()
        .filter(aggregateRoot -> aggregateRoot.contains(domainEntity))
        .map(AggregateRootPersistable.class::cast)
        .findFirst();
  }
}
