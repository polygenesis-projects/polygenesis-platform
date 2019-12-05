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

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.AbstractMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Projection metamodel repository.
 *
 * @author Christos Tsakostas
 */
public class ProjectionMetamodelRepository extends AbstractMetamodelRepository<Projection>
    implements DomainMetamodelRepository<Projection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection metamodel repository.
   *
   * @param items the items
   */
  public ProjectionMetamodelRepository(Set<Projection> items) {
    super(items);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  @Override
  public BaseDomainEntity findEntityByThingName(ThingName thingName) {
    Set<BaseDomainEntity> entities = new LinkedHashSet<>();

    entities.addAll(
        getItems().stream().map(BaseDomainEntity.class::cast).collect(Collectors.toSet()));

    return entities
        .stream()
        .filter(entity -> entity.getObjectName().equals(new ObjectName(thingName.getText())))
        .findFirst()
        .orElseThrow();
  }

  public Set<Projection> findByThings(Set<Thing> things) {
    Set<Projection> projections = new LinkedHashSet<>();

    things.forEach(
        thing -> {
          Optional<Projection> optionalProjection =
              getItemByObjectName(new ObjectName(thing.getThingName().getText()));
          if (optionalProjection.isPresent()) {
            projections.add(optionalProjection.get());
          }
        });

    return projections;
  }
}
