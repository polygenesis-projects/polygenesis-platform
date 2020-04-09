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

/**
 * The type Domain model repository.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectMetamodelRepository extends AbstractMetamodelRepository<DomainObject>
    implements MetamodelRepository<DomainObject>, DomainMetamodelRepository<DomainObject> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain model repository.
   *
   * @param items the items
   */
  public DomainObjectMetamodelRepository(Set<DomainObject> items) {
    super(items);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  @Override
  public Set<DomainObject> findByThings(Set<Thing> things) {
    Set<DomainObject> domainObjects = new LinkedHashSet<>();

    things.forEach(
        thing -> {
          Optional<DomainObject> optionalDomainObject =
              getItemByObjectName(new ObjectName(thing.getThingName().getText()));
          if (optionalDomainObject.isPresent()) {
            domainObjects.add(optionalDomainObject.get());
          }
        });

    return domainObjects;
  }

  /**
   * Find entity by thing name base domain entity.
   *
   * @param thingName the thing name
   * @return the base domain entity
   */
  @Override
  public DomainObject findEntityByThingName(ThingName thingName) {
    return getItems()
        .stream()
        .filter(entity -> entity.getObjectName().equals(new ObjectName(thingName.getText())))
        .findFirst()
        .orElseThrow();
  }

  /**
   * Gets all value objects by aggregate root.
   *
   * @param domainObject the aggregate root
   * @return the all value objects by aggregate root
   */
  public Set<ValueObject> getAllValueObjectsByDomainObject(DomainObject domainObject) {
    Set<ValueObject> valueObjects = getValueObjectsByDomainObject(domainObject);

    // TODO: deep nested

    valueObjects.addAll(
        getItems()
            .stream()
            .filter(
                domainObjectInCheck ->
                    domainObjectInCheck.getParent() != null
                        && domainObjectInCheck.getParent().equals(domainObject))
            .flatMap(domainObjectInCheck -> domainObjectInCheck.getProperties().stream())
            .filter(property -> property.getPropertyType().equals(PropertyType.VALUE_OBJECT))
            .map(ValueObject.class::cast)
            .collect(toCollection(LinkedHashSet::new)));

    return valueObjects;
  }

  /**
   * Gets value objects by aggregate root.
   *
   * @param domainObject the aggregate root
   * @return the value objects by aggregate root
   */
  public Set<ValueObject> getValueObjectsByDomainObject(DomainObject domainObject) {
    return domainObject
        .getProperties()
        .stream()
        .filter(property -> property.getPropertyType().equals(PropertyType.VALUE_OBJECT))
        .map(ValueObject.class::cast)
        .collect(toCollection(LinkedHashSet::new));
  }
}
