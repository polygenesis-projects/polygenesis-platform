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

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.datatype.PackageName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate root deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final AggregateRootPropertyDeducer aggregateRootPropertyDeducer;
  private final AggregateConstructorDeducer aggregateConstructorDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root deducer.
   *
   * @param aggregateRootPropertyDeducer the aggregate root property deducer
   * @param aggregateConstructorDeducer the aggregate constructor deducer
   */
  public AggregateRootDeducer(
      AggregateRootPropertyDeducer aggregateRootPropertyDeducer,
      AggregateConstructorDeducer aggregateConstructorDeducer) {
    this.aggregateRootPropertyDeducer = aggregateRootPropertyDeducer;
    this.aggregateConstructorDeducer = aggregateConstructorDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thingRepository the thing repository
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<AggregateRoot> deduceFrom(
      ThingRepository thingRepository, PackageName rootPackageName) {
    Set<AggregateRoot> aggregateRoots = new LinkedHashSet<>();

    thingRepository
        .getThings()
        .forEach(
            thing -> {
              PackageName packageName = makeAggregateRootPackageName(rootPackageName, thing);
              Name aggregateRootName = makeAggregateRootName(thing);
              Set<AbstractProperty> properties =
                  aggregateRootPropertyDeducer.deduceFrom(thing, rootPackageName);
              Set<Constructor> constructors =
                  aggregateConstructorDeducer.deduceFrom(thing, rootPackageName);
              Persistence persistence =
                  new Persistence(
                      packageName,
                      makePersistenceName(thing),
                      aggregateRootName,
                      makeAggregateRootIdName(thing));

              aggregateRoots.add(
                  new AggregateRoot(
                      packageName, aggregateRootName, properties, persistence, constructors));
            });

    return aggregateRoots;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  private PackageName makeAggregateRootPackageName(PackageName rootPackageName, Thing thing) {
    return new PackageName(
        rootPackageName.getText() + "." + thing.getName().getText().toLowerCase());
  }

  private Name makeAggregateRootName(Thing thing) {
    return new Name(thing.getName().getText());
  }

  private Name makeAggregateRootIdName(Thing thing) {
    return new Name(thing.getName().getText() + "Id");
  }

  private Name makePersistenceName(Thing thing) {
    return new Name(thing.getName().getText() + "Persistence");
  }
}
