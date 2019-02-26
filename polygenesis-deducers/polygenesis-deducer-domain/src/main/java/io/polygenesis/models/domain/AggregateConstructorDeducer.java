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

import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.core.Goal;
import io.polygenesis.core.Thing;
import io.polygenesis.core.datatype.PackageName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate constructor deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateConstructorDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final AggregateRootPropertyDeducer aggregateRootPropertyDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate constructor deducer.
   *
   * @param aggregateRootPropertyDeducer the aggregate root property deducer
   */
  public AggregateConstructorDeducer(AggregateRootPropertyDeducer aggregateRootPropertyDeducer) {
    this.aggregateRootPropertyDeducer = aggregateRootPropertyDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thing the thing
   * @return the set
   */
  public Set<Constructor> deduceFrom(Thing thing, PackageName rootPackageName) {
    Set<Constructor> constructors = new LinkedHashSet<>();

    thing
        .getFunctions()
        .stream()
        .filter(function -> function.getGoal().equals(new Goal(GoalType.CREATE)))
        .forEach(
            function -> {
              constructors.add(
                  new Constructor(
                      aggregateRootPropertyDeducer.deduceFrom(function, rootPackageName)));
            });

    return constructors;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

}
