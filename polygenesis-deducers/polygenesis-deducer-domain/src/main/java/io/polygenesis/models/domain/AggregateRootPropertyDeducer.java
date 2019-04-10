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

import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Thing;
import io.polygenesis.core.data.Data;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate root property deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootPropertyDeducer extends AbstractDomainObjectDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateEntityDeducer aggregateEntityDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public AggregateRootPropertyDeducer(AggregateEntityDeducer aggregateEntityDeducer) {
    this.aggregateEntityDeducer = aggregateEntityDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from Thing.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<DomainObjectProperty<? extends Data>> deduceFrom(
      Thing thing, PackageName rootPackageName) {
    Set<DomainObjectProperty<? extends Data>> properties = new LinkedHashSet<>();

    if (thing.getThingProperties().isEmpty()) {
      thing
          .getFunctions()
          .forEach(
              function ->
                  properties.addAll(deduceFromFunctionArguments(function, rootPackageName)));
    } else {
      properties.addAll(deduceFromThingProperties(thing));
    }

    properties.addAll(
        aggregateEntityDeducer.deduceAggregateEntityCollections(thing, rootPackageName));

    return properties;
  }
}
