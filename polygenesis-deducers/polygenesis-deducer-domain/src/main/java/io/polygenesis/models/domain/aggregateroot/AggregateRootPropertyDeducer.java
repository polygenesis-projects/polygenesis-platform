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

import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.AggregateRootId;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.TenantId;
import io.polygenesis.models.domain.shared.AbstractPropertyDeducer;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Aggregate root property deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootPropertyDeducer extends AbstractPropertyDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateEntityDeducer aggregateEntityDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root property deducer.
   *
   * @param aggregateEntityDeducer the aggregate entity deducer
   */
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
  @SuppressWarnings("rawtypes")
  public Set<DomainObjectProperty> deduceFromThing(Thing thing, PackageName rootPackageName) {
    Set<DomainObjectProperty> properties = new LinkedHashSet<>();

    assertThatThingHasIdentity(thing);

    properties.addAll(makeIdentityDomainObjectProperties(thing, rootPackageName));

    properties.addAll(
        getThingPropertiesSuitableForDomainModel(thing)
            .stream()
            .map(this::toDomainObjectProperty)
            .collect(Collectors.toCollection(LinkedHashSet::new)));

    properties.addAll(
        aggregateEntityDeducer.deduceAggregateEntityCollections(thing, rootPackageName));

    return properties;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  protected Set<DomainObjectProperty> makeIdentityDomainObjectProperties(
      Thing thing, PackageName rootPackageName) {
    Set<DomainObjectProperty> properties = new LinkedHashSet<>();

    properties.add(makeAggregateRootId(thing, rootPackageName));

    if (thing.getMultiTenant()) {
      properties.add(makeTenantId());
    }

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make aggregate root id.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the aggregate root id
   */
  protected AggregateRootId makeAggregateRootId(Thing thing, PackageName rootPackageName) {
    DataGroup dataGroup =
        new DataGroup(
            new ObjectName(thing.getThingName().getText() + "Id"),
            thing.makePackageName(rootPackageName, thing));

    return new AggregateRootId(dataGroup);
  }

  /**
   * Make tenant id .
   *
   * @return the tenant id
   */
  protected TenantId makeTenantId() {
    DataGroup dataGroup =
        new DataGroup(new ObjectName("TenantId"), new PackageName("com.oregor.trinity4j.domain"));

    return new TenantId(dataGroup);
  }
}