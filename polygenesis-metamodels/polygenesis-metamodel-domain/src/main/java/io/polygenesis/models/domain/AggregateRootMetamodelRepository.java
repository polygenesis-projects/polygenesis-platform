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

package io.polygenesis.models.domain;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.AbstractMetamodelRepository;
import io.polygenesis.core.MetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Domain model repository.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootMetamodelRepository extends AbstractMetamodelRepository<AggregateRoot>
    implements MetamodelRepository<AggregateRoot>, DomainMetamodelRepository<AggregateRoot> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain model repository.
   *
   * @param items the items
   */
  public AggregateRootMetamodelRepository(Set<AggregateRoot> items) {
    super(items);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  @Override
  public Set<AggregateRoot> findByThings(Set<Thing> things) {
    Set<AggregateRoot> aggregateRoots = new LinkedHashSet<>();

    things.forEach(
        thing -> {
          Optional<AggregateRoot> optionalAggregateRoot =
              getItemByObjectName(new ObjectName(thing.getThingName().getText()));
          if (optionalAggregateRoot.isPresent()) {
            aggregateRoots.add(optionalAggregateRoot.get());
          }
        });

    return aggregateRoots;
  }

  /**
   * Find entity by thing name base domain entity.
   *
   * @param thingName the thing name
   * @return the base domain entity
   */
  @Override
  public BaseDomainEntity findEntityByThingName(ThingName thingName) {
    Set<BaseDomainEntity> entities = new LinkedHashSet<>();

    entities.addAll(
        getItems().stream().map(BaseDomainEntity.class::cast).collect(Collectors.toSet()));

    entities.addAll(
        getItems()
            .stream()
            .flatMap(aggregateRoot -> getAggregateEntitiesByAggregateRoot(aggregateRoot).stream())
            .map(BaseDomainEntity.class::cast)
            .collect(Collectors.toSet()));

    return entities
        .stream()
        .filter(entity -> entity.getObjectName().equals(new ObjectName(thingName.getText())))
        .findFirst()
        .orElseThrow();
  }

  /**
   * Gets all value objects by aggregate root.
   *
   * @param aggregateRoot the aggregate root
   * @return the all value objects by aggregate root
   */
  public Set<ValueObject> getAllValueObjectsByAggregateRoot(AggregateRoot aggregateRoot) {
    Set<ValueObject> valueObjects = getValueObjectsByAggregateRoot(aggregateRoot);
    Set<AggregateEntity> aggregateEntities = getAggregateEntitiesByAggregateRoot(aggregateRoot);

    aggregateEntities.forEach(
        aggregateEntity -> valueObjects.addAll(getValueObjectsByAggregateEntity(aggregateEntity)));

    return valueObjects;
  }

  /**
   * Gets value objects by aggregate root.
   *
   * @param aggregateRoot the aggregate root
   * @return the value objects by aggregate root
   */
  public Set<ValueObject> getValueObjectsByAggregateRoot(AggregateRoot aggregateRoot) {
    return aggregateRoot
        .getProperties()
        .stream()
        .filter(property -> property.getPropertyType().equals(PropertyType.VALUE_OBJECT))
        .map(ValueObject.class::cast)
        .collect(toCollection(LinkedHashSet::new));
  }

  /**
   * Gets value objects by aggregate entity.
   *
   * @param aggregateEntity the aggregate entity
   * @return the value objects by aggregate entity
   */
  public Set<ValueObject> getValueObjectsByAggregateEntity(AggregateEntity aggregateEntity) {
    return aggregateEntity
        .getProperties()
        .stream()
        .filter(property -> property.getPropertyType().equals(PropertyType.VALUE_OBJECT))
        .map(ValueObject.class::cast)
        .collect(toCollection(LinkedHashSet::new));
  }

  /**
   * Gets aggregate entities by aggregate root.
   *
   * @param aggregateRoot the aggregate root
   * @return the aggregate entities by aggregate root
   */
  public Set<AggregateEntity> getAggregateEntitiesByAggregateRoot(AggregateRoot aggregateRoot) {
    return aggregateRoot
        .getProperties()
        .stream()
        .filter(
            property -> property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_COLLECTION))
        .map(AggregateEntityCollection.class::cast)
        .map(AggregateEntityCollection::getAggregateEntity)
        .collect(toCollection(LinkedHashSet::new));
  }
}
