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
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Aggregate entity property deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityPropertyDeducer extends BasePropertyDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thingChild the thingChild
   * @return the set
   */
  @SuppressWarnings("rawtypes")
  public Set<DomainObjectProperty> deduceFrom(
      Thing thingParent, Thing thingChild, PackageName rootPackageName) {
    Set<DomainObjectProperty> properties = new LinkedHashSet<>();

    ensureThingProperties(thingChild);

    properties.add(makeReferenceToAggregateRoot(thingParent, rootPackageName));

    properties.addAll(
        thingChild
            .getThingProperties()
            .stream()
            .map(this::thingPropertyToBaseProperty)
            .collect(Collectors.toCollection(LinkedHashSet::new)));

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make reference to aggregate root reference to aggregate root.
   *
   * @param thingParent the thingParent
   * @return the reference to aggregate root
   */
  private ReferenceToAggregateRoot makeReferenceToAggregateRoot(
      Thing thingParent, PackageName rootPackageName) {
    return new ReferenceToAggregateRoot(
        new DataGroup(
            new ObjectName(thingParent.getThingName().getText()),
            thingParent.makePackageName(rootPackageName, thingParent),
            new VariableName(thingParent.getThingName().getText())));
  }
}
